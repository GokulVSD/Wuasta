package com.walarm.wuasta;


import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by gokul on 23-09-2017.
 */

public class JSONCreaterFromStringURL {

    public static String getResponseFromHttpUrl(URL url) throws Exception {

        //Static method for pulling all the info in a URL and returning it as a String

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static int getDurationFromURL(URL url)throws Exception{

        /*
        Called for the asynchronous task to get the commute time from the API URL request.
        A new JSON is created from the String returned, and is parsed for the Integer value.
         */

        JSONObject json = new JSONObject(getResponseFromHttpUrl(url))
                .getJSONArray("routes")
                .getJSONObject(0)
                .getJSONArray("legs")
                .getJSONObject(0)
                .getJSONObject("duration_in_traffic");

        return (int)Math.ceil((json.getInt("value")/60.0));
    }

    public static String getWeatherJSON(URL url)throws Exception{

        /*
        Called for the asynchronous task to get weather info from API URL.
        The info is returned as a String, which is later converted to a JSON.
         */

        return getResponseFromHttpUrl(url);
    }
}
