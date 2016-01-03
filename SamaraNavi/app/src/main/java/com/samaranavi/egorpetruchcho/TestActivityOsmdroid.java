package com.samaranavi.egorpetruchcho;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.samaranavi.egorpetruchcho.samaranavi.R;
import com.samaranavi.egorpetruchcho.core.SamaraNaviActivity;
import com.samaranavi.egorpetruchcho.task.wfs.GetShortestPathTask;
import com.samaranavi.egorpetruchcho.utils.TransformationUtils;
import com.samaranavi.egorpetruchcho.samaranavi_api.api.wfs.ShortestPathResult;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.tileprovider.MapTileProviderBasic;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.tilesource.WMSTileSource;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.PathOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.List;

public class TestActivityOsmdroid extends SamaraNaviActivity {

    private static final String URL = "https://geoportal.samregion.ru/wms13?REQUEST=GetMap&LAYERS=E3,E2,E1&WIDTH=300&HEIGHT=300&FORMAT=image/png&TRANSPARENT=true&crs=EPSG:3857&srs=EPSG:3857&version=1.3.0&BBOX=";
    private MapView mapView;
    private MyLocationNewOverlay myLocationNewOverlay;

    private boolean touchOverlayAIsShowed = false;
    private IGeoPoint pointA;
    private IGeoPoint pointB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f_map_osm);

        ViewGroup mapHolder = (ViewGroup) findViewById(R.id.my_osmdroid_mapview);

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

        configureTwoPoints();

        // add the mapview to display
        mapHolder.addView(mapView);

        Button sendRequestButton = (Button) findViewById(R.id.send_request_button);
        sendRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pointA == null || pointB == null) {
                    return;
                }
                getBackgroundManager().execute(new GetShortestPathTask("EPSG:4326", pointA.getLatitude(), pointA.getLongitude(),
                        pointB.getLatitude(), pointB.getLongitude()), new RequestListener<ShortestPathResult>() {
                    @Override
                    public void onRequestFailure(SpiceException spiceException) {
                        Toast.makeText(TestActivityOsmdroid.this, "Error :(", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onRequestSuccess(ShortestPathResult shortestPathResult) {
//                        Toast.makeText(TestActivityOsmdroid.this,
//                                shortestPathResult.posList.get(0) + " " +
//                                        shortestPathResult.posList.get(1),
//                                Toast.LENGTH_LONG).show();
                        addPathOverlay(shortestPathResult.posList);
                    }
                });
            }
        });
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

    private void configureTwoPoints() {
        final Overlay touchOverlayA = new Overlay(this) {
            ItemizedIconOverlay<OverlayItem> anotherItemizedIconOverlay = null;

            @Override
            protected void draw(Canvas arg0, MapView arg1, boolean arg2) {

            }

            @Override
            public boolean onSingleTapConfirmed(final MotionEvent e, final MapView mapView) {
                if (touchOverlayAIsShowed) {
                    return false;
                }
                Projection proj = mapView.getProjection();
                touchOverlayAIsShowed = true;
                GeoPoint loc = (GeoPoint) proj.fromPixels((int) e.getX(), (int) e.getY());
                String longitude = Double.toString(((double) loc.getLongitudeE6()) / 1000000);
                String latitude = Double.toString(((double) loc.getLatitudeE6()) / 1000000);
                ArrayList<OverlayItem> overlayArray = new ArrayList<OverlayItem>();
                pointA = new GeoPoint((((double) loc.getLatitudeE6()) / 1000000), (((double) loc.getLongitudeE6()) / 1000000));
                OverlayItem mapItem = new OverlayItem("", "", pointA);
                //mapItem.setMarker(marker);
                overlayArray.add(mapItem);
                if (anotherItemizedIconOverlay == null) {
                    anotherItemizedIconOverlay = new ItemizedIconOverlay<OverlayItem>(getApplicationContext(), overlayArray, null);
                    mapView.getOverlays().add(anotherItemizedIconOverlay);
                    mapView.invalidate();
                } else {
                    mapView.getOverlays().remove(anotherItemizedIconOverlay);
                    mapView.invalidate();
                    anotherItemizedIconOverlay = new ItemizedIconOverlay<OverlayItem>(getApplicationContext(), overlayArray, null);
                    mapView.getOverlays().add(anotherItemizedIconOverlay);
                }
                // dlgThread();
                return true;
            }
        };
        mapView.getOverlays().add(touchOverlayA);

        Overlay touchOverlayB = new Overlay(this) {
            ItemizedIconOverlay<OverlayItem> anotherItemizedIconOverlay = null;

            @Override
            protected void draw(Canvas arg0, MapView arg1, boolean arg2) {

            }

            @Override
            public boolean onSingleTapConfirmed(final MotionEvent e, final MapView mapView) {
                if (!touchOverlayAIsShowed) {
                    return false;
                }
                Projection proj = mapView.getProjection();
                GeoPoint loc = (GeoPoint) proj.fromPixels((int) e.getX(), (int) e.getY());
                String longitude = Double.toString(((double) loc.getLongitudeE6()) / 1000000);
                String latitude = Double.toString(((double) loc.getLatitudeE6()) / 1000000);
                ArrayList<OverlayItem> overlayArray = new ArrayList<OverlayItem>();
                pointB = new GeoPoint((((double) loc.getLatitudeE6()) / 1000000), (((double) loc.getLongitudeE6()) / 1000000));
                OverlayItem mapItem = new OverlayItem("", "", pointB);
                //mapItem.setMarker(marker);
                overlayArray.add(mapItem);
                if (anotherItemizedIconOverlay == null) {
                    anotherItemizedIconOverlay = new ItemizedIconOverlay<OverlayItem>(getApplicationContext(), overlayArray, null);
                    mapView.getOverlays().add(anotherItemizedIconOverlay);
                    mapView.invalidate();
                } else {
                    mapView.getOverlays().remove(anotherItemizedIconOverlay);
                    mapView.invalidate();
                    anotherItemizedIconOverlay = new ItemizedIconOverlay<OverlayItem>(getApplicationContext(), overlayArray, null);
                    mapView.getOverlays().add(anotherItemizedIconOverlay);
                }
                // dlgThread();
                return true;
            }
        };
        mapView.getOverlays().add(touchOverlayB);
    }

    private void addPathOverlay(List<Double> points) {
        PathOverlay myPath = new PathOverlay(Color.RED, this);

        for (int i = 0; i < points.size(); i += 2) {
            myPath.addPoint(new GeoPoint(points.get(i), points.get(i + 1)));
        }

        mapView.getOverlays().add(myPath);
    }
}
