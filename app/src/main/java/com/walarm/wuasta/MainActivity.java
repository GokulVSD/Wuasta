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

        //Setting up listeners and services, and saving a reference to the bottom navigation bar

        setContentView(R.layout.activity_main);

        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation);

        alarmManager = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);

        //When a new item from the bottom nav bar is selected, (wuasta,modify,settings)
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

        //If back button is pressed, you are prompted to press again, in order to exit
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

        /*
        Called when a new item is selected.
        Fragment is transacted based on which item was selected.
         */

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

        /*
        This method is called from the Wuasta fragment. The time for which the alarm should be set is
        passed as parameters, along with the dayfactor. (Check the Wuasta fragment to understand
        the usage of dayfactor.
         */

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);
        calendar.add(Calendar.DATE,df);

        //New pending intent for the alarm reciever class, intent is run when epoch time in milliseconds is reached

        Intent intent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 10, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}
