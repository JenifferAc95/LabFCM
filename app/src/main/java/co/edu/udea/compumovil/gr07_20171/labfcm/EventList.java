package co.edu.udea.compumovil.gr07_20171.labfcm;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import co.edu.udea.compumovil.gr07_20171.labfcm.data.Event;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventList extends Fragment implements GoogleApiClient.OnConnectionFailedListener  {

    private static final String TAGLOG = "firebase-db";

    private RecyclerView recyclerEvents;
    FirebaseRecyclerAdapter mAdapter;

    ArrayList<Event> eventList;

    // captura de infor Usuario logueado con google
    private String userEmail;
    private GoogleApiClient googleApiClient;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;




    public EventList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        DatabaseReference dbEvent =
                FirebaseDatabase.getInstance().getReference()
                        .child("dblab4/eventos");


        final View view = inflater.inflate(R.layout.fragment_event_list, container,false);
        recyclerEvents = (RecyclerView) view.findViewById(R.id.lstEventos);
        recyclerEvents.setHasFixedSize(true);
        recyclerEvents.setLayoutManager(new LinearLayoutManager(view.getContext()));


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
                    setUserData(user);
                } else {
                    // no esta logueado con google
                }
            }
        };
        //

        //Value event listener for realtime data update
        dbEvent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot eventSnapshot : snapshot.getChildren()) {
                    //Getting the data from snapshot
                    Event event = eventSnapshot.getValue(Event.class);
                    eventList.add(event);
                    Log.d("Lista de Eventos","------------------> prueba consumo eventos : "+ event.toString());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        });



        recyclerEvents.setAdapter(mAdapter);


        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
    }

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

}

