package com.samaranavi.egorpetruchcho.ui.controllers;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.samaranavi.egorpetruchcho.samaranavi.R;
import com.samaranavi.egorpetruchcho.samaranavi_api.api.wfs.ShortestPathResult;
import com.samaranavi.egorpetruchcho.task.wfs.GetShortestPathTask;

import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.bonuspack.overlays.Polyline;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.PathOverlay;

import java.util.ArrayList;
import java.util.List;

public class FindPathController implements MapEventsReceiverUIController {

    private static final String MAP_FIND_PATH_MODE = "MAP_FIND_PATH_MODE";
    private static final String MAP_MARKER_A_LAT = "MAP_MARKER_A_LAT";
    private static final String MAP_MARKER_A_LON = "MAP_MARKER_A_LON";
    private static final String MAP_MARKER_B_LAT = "MAP_MARKER_B_LAT";
    private static final String MAP_MARKER_B_LON = "MAP_MARKER_B_LON";

    private final Context context;
    private final SpiceManager backgroundManager;
    private final MapView mapView;
    private final View buttonContainer;
    private final FloatingActionButton findPathButton;

    private Snackbar snackbar;

    private Marker markerA;
    private Marker markerB;

    private boolean modeTurnedOn = false;

    public FindPathController(Context context, SpiceManager backgroundManager, MapView mapView, View buttonContainer, FloatingActionButton findPathButton) {
        this.context = context;
        this.backgroundManager = backgroundManager;
        this.mapView = mapView;
        this.buttonContainer = buttonContainer;
        this.findPathButton = findPathButton;
    }

    private static Marker makeMarker(MapView mapView, GeoPoint p) {
        Marker marker = new Marker(mapView);
        marker.setPosition(p);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        return marker;
    }

    @Override
    public void restoreState(Bundle savedInstanceState) {
        modeTurnedOn = savedInstanceState != null && savedInstanceState.getBoolean(MAP_FIND_PATH_MODE, false);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(MAP_MARKER_A_LAT)) {
                GeoPoint p = new GeoPoint(savedInstanceState.getInt(MAP_MARKER_A_LAT), savedInstanceState.getInt(MAP_MARKER_A_LON));
                markerA = makeMarker(mapView, p);
            }
            if (savedInstanceState.containsKey(MAP_MARKER_B_LAT)) {
                GeoPoint p = new GeoPoint(savedInstanceState.getInt(MAP_MARKER_B_LAT), savedInstanceState.getInt(MAP_MARKER_B_LON));
                markerB = makeMarker(mapView, p);
            }
        }

        handleMode();
        findPathButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (modeTurnedOn) {
                    findPath();
                } else {
                    modeTurnedOn = true;
                    cleanMarkers();
                    cleanPreviousPath();
                    handleMode();
                }
            }
        });
    }

    private void cleanPreviousPath() {

    }

    private void showSnackBar() {
        snackbar = Snackbar.make(buttonContainer, R.string.pick_places_to_find_path_snackbar, Snackbar.LENGTH_INDEFINITE);
        TextView tv = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        if (tv != null) {
            tv.setMaxLines(3);
        }
        snackbar.setAction(R.string.close, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modeTurnedOn = false;
                handleMode();
            }
        });
        snackbar.show();
    }

    private void handleMode() {
        if (modeTurnedOn) {
            findPathButton.setImageResource(R.drawable.icon_search);
            showSnackBar();
            showMarkers();
        } else {
            findPathButton.setImageResource(R.drawable.icon_roots);
            cleanMarkers();
            hideSnackBar();
        }
    }

    private void showMarkers() {
        if (markerA != null) {
            mapView.getOverlays().remove(markerA);
        }
        if (markerB != null) {
            mapView.getOverlays().remove(markerB);
        }
        if (markerA != null) {
            mapView.getOverlays().add(markerA);
        }
        if (markerB != null) {
            mapView.getOverlays().add(markerB);
        }
        mapView.invalidate();
    }

    private void cleanMarkers() {
        if (markerA != null) {
            mapView.getOverlays().remove(markerA);
        }
        if (markerB != null) {
            mapView.getOverlays().remove(markerB);
        }
        markerA = null;
        markerB = null;
        mapView.invalidate();
    }

    private void hideSnackBar() {
        if (snackbar != null) {
            snackbar.dismiss();
        }
    }

    private void findPath() {
        if (markerA == null || markerB == null) {
            return;
        }
        backgroundManager.execute(new GetShortestPathTask("EPSG:4326", markerA.getPosition().getLatitude(), markerA.getPosition().getLongitude(),
                markerB.getPosition().getLatitude(), markerB.getPosition().getLongitude()), new RequestListener<ShortestPathResult>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
                Toast.makeText(context, "Error :(", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRequestSuccess(ShortestPathResult shortestPathResult) {
                //showPathAsPolyline(shortestPathResult.posList);
                showPathAsPathOverlay(shortestPathResult.posList);
            }
        });
    }

    private void showPathAsPolyline(List<Double> posList) {
        cleanPreviousPath();

        List<GeoPoint> geoPoints = new ArrayList<>();
        for (int i = 0; i < posList.size(); i += 2) {
            geoPoints.add(new GeoPoint(posList.get(i), posList.get(i + 1)));
        }
        Polyline path = new Polyline(context);
        path.setPoints(geoPoints);
        path.setColor(Color.RED);

        mapView.getOverlays().add(0, path);
        mapView.invalidate();
    }

    private void showPathAsPathOverlay(List<Double> points) {
        PathOverlay myPath = new PathOverlay(Color.RED, context);
        myPath.getPaint().setStrokeWidth(8);

        for (int i = 0; i < points.size(); i += 2) {
            myPath.addPoint(new GeoPoint(points.get(i), points.get(i + 1)));
        }

        mapView.getOverlays().add(0, myPath);
        mapView.invalidate();
    }

    @Override
    public void saveInstanceState(Bundle outState) {
        outState.putBoolean(MAP_FIND_PATH_MODE, modeTurnedOn);
        if (markerA != null) {
            outState.putInt(MAP_MARKER_A_LAT, markerA.getPosition().getLatitudeE6());
            outState.putInt(MAP_MARKER_A_LON, markerA.getPosition().getLongitudeE6());
        }
        if (markerB != null) {
            outState.putInt(MAP_MARKER_B_LAT, markerB.getPosition().getLatitudeE6());
            outState.putInt(MAP_MARKER_B_LON, markerB.getPosition().getLongitudeE6());
        }
    }

    @Override
    public void singleTapConfirmed(GeoPoint p) {
        if (!modeTurnedOn) {
            return;
        }
        if (markerA == null) {
            markerA = makeMarker(mapView, p);
            mapView.getOverlays().add(markerA);
            mapView.invalidate();
        } else if (markerB == null) {
            markerB = makeMarker(mapView, p);
            mapView.getOverlays().add(markerB);
            mapView.invalidate();
        } else {
            cleanMarkers();
            singleTapConfirmed(p);
        }
    }
}
