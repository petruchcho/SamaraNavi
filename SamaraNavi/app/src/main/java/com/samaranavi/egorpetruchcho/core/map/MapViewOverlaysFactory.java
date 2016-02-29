package com.samaranavi.egorpetruchcho.core.map;

public interface MapViewOverlaysFactory<TMarker> {
    MarkerOverlay<TMarker> createMarker(MapView mapView);
}
