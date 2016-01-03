package com.samaranavi.egorpetruchcho.core;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.octo.android.robospice.SpiceManager;
import com.samaranavi.egorpetruchcho.task.BackgroundSpiceService;

public abstract class SamaraNaviFragment extends Fragment {

    private final SpiceManager backgroundManager = new SpiceManager(BackgroundSpiceService.class);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        backgroundManager.start(getActivity());
    }

    @Override
    public void onDetach() {
        backgroundManager.shouldStop();
        super.onDetach();
    }

    protected SpiceManager getBackgroundManager() {
        return backgroundManager;
    }
}
