package com.walarm.wuasta;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by gokul on 18-09-2017.
 */

public class wuastaFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.wuastalayout, container, false);

        CardView directions = (CardView) v.findViewById(R.id.navigationCard);
        directions.setOnClickListener(this);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("wuastafile",MODE_PRIVATE);


        TextView today = (TextView)view.findViewById(R.id.wuastaDay);
        TextView time = (TextView)view.findViewById(R.id.timeText);

        boolean repeatsun = sharedPref.getBoolean("sun",false);
        boolean repeatmon = sharedPref.getBoolean("mon",true);
        boolean repeattue = sharedPref.getBoolean("tue",true);
        boolean repeatwed = sharedPref.getBoolean("wed",true);
        boolean repeatthu = sharedPref.getBoolean("thu",true);
        boolean repeatfri = sharedPref.getBoolean("fri",true);
        boolean repeatsat = sharedPref.getBoolean("sat",false);

        int hour = sharedPref.getInt("sethour",9);
        int min = sharedPref.getInt("setminute",0);

        if(hour>12){
            time.setText((hour-12)+":"+(min>9?"":"0")+min+" PM");
        }
        else{
            time.setText((hour==0?12:hour)+":"+(min>9?"":"0")+min+" AM");
        }

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int currenthour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentmin = calendar.get(Calendar.MINUTE);

        boolean timecondition;
        if (currenthour < hour) timecondition = true;
        else if(currenthour == hour && currentmin <= min) timecondition = true;
        else timecondition = false;

        switch (day) {
            case Calendar.SUNDAY:

                if(repeatsun && timecondition) today.setText("Today");
                else if(repeatmon) today.setText("Tomorrow");
                else if(repeattue) today.setText("Tuesday");
                else if(repeatwed) today.setText("Wednesday");
                else if(repeatthu) today.setText("Thursday");
                else if(repeatfri) today.setText("Friday");
                else today.setText("Saturday");
                break;

            case Calendar.MONDAY:

                if(repeatmon && timecondition) today.setText("Today");
                else if(repeattue) today.setText("Tomorrow");
                else if(repeatwed) today.setText("Wednesday");
                else if(repeatthu) today.setText("Thursday");
                else if(repeatfri) today.setText("Friday");
                else if(repeatsat) today.setText("Saturday");
                else today.setText("Sunday");
                break;

            case Calendar.TUESDAY:

                if(repeattue && timecondition) today.setText("Today");
                else if(repeatwed) today.setText("Tomorrow");
                else if(repeatthu) today.setText("Thursday");
                else if(repeatfri) today.setText("Friday");
                else if(repeatsat) today.setText("Saturday");
                else if(repeatsun) today.setText("Sunday");
                else today.setText("Monday");
                break;

            case Calendar.WEDNESDAY:

                if(repeatwed && timecondition) today.setText("Today");
                else if(repeatthu) today.setText("Tomorrow");
                else if(repeatfri) today.setText("Friday");
                else if(repeatsat) today.setText("Saturday");
                else if(repeatsun) today.setText("Sunday");
                else if(repeatmon) today.setText("Monday");
                else today.setText("Tuesday");
                break;

            case Calendar.THURSDAY:

                if(repeatthu && timecondition) today.setText("Today");
                else if(repeatfri) today.setText("Tomorrow");
                else if(repeatsat) today.setText("Saturday");
                else if(repeatsun) today.setText("Sunday");
                else if(repeatmon) today.setText("Monday");
                else if(repeattue) today.setText("Tuesday");
                else today.setText("Wednesday");
                break;

            case Calendar.FRIDAY:

                if(repeatfri && timecondition) today.setText("Today");
                else if(repeatsat) today.setText("Tomorrow");
                else if(repeatsun) today.setText("Sunday");
                else if(repeatmon) today.setText("Monday");
                else if(repeattue) today.setText("Tuesday");
                else if(repeatwed) today.setText("Wednesday");
                else today.setText("Thursday");
                break;

            case Calendar.SATURDAY:

                if(repeatsat && timecondition) today.setText("Today");
                else if(repeatsun) today.setText("Tomorrow");
                else if(repeatmon) today.setText("Monday");
                else if(repeattue) today.setText("Tuesday");
                else if(repeatwed) today.setText("Wednesday");
                else if(repeatthu) today.setText("Thursday");
                else today.setText("Friday");
                break;
        }

        TextView desttext = (TextView)view.findViewById(R.id.destinationText);
        desttext.setText(sharedPref.getString("workname","Work"));

        TextView desttextweather = (TextView)view.findViewById(R.id.destinationTextWeather);
        desttextweather.setText(sharedPref.getString("weatherloc","Work").equals("Work")?
                                    sharedPref.getString("workname","Work"):
                                sharedPref.getString("weatherloc","Work"));

        if(sharedPref.getString("weatherloc","Work").equals("Enroute")){
            CardView weathercard = (CardView)view.findViewById(R.id.weatherCard);
            TextView weatherAT = (TextView)weathercard.findViewById(R.id.textView3);
            weatherAT.setText("ON");
        }
    }

    @Override
    public void onClick(View view) {

        SharedPreferences sp = this.getActivity().getSharedPreferences("wuastafile",MODE_PRIVATE);

        String url = "http://maps.google.com/maps?saddr="+sp.getString("newhomelat","12.8614515")+","
                +sp.getString("newhomelong","77.6647081")+"&daddr="+sp.getString("newworklat","12.975686000000001")
                +","+sp.getString("newworklong","77.605852");

        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(url));
        startActivity(intent);

    }
}
