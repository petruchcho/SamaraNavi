package com.samaranavi.egorpetruchcho.core;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
