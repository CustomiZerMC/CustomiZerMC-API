package fr.elias.customiZer.api.shadergui;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/** Fired before a ShaderGUI screen opens. Cancel to keep it closed. */
public class ShaderGuiOpenEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private final String screenId;
    private final boolean fallback;
    private boolean cancelled;

    public ShaderGuiOpenEvent(@NotNull Player player, @NotNull String screenId, boolean fallback) {
        super(player);
        this.screenId = screenId;
        this.fallback = fallback;
    }

    @NotNull public String getScreenId() { return screenId; }

    /** True when the chest-menu fallback opens instead of the shader screen. */
    public boolean isFallback() { return fallback; }

    @Override public boolean isCancelled() { return cancelled; }

    @Override public void setCancelled(boolean cancel) { this.cancelled = cancel; }

    @NotNull @Override public HandlerList getHandlers() { return HANDLERS; }

    @NotNull public static HandlerList getHandlerList() { return HANDLERS; }
}
