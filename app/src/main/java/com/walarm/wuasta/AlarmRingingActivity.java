package com.walarm.wuasta;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class AlarmRingingActivity extends AppCompatActivity {

    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Called when the Alarm begins to ring, creates the UI

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_ringing);
        SharedPreferences sp = this.getSharedPreferences("wuastafile", MODE_PRIVATE);

        //Sets the time on the UI to the current time
        ((TextView)findViewById(R.id.timewake)).setText(sp.getInt("phour",0)+":"+(sp.getInt("pminute",0)>9?"":"0")+sp.getInt("pminute",0));
    }
    int backcounter=1;
    @Override
    public void onBackPressed(){

        //You are promted to press the back button again to exit the UI
        if(backcounter==1){
            if(toast != null) toast.cancel();
            toast = Toast.makeText(this, "Press Again to Exit", Toast.LENGTH_SHORT);
            toast.show();
            backcounter++;
            return;
        }
        backcounter=1;
        super.onBackPressed();
    }
}
