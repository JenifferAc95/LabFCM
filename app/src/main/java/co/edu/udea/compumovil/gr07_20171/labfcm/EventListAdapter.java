package co.edu.udea.compumovil.gr07_20171.labfcm;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import co.edu.udea.compumovil.gr07_20171.labfcm.data.Event;

/**
 * Created by Santiago on 01/05/2017.
 */

public class EventListAdapter extends ArrayAdapter<Event> {

    ArrayList<Event> mEvents;

    public EventListAdapter(Context context, ArrayList<Event> mEvents) {
        super(context, R.layout.item_evento_layout);
        Log.d("REGISTRO -->","CLASE: EventListAdapter, METODO: constructor");
        this.mEvents = mEvents;
    }


    public int getCount() {
        return mEvents.size();
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, final ViewGroup parent) {

        Log.d("REGISTRO -->","CLASE: OfertaServicioAdapter, METODO: getView");
        View view = null;
        Event event  = mEvents.get(position);
        LayoutInflater inflador = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.d("REGISTRO -->","CLASE: OfertaServicioAdapter, METODO: getView error al construir el inflador");
        view = inflador.inflate(R.layout.item_evento_layout, null);
        final EventHolder eventHolder = new EventHolder(view);

        eventHolder.setName(event.getName());
        eventHolder.setDate(event.getDate());
        eventHolder.setDes(event.getFirstDescription());
        eventHolder.setMail(event.getUser());


        //escuchador de evento
        view.setOnClickListener(getListener(position));
        return view;
    }

    private View.OnClickListener getListener(final int position){
        Log.d("REGISTRO -->","CLASE: EventListAdapter, METODO: getListener");
        View.OnClickListener listener = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Event event  = mEvents.get(position);

                        Intent intent = new Intent(getContext(), EventDetail.class);
                        intent.putExtra("event",(Serializable)event);
                        v.getContext().startActivity(intent);
                        v.setOnClickListener(getListener(position));
                        Log.d("REGISTRO -->","ITEM "+position+" CLIQUEADO");

                }
            };

        return listener;
    }
}
