package com.samaranavi.egorpetruchcho.core.map;

import com.samaranavi.egorpetruchcho.core.geo.GeoPoint;

public interface GeoPointConverter<T> {
    T convertFromCommon(GeoPoint geoPoint);

    GeoPoint convertFromCustom(T geoPoint);
}
