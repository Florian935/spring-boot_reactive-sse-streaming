package com.florian935.reactivesseemitter.service.implementation;

import com.florian935.reactivesseemitter.service.NotificationService;
import lombok.experimental.FieldDefaults;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class NotificationServiceImpl implements NotificationService {

    Sinks.Many<String> multicastReplay = Sinks.many().replay().all();
    Sinks.Many<String> multicastShare = Sinks.many().multicast().onBackpressureBuffer();
    Sinks.Many<String> multicastShareWithLatestEmittedValue = Sinks.many().replay().latest();

    @Override
    public Flux<ServerSentEvent<String>> replayAll(String data) {

        multicastReplay.tryEmitNext(data);

        return multicastReplay.asFlux()
                .map(d -> ServerSentEvent.<String>builder()
                        .id(UUID.randomUUID().toString())
                        .event("periodic-event")
                        .data("SSE - " + d)
                        .build())
                .share();
    }

    @Override
    public Flux<ServerSentEvent<String>> share(String data) {

        multicastShare.tryEmitNext(data);

        return multicastShare.asFlux()
                .map(d -> ServerSentEvent.<String>builder()
                        .id(UUID.randomUUID().toString())
                        .event("periodic-event")
                        .data("SSE - " + d)
                        .build())
                .share();
    }

    @Override
    public Flux<ServerSentEvent<String>> shareWithLatestEmittedValue(String data) {

        multicastShareWithLatestEmittedValue.tryEmitNext(data);

        return multicastShareWithLatestEmittedValue.asFlux()
                .map(d -> ServerSentEvent.<String>builder()
                        .id(UUID.randomUUID().toString())
                        .event("periodic-event")
                        .data("SSE - " + d)
                        .build())
                .share();
    }
}
