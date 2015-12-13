package com.samaranavi.egorpetruchcho.samaranavi.task.wfs;

import com.samaranavi.egorpetruchcho.samaranavi.task.BackgroundTask;
import com.samaranavi.egorpetruchcho.samaranavi_api.api.wfs.ShortestPathResult;
import com.samaranavi.egorpetruchcho.samaranavi_api.api.wfs.WfsApi;

import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

public class GetShortestPathTask extends BackgroundTask<ShortestPathResult> {

    private String srsName;
    private double point1X;
    private double point1Y;
    private double point2X;
    private double point2Y;

    public GetShortestPathTask(String srsName, double point1X, double point1Y, double point2X, double point2Y) {
        this();
        this.srsName = srsName;
        this.point1X = point1X;
        this.point1Y = point1Y;
        this.point2X = point2X;
        this.point2Y = point2Y;
    }

    public GetShortestPathTask() {
        super(ShortestPathResult.class);
    }

    @Override
    protected ShortestPathResult execute() throws ParserConfigurationException, SAXException, IOException {
        return WfsApi.getInstance().getShortestPath(srsName, point1X, point1Y, point2X, point2Y);
    }

}
