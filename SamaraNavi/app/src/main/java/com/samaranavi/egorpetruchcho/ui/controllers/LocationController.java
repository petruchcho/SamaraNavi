package com.samaranavi.egorpetruchcho.ui.controllers;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.samaranavi.egorpetruchcho.samaranavi.R;
import com.samaranavi.egorpetruchcho.ui.CustomResourceProxy;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class LocationController implements UIController {
    private final static String MAP_LOCATION_STATE = "MAP_LOCATION_STATE";

    private final Context context;
    private final MapView mapView;
    private final FloatingActionButton locationButton;

    private MyLocationNewOverlay locationOverlay;

    private LocationState currentLocationState = LocationState.DISABLED;

    public LocationController(Context context, MapView mapView, FloatingActionButton locationButton) {
        this.context = context;
        this.mapView = mapView;
        this.locationButton = locationButton;
    }

    @Override
    public void restoreState(Bundle savedInstanceState) {
        setUpLocationOverlay(savedInstanceState);

        if (savedInstanceState == null) {
            currentLocationState = LocationState.DISABLED;
        } else {
            currentLocationState = LocationState.valueOf(savedInstanceState.getString(MAP_LOCATION_STATE, LocationState.DISABLED.name()));
        }
        updateNavigationButton();
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentLocationState = currentLocationState.getNextState();
                updateNavigationButton();
            }
        });
    }

    private void setUpLocationOverlay(Bundle savedInstanceState) {
        GpsMyLocationProvider myLocationProvider = new GpsMyLocationProvider(context);
        locationOverlay = new MyLocationNewOverlay(myLocationProvider, mapView, new CustomResourceProxy(context));
        mapView.getOverlays().add(locationOverlay);
    }

    private void updateNavigationButton() {
        locationButton.setImageResource(currentLocationState.icon);
        switch (currentLocationState) {
            case SHOW_MY_LOCATION: {
                locationOverlay.enableMyLocation();
                break;
            }
            case FOLLOW_ME: {
                locationOverlay.enableMyLocation();
                locationOverlay.enableFollowLocation();
                break;
            }
            default: {
                locationOverlay.disableMyLocation();
                locationOverlay.disableFollowLocation();
            }
        }
    }

    @Override
    public void saveInstanceState(Bundle outState) {
        outState.putString(MAP_LOCATION_STATE, currentLocationState.name());
    }

    private enum LocationState {
        DISABLED(R.drawable.icon_location_disabled),
        SHOW_MY_LOCATION(R.drawable.icon_location_enabled),
        FOLLOW_ME(R.drawable.icon_navigation);

        final
        @DrawableRes
        int icon;

        LocationState(int icon) {
            this.icon = icon;
        }

        public LocationState getNextState() {
            switch (this) {
                case DISABLED:
                    return SHOW_MY_LOCATION;
                case SHOW_MY_LOCATION:
                    return FOLLOW_ME;
                default:
                    return DISABLED;
            }
        }
    }
}
