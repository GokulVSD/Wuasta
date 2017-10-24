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

    //GMaps API Key
    String apikey="ADD_YOUR_KEY_HERE";
    //Weatherbit.io API Key
    String weatherapikey="ADD_YOUR_WEATHER_KEY_HERE";

    //Modify based on your timezone. Currently set to IST, which is GMT +5:30
    final int timezoneHourOffset = -5;
    final int timezoneMinuteOffset = -30;

    Integer duration;
    public View v;
    Toast toast;
    int dayfactor;
    long departureepoch;
    int prevtime;

    //Called at the beginning of fragment transacted in MainActivity for this fragment
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        //Storing a reference to the views in the fragment
        v = inflater.inflate(R.layout.wuastalayout, container, false);

        //Setting Listeners

        CardView directions = (CardView) v.findViewById(R.id.navigationCard);
        directions.setOnClickListener(this);

        Button setalarmbutton = (Button) v.findViewById(R.id.setalarmbutton);
        setalarmbutton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Called everytime this fragment is loaded up

        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("wuastafile", MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPref.edit();


        //Restoring weather info
        TextView rain = (TextView) v.findViewById(R.id.rainpercentage);
        rain.setText(sharedPref.getString("ppop","90%"));

        TextView temperature = (TextView) v.findViewById(R.id.weatherdegrees);
        temperature.setText(sharedPref.getString("ptemperature","26°C"));

        TextView condition = (TextView) v.findViewById(R.id.weathercondition);
        condition.setText(sharedPref.getString("pcondition","Scattered clouds"));


        /*Setting the day textview at the top of the fragment and the "time to be at" textview.
        The day textview is set based on the day on which it is next set to (via the repeat days from modify fragment).
         */
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

        //Time condition takes care of the condition when it is still the same day, but the "time to be at" has passed
        boolean timecondition;
        if (currenthour < hour) timecondition = true;
        else if (currenthour == hour && currentmin <= min) timecondition = true;
        else timecondition = false;

        /*We store the day for which the time is predicted here, eg: for today, dayfactor is 0, for tomorrow, it is 1,
        ... upto 7 days, therefore dayfactor has values b/w 0...6
         */
        dayfactor=0;

        //Setting day textview and figuring out dayfactor
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


        //Incase Work has been renamed from settings fragment
        TextView desttext = (TextView) view.findViewById(R.id.destinationText);
        desttext.setText(sharedPref.getString("workname", "Work"));

        //Deals with symantics of the weather card
        TextView desttextweather = (TextView) view.findViewById(R.id.destinationTextWeather);
        desttextweather.setText(sharedPref.getString("weatherloc", "Work").equals("Work") ?
                sharedPref.getString("workname", "Work") :
                sharedPref.getString("weatherloc", "Work"));

        if (sharedPref.getString("weatherloc", "Work").equals("Enroute")) {
            CardView weathercard = (CardView) view.findViewById(R.id.weatherCard);
            TextView weatherAT = (TextView) weathercard.findViewById(R.id.textView3);
            weatherAT.setText("ON");
        }


        /*
        departureepoch is the epoch time in seconds, for the "time to be at" time
        timezone plays a role since the API is based off of GMT
         */
        long currentepoch = System.currentTimeMillis()/1000;
        long currentepochdate = (currentepoch+5*60*60+30*60) - (currentepoch+5*60*60+30*60)%86400;

        departureepoch = currentepochdate + (sharedPref.getInt("sethour",9) + timezoneHourOffset)*60*60
                + (sharedPref.getInt("setminute",0)+timezoneMinuteOffset)*60
                + dayfactor*86400;


        //Previously calculated commute time (if it has been calculated, else null
        if(sharedPref.getString("duration",null) == null)
            duration = null;
        else
            duration = Integer.valueOf(sharedPref.getString("duration","0"));

        //stores dayfactor for use in other methods
        edit.putInt("dayfactor",dayfactor);



        /*
        Recalculates predicted wake time based on 3 conditions,
        1. If anything was changed in Modify fragment, or if locations were changed in Settings fragment
        2. If commute time has never been calculated or if there was a network error on the previous attempt
        3. If it is not the same day as the last calculation of predicted wake time
         */
        if(sharedPref.getBoolean("recheck",true) || duration == null ||
                sharedPref.getInt("lastdaychecked",day) != calendar.get(Calendar.DAY_OF_WEEK)){

            edit.putBoolean("recheck",false);

            edit.putInt("lastdaychecked",day);

            edit.commit();


            //Update weather information
            setWeatherCard();

            //Previously calculated departure time, initially equal to "time to be at" minus 30 mins
            prevtime = sharedPref.getInt("sethour",9)*60 + sharedPref.getInt("setminute",0) - 30;

            toast = Toast.makeText(getActivity(), "Updating Wake Time...", Toast.LENGTH_LONG);
            toast.show();

            //Update predicted time based on commute time, inital commute time being -1 mins
            setDuration(Integer.valueOf(-1));

        }
        else {
            edit.commit();

            //Updated predicted wake time textview with the latest calculated time
            setTextView();
        }
    }

    @Override
    public void onClick(View view) {

        //Handling the click listeners

        SharedPreferences sp = this.getActivity().getSharedPreferences("wuastafile",MODE_PRIVATE);

        switch (view.getId()) {

            //Creating and running intent for GMaps Navigation
            case R.id.navigationCard:
                String url = "http://maps.google.com/maps?saddr=" + sp.getString("newhomelat", "12.8614515") + ","
                        + sp.getString("newhomelong", "77.6647081") + "&daddr=" + sp.getString("newworklat", "12.975686000000001")
                        + "," + sp.getString("newworklong", "77.605852");

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(url));
                startActivity(intent);
                break;

            //Passing the time at which a new alarm should be created to createAlarm method in MainActivity
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

        /*
        Recursively calling Gmaps API to get the correct commute time asynchronously.
        API keeps getting called via an algorithm that works similarly to binary search.
        Recursion continues until a commute time is found such that it is within ("time to to be at" - 3 mins, "time to be at")
         */

        SharedPreferences sharedPref1 = this.getActivity().getSharedPreferences("wuastafile", MODE_PRIVATE);
        SharedPreferences.Editor edit1 = sharedPref1.edit();

        //Newly calculated commute duration for this recursive iteration
        int fd=-1;
        if(sd != null)
        fd = sd.intValue();

        //This is the "time to be at" recieved from Modify fragment
        int hr = sharedPref1.getInt("sethour",9);
        int mn = sharedPref1.getInt("setminute",0);

        //Async task returned null, usually due to network error/no network
        if(sd==null){

            if(toast != null) toast.cancel();
            toast = Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT);
            toast.show();

            duration = null;
            edit1.putString("duration",null);
            edit1.commit();
        }

        //Async task that calls this method again on completion of the async task
        else if((prevtime + fd) < (hr*60 + mn - 3) || (prevtime + fd) > hr*60 + mn){

            //Changing previously predicted time to this recursive iterations time
            prevtime = hr*60 + mn - fd;

            /*Building a new link for API call, if first iteration, then fd is -1, so departureepoch is subtracted by 30 mins
            (initially assumed predicted time is always 30 mins before the "time to be at")
             */
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

            //Async task is run with the newly made URL
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

        //Recursion termination condition is satisfied, a new commute time is found, which is fd
        else{

            //"delay" is from Modify fragment, it's the additional time that the user requested, in order to get ready
            int settimeepoch = hr*60 + mn - fd - sharedPref1.getInt("delay",0);

            hr = settimeepoch >= 60 ? settimeepoch/60 : 0;
            mn = settimeepoch%60;

            //Storing the predicted wake time
            edit1.putInt("phour",hr);
            edit1.putInt("pminute",mn);

            //So that the one of the three conditions for calculating new predicted wake time is not satisfied anymore
            duration = sd;

            edit1.putString("duration",sd.toString());
            edit1.commit();

            if(toast != null) toast.cancel();
            toast = Toast.makeText(getActivity(), "Done!", Toast.LENGTH_SHORT);
            toast.show();

            //Setting predicted time textview with the newly found predicted time
            setTextView();

        }
    }

    void setTextView(){

        //Sets predicted wake time textview

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

        /*
        Weatherbit.io API is used for weather info, weather info can be shown for 3 locations, home, work and enroute.
        enroute is the midpoint between home and work.
         */

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

        //New URL for API call
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

        //Getting info from API call asynchronously. setWeatherJSON() method is called on completion
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

        /*
        Data is extracted from JSON, and stored.
        JSON contains a wide list of weather information for the next 16 days including today.
        We use the stored dayfactor, containing (0...6) to get the wather info
        for the particular day that the next predicted time is calculated for. 0 is for todays info,
        6 is for the weather info for 6 days from now.
         */
        if(wjson == null) return;

        JSONArray wjarray;

        /*Weather info is put into the respective textviews.
        "pop" gives the percentage chance of precipitation
        "temp" is the average weather for the day
        "description" is a few word description of the day's weather
         */
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
