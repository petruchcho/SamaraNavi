package com.samaranavi.egorpetruchcho.core.map;

import android.content.Context;

public abstract class MapView<TDelegate> {

    private static MapView instance;

    private TDelegate mapViewDelegate;

    public static MapView getInstance(Context context) {
        return null;
    }

    public abstract MapViewOverlaysFactory getMapOverlaysFactory();

    public TDelegate getMapViewDelegate() {
        return mapViewDelegate;
    }
}
