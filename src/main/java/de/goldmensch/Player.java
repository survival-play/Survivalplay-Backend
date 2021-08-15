package de.goldmensch;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;

import javax.persistence.Table;
import java.util.UUID;

@MappedEntity
@Table(name = "players")
public record Player(@Id UUID uuid, String name, @Nullable String currentServer, int joins) {
    public boolean isOnline() {
        return currentServer != null;
    }
}
