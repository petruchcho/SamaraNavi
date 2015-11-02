package com.samaranavi.egorpetruchcho.samaranavi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.tileprovider.MapTileProviderBasic;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.tilesource.WMSTileSource;
import org.osmdroid.views.MapView;

public class TestActivityOsmdroid extends AppCompatActivity {

    private static final String URL = "https://geoportal.samregion.ru/wms13?REQUEST=GetMap&LAYERS=E3,E2,E1&WIDTH=300&HEIGHT=300&FORMAT=image/png&TRANSPARENT=true&crs=EPSG:3857&srs=EPSG:3857&version=1.3.0&BBOX=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f_map_osm);

        LinearLayout mapHolder = (LinearLayout) findViewById(R.id.my_osmdroid_mapview);

        // create a new WMS provider

        final MapTileProviderBasic tileProvider = new MapTileProviderBasic(getApplicationContext());

        // create the WMS tile source
        final ITileSource tileSource = new WMSTileSource("WMS", 1, 20, 300, ".png", new String[] {URL});
        tileProvider.setTileSource(tileSource);

        // create a new basic map view
        MapView mapView = new MapView(this, new DefaultResourceProxyImpl(this), tileProvider);

        // add the layout params to the view so the map fills the screen
        mapView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        mapView.getController().setZoom(3);

        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        // add the mapview to display
        mapHolder.addView(mapView);
    }
}
