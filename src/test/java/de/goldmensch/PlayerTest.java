package de.goldmensch;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.netty.handler.codec.http.HttpMethod;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

@MicronautTest
class PlayerTest {

    @Inject
    EmbeddedServer server;

    @Inject
    @Client("/")
    HttpClient aClient;

    BlockingHttpClient client;


    void testJoin() {
        client = aClient.toBlocking();

        client.exchange(HttpRequest.PUT("/player/0d6b35d5-2115-4e7f-ac3a-fe5a4cb9be1e/task/join", "Fred"));
        client.exchange(HttpRequest.PUT("/player/53df0f8c-04d5-483d-8310-1d10c6bfe11e/task/join", "Gustav"));
    }

    void testStats() {
        client = aClient.toBlocking();

        String result = client.retrieve(HttpRequest.GET("/player/0d6b35d5-2115-4e7f-ac3a-fe5a4cb9be1e"));
        String result2 = client.retrieve(HttpRequest.GET("/player/53df0f8c-04d5-483d-8310-1d10c6bfe11e"));
        System.out.println(result);
        System.out.println(result2);
    }

    void testGetByName() {
        client = aClient.toBlocking();
        String uuidString = client.retrieve(HttpRequest.GET("/player/byName/Fred"));
        System.out.println(uuidString);

        UUID uuid = UUID.fromString(uuidString);
        String result = client.retrieve(HttpRequest.GET("/player/" + uuid));
        System.out.println(result);
    }

    @Test
    void testLeave() {
        client = aClient.toBlocking();

        client.exchange(HttpRequest.PUT("/player/53df0f8c-04d5-483d-8310-1d10c6bfe11e/task/leave", ""));
        System.out.println(
                client.retrieve(HttpRequest.GET("/player/53df0f8c-04d5-483d-8310-1d10c6bfe11e/currentServer"))
        );
    }
}
