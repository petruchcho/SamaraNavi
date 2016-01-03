package com.samaranavi.egorpetruchcho.core;

import android.support.v7.app.AppCompatActivity;

import com.octo.android.robospice.SpiceManager;
import com.samaranavi.egorpetruchcho.task.BackgroundSpiceService;

public abstract class SamaraNaviActivity extends AppCompatActivity {

    private final SpiceManager backgroundManager = new SpiceManager(BackgroundSpiceService.class);

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
