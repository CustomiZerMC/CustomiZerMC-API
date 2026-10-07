# CustomiZer API

> [!NOTE]
> The [CustomiZerMC-API](https://github.com/CustomiZerMC/CustomiZerMC-API) repository is a **read-only mirror**. It's synced automatically from `customizer-api/` in [CustomiZerMC-Plugin](https://github.com/CustomiZerMC/CustomiZerMC-Plugin), so make API changes there; direct edits here are overwritten.

The developer API for **CustomiZer**, a Paper plugin for custom items, blocks, furniture, armor, cosmetics, glyphs and resource packs.

Use it to read CustomiZer content from your own plugin, build and give CustomiZer items, and hook into CustomiZer's events.

| | |
|---|---|
| Group / artifact | `fr.elias:customizer-api` |
| Latest version | `BETA-2.1` |
| Repository | `https://maven.oreostudios.fr/customizer` |
| Java | 21 |
| Server | Paper 1.21.x (built against `1.21.8-R0.1-SNAPSHOT`) |

---

## 1. Add the dependency

The API is published to the CustomiZer section of the Oreo Studios Maven repository. It is public: no credentials are needed.

Always use `provided` / `compileOnly` scope. The real implementation is supplied at runtime by the CustomiZer plugin, so **never shade the API into your jar**.

### Maven

```xml
<repositories>
    <repository>
        <id>oreostudios-customizer</id>
        <url>https://maven.oreostudios.fr/customizer</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>fr.elias</groupId>
        <artifactId>customizer-api</artifactId>
        <version>BETA-2.1</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

### Gradle (Kotlin DSL)

```kotlin
repositories {
    maven("https://maven.oreostudios.fr/customizer")
}

dependencies {
    compileOnly("fr.elias:customizer-api:BETA-2.1")
}
```

### Gradle (Groovy)

```groovy
repositories {
    maven { url 'https://maven.oreostudios.fr/customizer' }
}

dependencies {
    compileOnly 'fr.elias:customizer-api:BETA-2.1'
}
```

You can browse every published version at <https://maven.oreostudios.fr/#/customizer/fr/elias/customizer-api>.

## 2. Declare the dependency in `plugin.yml`

CustomiZer must be enabled before your plugin calls the API:

```yaml
depend: [CustomiZer]      # your plugin requires CustomiZer
# or
softdepend: [CustomiZer]  # optional integration
```

## 3. Get the API instance

```java
import fr.elias.customiZer.api.CustomiZerAPI;

public final class MyPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        if (!CustomiZerAPI.isAvailable()) {
            getLogger().warning("CustomiZer is not loaded, integration disabled");
            return;
        }
        CustomiZerAPI api = CustomiZerAPI.get();
        getLogger().info("CustomiZer packs: " + api.getPackNames());
    }
}
```

| Method | Behaviour |
|---|---|
| `CustomiZerAPI.get()` | Returns the instance; throws `IllegalStateException` if CustomiZer is not enabled |
| `CustomiZerAPI.getOrNull()` | Returns the instance or `null`, for soft dependencies |
| `CustomiZerAPI.isAvailable()` | `true` between CustomiZer's enable and disable |

**Rules**
- Call API methods on the **main server thread**, unless a method says otherwise.
- **Don't keep the instance** across reloads. Call `get()` again when you need it.
- Builder methods (`build…`) only create an `ItemStack`. They don't give it, equip it or check permissions.

---

## Usage examples

### Custom items (`items.yml`)

```java
CustomiZerAPI api = CustomiZerAPI.get();

api.giveItem(player, "blood_ingot");                  // give by key or display name
ItemStack stack = api.buildItemStack("blood_ingot");  // build without giving
boolean custom = api.isCustomItem(player.getInventory().getItemInMainHand());

CustomItemData data = api.getItemData("blood_ingot");
if (data != null) {
    int cooldown = data.getCooldown();
}

// ItemsAdder-style shortcut
CustomStack cs = CustomStack.getInstance("blood_ingot");
if (cs != null) player.getInventory().addItem(cs.getItemStack());

// drop item from a key, "pack:item" reference or vanilla Material
ItemStack drop = api.createDropItem("blossom_studios:ruby", 3);
```

### Pack items

```java
api.givePackItem(player, "blossom_studios", "ruby_sword"); // fires PackItemGiveEvent

PackItemInfo info = api.getPackItem("blossom_studios", "ruby_sword");
ItemStack sword = api.buildPackItem("blossom_studios", "ruby_sword");
PackModelInfo model = api.getPackModelInfo("blossom_studios", "ruby_sword");

List<PackItemInfo> all = api.getAllPackItems();
List<PackItemInfo> swords = api.getPackItems("swords");
```

### Custom blocks

```java
api.giveBlockItem(player, "ruby_ore");

// place in a loaded chunk (main thread; does not check region protection)
api.placeBlock(location, "ruby_ore");

// world generation: resolve once on the main thread, then reuse a clone per worker
BlockData ruby = api.getBlockData("ruby_ore");
chunkData.setBlock(x, y, z, ruby.clone());
```

### Armor, cosmetics and functional items

```java
ItemStack helmet = api.buildArmorItem("knight_helmet");
ItemStack hat = api.buildCosmeticItem("top_hat");

for (FunctionalItemInfo fi : api.getFunctionalItems("blossom_studios")) {
    ItemStack item = fi.buildItemStack();
}
```

### Furniture

```java
PlacedFurnitureInfo f = api.getFurnitureAt(block.getLocation());
if (f != null) {
    player.sendMessage("This is " + f.packName() + ":" + f.itemId());
}
boolean isFurniture = api.isFurnitureEntity(entity);
```

### Glyphs (font images)

```java
String heart = api.getGlyphChar("blossom_studios", "heart"); // a single unicode char
player.sendMessage(heart + " Welcome!");

List<Glyph> results = api.searchGlyphs("heart");
```

Glyphs are only available after CustomiZer has built the resource pack at least once.

### Player progression

```java
UUID id = player.getUniqueId();
long xp = api.getMasteryXP(id, "mining");
int level = api.getMasteryLevel(id, "mining");
api.setMasteryXP(id, "mining", xp + 100); // saved immediately

int prestige = api.getPrestigeLevel(id);
boolean done = api.isMissionCompleted(id, "first_diamond");
```

### Events

```java
@EventHandler
public void onUse(CustomItemUseEvent e) {
    if (e.getItemData().getId().equals("blood_ingot") && !e.getPlayer().hasPermission("x.use")) {
        e.setCancelled(true);
    }
}

@EventHandler
public void onReward(CustomBlockRewardEvent e) {
    e.setMoney(e.getMoney() * 2); // double money from custom block rewards
}

@EventHandler
public void onGive(PackItemGiveEvent e) {
    // inspect, replace or cancel the item before it is given
}
```

---

## What the API contains

All classes are in `fr.elias.customiZer.api`.

### Entry point

| Class | Description |
|---|---|
| `CustomiZerAPI` | Main singleton: items, blocks, furniture, pack items, armor, cosmetics, glyphs, progression and rewards |
| `CustomStack` | ItemsAdder-style shortcut: `CustomStack.getInstance(id).getItemStack()` |

### `CustomiZerAPI` methods by area

| Area | Methods |
|---|---|
| Custom items | `getItemNames`, `getItemKeys`, `getItemData`, `buildItemStack`, `giveItem`, `createDropItem`, `isCustomItem` |
| Custom blocks | `getBlockKeys`, `buildBlockItem`, `giveBlockItem`, `isCustomBlockItem`, `getBlockData`, `placeBlock` |
| Furniture | `getFurnitureAt`, `getAllPlacedFurniture`, `isFurnitureEntity` |
| Packs | `getPackNames`, `getPackItem`, `getPackItemByModelData`, `getPackItems`, `getAllPackItems`, `buildPackItem`, `getPackModelInfo`, `givePackItem` |
| Armor | `getArmorKeys`, `buildArmorItem` |
| Cosmetics | `getCosmeticKeys`, `buildCosmeticItem` |
| Functional items (`/zitems`) | `getFunctionalItems`, `buildFunctionalItem` |
| Glyphs | `getGlyph`, `getGlyphChar`, `getAllGlyphs`, `searchGlyphs`, `getPackGlyphs` |
| Progression | `getMasteryXP`, `setMasteryXP`, `getMasteryLevel`, `getPrestigeLevel`, `getMissionProgress`, `isMissionCompleted` |
| Block rewards | `getRewardData`, `getAllRewardData` |

### Data types

| Class | Description |
|---|---|
| `CustomItemData` | Everything configured for an `items.yml` item: material, name, lore, model data, action, cooldown, uses, food settings… |
| `PackItemInfo` | Pack item: pack name, item id, display name, material, model data, category |
| `PackModelInfo` | Modern model metadata: item model, equippable model and sound, tooltip style |
| `FunctionalItemInfo` | Entry from the `/zitems` catalog: pack, type, item id, slot |
| `PlacedFurnitureInfo` | Placed furniture: frame UUID, pack, item id, location |
| `Glyph` | Font image: codepoint, character, texture, height, ascent, plus config/tag helpers |
| `RewardData` / `DropData` | Block mining rewards: Jobs XP, money, points, drops, effects, banned tools |
| `ClickTypes` | Click triggers used by custom items (`RIGHT_CLICK_AIR`, `SHIFT_LEFT_CLICK_BLOCK`, …) |

### Events (`fr.elias.customiZer.api.event`)

| Event | Cancellable | Fired when |
|---|---|---|
| `CustomItemUseEvent` | yes | A player uses a custom item |
| `CustomBlockRewardEvent` | yes | A player gets rewards from mining; XP, money and points can be changed |
| `PackItemGiveEvent` | yes | A pack item is about to be given; the `ItemStack` can be replaced |

---

## Building from source

```bash
mvn clean package
```

The jar is written to `target/customizer-api-<version>.jar`.

Every push to `main` is published automatically to `https://maven.oreostudios.fr/customizer` by GitHub Actions (`.github/workflows/publish.yml`). To release a new version, bump `<version>` in `pom.xml` and push.
