package com.samaranavi.egorpetruchcho.samaranavi_api.api;

import com.squareup.okhttp.MediaType;

import java.io.IOException;

public abstract class XmlApi implements Api {

    protected ApiClient apiClient = new OkHttpApiClient();

    @Override
    public String getRequest(String url) throws IOException {
        return apiClient.getRequest(url);
    }

    @Override
    public String postRequest(String url, String body) throws IOException {
        return apiClient.postRequest(url, body, MediaType.parse("text/xml; charset=utf-8"));
    }
}
