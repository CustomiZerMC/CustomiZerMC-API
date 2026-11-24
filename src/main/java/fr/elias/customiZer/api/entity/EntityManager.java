package fr.elias.customiZer.api.entity;

import org.bukkit.Location;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/** Server-thread service. Instance IDs are the persistent living base UUIDs. */
public interface EntityManager {
    Set<String> definitions();

    Optional<CustomEntity> find(UUID id);

    CustomEntity spawn(String definitionId, Location location);
}
