package com.samaranavi.egorpetruchcho.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samaranavi.egorpetruchcho.core.SamaraNaviFragment;
import com.samaranavi.egorpetruchcho.samaranavi.R;
import com.samaranavi.egorpetruchcho.utils.GeoportalTileSource;
import com.samaranavi.egorpetruchcho.utils.TransformationUtils;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.tileprovider.MapTileProviderBasic;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

public class MapFragment extends SamaraNaviFragment {

    private final static String MAP_CENTER_LAT_KEY = "MAP_CENTER_LAT_KEY";
    private final static String MAP_CENTER_LON_KEY = "MAP_CENTER_LON_KEY";
    private final static String MAP_ZOOM_KEY = "MAP_ZOOM_KEY";

    private MapView mapView;

    public MapFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_map, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        configureMapView(view, savedInstanceState);
    }

    private void configureMapView(View view, Bundle savedInstanceState) {
        final MapTileProviderBasic tileProvider = new MapTileProviderBasic(getContext());
        final ITileSource tileSource = new GeoportalTileSource(1, 20);
        tileProvider.setTileSource(tileSource);

        mapView = (MapView) view.findViewById(R.id.map_view);
        mapView.setTileProvider(tileProvider);

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

        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        IGeoPoint mapCenter = mapView.getMapCenter();
        int mapZoom = mapView.getZoomLevel();
        outState.putInt(MAP_CENTER_LAT_KEY, mapCenter.getLatitudeE6());
        outState.putInt(MAP_CENTER_LON_KEY, mapCenter.getLongitudeE6());
        outState.putInt(MAP_ZOOM_KEY, mapZoom);
    }
}
