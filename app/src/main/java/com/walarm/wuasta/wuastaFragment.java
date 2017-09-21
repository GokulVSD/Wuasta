package com.walarm.wuasta;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by gokul on 18-09-2017.
 */

public class wuastaFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.wuastalayout, container, false);

        CardView directions = (CardView) v.findViewById(R.id.navigationCard);
        directions.setOnClickListener(this);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View view) {

        //Call Google Maps Navigation Intent
        Toast.makeText(getActivity(), "Navigation Intent",
                Toast.LENGTH_SHORT).show();

    }
}
