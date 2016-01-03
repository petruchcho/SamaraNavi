package com.samaranavi.egorpetruchcho.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samaranavi.egorpetruchcho.core.SamaraNaviFragment;
import com.samaranavi.egorpetruchcho.samaranavi.R;

public class MapFragment extends SamaraNaviFragment {

    public MapFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_map, container, false);
    }
}
