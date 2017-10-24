package com.walarm.wuasta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.w3c.dom.Text;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.CONTEXT_IGNORE_SECURITY;
import static android.content.Context.CONTEXT_RESTRICTED;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by gokul on 18-09-2017.
 */

public class settingsFragment extends Fragment implements View.OnClickListener {

    View v;
    Toast toast;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        //Stores a reference to the views, and sets listeners. Called when fragment creation is requested.

        v = inflater.inflate(R.layout.settingslayout , container , false);

        Button homePicker = (Button)v.findViewById(R.id.changehomelocationbutton);
        homePicker.setOnClickListener(this);

        Button workPicker = (Button)v.findViewById(R.id.changeworklocationbutton);
        workPicker.setOnClickListener(this);

        Button renameWork = (Button)v.findViewById(R.id.renameworkbutton);
        renameWork.setOnClickListener(this);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Once the view is created, the previously selected Home and Work are restored.

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("wuastafile",MODE_PRIVATE);

        ((TextView)view.findViewById(R.id.currenthome)).setText(sharedPreferences.getString("homeplace","PESIT South Campus"));
        ((TextView)view.findViewById(R.id.currentwork)).setText(sharedPreferences.getString("workplace","MG Road Metro Station"));
    }

    @Override
    public void onClick(View view){

        //User wants to pick a new location for either Home or Work.

        //Google's Placepicker API. an Intent is created, with a result from the intent being expected.

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("wuastafile",MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();

        //Checking which location is being modified, condition codes 1 and 2 are passed so that
        //we know which location was updated when the intent result arrives.
        switch (view.getId()) {

            case R.id.changehomelocationbutton:
                try {
                    startActivityForResult(builder.build(getActivity()), 1);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.changeworklocationbutton:
                try {
                    startActivityForResult(builder.build(getActivity()), 2);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                break;

            //If you had clicked the button to replace the name of "Work" throughout the app.
            case R.id.renameworkbutton:
                EditText workedit = (EditText)v.findViewById(R.id.renameworktext);

                //If button was clicked on accident, or no new name was typed
                if(workedit.getText().toString().equals("")) break;

                //Can't have more than 10 characters due to formatting of app layout
                else if(workedit.getText().toString().length()>10) {

                    if(toast != null) toast.cancel();
                    toast = Toast.makeText(getActivity(), "10 Character Limit", Toast.LENGTH_SHORT);
                    toast.show();
                    break;
                }

                edit.putString("workname",workedit.getText().toString().trim());

                if(toast != null) toast.cancel();
                toast = Toast.makeText(getActivity(), "Work Renamed: "+workedit.getText().toString().trim(), Toast.LENGTH_SHORT);
                toast.show();

                break;
        }
        edit.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //If no network, or the place picking was cancelled midway
        if(data == null) return;

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("wuastafile",MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();

        Place place = PlacePicker.getPlace(getContext(),data);
        double latitude,longitude;
        String placeName;

        if (resultCode == RESULT_OK) {

            //Checking request code that was used to create the intent, 1 for Home location, 2 for Work locations
            switch (requestCode){
                case 1:
                    placeName = String.format("%s", place.getName());
                    latitude = place.getLatLng().latitude;
                    longitude = place.getLatLng().longitude;

                    edit.putString("homeplace", placeName);
                    edit.putString("newhomelat", ""+latitude);
                    edit.putString("newhomelong", ""+longitude);
                    edit.putBoolean("recheck",true);

                    ((TextView)v.findViewById(R.id.currenthome)).setText(placeName);

                    if(toast != null) toast.cancel();
                    toast = Toast.makeText(getActivity(), "Home Changed", Toast.LENGTH_SHORT);
                    toast.show();

                    break;

                case 2:
                    placeName = String.format("%s", place.getName());
                    latitude = place.getLatLng().latitude;
                    longitude = place.getLatLng().longitude;

                    edit.putString("workplace", placeName);
                    edit.putString("newworklat", ""+latitude);
                    edit.putString("newworklong", ""+longitude);
                    edit.putBoolean("recheck",true);

                    ((TextView)v.findViewById(R.id.currentwork)).setText(placeName);

                    if(toast != null) toast.cancel();
                    toast = Toast.makeText(getActivity(), "Work Changed", Toast.LENGTH_SHORT);
                    toast.show();

                    break;


            }
            edit.commit();
        }

    }
}
