package com.samaranavi.egorpetruchcho.ui.controllers;

import android.content.Context;
import android.os.Bundle;

import com.samaranavi.egorpetruchcho.utils.GeoportalTileSource;
import com.samaranavi.egorpetruchcho.utils.TransformationUtils;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.tileprovider.MapTileProviderBasic;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

public class MapViewController implements UIController {

    private final static String MAP_CENTER_LAT_KEY = "MAP_CENTER_LAT_KEY";
    private final static String MAP_CENTER_LON_KEY = "MAP_CENTER_LON_KEY";
    private final static String MAP_ZOOM_KEY = "MAP_ZOOM_KEY";

    private final Context context;
    private final MapView mapView;

    public MapViewController(Context context, MapView mapView) {
        this.context = context;
        this.mapView = mapView;
    }

    @Override
    public void restoreState(Bundle savedInstanceState) {
        final MapTileProviderBasic tileProvider = new MapTileProviderBasic(context);
        final ITileSource tileSource = new GeoportalTileSource(1, 20);
        tileProvider.setTileSource(tileSource);
        mapView.setTileProvider(tileProvider);

        mapView.setMultiTouchControls(true);

        if (savedInstanceState == null) {
            // TODO Should be received from server in normal activity(no TestActivity)
            IGeoPoint transformedGeoPoint = TransformationUtils.transformWebMercatorToWGS84(new GeoPoint(7023443, 5587902));
            int currentZoom = 10;
            mapView.getController().setZoom(currentZoom);
            mapView.getController().setCenter(transformedGeoPoint);
        } else {
            int mapCenterLat = savedInstanceState.getInt(MAP_CENTER_LAT_KEY);
            int mapCenterLon = savedInstanceState.getInt(MAP_CENTER_LON_KEY);
            int mapZoomLevel = savedInstanceState.getInt(MAP_ZOOM_KEY);
            mapView.getController().setCenter(new GeoPoint(mapCenterLat, mapCenterLon));
            mapView.getController().setZoom(mapZoomLevel);
        }
    }

    @Override
    public void saveInstanceState(Bundle outState) {
        IGeoPoint mapCenter = mapView.getMapCenter();
        int mapZoom = mapView.getZoomLevel();
        outState.putInt(MAP_CENTER_LAT_KEY, mapCenter.getLatitudeE6());
        outState.putInt(MAP_CENTER_LON_KEY, mapCenter.getLongitudeE6());
        outState.putInt(MAP_ZOOM_KEY, mapZoom);
    }
}
