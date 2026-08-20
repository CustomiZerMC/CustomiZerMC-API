package fr.elias.customiZer.api.event;

import fr.elias.customiZer.api.CustomItemData;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Fired when a player successfully activates a CustomiZer item action.
 *
 * <p>Cancelling this event prevents the action from executing (but the
 * cooldown is still consumed to prevent abuse).
 */
public class CustomItemUseEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private final CustomItemData itemData;
    private final ItemStack itemStack;
    private boolean cancelled = false;

    public CustomItemUseEvent(@NotNull Player player,
                              @NotNull CustomItemData itemData,
                              @NotNull ItemStack itemStack) {
        super(player);
        this.itemData = itemData;
        this.itemStack = itemStack;
    }

    /** The CustomiZer item definition that was used. */
    @NotNull public CustomItemData getItemData() { return itemData; }

    /** The physical ItemStack held by the player when the event fired. */
    @NotNull public ItemStack getItemStack() { return itemStack; }

    @Override public boolean isCancelled() { return cancelled; }
    @Override public void setCancelled(boolean cancel) { this.cancelled = cancel; }
    @Override @NotNull public HandlerList getHandlers() { return HANDLERS; }
    @NotNull public static HandlerList getHandlerList() { return HANDLERS; }
}
