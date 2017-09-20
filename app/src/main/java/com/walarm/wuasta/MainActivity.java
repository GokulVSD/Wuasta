package com.walarm.wuasta;

import android.support.v4.app.Fragment;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView mBottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation);

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
}
