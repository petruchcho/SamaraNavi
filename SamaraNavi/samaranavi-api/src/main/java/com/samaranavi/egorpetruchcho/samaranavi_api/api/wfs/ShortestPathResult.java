package com.samaranavi.egorpetruchcho.samaranavi_api.api.wfs;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ShortestPathResult {

    private String[] description;
    public List<Double> posList;

    public ShortestPathResult(String data) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(data));
        Document document = builder.parse(is);

        NodeList posListElement = document.getElementsByTagName("posList");
        String stringPointArray = posListElement.item(0).getFirstChild().getTextContent();

        posList = new ArrayList<>();
        for (String point : stringPointArray.split(" ")) {
            posList.add(Double.parseDouble(point));
        }
    }
}
