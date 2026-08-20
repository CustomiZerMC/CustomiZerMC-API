package fr.elias.customiZer.api;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A wrapper around a CustomiZer item — analogous to ItemsAdder's {@code CustomStack}.
 *
 * <pre>{@code
 * CustomStack stack = CustomStack.getInstance("blood_ingot");
 * if (stack != null) {
 *     ItemStack item = stack.getItemStack().clone();
 *     item.setAmount(amount);
 * }
 * }</pre>
 */
public final class CustomStack {

    private final String id;
    private final ItemStack item;

    private CustomStack(@NotNull String id, @NotNull ItemStack item) {
        this.id = id;
        this.item = item;
    }

    /**
     * Returns a {@link CustomStack} for the given item id, or {@code null} if no
     * item with that id is registered in CustomiZer's {@code items.yml}.
     *
     * @param id the config key / display-name key of the item
     */
    @Nullable
    public static CustomStack getInstance(@NotNull String id) {
        try {
            ItemStack item = CustomiZerAPI.get().buildItemStack(id);
            if (item == null) return null;
            return new CustomStack(id, item);
        } catch (IllegalStateException e) {
            return null;
        }
    }

    /** The config key this stack was resolved from. */
    @NotNull
    public String getId() {
        return id;
    }

    /**
     * Returns a clone of the backing {@link ItemStack}.
     * Always clone before modifying (amount, meta, etc.).
     */
    @NotNull
    public ItemStack getItemStack() {
        return item.clone();
    }
}
