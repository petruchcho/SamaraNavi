package com.samaranavi.egorpetruchcho.ui.controllers;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.samaranavi.egorpetruchcho.samaranavi.R;

import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

public class FindPathController implements MapEventsReceiverUIController {

    private static final String MAP_FIND_PATH_MODE = "MAP_FIND_PATH_MODE";
    private static final String MAP_MARKER_A_LAT = "MAP_MARKER_A_LAT";
    private static final String MAP_MARKER_A_LON = "MAP_MARKER_A_LON";
    private static final String MAP_MARKER_B_LAT = "MAP_MARKER_B_LAT";
    private static final String MAP_MARKER_B_LON = "MAP_MARKER_B_LON";

    private final Context context;
    private final MapView mapView;
    private final View buttonContainer;
    private final FloatingActionButton findPathButton;

    private Snackbar snackbar;

    private Marker markerA;
    private Marker markerB;

    private boolean modeTurnedOn = false;

    public FindPathController(Context context, MapView mapView, View buttonContainer, FloatingActionButton findPathButton) {
        this.context = context;
        this.mapView = mapView;
        this.buttonContainer = buttonContainer;
        this.findPathButton = findPathButton;
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
        snackbar.getView().addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                findPathButton.setTranslationY(0);
            }
        });
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
            showSnackBar();
            showMarkers();
        } else {
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

    private static Marker makeMarker(MapView mapView, GeoPoint p) {
        Marker marker = new Marker(mapView);
        marker.setPosition(p);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        return marker;
    }
}
