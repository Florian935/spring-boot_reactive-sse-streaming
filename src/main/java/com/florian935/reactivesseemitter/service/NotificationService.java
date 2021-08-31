package com.florian935.reactivesseemitter.service;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;

public interface NotificationService {

    Flux<ServerSentEvent<String>> replayAll(String data);

    Flux<ServerSentEvent<String>> share( String data);

    Flux<ServerSentEvent<String>> shareWithLatestEmittedValue(@PathVariable String data);
}
