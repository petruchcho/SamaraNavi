package com.samaranavi.egorpetruchcho.utils;

import org.osmdroid.tileprovider.MapTile;

public class GeoportalTileSource extends BoundingBoxOnlineTileSource {

    private static final String NAME = "GeoportalTileSource";
    private static final String URL = "https://geoportal.samregion.ru/wms13";
    private static final int TILE_SIZE_PX = 300;
    private static final String IMAGE_FILE_FORMAT = "png";

    public GeoportalTileSource(int aZoomMinLevel, int aZoomMaxLevel) {
        this(NAME, aZoomMinLevel, aZoomMaxLevel, TILE_SIZE_PX, "." + IMAGE_FILE_FORMAT, new String[]{URL});
    }

    protected GeoportalTileSource(String aName, int aZoomMinLevel, int aZoomMaxLevel, int aTileSizePixels, String aImageFilenameEnding, String[] aBaseUrl) {
        super(aName, aZoomMinLevel, aZoomMaxLevel, aTileSizePixels, aImageFilenameEnding, aBaseUrl);
    }

    @Override
    protected String buildTileURLString(MapTile tile) {
        return URL + configureParameters() + boundingBox2string(tile2boundingBox(tile));
    }

    @Override
    protected BoundingBox tile2boundingBox(MapTile tile) {
        return new BoundingBox(
                TransformationUtils.transformYtoWebMercator(tile.getY(), tile.getZoomLevel()),
                TransformationUtils.transformYtoWebMercator(tile.getY() + 1, tile.getZoomLevel()),
                TransformationUtils.transformXtoWebMercator(tile.getX(), tile.getZoomLevel()),
                TransformationUtils.transformXtoWebMercator(tile.getX() + 1, tile.getZoomLevel()));
    }

    @Override
    protected String boundingBox2string(BoundingBox boundingBox) {
        return String.format("%s,%s,%s,%s",
                coordinateToString(boundingBox.getWest()),
                coordinateToString(boundingBox.getSouth()),
                coordinateToString(boundingBox.getEast()),
                coordinateToString(boundingBox.getNorth()));
    }

    private String coordinateToString(double coordinate) {
        return String.valueOf((int) Math.round(coordinate));
    }

    private String configureParameters() {
        return "?REQUEST=GetMap&LAYERS=E3,E2,E1&WIDTH=" + TILE_SIZE_PX +
                "&HEIGHT=" + TILE_SIZE_PX +
                "&FORMAT=image/" + IMAGE_FILE_FORMAT +
                "&TRANSPARENT=true&crs=EPSG:3857&srs=EPSG:3857&version=1.3.0&BBOX=";
    }
}
