package com.walarm.wuasta;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * Created by gokul on 18-09-2017.
 */

public class modifyFragment extends Fragment implements CompoundButton.OnCheckedChangeListener,View.OnClickListener{

    public View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.modifylayout, container , false);

        Button timeset = (Button)v.findViewById(R.id.timesetbutton);
        timeset.setOnClickListener(this);

        ToggleButton sun = (ToggleButton)v.findViewById(R.id.suntoggle);
        sun.setOnCheckedChangeListener(this);

        ToggleButton mon = (ToggleButton)v.findViewById(R.id.montoggle);
        mon.setOnCheckedChangeListener(this);

        ToggleButton tue = (ToggleButton)v.findViewById(R.id.tuetoggle);
        tue.setOnCheckedChangeListener(this);

        ToggleButton wed = (ToggleButton)v.findViewById(R.id.wedtoggle);
        wed.setOnCheckedChangeListener(this);

        ToggleButton thu = (ToggleButton)v.findViewById(R.id.thutoggle);
        thu.setOnCheckedChangeListener(this);

        ToggleButton fri = (ToggleButton)v.findViewById(R.id.fritoggle);
        fri.setOnCheckedChangeListener(this);

        ToggleButton sat = (ToggleButton)v.findViewById(R.id.sattoggle);
        sat.setOnCheckedChangeListener(this);

        ToggleButton plusfive = (ToggleButton)v.findViewById(R.id.fiveone);
        plusfive.setOnCheckedChangeListener(this);

        ToggleButton plusten = (ToggleButton)v.findViewById(R.id.tenone);
        plusten.setOnCheckedChangeListener(this);

        ToggleButton plusfifteen = (ToggleButton)v.findViewById(R.id.fifteenone);
        plusfifteen.setOnCheckedChangeListener(this);

        ToggleButton plusthirty = (ToggleButton)v.findViewById(R.id.thirtyone);
        plusthirty.setOnCheckedChangeListener(this);

        ToggleButton weatherhome = (ToggleButton)v.findViewById(R.id.weatherhome);
        weatherhome.setOnCheckedChangeListener(this);

        ToggleButton weatherwork = (ToggleButton)v.findViewById(R.id.weatherwork);
        weatherwork.setOnCheckedChangeListener(this);

        ToggleButton weatherenroute = (ToggleButton)v.findViewById(R.id.weatherenroute);
        weatherenroute.setOnCheckedChangeListener(this);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()){

            case R.id.suntoggle: if(isChecked){
                Toast.makeText(getActivity(), "Sunday Toggled ON",
                        Toast.LENGTH_SHORT).show();
            }
                else{
                Toast.makeText(getActivity(), "Sunday Toggled OFF",
                        Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.montoggle: if(isChecked){
                Toast.makeText(getActivity(), "Monday Toggled ON",
                        Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getActivity(), "Monday Toggled OFF",
                        Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.tuetoggle: if(isChecked){
                Toast.makeText(getActivity(), "Tuesday Toggled ON",
                        Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getActivity(), "Tuesday Toggled OFF",
                        Toast.LENGTH_SHORT).show();
            }
                break;
            case R.id.wedtoggle: if(isChecked){
                Toast.makeText(getActivity(), "Wednesday Toggled ON",
                        Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getActivity(), "Wednesday Toggled OFF",
                        Toast.LENGTH_SHORT).show();
            }
                break;
            case R.id.thutoggle: if(isChecked){
                Toast.makeText(getActivity(), "Thursday Toggled ON",
                        Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getActivity(), "Thursday Toggled OFF",
                        Toast.LENGTH_SHORT).show();
            }
                break;
            case R.id.fritoggle: if(isChecked){
                Toast.makeText(getActivity(), "Friday Toggled ON",
                        Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getActivity(), "Friday Toggled OFF",
                        Toast.LENGTH_SHORT).show();
            }
                break;
            case R.id.sattoggle: if(isChecked){
                Toast.makeText(getActivity(), "Saturday Toggled ON",
                        Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getActivity(), "Saturday Toggled OFF",
                        Toast.LENGTH_SHORT).show();
            }
                break;
            case R.id.fiveone: if(isChecked){
                Toast.makeText(getActivity(), "5 Toggled ON",
                        Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getActivity(), "5 Toggled OFF",
                        Toast.LENGTH_SHORT).show();
            }
                break;
            case R.id.tenone: if(isChecked){
                Toast.makeText(getActivity(), "10 Toggled ON",
                        Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getActivity(), "10 Toggled OFF",
                        Toast.LENGTH_SHORT).show();
            }
                break;
            case R.id.fifteenone: if(isChecked){
                Toast.makeText(getActivity(), "15 Toggled ON",
                        Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getActivity(), "15 Toggled OFF",
                        Toast.LENGTH_SHORT).show();
            }
                break;
            case R.id.thirtyone: if(isChecked){
                Toast.makeText(getActivity(), "30 Toggled ON",
                        Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getActivity(), "30 Toggled OFF",
                        Toast.LENGTH_SHORT).show();
            }
                break;
            case R.id.weatherhome: if(isChecked){

                ((ToggleButton)v.findViewById(R.id.weatherwork)).setOnCheckedChangeListener(null);
                ((ToggleButton)v.findViewById(R.id.weatherenroute)).setOnCheckedChangeListener(null);
                ((ToggleButton)v.findViewById(R.id.weatherwork)).setChecked(false);
                ((ToggleButton)v.findViewById(R.id.weatherenroute)).setChecked(false);
                ((ToggleButton)v.findViewById(R.id.weatherwork)).setOnCheckedChangeListener(this);
                ((ToggleButton)v.findViewById(R.id.weatherenroute)).setOnCheckedChangeListener(this);

                Toast.makeText(getActivity(), "Weather Home Toggled ON",
                        Toast.LENGTH_SHORT).show();
            }
            else{
                buttonView.setChecked(true);
            }
                break;
            case R.id.weatherwork: if(isChecked){

                ((ToggleButton)v.findViewById(R.id.weatherhome)).setOnCheckedChangeListener(null);
                ((ToggleButton)v.findViewById(R.id.weatherenroute)).setOnCheckedChangeListener(null);
                ((ToggleButton)v.findViewById(R.id.weatherhome)).setChecked(false);
                ((ToggleButton)v.findViewById(R.id.weatherenroute)).setChecked(false);
                ((ToggleButton)v.findViewById(R.id.weatherhome)).setOnCheckedChangeListener(this);
                ((ToggleButton)v.findViewById(R.id.weatherenroute)).setOnCheckedChangeListener(this);

                Toast.makeText(getActivity(), "Weather Work Toggled ON",
                        Toast.LENGTH_SHORT).show();
            }
            else{
                buttonView.setChecked(true);
            }
                break;
            case R.id.weatherenroute: if(isChecked){

                ((ToggleButton)v.findViewById(R.id.weatherhome)).setOnCheckedChangeListener(null);
                ((ToggleButton)v.findViewById(R.id.weatherwork)).setOnCheckedChangeListener(null);
                ((ToggleButton)v.findViewById(R.id.weatherhome)).setChecked(false);
                ((ToggleButton)v.findViewById(R.id.weatherwork)).setChecked(false);
                ((ToggleButton)v.findViewById(R.id.weatherhome)).setOnCheckedChangeListener(this);
                ((ToggleButton)v.findViewById(R.id.weatherwork)).setOnCheckedChangeListener(this);

                Toast.makeText(getActivity(), "Weather Enroute Toggled ON",
                        Toast.LENGTH_SHORT).show();
            }
            else{
                buttonView.setChecked(true);
            }
                break;
        }

    }

    @Override
    public void onClick(View view) {
        //set time
        Toast.makeText(getActivity(), "Set Time",
                Toast.LENGTH_SHORT).show();


    }
}
