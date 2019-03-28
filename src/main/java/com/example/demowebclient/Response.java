package com.example.demowebclient;

import com.example.demowebclient.httpbin.Prefecture;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

public class Response {

    private final List<Prefecture> prefectures;

    @JsonCreator
    public Response(@JsonProperty("prefectures") List<Prefecture> prefectures) {
        this.prefectures = Collections.unmodifiableList(prefectures);
    }

    public List<Prefecture> getPrefectures() {
        return prefectures;
    }
}
