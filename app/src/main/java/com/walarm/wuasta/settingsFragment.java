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

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("wuastafile",MODE_PRIVATE);

        ((TextView)view.findViewById(R.id.currenthome)).setText(sharedPreferences.getString("homeplace","PESIT South Campus"));
        ((TextView)view.findViewById(R.id.currentwork)).setText(sharedPreferences.getString("workplace","MG Road Metro Station"));
    }

    @Override
    public void onClick(View view){
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("wuastafile",MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();

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
            case R.id.renameworkbutton:
                EditText workedit = (EditText)v.findViewById(R.id.renameworktext);

                if(workedit.getText().toString().equals("")) break;

                else if(workedit.getText().toString().length()>10) {

                    if(toast != null) toast.cancel();
                    toast = Toast.makeText(getActivity(), "10 Character Limit", Toast.LENGTH_SHORT);
                    toast.show();
                    break;
                }

                edit.putString("workname",workedit.getText().toString());

                if(toast != null) toast.cancel();
                toast = Toast.makeText(getActivity(), "Work Renamed: "+workedit.getText().toString(), Toast.LENGTH_SHORT);
                toast.show();

                break;
        }
        edit.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data == null) return;

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("wuastafile",MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();

        Place place = PlacePicker.getPlace(getContext(),data);
        double latitude,longitude;
        String placeName;

        if (resultCode == RESULT_OK) {
            switch (requestCode){
                case 1:
                    placeName = String.format("%s", place.getName());
                    latitude = place.getLatLng().latitude;
                    longitude = place.getLatLng().longitude;

                    edit.putString("homeplace", placeName);
                    edit.putString("newhomelat", ""+latitude);
                    edit.putString("newhomelong", ""+longitude);

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
