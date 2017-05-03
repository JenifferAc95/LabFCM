package co.edu.udea.compumovil.gr07_20171.labfcm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import co.edu.udea.compumovil.gr07_20171.labfcm.data.Event;

public class EventDetail extends AppCompatActivity {

    Event event;
    TextView tvName;
    TextView tvDescr;
    TextView tvDate;
    TextView tvEmail;
    TextView tvPlace;
    TextView tvOtherInfo;

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
            tvName =(TextView) findViewById(R.id.event_name_tv);
            tvName.setText(event.getName().toString());
            tvDescr =(TextView) findViewById(R.id.event_description_tv);
            tvDescr.setText(event.getFirstDescription().toString());
            tvDate =(TextView) findViewById(R.id.date_tv);
            tvDate.setText(event.getDate().toString());
            tvEmail =(TextView) findViewById(R.id.email_tv);
            tvEmail.setText(event.getName().toString());
            tvPlace =(TextView) findViewById(R.id.place_tv);
            tvPlace.setText(event.getPlace().toString());
            tvOtherInfo = (TextView) findViewById(R.id.otherinfo_tv);
            tvOtherInfo.setText(event.getInformation().toString());


        }
    }
}
