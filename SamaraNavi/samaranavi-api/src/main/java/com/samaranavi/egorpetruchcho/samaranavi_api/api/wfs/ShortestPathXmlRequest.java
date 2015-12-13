package com.samaranavi.egorpetruchcho.samaranavi_api.api.wfs;

public class ShortestPathXmlRequest {
    private static final String REQUEST =
            "<wfs:GetFeature service=\"WFS\" version=\"2.0.0\" outputFormat=\"application/gml+xml; version=3.2\" xsi:schemaLocation=\"http://www.opengis.net/wfs/2.0 http://schemas.opengis.net/wfs/2.0.0/wfs.xsd\" xmlns:geosmr=\"http://www.geosamara.ru/wfs/geosmr/namespace\" xmlns:wfs=\"http://www.opengis.net/wfs/2.0\" xmlns:fes=\"http://www.opengis.net/fes/2.0\" xmlns:gml=\"http://www.opengis.net/gml/3.2\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" srsName=\"EPSG:4326\">\n" +
                    "  <wfs:Query typeNames=\"geosmr:ShortestPath\"  srsName=\"%s\">\n" +
                    "    <fes:Filter>\n" +
                    "      <fes:And>\n" +
                    "        <fes:And>\n" +
                    "          <fes:Within>\n" +
                    "            <fes:ValueReference>streets/startPoint</fes:ValueReference>\n" +
                    "            <fes:Literal>\n" +
                    "              <gml:Point>\n" +
                    "                <gml:pos>%s %s</gml:pos>\n" +
                    "              </gml:Point>\n" +
                    "            </fes:Literal>\n" +
                    "          </fes:Within>\n" +
                    "          <fes:Within>\n" +
                    "            <fes:ValueReference>streets/endPoint</fes:ValueReference>\n" +
                    "            <fes:Literal>\n" +
                    "              <gml:Point>\n" +
                    "                <gml:pos>%s %s</gml:pos>\n" +
                    "              </gml:Point>\n" +
                    "            </fes:Literal>\n" +
                    "          </fes:Within>\n" +
                    "        </fes:And>\n" +
                    "        <fes:PropertyIsEqualTo>\n" +
                    "          <fes:ValueReference>geosmr:ShortestPath/geosmr:transport</fes:ValueReference>\n" +
                    "          <fes:Literal>Auto</fes:Literal>\n" +
                    "        </fes:PropertyIsEqualTo>\n" +
                    "        <fes:PropertyIsEqualTo>\n" +
                    "          <fes:ValueReference>geosmr:ShortestPath/geosmr:optimalityCriterion</fes:ValueReference>\n" +
                    "          <fes:Literal>Time</fes:Literal>\n" +
                    "        </fes:PropertyIsEqualTo>\n" +
                    "      </fes:And>\n" +
                    "    </fes:Filter>\n" +
                    "  </wfs:Query>\n" +
                    "</wfs:GetFeature>";

    public static String getRequest(String srsName, int point1X, int point1Y, int point2X, int point2Y) {
        return String.format(REQUEST, srsName, point1X, point1Y, point2X, point2Y);
    }

    public static String getRequest(String srsName, double point1X, double point1Y, double point2X, double point2Y) {
        return String.format(REQUEST, srsName, point1X, point1Y, point2X, point2Y);
    }
}
