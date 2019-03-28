package com.example.demowebclient;

import com.example.demowebclient.httpbin.HttpbinResponse;
import com.example.demowebclient.httpbin.HttpbinService;
import com.example.demowebclient.httpbin.Prefecture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SyncController {

    private final HttpbinService httpbinService;

    public SyncController(HttpbinService httpbinService) {
        this.httpbinService = httpbinService;
    }

    @GetMapping(path = "sync")
    public Response get() {
        List<Prefecture> prefectures = new ArrayList<>();
        for (int i = 1; i <= 48; i++) {
            HttpbinResponse httpbinResponse = this.httpbinService.get(String.format("%02d", i));
            prefectures.add(httpbinResponse.getArgs());
        }
        return new Response(prefectures);
    }
}
