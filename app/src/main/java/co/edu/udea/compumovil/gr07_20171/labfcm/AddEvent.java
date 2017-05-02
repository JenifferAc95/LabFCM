package co.edu.udea.compumovil.gr07_20171.labfcm;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.Calendar;

import co.edu.udea.compumovil.gr07_20171.labfcm.data.Event;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddEvent.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddEvent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEvent extends Fragment implements GoogleApiClient.OnConnectionFailedListener{

    Bitmap pict;
    private static final int REQUEST_CODE_GALLERY=1;
    private static final int REQUEST_CODE_CAMERA=2;
    private ImageView targetImageR;

    Calendar calendar = Calendar.getInstance();
    static final int PICK_REQUEST =1337;
    Uri contact = null;
    Button btnR;
    EditText[] txtValidateR = new EditText[4];
    View view;

    // captura de infor Usuario logueado con google
    private String userEmail;
    private GoogleApiClient googleApiClient;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    public AddEvent() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_add_event,container,false);

        pict = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.calendar);
        targetImageR = (ImageView)view.findViewById(R.id.eventImage);
        targetImageR.setImageBitmap(pict);
        txtValidateR[0]=(EditText)view.findViewById(R.id.editTextEventName);
        txtValidateR[1]=(EditText)view.findViewById(R.id.editTextEventDescription);
        txtValidateR[2]=(EditText)view.findViewById(R.id.editTextOtherInformation);
        txtValidateR[3]=(EditText)view.findViewById(R.id.editTextEventPlace);
        btnR = (Button)view.findViewById(R.id.buttonEvent);
        btnR.setEnabled(false);


        TextWatcher btnActivation = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(verificarVaciosSinMessageR(txtValidateR)){btnR.setEnabled(true);}
                else{btnR.setEnabled(false);}
            }
        };

        for (int n = 0; n < txtValidateR.length; n++)
        {
            txtValidateR[n].addTextChangedListener(btnActivation);
        }

        // prueba de obtener Email
        //
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(view.getContext())
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //esta logueado con google
                    Log.d("REGISTRO -->","CLASE: AddEvent, esta logueado con google con "+user.toString());
                    setUserData(user);
                } else {
                    // no esta logueado con google
                }
            }
        };
        ///

        return view;
    }

    public void DateClic()
    {
        setDate();
    }
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        }
    };

    public void setDate()
    {
        new DatePickerDialog(getActivity(),d,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == PICK_REQUEST){
            if(resultCode == getActivity().RESULT_OK){
                contact = data.getData();
            }
        }
        if (resultCode == getActivity().RESULT_OK && (requestCode==REQUEST_CODE_GALLERY || requestCode==REQUEST_CODE_CAMERA)){
            try {
                Uri targetUri = data.getData();
                pict = redimensionarImagenMaximo(BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(targetUri)),400,350);
                targetImageR.setImageBitmap(pict);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean verificarVaciosSinMessageR(EditText[] txtValidate)
    {
        for(int i=0; i<txtValidate.length;i++)
        {
            if((txtValidate[i].getText().toString()).isEmpty())
            {
                return false;
            }
        }
        return true;
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public void onClickEvent() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference dbReference = firebaseDatabase.getReference("dblab4");

        String name = txtValidateR[0].getText().toString();
        String firstDescription = txtValidateR[1].getText().toString();
        String information = txtValidateR[2].getText().toString();
        String place = txtValidateR[3].getText().toString();
        String date = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + " / " + Integer.toString(calendar.get(Calendar.MONTH)) + " / " + Integer.toString(calendar.get(Calendar.YEAR));
        String user = "userTemp";
        String fotoRef = "getBitmapAsByteArray(pict)";

        Log.d("REGISTRO -->","CLASE: AddEvent,Metodo onClickEvent el usuario que crea el evento es  "+userEmail);

        Event event = new Event(name, firstDescription, information, place, date, user, fotoRef);

        this.getActivity().getIntent().getExtras();
        dbReference.child("eventos/").push().setValue(event);
        dbReference.child("eventos").equalTo("name");

    }

    public void ClickGalleryR() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_GALLERY);
    }

    public void ClickCameraR() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA);
    }

    public Bitmap redimensionarImagenMaximo(Bitmap mBitmap, float newWidth, float newHeigth){
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeigth) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
    }

    // para capturar User
    private void setUserData(FirebaseUser user) {
        //nameTextView.setText(user.getDisplayName());
        userEmail = user.getEmail();
        Log.d("EventList :    ", "------------> setUserData: el email ingresado es "+userEmail);
        //idTextView.setText(user.getUid());
        //Glide.with(this).load(user.getPhotoUrl()).into(photoImageView);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    //

}
