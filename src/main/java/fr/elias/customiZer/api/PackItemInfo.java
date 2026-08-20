package fr.elias.customiZer.api;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Immutable snapshot of a pack item's metadata.
 *
 * <p>Obtain instances via {@link CustomiZerAPI#getPackItem(String, String)} or
 * {@link CustomiZerAPI#getPackItems(String)}.
 */
public record PackItemInfo(
        @NotNull String packName,
        @NotNull String itemId,
        @NotNull String displayName,
        @NotNull Material material,
        int modelData,
        @NotNull String category
) {
    /**
     * Builds and returns the ItemStack for this pack item.
     * Returns {@code null} if the item cannot be resolved.
     */
    @Nullable
    public ItemStack buildItemStack() {
        return CustomiZerAPI.get().createDropItem(packName + ":" + itemId, 1);
    }
}
