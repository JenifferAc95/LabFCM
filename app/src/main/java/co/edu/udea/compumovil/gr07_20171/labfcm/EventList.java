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

    List<Event> list = new ArrayList<Event>();
    ArrayList<Event> eventList = new ArrayList<Event>(list);

    public EventList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        DatabaseReference dbEvent =
                FirebaseDatabase.getInstance().getReference()
                        .child("dblab4").child("eventos");

        final View view = inflater.inflate(R.layout.fragment_event_list, container,false);

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

                eventHolder.getmView().setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Log.d("REGISTRO -->", "EventList ------------>  Clic en el evento " +e.toString());
                        Intent intent = new Intent(getContext(), EventDetail.class);
                        intent.putExtra("event", (Serializable) e);
                        v.getContext().startActivity(intent);
                    }
                });

            }
        };

        recyclerEvents.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //mAdapter.cleanup();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}

