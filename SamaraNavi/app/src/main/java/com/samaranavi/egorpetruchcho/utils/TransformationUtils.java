package com.samaranavi.egorpetruchcho.utils;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.util.GeoPoint;

public class TransformationUtils {

    public final static double ORIGIN_SHIFT = Math.PI * 6378137;

    public static IGeoPoint transformWebMercatorToWGS84(IGeoPoint srcPoint) {
        double srcPointX = srcPoint.getLatitudeE6();
        double srcPointY = srcPoint.getLongitudeE6();

        double num1 = 1.5707963267948966 - (2.0 * Math.atan(Math.exp((-1.0 * srcPointX) / 6378137.0)));
        double num3 = srcPointY / 6378137.0;
        double num4 = num3 * 57.295779513082323;
        double num5 = Math.floor((num4 + 180.0) / 360.0);
        double num6 = num4 - (num5 * 360.0);

        return new GeoPoint(num1 * 57.295779513082323, num6);
    }

    public static WebMercatorGeoPoint transformWGS84ToWebMercator(IGeoPoint srcPoint) {
        double srcPointX = srcPoint.getLatitude();
        double srcPointY = srcPoint.getLongitude();

        double num = srcPointX * 0.017453292519943295;
        double x = 6378137.0 * num;
        double a = srcPointY * 0.017453292519943295;

        return new WebMercatorGeoPoint(new GeoPoint(3189068.5 * Math.log((1.0 + Math.sin(a)) / (1.0 - Math.sin(a))), x));
    }

    public static double transformXtoWebMercator(int x, int zoom) {
        // convert x tile position and zoom to wgs84 longitude
        double value = x / Math.pow(2.0, zoom) * 360.0 - 180;

        // apply the shift to get the EPSG longitude
        return value * ORIGIN_SHIFT / 180.0;
    }

    public static double transformYtoWebMercator(int y, int zoom) {
        // convert x tile position and zoom to wgs84 latitude
        double value = Math.PI - (2.0 * Math.PI * y) / Math.pow(2.0, zoom);
        value = Math.toDegrees(Math.atan(Math.sinh(value)));

        value = Math.log(Math.tan((90 + value) * Math.PI / 360.0)) / (Math.PI / 180.0);

        // apply the shift to get the EPSG latitude
        return value * ORIGIN_SHIFT / 180.0;
    }

    /**
     * Simple decorator to know if we are using WebMercator
     */
    private static class WebMercatorGeoPoint implements IGeoPoint {
        private final IGeoPoint geoPoint;

        public WebMercatorGeoPoint(IGeoPoint geoPoint) {
            this.geoPoint = geoPoint;
        }

        @Override
        public int getLatitudeE6() {
            return geoPoint.getLatitudeE6();
        }

        @Override
        public int getLongitudeE6() {
            return geoPoint.getLongitudeE6();
        }

        @Override
        public double getLatitude() {
            return geoPoint.getLatitude();
        }

        @Override
        public double getLongitude() {
            return geoPoint.getLongitude();
        }
    }
}
