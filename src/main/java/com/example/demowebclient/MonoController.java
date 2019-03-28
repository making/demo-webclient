package com.example.demowebclient;

import com.example.demowebclient.httpbin.HttpbinResponse;
import com.example.demowebclient.httpbin.HttpbinService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class MonoController {

    private final HttpbinService httpbinService;

    public MonoController(HttpbinService httpbinService) {
        this.httpbinService = httpbinService;
    }

    @GetMapping(path = "mono")
    public Mono<Response> get() {
        return Flux.range(1, 48)
            .flatMap(i -> this.httpbinService.getMono(String.format("%02d", i)).map(HttpbinResponse::getArgs))
            .sort()
            .collectList()
            .map(Response::new);
    }
}

