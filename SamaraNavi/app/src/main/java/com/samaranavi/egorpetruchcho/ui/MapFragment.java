package com.samaranavi.egorpetruchcho.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samaranavi.egorpetruchcho.core.SamaraNaviFragment;
import com.samaranavi.egorpetruchcho.samaranavi.R;
import com.samaranavi.egorpetruchcho.ui.controllers.LocationController;
import com.samaranavi.egorpetruchcho.ui.controllers.MapViewController;

import org.osmdroid.views.MapView;

public class MapFragment extends SamaraNaviFragment {

    private MapView mapView;

    private LocationController locationController;
    private MapViewController mapViewController;

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

        mapViewController = new MapViewController(getActivity(), mapView);
        locationController = new LocationController(getActivity(), mapView, (FloatingActionButton) view.findViewById(R.id.navigation_button));

        restoreState(savedInstanceState);
    }

    private void restoreState(Bundle savedInstanceState) {
        mapViewController.restoreState(savedInstanceState);
        locationController.restoreState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapViewController.saveInstanceState(outState);
        locationController.saveInstanceState(outState);
    }
}
