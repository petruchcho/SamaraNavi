package com.samaranavi.egorpetruchcho.samaranavi_api.parser;

public interface Parser<T> {
    T parse(String data);
}
