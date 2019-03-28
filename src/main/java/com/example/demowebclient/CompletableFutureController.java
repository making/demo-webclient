package com.example.demowebclient;

import com.example.demowebclient.httpbin.HttpbinResponse;
import com.example.demowebclient.httpbin.HttpbinService;
import com.example.demowebclient.httpbin.Prefecture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class CompletableFutureController {

    private final HttpbinService httpbinService;

    public CompletableFutureController(HttpbinService httpbinService) {
        this.httpbinService = httpbinService;
    }

    @GetMapping(path = "/completablefuture")
    public CompletableFuture<Response> get() {
        List<CompletableFuture<Prefecture>> futures = new ArrayList<>();
        for (int i = 1; i <= 48; i++) {
            CompletableFuture<HttpbinResponse> httpbinResponse = this.httpbinService.getCompletableFuture(String.format("%02d", i));
            futures.add(httpbinResponse.thenApply(HttpbinResponse::getArgs));
        }
        return futures.stream()
            .map(f -> f.thenApply(prefecture -> {
                List<Prefecture> prefectures = new ArrayList<>();
                prefectures.add(prefecture);
                return prefectures;
            }))
            .reduce((f1, f2) -> f1.thenCombine(f2, (l1, l2) -> {
                l1.addAll(l2);
                l2.clear();
                return l1;
            }))
            .map(f -> f.thenApply(Response::new))
            .orElseGet(() -> CompletableFuture.completedFuture(new Response(Collections.emptyList())));
    }
}
