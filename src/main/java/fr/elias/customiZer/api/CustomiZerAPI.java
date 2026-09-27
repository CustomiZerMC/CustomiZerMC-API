package fr.elias.customiZer.api;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * The CustomiZer developer API.
 *
 * <p>Unless explicitly documented otherwise, call instance methods on the server
 * thread. Builders do not grant items or bypass equipment/permission rules.
 * Reacquire the API after plugin reload; do not retain instances across disable.
 *
 * <p>Use this class to interact with CustomiZer from another plugin.
 * Obtain the singleton instance with {@link #get()} after CustomiZer
 * has been enabled.
 *
 * <pre>{@code
 * // in your plugin's onEnable(), after all plugins have loaded:
 * CustomiZerAPI api = CustomiZerAPI.get();
 *
 * // get a custom item stack (like ItemsAdder's CustomStack)
 * CustomStack stack = CustomStack.getInstance("blood_ingot");
 *
 * // give a custom item
 * api.giveItem(player, "blood_ingot");
 *
 * // give a pack item
 * api.givePackItem(player, "blossom_studios", "ruby_sword");
 *
 * // read player progression
 * long miningXP = api.getMasteryXP(player.getUniqueId(), "mining");
 * }</pre>
 *
 * <p>Listen to {@link fr.elias.customiZer.api.event.CustomItemUseEvent},
 * {@link fr.elias.customiZer.api.event.CustomBlockRewardEvent}, and
 * {@link fr.elias.customiZer.api.event.PackItemGiveEvent} to hook into
 * CustomiZer's core flows.
 */
public abstract class CustomiZerAPI {

    private static volatile CustomiZerAPI instance;

    /**
     * Registers the API implementation. Called internally by CustomiZer during {@code onEnable()}.
     * Not part of the public API.
     */
    protected static synchronized void register(@NotNull CustomiZerAPI api) {
        instance = java.util.Objects.requireNonNull(api, "api");
    }

    /** Internal lifecycle cleanup; an old implementation cannot clear a newer one. */
    protected static synchronized void unregister(@NotNull CustomiZerAPI api) {
        if (instance == api) instance = null;
    }

    /** True only after initialization and before shutdown. */
    public static boolean isAvailable() { return instance != null; }

    /** Optional integration lookup; null while unavailable. Does not imply async safety. */
    @Nullable
    public static CustomiZerAPI getOrNull() { return instance; }

    /**
     * Returns the API instance.
     *
     * @throws IllegalStateException if CustomiZer has not been enabled yet
     */
    @NotNull
    public static CustomiZerAPI get() {
        CustomiZerAPI current = instance;
        if (current == null) {
            throw new IllegalStateException("CustomiZer has not been enabled yet.");
        }
        return current;
    }

    // Additive BETA-2.1 capabilities. Concrete defaults preserve existing subclasses;
    // the current plugin implements every method below. Old implementations fail explicitly.

    /** Loaded visual and functional pack IDs, including pack-local armor packs. */
    @NotNull public Set<String> getPackNames() { throw unsupported(); }

    /** Original YAML config keys, as distinct from legacy display-name lookup IDs. */
    @NotNull public Set<String> getItemKeys() { throw unsupported(); }

    /** Snapshot of all registered visual pack items (not just one category). */
    @NotNull public List<PackItemInfo> getAllPackItems() { throw unsupported(); }

    /** Build a visual pack item with its full modern model/equipment metadata. */
    @Nullable public ItemStack buildPackItem(@NotNull String packName, @NotNull String itemId) { throw unsupported(); }

    /** Modern model/equipment metadata, or null when the pack item does not exist. */
    @Nullable public PackModelInfo getPackModelInfo(@NotNull String packName, @NotNull String itemId) { throw unsupported(); }

    /** Loaded custom armor IDs, including definitions from packs/<pack>/armors.yml. */
    @NotNull public Set<String> getArmorKeys() { throw unsupported(); }

    /** Build through the armor handler (PAPER heads, CORE_SHADER and DISPLAY_3D included). */
    @Nullable public ItemStack buildArmorItem(@NotNull String armorId) { throw unsupported(); }

    /** Loaded cosmetic IDs, rather than visual pack model IDs. */
    @NotNull public Set<String> getCosmeticKeys() { throw unsupported(); }

    /** Builds a configured cosmetic; does not equip it or change permissions. */
    @Nullable public ItemStack buildCosmeticItem(@NotNull String cosmeticId) { throw unsupported(); }

    /** Functional /zitems catalog for this pack, not every handler's private registry. */
    @NotNull public List<FunctionalItemInfo> getFunctionalItems(@NotNull String packName) { throw unsupported(); }

    /** Resolve a catalog entry through the same item factory used by /zitems.
     * Returns null when absent. Does not apply GUI-only name/lore overlays or give/equip it. */
    @Nullable public ItemStack buildFunctionalItem(@NotNull String packName, @NotNull String type,
                                                  @NotNull String itemId) { throw unsupported(); }

    private static UnsupportedOperationException unsupported() {
        return new UnsupportedOperationException("This API implementation lacks BETA-2.1 catalog support");
    }

    // ── Custom Items (items.yml) ─────────────────────────────────────────────

    /** Legacy display-name lookup IDs. Use getItemKeys() for original YAML keys. */
    @NotNull
    public abstract Set<String> getItemNames();

    /**
     * Returns item data by display name or config key, or {@code null} if not found.
     */
    @Nullable
    public abstract CustomItemData getItemData(@NotNull String nameOrKey);

    /**
     * Builds and returns an {@link ItemStack} for the given item name/key,
     * or {@code null} if not found.
     */
    @Nullable
    public abstract ItemStack buildItemStack(@NotNull String nameOrKey);

    /** Gives the named custom item to the player. No-op if the item does not exist. */
    public abstract void giveItem(@NotNull Player player, @NotNull String nameOrKey);

    /**
     * Creates a drop-style {@link ItemStack} for the given reference.
     * The reference can be a config key, a {@code "packName:itemId"} pack reference,
     * or a vanilla {@link org.bukkit.Material} name.
     *
     * @return the ItemStack, or {@code null} if unresolvable
     */
    @Nullable
    public abstract ItemStack createDropItem(@NotNull String ref, int amount);

    /**
     * Returns {@code true} if the given ItemStack was created by CustomiZer's
     * items.yml system (it carries the {@code custom_item_uuid} PDC tag).
     */
    public abstract boolean isCustomItem(@NotNull ItemStack item);

    // ── Custom Blocks (blocks.yml) ───────────────────────────────────────────

    /** All block keys registered under {@code blocks:} in blocks.yml. */
    @NotNull
    public abstract Set<String> getBlockKeys();

    /** Builds and returns an {@link ItemStack} for the given block key, or {@code null}. */
    @Nullable
    public abstract ItemStack buildBlockItem(@NotNull String blockKey);

    /**
     * Resolves a native, non-emissive custom block to a fresh BlockData, or null for
     * an unknown key. Accepts a blocks.yml key, customizer:key, or pack:item.
     * Call on the server thread after CustomiZer is ready; cache the result for
     * ChunkData generation (use a clone per worker). Never reduce it to Material.
     * DISPLAY/entity-backed and light-emitting blocks throw UnsupportedOperationException.
     */
    @Nullable
    public org.bukkit.block.data.BlockData getBlockData(@NotNull String blockKey) {
        throw new UnsupportedOperationException("Native block lookup is not supported by this implementation");
    }

    /**
     * Places a native, non-emissive custom block in an already loaded chunk.
     * Main thread only. Returns false for an unknown key or a protected target
     * (tile entity, furniture, barrier, light or note block). Does not simulate
     * player events, permissions, drops or region protection: callers must check them.
     * Unsupported block representations throw UnsupportedOperationException.
     * Use getBlockData and ChunkData.setBlock for terrain generation instead.
     */
    public boolean placeBlock(@NotNull Location location, @NotNull String blockKey) {
        throw new UnsupportedOperationException("Block placement is not supported by this implementation");
    }

    /** Gives the named custom block item to a player. No-op if key does not exist. */
    public abstract void giveBlockItem(@NotNull Player player, @NotNull String blockKey);

    /** Returns {@code true} if the given ItemStack is a CustomiZer custom block. */
    public abstract boolean isCustomBlockItem(@NotNull ItemStack item);

    // ── Furniture ────────────────────────────────────────────────────────────

    /**
     * Returns info about placed furniture at the given location, or {@code null}.
     * The location is block-aligned; sub-block precision is ignored.
     */
    @Nullable
    public abstract PlacedFurnitureInfo getFurnitureAt(@NotNull Location location);

    /** Returns a snapshot of every piece of furniture currently placed in the world. */
    @NotNull
    public abstract Collection<PlacedFurnitureInfo> getAllPlacedFurniture();

    /** Returns {@code true} if the given entity is a CustomiZer furniture ItemFrame. */
    public abstract boolean isFurnitureEntity(@NotNull Entity entity);

    // ── Pack Items ───────────────────────────────────────────────────────────

    /**
     * Returns a pack item by pack name and item id, or {@code null} if not found.
     */
    @Nullable
    public abstract PackItemInfo getPackItem(@NotNull String packName, @NotNull String itemId);

    /** Returns a pack item by its custom model data value, or {@code null}. */
    @Nullable
    public abstract PackItemInfo getPackItemByModelData(int modelData);

    /** Returns all pack items for a given category, or an empty list. */
    @NotNull
    public abstract List<PackItemInfo> getPackItems(@NotNull String category);

    /**
     * Gives the specified pack item to a player.
     * Fires {@link fr.elias.customiZer.api.event.PackItemGiveEvent};
     * does nothing if the event is cancelled or the item is not found.
     *
     * @return {@code true} if the item was successfully given
     * (inventory overflow is dropped at the player's location)
     */
    public abstract boolean givePackItem(@NotNull Player player,
                                         @NotNull String packName,
                                         @NotNull String itemId);

    // ── Player Progression ───────────────────────────────────────────────────

    /** Returns the mastery XP for a player on a given track. */
    public abstract long getMasteryXP(@NotNull UUID uuid, @NotNull String track);

    /** Sets the mastery XP for a player on a given track and saves immediately. */
    public abstract void setMasteryXP(@NotNull UUID uuid, @NotNull String track, long xp);

    /** Returns the mastery level for a player on a given track. */
    public abstract int getMasteryLevel(@NotNull UUID uuid, @NotNull String track);

    /** Returns the prestige level for a player. */
    public abstract int getPrestigeLevel(@NotNull UUID uuid);

    /** Returns raw mission progress for a player. */
    public abstract int getMissionProgress(@NotNull UUID uuid, @NotNull String missionKey);

    /** Returns whether a mission has been completed by a player. */
    public abstract boolean isMissionCompleted(@NotNull UUID uuid, @NotNull String missionKey);

    // ── Glyphs (font images) ─────────────────────────────────────────────────

    /**
     * Returns the {@link Glyph} for the given pack and font ID, or {@code null}.
     * Only returns a result after the resource pack has been built at least once.
     */
    @Nullable
    public abstract Glyph getGlyph(@NotNull String packName, @NotNull String fontId);

    /**
     * Returns the single Unicode character that renders the given font image,
     * or {@code null} if not found.
     */
    @Nullable
    public abstract String getGlyphChar(@NotNull String packName, @NotNull String fontId);

    /** Returns all registered glyphs sorted by codepoint. */
    @NotNull
    public abstract List<Glyph> getAllGlyphs();

    /** Returns glyphs whose pack name or font ID contains the query (case-insensitive). */
    @NotNull
    public abstract List<Glyph> searchGlyphs(@NotNull String query);

    /** Returns the registered glyphs for a specific pack. */
    @NotNull
    public abstract List<Glyph> getPackGlyphs(@NotNull String packName);

    // ── Rewards ──────────────────────────────────────────────────────────────

    /**
     * Returns the reward data for a block by its lower-case material name, or {@code null}.
     */
    @Nullable
    public abstract RewardData getRewardData(@NotNull String blockMaterial);

    /** Returns all registered block reward data, keyed by lower-case material name. */
    @NotNull
    public abstract Map<String, RewardData> getAllRewardData();
}
