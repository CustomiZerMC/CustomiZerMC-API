package fr.elias.customiZer.api;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Read-only snapshot of a piece of furniture that is currently placed in the world.
 *
 * <p>Obtained via {@link CustomiZerAPI#getFurnitureAt(Location)} or
 * {@link CustomiZerAPI#getAllPlacedFurniture()}.
 */
public record PlacedFurnitureInfo(
        /** UUID of the hidden ItemFrame entity that represents this furniture. */
        @NotNull UUID frameUuid,
        /** Pack name this furniture belongs to (e.g. {@code "blossom_studios"}). */
        @NotNull String packName,
        /** Item id within the pack (e.g. {@code "oak_chair"}). */
        @NotNull String itemId,
        /** Custom model data value assigned to this furniture. */
        int modelData,
        /** The base block location where the furniture sits. */
        @NotNull Location baseLocation
) {}
