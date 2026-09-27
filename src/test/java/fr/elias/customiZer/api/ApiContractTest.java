package fr.elias.customiZer.api;

import org.junit.Test;
import org.junit.After;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.*;
import static org.junit.Assert.*;

public class ApiContractTest {
    private final LegacyImplementation first = new LegacyImplementation();
    private final LegacyImplementation second = new LegacyImplementation();
    @After public void cleanup() {
        LegacyImplementation.stop(first);
        LegacyImplementation.stop(second);
    }

    @Test public void availabilityTracksLifecycle() {
        assertFalse(CustomiZerAPI.isAvailable());
        assertNull(CustomiZerAPI.getOrNull());
        try { CustomiZerAPI.get(); fail("Must reject unavailable lookup"); }
        catch (IllegalStateException expected) { }
        LegacyImplementation.start(first);
        assertSame(first, CustomiZerAPI.get());
        assertTrue(CustomiZerAPI.isAvailable());
        LegacyImplementation.stop(first);
        assertNull(CustomiZerAPI.getOrNull());
    }

    @Test public void oldShutdownCannotClearReplacement() {
        LegacyImplementation.start(first);
        LegacyImplementation.start(second);
        LegacyImplementation.stop(first);
        assertSame(second, CustomiZerAPI.get());
    }

    @Test(expected = NullPointerException.class)
    public void cannotRegisterNull() { LegacyImplementation.start(null); }

    @Test(expected = UnsupportedOperationException.class)
    public void oldImplementationsStillCompileButRejectUnsupportedNewCapability() {
        first.getArmorKeys();
    }

    @Test public void furnitureLocationIsDefensivelyCopied() {
        Location location = new Location(null, 1, 2, 3);
        var info = new PlacedFurnitureInfo(UUID.randomUUID(), "pack", "chair", 1, location);
        location.setX(100);
        assertEquals(1, info.baseLocation().getX(), 0);
        info.baseLocation().setY(100);
        assertEquals(2, info.baseLocation().getY(), 0);
    }

    @Test public void functionalMetadataIsImmutableAndValidated() {
        var info = new FunctionalItemInfo("pack", "armor", "helmet", 12);
        assertEquals("armor", info.type());
        assertEquals(12, info.slot());
        try { new FunctionalItemInfo(null, "armor", "helmet", -1); fail(); }
        catch (NullPointerException expected) { }
    }

    // Intentionally implements only the pre-update abstract contract.
    private static final class LegacyImplementation extends CustomiZerAPI {
        static void start(CustomiZerAPI api) { register(api); }
        static void stop(CustomiZerAPI api) { unregister(api); }
        @Override public Set<String> getItemNames() { return null; }
        @Override public CustomItemData getItemData(String nameOrKey) { return null; }
        @Override public ItemStack buildItemStack(String nameOrKey) { return null; }
        @Override public void giveItem(Player player, String nameOrKey) {  }
        @Override public ItemStack createDropItem(String ref, int amount) { return null; }
        @Override public boolean isCustomItem(ItemStack item) { return false; }
        @Override public Set<String> getBlockKeys() { return null; }
        @Override public ItemStack buildBlockItem(String blockKey) { return null; }
        @Override public void giveBlockItem(Player player, String blockKey) {  }
        @Override public boolean isCustomBlockItem(ItemStack item) { return false; }
        @Override public PlacedFurnitureInfo getFurnitureAt(Location location) { return null; }
        @Override public Collection<PlacedFurnitureInfo> getAllPlacedFurniture() { return null; }
        @Override public boolean isFurnitureEntity(Entity entity) { return false; }
        @Override public PackItemInfo getPackItem(String packName, String itemId) { return null; }
        @Override public PackItemInfo getPackItemByModelData(int modelData) { return null; }
        @Override public List<PackItemInfo> getPackItems(String category) { return null; }
        @Override public boolean givePackItem(Player player, String packName, String itemId) { return false; }
        @Override public long getMasteryXP(UUID uuid, String track) { return 0; }
        @Override public void setMasteryXP(UUID uuid, String track, long xp) {  }
        @Override public int getMasteryLevel(UUID uuid, String track) { return 0; }
        @Override public int getPrestigeLevel(UUID uuid) { return 0; }
        @Override public int getMissionProgress(UUID uuid, String missionKey) { return 0; }
        @Override public boolean isMissionCompleted(UUID uuid, String missionKey) { return false; }
        @Override public Glyph getGlyph(String packName, String fontId) { return null; }
        @Override public String getGlyphChar(String packName, String fontId) { return null; }
        @Override public List<Glyph> getAllGlyphs() { return null; }
        @Override public List<Glyph> searchGlyphs(String query) { return null; }
        @Override public List<Glyph> getPackGlyphs(String packName) { return null; }
        @Override public RewardData getRewardData(String blockMaterial) { return null; }
        @Override public Map<String, RewardData> getAllRewardData() { return null; }
    }
}
