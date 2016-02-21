package com.samaranavi.egorpetruchcho.ui;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samaranavi.egorpetruchcho.core.SamaraNaviFragment;
import com.samaranavi.egorpetruchcho.samaranavi.R;
import com.samaranavi.egorpetruchcho.ui.controllers.FindPathController;
import com.samaranavi.egorpetruchcho.ui.controllers.LocationController;
import com.samaranavi.egorpetruchcho.ui.controllers.MapViewController;

import org.osmdroid.bonuspack.overlays.MapEventsOverlay;
import org.osmdroid.bonuspack.overlays.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

public class MapFragment extends SamaraNaviFragment implements MapEventsReceiver {

    private MapView mapView;
    private MapEventsOverlay mapEventsOverlay;

    private LocationController locationController;
    private MapViewController mapViewController;
    private FindPathController findPathController;

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
        mapView = (MapView) view.findViewById(R.id.map_view);
        mapEventsOverlay = new MapEventsOverlay(getActivity(), this);
        mapView.getOverlays().add(0, mapEventsOverlay);

        mapViewController = new MapViewController(getActivity(), mapView);
        locationController = new LocationController(getActivity(), mapView, (FloatingActionButton) view.findViewById(R.id.navigation_button));
        findPathController = new FindPathController(getActivity(), getBackgroundManager(), mapView, view.findViewById(R.id.coordinator_view), (FloatingActionButton) view.findViewById(R.id.find_path_button));

        restoreState(savedInstanceState);
    }

    private void restoreState(Bundle savedInstanceState) {
        mapViewController.restoreState(savedInstanceState);
        locationController.restoreState(savedInstanceState);
        findPathController.restoreState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapViewController.saveInstanceState(outState);
        locationController.saveInstanceState(outState);
        findPathController.saveInstanceState(outState);
    }

    @Override
    public boolean singleTapConfirmedHelper(GeoPoint p) {
        findPathController.singleTapConfirmed(p);
        return false;
    }

    @Override
    public boolean longPressHelper(GeoPoint p) {
        return false;
    }
}
