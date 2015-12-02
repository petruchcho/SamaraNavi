package com.samaranavi.egorpetruchcho.samaranavi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.samaranavi.egorpetruchcho.samaranavi.utils.TransformationUtils;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.tileprovider.MapTileProviderBasic;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.tilesource.WMSTileSource;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class TestActivityOsmdroid extends AppCompatActivity {

    private static final String URL = "https://geoportal.samregion.ru/wms13?REQUEST=GetMap&LAYERS=E3,E2,E1&WIDTH=300&HEIGHT=300&FORMAT=image/png&TRANSPARENT=true&crs=EPSG:3857&srs=EPSG:3857&version=1.3.0&BBOX=";
    private MapView mapView;
    private MyLocationNewOverlay myLocationNewOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f_map_osm);

        LinearLayout mapHolder = (LinearLayout) findViewById(R.id.my_osmdroid_mapview);

        // create a new WMS provider

        final MapTileProviderBasic tileProvider = new MapTileProviderBasic(getApplicationContext());

        // create the WMS tile source
        final ITileSource tileSource = new WMSTileSource("WMS", 1, 20, 300, ".png", new String[]{URL});
        tileProvider.setTileSource(tileSource);

        // create a new basic map view
        mapView = new MapView(this, new DefaultResourceProxyImpl(this), tileProvider);

        // add the layout params to the view so the map fills the screen
        mapView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // Should be received from server in normal activity(no TestActivity)
        IGeoPoint transformedGeoPoint = TransformationUtils.transformWebMercatorToWGS84(new GeoPoint(7023443, 5587902));
        int currentZoom = 10;
        mapView.getController().setZoom(currentZoom);
        mapView.getController().setCenter(transformedGeoPoint);

        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        // Add MyLocationMarker
        GpsMyLocationProvider myLocationProvider = new GpsMyLocationProvider(this);
        myLocationNewOverlay = new MyLocationNewOverlay(this, myLocationProvider, mapView);
        mapView.getOverlays().add(myLocationNewOverlay);

        // add the mapview to display
        mapHolder.addView(mapView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        myLocationNewOverlay.enableMyLocation();
        myLocationNewOverlay.enableFollowLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        myLocationNewOverlay.disableMyLocation();
        myLocationNewOverlay.disableFollowLocation();
    }
}
