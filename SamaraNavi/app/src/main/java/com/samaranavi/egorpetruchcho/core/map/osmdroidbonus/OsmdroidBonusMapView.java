package com.samaranavi.egorpetruchcho.core.map.osmdroidbonus;

import com.samaranavi.egorpetruchcho.core.map.MapView;
import com.samaranavi.egorpetruchcho.core.map.MapViewOverlaysFactory;

class OsmdroidBonusMapView extends MapView {

    @Override
    public MapViewOverlaysFactory getMapOverlaysFactory() {
        return new OsmdroidBonusMapViewOverlaysFactory();
    }
}
