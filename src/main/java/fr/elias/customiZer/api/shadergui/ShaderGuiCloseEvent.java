package fr.elias.customiZer.api.shadergui;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/** Fired after a ShaderGUI screen closed. */
public class ShaderGuiCloseEvent extends PlayerEvent {

    /** Why the screen closed. */
    public enum Reason { ACTION, API, OTHER_SCREEN, QUIT, DEATH, WORLD_CHANGE, TELEPORT, RELOAD, SHUTDOWN }

    private static final HandlerList HANDLERS = new HandlerList();
    private final String screenId;
    private final Reason reason;

    public ShaderGuiCloseEvent(@NotNull Player player, @NotNull String screenId, @NotNull Reason reason) {
        super(player);
        this.screenId = screenId;
        this.reason = reason;
    }

    @NotNull public String getScreenId() { return screenId; }

    @NotNull public Reason getReason() { return reason; }

    @NotNull @Override public HandlerList getHandlers() { return HANDLERS; }

    @NotNull public static HandlerList getHandlerList() { return HANDLERS; }
}
