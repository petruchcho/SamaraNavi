package com.samaranavi.egorpetruchcho.core.map.osmdroidbonus;

import com.samaranavi.egorpetruchcho.core.geo.GeoPoint;
import com.samaranavi.egorpetruchcho.core.map.MapView;
import com.samaranavi.egorpetruchcho.core.map.MapViewOverlaysFactory;
import com.samaranavi.egorpetruchcho.core.map.MarkerOverlay;

import org.osmdroid.bonuspack.overlays.Marker;

public class OsmdroidBonusMapViewOverlaysFactory implements MapViewOverlaysFactory<Marker> {

    @Override
    public MarkerOverlay<Marker> createMarker(MapView mapView) {
        return new MarkerOverlay<Marker>(new Marker((org.osmdroid.views.MapView) mapView.getMapViewDelegate())) {
            @Override
            public GeoPoint getPosition() {
                return OsmdroidBonusGeoPointConverter.getInstance().convertFromCustom(getDelegate().getPosition());
            }

            @Override
            public void setPosition(GeoPoint geoPoint) {
                getDelegate().setPosition(OsmdroidBonusGeoPointConverter.getInstance().convertFromCommon(geoPoint));
            }
        };
    }
}
