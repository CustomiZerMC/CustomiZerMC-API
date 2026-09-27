package fr.elias.customiZer.api;

import java.util.Objects;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

/** Immutable /zitems catalog entry. Slot is the configured zero-based slot, or -1 for automatic. */
public record FunctionalItemInfo(String packName, String type, String itemId, int slot) {
    public FunctionalItemInfo {
        Objects.requireNonNull(packName, "packName");
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(itemId, "itemId");
    }

    /** Resolves current configuration, so removed entries return null after reload. */
    @Nullable public ItemStack buildItemStack() {
        return CustomiZerAPI.get().buildFunctionalItem(packName, type, itemId);
    }
}
