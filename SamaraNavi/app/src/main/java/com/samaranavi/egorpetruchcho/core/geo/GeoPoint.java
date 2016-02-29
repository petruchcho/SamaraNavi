package com.samaranavi.egorpetruchcho.core.geo;

import android.location.Location;

public class GeoPoint {

    private int mLongitudeE6;
    private int mLatitudeE6;
    private int mAltitude;

    public GeoPoint(final int aLatitudeE6, final int aLongitudeE6) {
        this.mLatitudeE6 = aLatitudeE6;
        this.mLongitudeE6 = aLongitudeE6;
    }

    public GeoPoint(final int aLatitudeE6, final int aLongitudeE6, final int aAltitude) {
        this.mLatitudeE6 = aLatitudeE6;
        this.mLongitudeE6 = aLongitudeE6;
        this.mAltitude = aAltitude;
    }

    public GeoPoint(final double aLatitude, final double aLongitude) {
        this.mLatitudeE6 = (int) (aLatitude * 1E6);
        this.mLongitudeE6 = (int) (aLongitude * 1E6);
    }

    public GeoPoint(final double aLatitude, final double aLongitude, final double aAltitude) {
        this.mLatitudeE6 = (int) (aLatitude * 1E6);
        this.mLongitudeE6 = (int) (aLongitude * 1E6);
        this.mAltitude = (int) aAltitude;
    }

    public GeoPoint(final Location aLocation) {
        this(aLocation.getLatitude(), aLocation.getLongitude(), aLocation.getAltitude());
    }

    public GeoPoint(final GeoPoint aGeopoint) {
        this.mLatitudeE6 = aGeopoint.mLatitudeE6;
        this.mLongitudeE6 = aGeopoint.mLongitudeE6;
        this.mAltitude = aGeopoint.mAltitude;
    }

    public int getLongitudeE6() {
        return this.mLongitudeE6;
    }

    public void setLongitudeE6(final int aLongitudeE6) {
        this.mLongitudeE6 = aLongitudeE6;
    }

    public int getLatitudeE6() {
        return this.mLatitudeE6;
    }

    public void setLatitudeE6(final int aLatitudeE6) {
        this.mLatitudeE6 = aLatitudeE6;
    }

    public double getLongitude() {
        return this.mLongitudeE6 * 1E-6;
    }

    public double getLatitude() {
        return this.mLatitudeE6 * 1E-6;
    }

    public int getAltitude() {
        return this.mAltitude;
    }

    public void setAltitude(final int aAltitude) {
        this.mAltitude = aAltitude;
    }
}