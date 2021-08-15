package de.goldmensch;

import io.micronaut.http.HttpResponse;

import java.util.Optional;
import java.util.function.Function;

public class Response {
    private Response() {}

    public static <T, R> HttpResponse<R> mapOptional(Optional<T> optional, Function<T, R> mapper) {
        return getOptional(optional.map(mapper));
    }

    public static <T> HttpResponse<T> getOptional(Optional<T> optional) {
        return optional
                .map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }
}
