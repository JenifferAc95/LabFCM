package co.edu.udea.compumovil.gr07_20171.labfcm;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import co.edu.udea.compumovil.gr07_20171.labfcm.data.Event;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventList extends Fragment {

    private static final String TAGLOG = "firebase-db";

    private RecyclerView lstPredicciones;

    FirebaseRecyclerAdapter mAdapter;


    public EventList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        DatabaseReference dbEvent =
                FirebaseDatabase.getInstance().getReference()
                        .child("dblab4").child("eventos");

        final View view = inflater.inflate(R.layout.fragment_event_list, container,false);

        RecyclerView recycler = (RecyclerView) view.findViewById(R.id.lstEventos);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));

        mAdapter = new FirebaseRecyclerAdapter<Event, EventHolder>(Event.class, R.layout.fragment_add_event, EventHolder.class, dbEvent) {
            @Override
            protected void populateViewHolder(EventHolder eventHolder, Event e, int position) {
                eventHolder.setName("Name: " + e.getName());
                eventHolder.setDes("Descripción: " + e.getFirstDescription());
                eventHolder.setDate("Date: " + e.getDate());
                eventHolder.setMail("Email: " + "jeniffer.acostao@gmail.com");
            }
        };

        recycler.setAdapter(mAdapter);


        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
    }

}

