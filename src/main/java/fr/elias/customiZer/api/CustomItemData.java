package fr.elias.customiZer.api;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public class CustomItemData {

    /** One potion effect applied on eat, with a probability. */
    public record FoodEffect(@NotNull PotionEffect effect, float chance) {}

    private final Material type;
    private final String id;
    private final String name;
    private final List<String> lore;
    private final int modelData;
    private final String actionType;
    private final String command;
    private final List<ClickTypes> interact;
    private final int duration;
    private final int cooldown;
    private final int uses;
    private final int area;
    private final String effectOnHold;
    private final boolean twoBlocks;
    private final int customRadius;
    private final int customDurability;

    private final Location teleportLocation;
    private final int amount;
    private final int maxAmount;
    private final boolean godMode;
    private final boolean flyMode;
    private final double jobBoostMultiplier;
    private final int jobBoostDuration;
    private final File modelFile;

    private final boolean food;
    private final int foodNutrition;
    private final float foodSaturation;
    private final boolean foodCanAlwaysEat;
    private final List<FoodEffect> foodEffects;

    public CustomItemData(Material type, String id, String name, List<String> lore, int modelData, String actionType,
                          String command, List<ClickTypes> interact, int duration, int cooldown, int uses, int area,
                          String effectOnHold, boolean twoBlocks, int customRadius, int customDurability,
                          Location teleportLocation, int amount, int maxAmount, boolean godMode, boolean flyMode,
                          double jobBoostMultiplier, int jobBoostDuration, File modelFile,
                          boolean food, int foodNutrition, float foodSaturation, boolean foodCanAlwaysEat,
                          List<FoodEffect> foodEffects) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.lore = lore;
        this.modelData = modelData;
        this.actionType = actionType;
        this.command = command;
        this.interact = interact;
        this.duration = duration;
        this.cooldown = cooldown;
        this.uses = uses;
        this.area = area;
        this.effectOnHold = effectOnHold;
        this.twoBlocks = twoBlocks;
        this.customRadius = customRadius;
        this.customDurability = customDurability;
        this.teleportLocation = teleportLocation;
        this.amount = amount;
        this.maxAmount = maxAmount;
        this.godMode = godMode;
        this.flyMode = flyMode;
        this.jobBoostMultiplier = jobBoostMultiplier;
        this.jobBoostDuration = jobBoostDuration;
        this.modelFile = modelFile;
        this.food = food;
        this.foodNutrition = foodNutrition;
        this.foodSaturation = foodSaturation;
        this.foodCanAlwaysEat = foodCanAlwaysEat;
        this.foodEffects = foodEffects != null ? List.copyOf(foodEffects) : List.of();
    }

    public String getId() { return id; }
    public Material getType() { return type; }
    public List<ClickTypes> getInteract() { return interact; }
    public String getName() { return name; }
    public List<String> getLore() { return lore; }
    public int getModelData() { return modelData; }
    public String getActionType() { return actionType; }
    public String getCommand() { return command; }
    public int getDuration() { return duration; }
    public int getCooldown() { return cooldown; }
    public int getUses() { return uses; }
    public int getArea() { return area; }
    public String getEffectOnHold() { return effectOnHold; }
    public boolean isTwoBlocksEnabled() { return twoBlocks; }
    public int getCustomRadius() { return customRadius; }
    public int getCustomDurability() { return customDurability; }
    public Location getTeleportLocation() { return teleportLocation; }
    public int getAmount() { return amount; }
    public int getMaxAmount() { return maxAmount; }
    public boolean isGodModeEnabled() { return godMode; }
    public boolean isFlyModeEnabled() { return flyMode; }
    public double getJobBoostMultiplier() { return jobBoostMultiplier; }
    public int getJobBoostDuration() { return jobBoostDuration; }
    public File getModelFile() { return modelFile; }
    public boolean isFood() { return food; }
    public int getFoodNutrition() { return foodNutrition; }
    public float getFoodSaturation() { return foodSaturation; }
    public boolean isFoodCanAlwaysEat() { return foodCanAlwaysEat; }
    public @NotNull List<FoodEffect> getFoodEffects() { return foodEffects; }

    @Override
    public String toString() {
        return "CustomItemData{type=" + type + ", name='" + name + "', modelData=" + modelData +
                ", actionType='" + actionType + "'}";
    }
}
