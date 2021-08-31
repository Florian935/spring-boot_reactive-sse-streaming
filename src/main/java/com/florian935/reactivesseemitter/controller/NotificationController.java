package com.florian935.reactivesseemitter.controller;

import lombok.experimental.FieldDefaults;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/api/v1.0/notifications")
@FieldDefaults(level = PRIVATE)
public class NotificationController {

    final Sinks.Many<String> multicastReplay = Sinks.many().replay().all();
    final Sinks.Many<String> multicastShare = Sinks.many().multicast().onBackpressureBuffer();
    final Sinks.Many<String> multicastShareWithLatest = Sinks.many().replay().latest();

    @GetMapping(path = "/replay/{data}")
    Flux<ServerSentEvent<String>> replayAll(@PathVariable String data) {

        multicastReplay.tryEmitNext(data);

        return multicastReplay.asFlux()
                .map(d -> ServerSentEvent.<String>builder()
                        .id(UUID.randomUUID().toString())
                        .event("periodic-event")
                        .data("SSE - " + d)
                        .build())
                .share();
    }

    @GetMapping(path = "/share/{data}")
    Flux<ServerSentEvent<String>> share(@PathVariable String data) {

        multicastShare.tryEmitNext(data);

        return multicastShare.asFlux()
                .map(d -> ServerSentEvent.<String>builder()
                        .id(UUID.randomUUID().toString())
                        .event("periodic-event")
                        .data("SSE - " + d)
                        .build())
                .share();
    }

    @GetMapping(path = "/share-with-latest/{data}")
    Flux<ServerSentEvent<String>> shareWithLatest(@PathVariable String data) {

        multicastShareWithLatest.tryEmitNext(data);

        return multicastShareWithLatest.asFlux()
                .map(d -> ServerSentEvent.<String>builder()
                        .id(UUID.randomUUID().toString())
                        .event("periodic-event")
                        .data("SSE - " + d)
                        .build())
                .share();
    }
}
