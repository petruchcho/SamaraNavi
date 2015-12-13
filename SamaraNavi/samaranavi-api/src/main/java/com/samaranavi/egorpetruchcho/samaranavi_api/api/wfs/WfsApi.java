package com.samaranavi.egorpetruchcho.samaranavi_api.api.wfs;

import com.samaranavi.egorpetruchcho.samaranavi_api.api.Config;
import com.samaranavi.egorpetruchcho.samaranavi_api.api.XmlApi;

import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;


public class WfsApi extends XmlApi {

    private static WfsApi instance;

    private WfsApi() {
    }

    public static WfsApi getInstance() {
        if (instance == null) {
            instance = new WfsApi();
        }
        return instance;
    }

    public ShortestPathResult getShortestPath(String srsName, int point1X, int point1Y, int point2X, int point2Y) throws IOException, ParserConfigurationException, SAXException {
        String body = ShortestPathXmlRequest.getRequest(srsName, point1X, point1Y, point2X, point2Y);
        String response = postRequest(body);
        return new ShortestPathResult(response);
    }

    public ShortestPathResult getShortestPath(String srsName, double point1X, double point1Y, double point2X, double point2Y) throws IOException, ParserConfigurationException, SAXException {
        String body = ShortestPathXmlRequest.getRequest(srsName, point1X, point1Y, point2X, point2Y);
        String response = postRequest(body);
        return new ShortestPathResult(response);
    }

    protected String postRequest(String body) throws IOException {
        return postRequest(Config.URL_WFS_SERVER, body);
    }
}
