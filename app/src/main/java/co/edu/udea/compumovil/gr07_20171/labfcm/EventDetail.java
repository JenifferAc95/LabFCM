package co.edu.udea.compumovil.gr07_20171.labfcm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import co.edu.udea.compumovil.gr07_20171.labfcm.data.Event;

public class EventDetail extends AppCompatActivity {

    Event event;
    TextView tvEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);


    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent startingIntent = getIntent();
        if (startingIntent != null) {
            //recibimos el evento enviado
             event = (Event) getIntent().getSerializableExtra("event");
            tvEvento =(TextView) findViewById(R.id.TvEvento);
            tvEvento.setText(event.toString());

        }


}


}
