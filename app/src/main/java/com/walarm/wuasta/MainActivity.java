package com.walarm.wuasta;

import android.support.v4.app.Fragment;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView mBottomNav = (BottomNavigationView) findViewById(R.id.navigation);

        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                selectFrag(item);
                return true;
            }
        });
        MenuItem mDefault;
        mDefault = mBottomNav.getMenu().getItem(0);
        selectFrag(mDefault);

    }
    private void selectFrag(MenuItem item){

        Fragment frag = null;

        switch (item.getItemId()) {

            case R.id.menu_wuasta:
                frag = new wuastaFragment();
                break;
            case R.id.menu_modify:
                frag = new modifyFragment();
                break;
            case R.id.menu_settings:
                frag = new settingsFragment();
                break;
        }

        ((TextView)findViewById(R.id.title)).setText(item.getTitle());

        if(frag != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, frag);
            ft.commit();

        }
    }
}
