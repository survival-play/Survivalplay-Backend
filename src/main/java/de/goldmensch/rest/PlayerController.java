package de.goldmensch.rest;

import de.goldmensch.Player;
import de.goldmensch.Response;
import de.goldmensch.storage.PlayerRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Controller("/player")
public class PlayerController {

    @Inject
    private PlayerRepository repository;

    @Get("/byName/{name}")
    public HttpResponse<String> getUUIDByName(@PathVariable String name) {
        return Response.mapOptional(repository.findUuidByName(name), UUID::toString);
    }

    @Controller("/player/{uuid}")
    public static class PlayerStatsController {

        @Inject
        private PlayerRepository repository;

        @Get
        public HttpResponse<String> index(@PathVariable UUID uuid) {
            return Response.mapOptional(repository.findById(uuid), Player::toString);
        }

        @Get("/name")
        public HttpResponse<String> getName(@PathVariable UUID uuid) {
            return Response.getOptional(repository.findNameByUuid(uuid));
        }

        @Get("/joins")
        public HttpResponse<Integer> getJoins(@PathVariable UUID uuid) {
            return Response.getOptional(repository.findJoinsByUuid(uuid));
        }

        @Get("/currentServer")
        public HttpResponse<String> getCurrentServer(@PathVariable UUID uuid) {
            return HttpResponse.ok(repository.findCurrentServerByUuid(uuid).orElse("none"));
        }

        @Get("/isOnline")
        public HttpResponse<Boolean> isOnline(@PathVariable UUID uuid) {
            return Response.mapOptional(repository.findCurrentServerByUuid(uuid), Objects::nonNull);
        }

        @Put("/name")
        public void setName(@PathVariable UUID uuid, @Body String name) {
            repository.updateNameByUuid(uuid, name);
        }

        @Put("/joins")
        public void setJoins(@PathVariable UUID uuid, @Body int joins) {
            repository.updateJoinsByUuid(uuid, joins);
        }

        @Put("/currentServer")
        public void setCurrentServer(@PathVariable UUID uuid, @Body String server) {
            repository.updateCurrentServerByUuid(uuid, server);
        }

        @Put("/offline")
        public void setOnline(@PathVariable UUID uuid) {
            repository.updateCurrentServerByUuid(uuid, null);
        }
    }

    @Controller("/player/{uuid}/task")
    public static class PlayerUUIDTaskController {

        @Inject
        private PlayerRepository repository;

        @Put("/join")
        public HttpResponse<Void> join(@PathVariable UUID uuid, @Body String name) {
            Optional<Player> optional = repository.findById(uuid);
            if(optional.isPresent()) {
                repository.update(new Player(uuid, name, "proxy", optional.get().joins()+1));
            }else {
                repository.save(new Player(uuid, name, "proxy", 1));
            }
            return HttpResponse.ok();
        }

        @Put("/leave")
        public HttpResponse<Void> leave(@PathVariable UUID uuid) {
            repository.updateCurrentServerByUuid(uuid, null);
            return HttpResponse.ok();
        }
    }
}
