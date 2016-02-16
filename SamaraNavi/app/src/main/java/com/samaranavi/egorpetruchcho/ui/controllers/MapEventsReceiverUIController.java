package com.samaranavi.egorpetruchcho.ui.controllers;

import org.osmdroid.util.GeoPoint;

public interface MapEventsReceiverUIController extends UIController {
    void singleTapConfirmed(GeoPoint p);
}
