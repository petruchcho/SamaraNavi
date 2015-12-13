package com.samaranavi.egorpetruchcho.samaranavi_api.api;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public final class OkHttpApiClient implements ApiClient {

    private final OkHttpClient okHttpClient;

    public OkHttpApiClient() {
        okHttpClient = new OkHttpClient();
    }

    @Override
    public String getRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }

    @Override
    public String postRequest(String url, String body, MediaType mediaType) throws IOException {
        RequestBody requestBody = RequestBody.create(mediaType, body);
        Request request = new Request.Builder()
                .post(requestBody)
                .url(url)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }
}
