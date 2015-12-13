package com.samaranavi.egorpetruchcho.samaranavi_api.api;

import java.io.IOException;

public interface Api {
    String getRequest(String url) throws IOException;

    String postRequest(String url, String body) throws IOException;
}
