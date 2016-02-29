package com.samaranavi.egorpetruchcho.core.map.osmdroidbonus;

import com.samaranavi.egorpetruchcho.core.geo.GeoPoint;
import com.samaranavi.egorpetruchcho.core.map.GeoPointConverter;

public class OsmdroidBonusGeoPointConverter implements GeoPointConverter<org.osmdroid.util.GeoPoint> {

    private OsmdroidBonusGeoPointConverter() {
    }

    private static OsmdroidBonusGeoPointConverter instance = new OsmdroidBonusGeoPointConverter();

    public static OsmdroidBonusGeoPointConverter getInstance() {
        return instance;
    }

    @Override
    public org.osmdroid.util.GeoPoint convertFromCommon(GeoPoint geoPoint) {
        return new org.osmdroid.util.GeoPoint(geoPoint.getLatitude(), geoPoint.getLongitude(), geoPoint.getAltitude());
    }

    @Override
    public GeoPoint convertFromCustom(org.osmdroid.util.GeoPoint geoPoint) {
        return new GeoPoint(geoPoint.getLatitude(), geoPoint.getLongitude(), geoPoint.getAltitude());
    }
}
