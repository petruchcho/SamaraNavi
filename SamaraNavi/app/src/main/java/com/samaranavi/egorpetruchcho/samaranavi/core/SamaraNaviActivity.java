package com.samaranavi.egorpetruchcho.samaranavi.core;

import android.support.v7.app.AppCompatActivity;

import com.octo.android.robospice.SpiceManager;
import com.samaranavi.egorpetruchcho.samaranavi.task.BackgroundSpiceService;

public class SamaraNaviActivity extends AppCompatActivity {

    private SpiceManager backgroundManager = new SpiceManager(BackgroundSpiceService.class);

    @Override
    protected void onStart() {
        backgroundManager.start(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        backgroundManager.shouldStop();
        super.onStop();
    }

    protected SpiceManager getBackgroundManager() {
        return backgroundManager;
    }
}
