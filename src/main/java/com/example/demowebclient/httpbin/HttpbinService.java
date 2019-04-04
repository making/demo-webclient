package com.example.demowebclient.httpbin;

import io.netty.channel.ChannelOption;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Component
public class HttpbinService {

    private final RestTemplate restTemplate;

    private final WebClient webClient;

    public HttpbinService(RestTemplateBuilder restTemplateBuilder, WebClient.Builder webClientBuilder) {
        this.restTemplate = restTemplateBuilder.rootUri("http://httpbin.org").build();
        this.webClient = webClientBuilder
            .clientConnector(
                new ReactorClientHttpConnector(HttpClient.create()
                    .tcpConfiguration(tcpClient -> tcpClient
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30_000))
                    //    .wiretap(true) /* debug log */
                ))
            .baseUrl("http://httpbin.org").build();
    }

    public HttpbinResponse get(String code) {
        return this.restTemplate.getForObject("/get?code={code}", HttpbinResponse.class, code);
    }

    @Async
    public Future<HttpbinResponse> getFuture(String code) {
        return new AsyncResult<>(this.get(code));
    }

    @Async
    public CompletableFuture<HttpbinResponse> getCompletableFuture(String code) {
        return CompletableFuture.completedFuture(this.get(code));
    }

    public Mono<HttpbinResponse> getMono(String code) {
        return this.webClient.get()
            .uri("/get?code={code}", code)
            .retrieve()
            .bodyToMono(HttpbinResponse.class);
    }
}
