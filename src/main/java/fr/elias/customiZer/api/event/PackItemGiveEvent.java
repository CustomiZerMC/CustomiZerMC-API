package fr.elias.customiZer.api.event;

import fr.elias.customiZer.api.PackItemInfo;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Fired just before a pack item's built {@link ItemStack} is added to a
 * player's inventory via the {@code /zitems} GUI or
 * {@link fr.elias.customiZer.api.CustomiZerAPI#givePackItem}.
 *
 * <p>Cancelling prevents the item from being given.
 */
public class PackItemGiveEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private final PackItemInfo packItem;
    private ItemStack itemStack;
    private boolean cancelled = false;

    public PackItemGiveEvent(@NotNull Player player,
                             @NotNull PackItemInfo packItem,
                             @NotNull ItemStack itemStack) {
        super(player);
        this.packItem = packItem;
        this.itemStack = itemStack;
    }

    /** The pack item definition. */
    @NotNull public PackItemInfo getPackItem() { return packItem; }

    /**
     * The ItemStack that will be added to the player's inventory.
     * Can be replaced to change what is given.
     */
    @NotNull public ItemStack getItemStack() { return itemStack; }
    public void setItemStack(@NotNull ItemStack itemStack) { this.itemStack = itemStack; }

    @Override public boolean isCancelled() { return cancelled; }
    @Override public void setCancelled(boolean cancel) { this.cancelled = cancel; }
    @Override @NotNull public HandlerList getHandlers() { return HANDLERS; }
    @NotNull public static HandlerList getHandlerList() { return HANDLERS; }
}
