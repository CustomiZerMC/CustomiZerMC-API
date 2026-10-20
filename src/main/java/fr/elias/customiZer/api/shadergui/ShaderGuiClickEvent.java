package fr.elias.customiZer.api.shadergui;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/** Fired when a player clicks an element of a ShaderGUI screen, before its actions run. Cancel to skip them. */
public class ShaderGuiClickEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private final String screenId;
    private final String elementId;
    private final boolean rightClick;
    private boolean cancelled;

    public ShaderGuiClickEvent(@NotNull Player player, @NotNull String screenId, @NotNull String elementId, boolean rightClick) {
        super(player);
        this.screenId = screenId;
        this.elementId = elementId;
        this.rightClick = rightClick;
    }

    @NotNull public String getScreenId() { return screenId; }

    @NotNull public String getElementId() { return elementId; }

    public boolean isRightClick() { return rightClick; }

    @Override public boolean isCancelled() { return cancelled; }

    @Override public void setCancelled(boolean cancel) { this.cancelled = cancel; }

    @NotNull @Override public HandlerList getHandlers() { return HANDLERS; }

    @NotNull public static HandlerList getHandlerList() { return HANDLERS; }
}
