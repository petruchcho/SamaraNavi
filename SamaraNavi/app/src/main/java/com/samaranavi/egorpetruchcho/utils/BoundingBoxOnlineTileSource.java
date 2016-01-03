package com.samaranavi.egorpetruchcho.utils;

import android.util.Log;

import org.osmdroid.tileprovider.MapTile;
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;

public abstract class BoundingBoxOnlineTileSource extends OnlineTileSourceBase {

    public BoundingBoxOnlineTileSource(final String aName,
                                       final int aZoomMinLevel, final int aZoomMaxLevel, final int aTileSizePixels,
                                       final String aImageFilenameEnding, final String[] aBaseUrl) {
        super(aName, aZoomMinLevel, aZoomMaxLevel, aTileSizePixels,
                aImageFilenameEnding, aBaseUrl);
    }

    @Override
    public final String getTileURLString(MapTile tile) {
        String urlString = buildTileURLString(tile);
        Log.d(name(), "--------------- link = " + urlString);
        return urlString;
    }

    protected abstract String buildTileURLString(MapTile tile);

    protected abstract BoundingBox tile2boundingBox(MapTile tile);

    protected abstract String boundingBox2string(BoundingBox boundingBox);

    public static class BoundingBox {
        private final double north;
        private final double south;
        private final double west;
        private final double east;

        public BoundingBox(double north, double south, double west, double east) {
            this.north = north;
            this.south = south;
            this.west = west;
            this.east = east;
        }

        public double getNorth() {
            return north;
        }

        public double getSouth() {
            return south;
        }

        public double getEast() {
            return east;
        }

        public double getWest() {
            return west;
        }
    }
}
