package com.florian935.reactivesseemitter.utils;

import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Sinks;

@Configuration
public abstract class SinkUtils {

    public static <T> Sinks.Many<T> multicastReplay() {

        return Sinks.many().replay().all();
    }

    public static <T> Sinks.Many<T> multicastReplay(int i) {

        return Sinks.many().replay().limit(i);
    }

    public static <T> Sinks.Many<T> multicastShareWithLatestEmittedValue() {

        return Sinks.many().replay().latest();
    }

    public static <T> Sinks.Many<T> multicastShare(int bufferSize, boolean autoCancel) {

        return Sinks.many().multicast().onBackpressureBuffer(bufferSize, autoCancel);
    }
}
