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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.URL;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by gokul on 18-09-2017.
 */

public class wuastaFragment extends Fragment implements View.OnClickListener {

    String apikey="ADD_YOUR_KEY_HERE";
    String weatherapikey="ADD_YOUR_WEATHER_KEY_HERE";
    final int timezoneHourOffset = -5;
    final int timezoneMinuteOffset = -30;
    Integer duration;
    public View v;
    Toast toast;
    int dayfactor;
    long departureepoch;
    int prevtime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.wuastalayout, container, false);

        CardView directions = (CardView) v.findViewById(R.id.navigationCard);
        directions.setOnClickListener(this);

        Button setalarmbutton = (Button) v.findViewById(R.id.setalarmbutton);
        setalarmbutton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("wuastafile", MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPref.edit();

        TextView rain = (TextView) v.findViewById(R.id.rainpercentage);
        rain.setText(sharedPref.getString("ppop","90%"));

        TextView temperature = (TextView) v.findViewById(R.id.weatherdegrees);
        temperature.setText(sharedPref.getString("ptemperature","26°C"));

        TextView condition = (TextView) v.findViewById(R.id.weathercondition);
        condition.setText(sharedPref.getString("pcondition","Scattered clouds"));


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

        dayfactor=0;

        switch (day) {
            case Calendar.SUNDAY:

                if (repeatsun && timecondition){ today.setText("Today"); dayfactor=0;}
                else if (repeatmon){ today.setText("Tomorrow"); dayfactor=1;}
                else if (repeattue){ today.setText("Tuesday"); dayfactor=2;}
                else if (repeatwed){ today.setText("Wednesday"); dayfactor=3;}
                else if (repeatthu){ today.setText("Thursday"); dayfactor=4;}
                else if (repeatfri){ today.setText("Friday"); dayfactor=5;}
                else if (repeatsat){ today.setText("Saturday"); dayfactor=6;}
                else{ today.setText("Sunday"); dayfactor=7;}
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
                else{ today.setText("Thursday"); dayfactor=7;}
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


        long currentepoch = System.currentTimeMillis()/1000;
        long currentepochdate = (currentepoch+5*60*60+30*60) - (currentepoch+5*60*60+30*60)%86400;

        departureepoch = currentepochdate + (sharedPref.getInt("sethour",9) + timezoneHourOffset)*60*60
                + (sharedPref.getInt("setminute",0)+timezoneMinuteOffset)*60
                + dayfactor*86400;

        if(sharedPref.getString("duration",null) == null)
            duration = null;
        else
            duration = Integer.valueOf(sharedPref.getString("duration","0"));

        edit.putInt("dayfactor",dayfactor);


        if(sharedPref.getBoolean("recheck",true) || duration == null ||
                sharedPref.getInt("lastdaychecked",day) != calendar.get(Calendar.DAY_OF_WEEK)){

            edit.putBoolean("recheck",false);

            edit.putInt("lastdaychecked",day);

            edit.commit();

            setWeatherCard();

            prevtime = sharedPref.getInt("sethour",9)*60 + sharedPref.getInt("setminute",0) - 30;

            toast = Toast.makeText(getActivity(), "Updating Wake Time...", Toast.LENGTH_LONG);
            toast.show();

            setDuration(Integer.valueOf(-1));

        }
        else
        edit.commit();

        setTextView();
    }

    @Override
    public void onClick(View view) {

        SharedPreferences sp = this.getActivity().getSharedPreferences("wuastafile",MODE_PRIVATE);

        switch (view.getId()) {

            case R.id.navigationCard:
                String url = "http://maps.google.com/maps?saddr=" + sp.getString("newhomelat", "12.8614515") + ","
                        + sp.getString("newhomelong", "77.6647081") + "&daddr=" + sp.getString("newworklat", "12.975686000000001")
                        + "," + sp.getString("newworklong", "77.605852");

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(url));
                startActivity(intent);
                break;

            case R.id.setalarmbutton:

                int df = sp.getInt("dayfactor",0);
                int hur = sp.getInt("phour",0);
                int mun = sp.getInt("pminute",0);

                ((MainActivity) getActivity()).createAlarm(df, hur, mun);

                if(toast != null) toast.cancel();
                toast = Toast.makeText(getActivity(), "Alarm Set", Toast.LENGTH_LONG);
                toast.show();
                break;

        }

    }

    public void setDuration(Integer sd){

        SharedPreferences sharedPref1 = this.getActivity().getSharedPreferences("wuastafile", MODE_PRIVATE);
        SharedPreferences.Editor edit1 = sharedPref1.edit();

        int fd = sd.intValue();

        int hr = sharedPref1.getInt("sethour",9);
        int mn = sharedPref1.getInt("setminute",0);

        if(sd==null){

            if(toast != null) toast.cancel();
            toast = Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT);
            toast.show();

            duration = null;
            edit1.putString("duration",null);
            edit1.commit();
        }

        else if((prevtime + fd) < (hr*60 + mn - 3) || (prevtime + fd) > hr*60 + mn){

            prevtime = hr*60 + mn - fd;

            URI uri;
            URL link;
            try {
                uri = new URI("https://maps.googleapis.com/maps/api/directions/json?" +
                        "origin="+ sharedPref1.getString("newhomelat","12.8614515") +","+ sharedPref1.getString("newhomelong","77.6647081") +
                        "&destination="+ sharedPref1.getString("newworklat","12.975686000000001") +","+ sharedPref1.getString("newworklong","77.605852") +
                        "&departure_time="+ (departureepoch - (fd==-1?30:fd)*60) +
                        "&mode=driving" +
                        "&key="+apikey);

                link = uri.toURL();
            } catch (Exception e) {
                link = null;
            }

            new AsyncTask<URL, Void, Integer>() {

                @Override
                protected void onPostExecute(Integer integer) {

                    setDuration(integer);

                    super.onPostExecute(integer);
                }

                @Override
                protected Integer doInBackground(URL... params) {
                    try {
                        return JSONCreaterFromStringURL.getDurationFromURL(params[0]);
                    } catch (Exception e) {
                        return null;
                    }
                }
            }.execute(link);

        }
        else{

            int settimeepoch = hr*60 + mn - fd - sharedPref1.getInt("delay",0);

            hr = settimeepoch >= 60 ? settimeepoch/60 : 0;
            mn = settimeepoch%60;

            edit1.putInt("phour",hr);
            edit1.putInt("pminute",mn);

            duration = sd;

            edit1.putString("duration",sd.toString());
            edit1.commit();

            if(toast != null) toast.cancel();
            toast = Toast.makeText(getActivity(), "Done!", Toast.LENGTH_SHORT);
            toast.show();

            setTextView();

        }
    }

    void setTextView(){

        SharedPreferences sharedPref1 = this.getActivity().getSharedPreferences("wuastafile", MODE_PRIVATE);

        int hr = sharedPref1.getInt("phour",0);
        int mn = sharedPref1.getInt("pminute",0);

        TextView suggtime = (TextView)v.findViewById(R.id.predictedTimeText);

        if (hr > 12) {
            suggtime.setText((hr - 12) + ":" + (mn > 9 ? "" : "0") + mn + " PM");
        } else if (hr == 12) {
            suggtime.setText(hr + ":" + (mn > 9 ? "" : "0") + mn + " PM");
        } else {
            suggtime.setText((hr == 0 ? 12 : hr) + ":" + (mn > 9 ? "" : "0") + mn + " AM");
        }

    }

    void setWeatherCard(){

        SharedPreferences sharedPref1 = this.getActivity().getSharedPreferences("wuastafile", MODE_PRIVATE);

        String weatherloc = sharedPref1.getString("weatherloc","Home");

        String lathome,latwork,lonhome,lonwork;
        String lat,lon;

        lathome = sharedPref1.getString("newhomelat","12.8614515");
        lonhome = sharedPref1.getString("newhomelong","77.6647081");
        latwork = sharedPref1.getString("newworklat","12.975686000000001");
        lonwork = sharedPref1.getString("newworklong","77.605852");

        if(weatherloc.equals("Home")){
            lat=lathome;
            lon=lonhome;
        }
        else if(weatherloc.equals("Work")){
            lat=latwork;
            lon=lonwork;
        }
        else{
            lat = ""+((Double.valueOf(lathome) + Double.valueOf(latwork))/2.0);
            lon = ""+((Double.valueOf(lonhome) + Double.valueOf(lonwork))/2.0);
        }

        URI uri;
        URL link;
        try {
            uri = new URI("https://api.weatherbit.io/v2.0/forecast/daily?" +
                    "lat=" + lat +
                    "&lon=" + lon +
                    "&key="+weatherapikey);

            link = uri.toURL();
        } catch (Exception e) {
            link = null;
        }

        new AsyncTask<URL, Void, String>() {

            @Override
            protected void onPostExecute(String wjson) {

                setWeatherJSON(wjson);

                super.onPostExecute(wjson);
            }

            @Override
            protected String doInBackground(URL... params) {
                try {
                    return JSONCreaterFromStringURL.getWeatherJSON(params[0]);
                } catch (Exception e) {
                    return null;
                }
            }
        }.execute(link);

    }

    void setWeatherJSON(String wjson){
        if(wjson == null) return;

        JSONArray wjarray;

        try {
            SharedPreferences sharedPref3 = this.getActivity().getSharedPreferences("wuastafile", MODE_PRIVATE);
            SharedPreferences.Editor edit3 = sharedPref3.edit();

            wjarray = new JSONObject(wjson).getJSONArray("data");

            TextView rain = (TextView) v.findViewById(R.id.rainpercentage);
            rain.setText(wjarray.getJSONObject(dayfactor).getInt("pop") + "%");

            TextView temperature = (TextView) v.findViewById(R.id.weatherdegrees);
            temperature.setText(wjarray.getJSONObject(dayfactor).getInt("temp") + "°C");

            TextView condition = (TextView) v.findViewById(R.id.weathercondition);
            condition.setText(wjarray.getJSONObject(dayfactor).getJSONObject("weather").getString("description"));

            edit3.putString("ppop",wjarray.getJSONObject(dayfactor).getInt("pop") + "%");
            edit3.putString("ptemperature",wjarray.getJSONObject(dayfactor).getInt("temp") + "°C");
            edit3.putString("pcondition",wjarray.getJSONObject(dayfactor).getJSONObject("weather").getString("description"));

            edit3.commit();
        }
        catch (Exception e){
            return;
        }
    }

}
