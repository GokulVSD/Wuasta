package com.walarm.wuasta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.net.URL;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by gokul on 18-09-2017.
 */

public class wuastaFragment extends Fragment implements View.OnClickListener {

    final int timezoneHourOffset = -5;
    final int timezoneMinuteOffset = -30;
    Integer duration;

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

        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("wuastafile", MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPref.edit();


        TextView today = (TextView) view.findViewById(R.id.wuastaDay);
        TextView time = (TextView) view.findViewById(R.id.timeText);

        boolean repeatsun = sharedPref.getBoolean("sun", false);
        boolean repeatmon = sharedPref.getBoolean("mon", true);
        boolean repeattue = sharedPref.getBoolean("tue", true);
        boolean repeatwed = sharedPref.getBoolean("wed", true);
        boolean repeatthu = sharedPref.getBoolean("thu", true);
        boolean repeatfri = sharedPref.getBoolean("fri", true);
        boolean repeatsat = sharedPref.getBoolean("sat", false);

        int hour = sharedPref.getInt("sethour", 9);
        int min = sharedPref.getInt("setminute", 0);

        if (hour > 12) {
            time.setText((hour - 12) + ":" + (min > 9 ? "" : "0") + min + " PM");
        } else if (hour == 12) {
            time.setText(hour + ":" + (min > 9 ? "" : "0") + min + " PM");
        } else {
            time.setText((hour == 0 ? 12 : hour) + ":" + (min > 9 ? "" : "0") + min + " AM");
        }

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int currenthour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentmin = calendar.get(Calendar.MINUTE);

        boolean timecondition;
        if (currenthour < hour) timecondition = true;
        else if (currenthour == hour && currentmin <= min) timecondition = true;
        else timecondition = false;

        int dayfactor=0;

        switch (day) {
            case Calendar.SUNDAY:

                if (repeatsun && timecondition){ today.setText("Today"); dayfactor=0;}
                else if (repeatmon){ today.setText("Tomorrow"); dayfactor=1;}
                else if (repeattue){ today.setText("Tuesday"); dayfactor=2;}
                else if (repeatwed){ today.setText("Wednesday"); dayfactor=3;}
                else if (repeatthu){ today.setText("Thursday"); dayfactor=4;}
                else if (repeatfri){ today.setText("Friday"); dayfactor=5;}
                else if (repeatsat){ today.setText("Saturday"); dayfactor=6;}
                else{ today.setText("Sunday"); dayfactor=0;}
                break;

            case Calendar.MONDAY:

                if (repeatmon && timecondition){ today.setText("Today"); dayfactor=0;}
                else if (repeattue){ today.setText("Tomorrow"); dayfactor=1;}
                else if (repeatwed){ today.setText("Wednesday"); dayfactor=2;}
                else if (repeatthu){ today.setText("Thursday"); dayfactor=3;}
                else if (repeatfri){ today.setText("Friday"); dayfactor=4;}
                else if (repeatsat){ today.setText("Saturday"); dayfactor=5;}
                else if (repeatsun){ today.setText("Sunday"); dayfactor=6;}
                else{ today.setText("Monday"); dayfactor=7;}
                break;

            case Calendar.TUESDAY:

                if (repeattue && timecondition){ today.setText("Today"); dayfactor=0;}
                else if (repeatwed){ today.setText("Tomorrow"); dayfactor=1;}
                else if (repeatthu){ today.setText("Thursday"); dayfactor=2;}
                else if (repeatfri){ today.setText("Friday"); dayfactor=3;}
                else if (repeatsat){ today.setText("Saturday"); dayfactor=4;}
                else if (repeatsun){ today.setText("Sunday"); dayfactor=5;}
                else if (repeatmon){ today.setText("Monday"); dayfactor=6;}
                else{ today.setText("Tuesday"); dayfactor=7;}
                break;

            case Calendar.WEDNESDAY:

                if (repeatwed && timecondition){ today.setText("Today"); dayfactor=0;}
                else if (repeatthu){ today.setText("Tomorrow"); dayfactor=1;}
                else if (repeatfri){ today.setText("Friday"); dayfactor=2;}
                else if (repeatsat){ today.setText("Saturday"); dayfactor=3;}
                else if (repeatsun){ today.setText("Sunday"); dayfactor=4;}
                else if (repeatmon){ today.setText("Monday"); dayfactor=5;}
                else if (repeattue){ today.setText("Tuesday"); dayfactor=6;}
                else{ today.setText("Wednesday"); dayfactor=7;}
                break;

            case Calendar.THURSDAY:

                if (repeatthu && timecondition){ today.setText("Today"); dayfactor=0;}
                else if (repeatfri){ today.setText("Tomorrow"); dayfactor=1;}
                else if (repeatsat){ today.setText("Saturday"); dayfactor=2;}
                else if (repeatsun){ today.setText("Sunday"); dayfactor=3;}
                else if (repeatmon){ today.setText("Monday"); dayfactor=4;}
                else if (repeattue){ today.setText("Tuesday"); dayfactor=5;}
                else if (repeatwed){ today.setText("Wednesday"); dayfactor=6;}
                else{ today.setText("Thursday"); dayfactor=6;}
                break;

            case Calendar.FRIDAY:

                if (repeatfri && timecondition){ today.setText("Today"); dayfactor=0;}
                else if (repeatsat){ today.setText("Tomorrow"); dayfactor=1;}
                else if (repeatsun){ today.setText("Sunday"); dayfactor=2;}
                else if (repeatmon){ today.setText("Monday"); dayfactor=3;}
                else if (repeattue){ today.setText("Tuesday"); dayfactor=4;}
                else if (repeatwed){ today.setText("Wednesday"); dayfactor=5;}
                else if (repeatthu){ today.setText("Thursday"); dayfactor=6;}
                else{ today.setText("Friday"); dayfactor=7;}
                break;

            case Calendar.SATURDAY:

                if (repeatsat && timecondition){ today.setText("Today"); dayfactor=0;}
                else if (repeatsun){ today.setText("Tomorrow"); dayfactor=1;}
                else if (repeatmon){ today.setText("Monday"); dayfactor=2;}
                else if (repeattue){ today.setText("Tuesday"); dayfactor=3;}
                else if (repeatwed){ today.setText("Wednesday"); dayfactor=4;}
                else if (repeatthu){ today.setText("Thursday"); dayfactor=5;}
                else if (repeatfri){ today.setText("Friday"); dayfactor=6;}
                else{ today.setText("Saturday"); dayfactor=7;}
                break;
        }

        TextView desttext = (TextView) view.findViewById(R.id.destinationText);
        desttext.setText(sharedPref.getString("workname", "Work"));

        TextView desttextweather = (TextView) view.findViewById(R.id.destinationTextWeather);
        desttextweather.setText(sharedPref.getString("weatherloc", "Work").equals("Work") ?
                sharedPref.getString("workname", "Work") :
                sharedPref.getString("weatherloc", "Work"));

        if (sharedPref.getString("weatherloc", "Work").equals("Enroute")) {
            CardView weathercard = (CardView) view.findViewById(R.id.weatherCard);
            TextView weatherAT = (TextView) weathercard.findViewById(R.id.textView3);
            weatherAT.setText("ON");
        }


        long currentepoch = System.currentTimeMillis()/1000L;
        long currentepochdate = currentepoch - currentepoch%86400L;

        long arrivalepoch = currentepochdate + (sharedPref.getInt("sethour",9) + timezoneHourOffset)*60*60
                + (sharedPref.getInt("setminute",0)+timezoneMinuteOffset)*60
                + dayfactor*86400;

        edit.putLong("arrivaltime",arrivalepoch);




        if(sharedPref.getString("duration",null) == null)
            duration = null;
        else
            duration = new Integer(sharedPref.getString("duration","0"));

        if(sharedPref.getBoolean("recheck",false) || duration == null){

            edit.putBoolean("recheck",false);

        URI uri;
        URL link;
        try {
            uri = new URI("https://maps.googleapis.com/maps/api/directions/json?" +
                    "origin="+ sharedPref.getString("newhomelat","12.8614515") +","+ sharedPref.getString("newhomelong","77.6647081") +
                    "&destination="+ sharedPref.getString("newworklat","12.975686000000001") +","+ sharedPref.getString("newworklong","77.605852") +
                    "&arrival_time="+ sharedPref.getLong("arrivaltime",3000000000L) +
                    "&mode=driving" +
                    "&key=");

            link = uri.toURL();
        } catch (Exception e) {
            link = null;
        }

        new AsyncTask<URL, Void, Integer>() {

            @Override
            protected void onPostExecute(Integer integer) {
                if(integer == null) Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
                else Toast.makeText(getActivity(), "Duration:"+integer.toString(), Toast.LENGTH_SHORT).show();

                setDuration(integer);

                super.onPostExecute(integer);
            }

            @Override
            protected Integer doInBackground(URL... params) {
                try {
                    return new Integer(JSONCreaterFromStringURL.getDurationFromURL(params[0]));
                } catch (Exception e) {
                    return null;
                }
            }
        }.execute(link);

    }




        edit.commit();
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

    public void setDuration(Integer sd){

        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("wuastafile", MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPref.edit();

        duration = sd;

        edit.putString("duration",sd==null?null:sd.toString());
        edit.commit();
    }
}
