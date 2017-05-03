package co.edu.udea.compumovil.gr07_20171.labfcm;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import co.edu.udea.compumovil.gr07_20171.labfcm.data.Event;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventList extends Fragment implements GoogleApiClient.OnConnectionFailedListener  {

    private static final String TAGLOG = "firebase-db";

    private RecyclerView recyclerEvents;
    FirebaseRecyclerAdapter mAdapter;

    ListView lvEvents;
    List<Event> list = new ArrayList<Event>();
    ArrayList<Event> eventList = new ArrayList<Event>(list);

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
                        .child("dblab4").child("eventos");


        final View view = inflater.inflate(R.layout.fragment_event_list, container,false);
        //lvEvents = (ListView) view.findViewById(R.id.LvEvents);



        recyclerEvents = (RecyclerView) view.findViewById(R.id.lstEventos);
        recyclerEvents.setHasFixedSize(true);
        recyclerEvents.setLayoutManager(new LinearLayoutManager(view.getContext()));

        mAdapter = new FirebaseRecyclerAdapter<Event, EventHolder>(Event.class, R.layout.item_evento_layout, EventHolder.class, dbEvent) {
            @Override
            protected void populateViewHolder(EventHolder eventHolder, final Event e, final int position) {
                eventHolder.setName("Name: " + e.getName());
                eventHolder.setDes("Descripción: " + e.getFirstDescription());
                eventHolder.setDate("Date: " + e.getDate());
                eventHolder.setMail("Email: " + "jeniffer.acostao@gmail.com");

                eventHolder.getmView().setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Log.d("REGISTRO -->","Se ha dado clic en el evento"+e.toString()+ "esta en la pos:" + position);

                        Intent intent = new Intent(getContext(), EventDetail.class);
                        intent.putExtra("event",(Serializable)e);
                        v.getContext().startActivity(intent);
                        Log.d("REGISTRO -->","ITEM "+position+" CLIQUEADO");

                    }
                });
            }
        };


        // prueba de obtener Email
        /*
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
        */

        //
        /*
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
        }); */


         //lvEvents.setAdapter(new EventListAdapter( view.getContext() , eventList));
        recyclerEvents.setAdapter(mAdapter);


        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //mAdapter.cleanup();
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

