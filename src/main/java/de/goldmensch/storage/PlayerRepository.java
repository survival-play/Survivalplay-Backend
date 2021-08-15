package de.goldmensch.storage;

import de.goldmensch.Player;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import javax.inject.Singleton;
import java.util.Optional;
import java.util.UUID;

@Singleton
@JdbcRepository(dialect = Dialect.MYSQL)
public interface PlayerRepository extends CrudRepository<Player, UUID> {
    void updateCurrentServerByUuid(@Id UUID uuid, @Nullable String currentServer);
    void updateNameByUuid(@Id UUID uuid, String name);
    void updateJoinsByUuid(@Id UUID uuid, int joins);
    Optional<String> findNameByUuid(@Id UUID uuid);
    Optional<Integer> findJoinsByUuid(@Id UUID uuid);
    Optional<String> findCurrentServerByUuid(@Id UUID uuid);
    Optional<UUID> findUuidByName(String name);
}
