package com.walarm.wuasta;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v4.app.Fragment;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView mBottomNav;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation);

        alarmManager = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);

        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                selectFrag(item);
                return true;
            }
        });

        MenuItem selectedItem = mBottomNav.getMenu().getItem(0);
        selectFrag(selectedItem);

    }

    int backcounter=1;
    Toast toast;
    @Override
    public void onBackPressed(){
        if(backcounter==1){
            if(toast != null) toast.cancel();
            toast = Toast.makeText(this, "Press Again to Exit", Toast.LENGTH_SHORT);
            toast.show();
            backcounter++;
            return;
        }
        backcounter=1;
        super.onBackPressed();
    }



    private void selectFrag(MenuItem item){

        Fragment frag = null;

        switch (item.getItemId()) {

            case R.id.menu_wuasta:
                ((TextView)findViewById(R.id.title)).setText("Wuasta");
                frag = new wuastaFragment();
                break;
            case R.id.menu_modify:
                ((TextView)findViewById(R.id.title)).setText("Modify");
                frag = new modifyFragment();
                break;
            case R.id.menu_settings:
                ((TextView)findViewById(R.id.title)).setText("Settings");
                frag = new settingsFragment();
                break;
        }



        if(frag != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, frag);
            ft.commit();

        }
    }

    public void createAlarm(int df, int hour, int minute){

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);
        calendar.add(Calendar.DATE,df);

        Intent intent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 10, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}
