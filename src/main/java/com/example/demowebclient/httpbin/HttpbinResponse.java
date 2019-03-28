package com.example.demowebclient.httpbin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

// http://httpbin.org/get?code=1
public class HttpbinResponse {

    private final Prefecture args;

    @JsonCreator
    public HttpbinResponse(@JsonProperty("args") Prefecture args) {
        this.args = args;
    }

    public Prefecture getArgs() {
        return args;
    }
}
