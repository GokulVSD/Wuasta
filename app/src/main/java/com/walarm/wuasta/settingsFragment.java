package com.walarm.wuasta;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import static android.app.Activity.RESULT_OK;

/**
 * Created by gokul on 18-09-2017.
 */

public class settingsFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.settingslayout , container , false);

        Button homePicker = (Button)v.findViewById(R.id.changehomelocationbutton);
        homePicker.setOnClickListener(this);

        Button workPicker = (Button)v.findViewById(R.id.changeworklocationbutton);
        workPicker.setOnClickListener(this);

        Button renameHome = (Button)v.findViewById(R.id.renamehomebutton);
        renameHome.setOnClickListener(this);

        Button renameWork = (Button)v.findViewById(R.id.renameworkbutton);
        renameWork.setOnClickListener(this);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View view){
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

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
            case R.id.renamehomebutton:
                Toast.makeText(getActivity(), "Home rename",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.renameworkbutton:
                Toast.makeText(getActivity(), "Work rename",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Place place = PlacePicker.getPlace(getContext(),data);
        double latitude,longitude;

        if (resultCode == RESULT_OK) {
            switch (requestCode){
                case 1:
                    latitude = place.getLatLng().latitude;
                    longitude = place.getLatLng().longitude;
                    Toast.makeText(getActivity(), "Home: LAT="+latitude+" LONG="+longitude,
                            Toast.LENGTH_LONG).show();
                    break;

                case 2:
                    latitude = place.getLatLng().latitude;
                    longitude = place.getLatLng().longitude;
                    Toast.makeText(getActivity(), "Work: LAT="+latitude+" LONG="+longitude,
                            Toast.LENGTH_LONG).show();
                    break;


            }
        }

    }
}
