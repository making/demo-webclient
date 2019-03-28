package com.example.demowebclient.httpbin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Prefecture implements Comparable<Prefecture> {

    private final String code;

    @JsonCreator
    public Prefecture(@JsonProperty("code") String code) {
        this.code = code;
    }

    @Override
    public int compareTo(Prefecture o) {
        return this.code.compareTo(o.code);
    }

    public String getCode() {
        return code;
    }
}
