package com.florian935.reactivesseemitter.controller;

import com.florian935.reactivesseemitter.service.NotificationService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class NotificationController {

    NotificationService notificationService;

    @GetMapping(path = "/replay/{data}")
    Flux<ServerSentEvent<String>> replayAll(@PathVariable String data) {

        return notificationService.replayAll(data);
    }

    @GetMapping(path = "/share/{data}")
    Flux<ServerSentEvent<String>> share(@PathVariable String data) {

        return notificationService.share(data);
    }

    @GetMapping(path = "/share-with-latest/{data}")
    Flux<ServerSentEvent<String>> shareWithLatestEmittedValue(@PathVariable String data) {

        return notificationService.shareWithLatestEmittedValue(data);
    }
}
