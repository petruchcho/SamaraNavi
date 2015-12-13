package com.samaranavi.egorpetruchcho.samaranavi_api.api;

import com.squareup.okhttp.MediaType;

import java.io.IOException;

public interface ApiClient {

    String getRequest(String url) throws IOException;
    String postRequest(String url, String body, MediaType mediaType) throws IOException;

}
