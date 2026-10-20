package fr.elias.customiZer.api.shadergui;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

/**
 * CustomiZer ShaderGUI: full-screen interactive screens and HUDs drawn by a core shader on
 * vanilla Java clients, with no client mods.
 *
 * <p>Screens and HUDs are configured in {@code plugins/CustomiZer/shadergui/}. Obtain this through
 * {@link fr.elias.customiZer.api.CustomiZerAPI#shaderGui()}. Call on the server thread.
 *
 * <pre>{@code
 * ShaderGui gui = CustomiZerAPI.get().shaderGui();
 * gui.setValue(player, "mana", 42);          // {mana} in HUD text and bars
 * gui.showHud(player, "rpg");
 * gui.open(player, "world_map");
 * gui.registerAction("teleport", (p, screen, element, arg) -> p.performCommand("warp " + arg));
 * }</pre>
 */
public interface ShaderGui {

    /**
     * Opens {@code screenId} for {@code player}. Players whose client can't render ShaderGUI
     * (Bedrock, versions before 1.21.4, resource pack not loaded) get the chest-menu fallback.
     *
     * @return false when the screen doesn't exist, permission is missing or the open was cancelled
     */
    boolean open(@NotNull Player player, @NotNull String screenId);

    /** Closes the player's screen (playing its close animation). */
    void close(@NotNull Player player);

    /** Id of the screen the player has open, or null. */
    @Nullable String openScreen(@NotNull Player player);

    @NotNull Set<String> screens();

    @NotNull Set<String> huds();

    void showHud(@NotNull Player player, @NotNull String hudId);

    void hideHud(@NotNull Player player, @NotNull String hudId);

    boolean isHudVisible(@NotNull Player player, @NotNull String hudId);

    /**
     * Sets a per-player value readable as {@code {key}} in texts and as a bar value or maximum.
     * Use it for anything CustomiZer doesn't track itself (mana, stamina, boss health…).
     */
    void setValue(@NotNull Player player, @NotNull String key, @NotNull Object value);

    void clearValue(@NotNull Player player, @NotNull String key);

    /** Shows a timed notification on the player's HUD. */
    void notify(@NotNull Player player, @NotNull String text, int ticks);

    /** Handles {@code custom:<name>[:argument]} actions from configs. Replaces a handler of the same name. */
    void registerAction(@NotNull String name, @NotNull ActionHandler handler);

    void unregisterAction(@NotNull String name);

    /** True when the player's client renders ShaderGUI (Java 1.21.4+, pack loaded, not Bedrock). */
    boolean supports(@NotNull Player player);

    /** A {@code custom:} action handler. {@code elementId} is null for keybinds. */
    @FunctionalInterface
    interface ActionHandler {
        void handle(@NotNull Player player, @NotNull String screenId, @Nullable String elementId, @NotNull String argument);
    }
}
