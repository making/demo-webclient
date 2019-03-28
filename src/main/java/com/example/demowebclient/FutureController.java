package com.example.demowebclient;

import com.example.demowebclient.httpbin.HttpbinResponse;
import com.example.demowebclient.httpbin.HttpbinService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@RestController
public class FutureController {

    private final HttpbinService httpbinService;

    public FutureController(HttpbinService httpbinService) {
        this.httpbinService = httpbinService;
    }

    @GetMapping(path = "/future")
    public Response get() {
        List<Future<HttpbinResponse>> futures = new ArrayList<>();
        for (int i = 1; i <= 48; i++) {
            Future<HttpbinResponse> httpbinResponse = this.httpbinService.getFuture(String.format("%02d", i));
            futures.add(httpbinResponse);
        }
        return new Response(futures.stream().map(f -> {
            try {
                return f.get().getArgs();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList()));
    }

}
