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
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.sql.Time;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by gokul on 18-09-2017.
 */

public class modifyFragment extends Fragment implements CompoundButton.OnCheckedChangeListener,View.OnClickListener{

    public View v;
    Toast toast;
    int daycounter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.modifylayout, container , false);

        Button timeset = (Button)v.findViewById(R.id.timesetbutton);
        timeset.setOnClickListener(this);

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

        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("wuastafile",MODE_PRIVATE);
        daycounter=0;

        ((TimePicker)view.findViewById(R.id.timePicker)).setHour(sharedPref.getInt("sethour",9));
        ((TimePicker)view.findViewById(R.id.timePicker)).setMinute(sharedPref.getInt("setminute",0));

        ToggleButton sun = (ToggleButton)view.findViewById(R.id.suntoggle);
        sun.setChecked(sharedPref.getBoolean("sun",false));
        if(sun.isChecked()) daycounter++;
        sun.setOnCheckedChangeListener(this);

        ToggleButton mon = (ToggleButton)view.findViewById(R.id.montoggle);
        mon.setChecked(sharedPref.getBoolean("mon",true));
        if(mon.isChecked()) daycounter++;
        mon.setOnCheckedChangeListener(this);

        ToggleButton tue = (ToggleButton)view.findViewById(R.id.tuetoggle);
        tue.setChecked(sharedPref.getBoolean("tue",true));
        if(tue.isChecked()) daycounter++;
        tue.setOnCheckedChangeListener(this);

        ToggleButton wed = (ToggleButton)view.findViewById(R.id.wedtoggle);
        wed.setChecked(sharedPref.getBoolean("wed",true));
        if(wed.isChecked()) daycounter++;
        wed.setOnCheckedChangeListener(this);

        ToggleButton thu = (ToggleButton)view.findViewById(R.id.thutoggle);
        thu.setChecked(sharedPref.getBoolean("thu",true));
        if(thu.isChecked()) daycounter++;
        thu.setOnCheckedChangeListener(this);

        ToggleButton fri = (ToggleButton)view.findViewById(R.id.fritoggle);
        fri.setChecked(sharedPref.getBoolean("fri",true));
        if(fri.isChecked()) daycounter++;
        fri.setOnCheckedChangeListener(this);

        ToggleButton sat = (ToggleButton)view.findViewById(R.id.sattoggle);
        sat.setChecked(sharedPref.getBoolean("sat",false));
        if(sat.isChecked()) daycounter++;
        sat.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("wuastafile",MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();

        switch (buttonView.getId()){

            case R.id.suntoggle: if(isChecked){
                edit.putBoolean("sun",true);
                daycounter++;
            }
                else{
                if(daycounter==1){
                    buttonView.setChecked(true);
                }
                else{
                    daycounter--;
                    edit.putBoolean("sun",false);
                }
            }
            break;
            case R.id.montoggle: if(isChecked){
                edit.putBoolean("mon",true);
                daycounter++;
            }
            else{
                if(daycounter==1){
                    buttonView.setChecked(true);
                }
                else{
                    daycounter--;
                    edit.putBoolean("mon",false);
                }
            }
            break;
            case R.id.tuetoggle: if(isChecked){
                edit.putBoolean("tue",true);
                daycounter++;
            }
            else{
                if(daycounter==1){
                    buttonView.setChecked(true);
                }
                else{
                    daycounter--;
                    edit.putBoolean("tue",false);
                }
            }
                break;
            case R.id.wedtoggle: if(isChecked){
                edit.putBoolean("wed",true);
                daycounter++;
            }
            else{
                if(daycounter==1){
                    buttonView.setChecked(true);
                }
                else{
                    daycounter--;
                    edit.putBoolean("wed",false);
                }
            }
                break;
            case R.id.thutoggle: if(isChecked){
                edit.putBoolean("thu",true);
                daycounter++;
            }
            else{
                if(daycounter==1){
                    buttonView.setChecked(true);
                }
                else{
                    daycounter--;
                    edit.putBoolean("thu",false);
                }
            }
                break;
            case R.id.fritoggle: if(isChecked){
                edit.putBoolean("fri",true);
                daycounter++;
            }
            else{
                if(daycounter==1){
                    buttonView.setChecked(true);
                }
                else{
                    daycounter--;
                    edit.putBoolean("fri",false);
                }
            }
                break;
            case R.id.sattoggle: if(isChecked){
                edit.putBoolean("sat",true);
                daycounter++;
            }
            else{
                if(daycounter==1){
                    buttonView.setChecked(true);
                }
                else{
                    daycounter--;
                    edit.putBoolean("sat",false);
                }
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
        edit.commit();

    }

    @Override
    public void onClick(View view) {

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("wuastafile",MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();

        TimePicker timePicker = (TimePicker)v.findViewById(R.id.timePicker);
        edit.putInt("sethour",timePicker.getHour());
        edit.putInt("setminute",timePicker.getMinute());

        if(toast != null) toast.cancel();
        toast = Toast.makeText(getActivity(), "Time Set", Toast.LENGTH_SHORT);
        toast.show();

        edit.commit();
    }
}
