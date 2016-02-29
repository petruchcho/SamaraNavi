package com.samaranavi.egorpetruchcho.core.map;

import com.samaranavi.egorpetruchcho.core.geo.GeoPoint;

public abstract class MarkerOverlay<TDelegate> implements MapOverlay {

    protected final TDelegate delegate;

    protected MarkerOverlay(TDelegate delegate) {
        this.delegate = delegate;
    }

    protected TDelegate getDelegate() {
        return delegate;
    }

    public abstract GeoPoint getPosition();

    public abstract void setPosition(GeoPoint geoPoint);
}
