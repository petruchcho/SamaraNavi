package com.samaranavi.egorpetruchcho.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.samaranavi.egorpetruchcho.core.SamaraNaviActivity;

public class MapActivity extends SamaraNaviActivity {

    private static final String FRAGMENT_TAG = "MAP_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentByTag(FRAGMENT_TAG) == null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(android.R.id.content, new MapFragment(), FRAGMENT_TAG);
            transaction.commit();
        }
    }
}
