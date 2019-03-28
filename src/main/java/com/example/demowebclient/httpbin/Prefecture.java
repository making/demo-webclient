package com.example.demowebclient.httpbin;

public class Prefecture implements Comparable<Prefecture> {

    private String code;

    @Override
    public int compareTo(Prefecture o) {
        return this.code.compareTo(o.code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
