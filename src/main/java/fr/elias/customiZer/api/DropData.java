package fr.elias.customiZer.api;

/**
 * Represents a single drop entry for a CustomiZer block reward.
 */
public class DropData {
    private final String type;
    private final String drop;
    private final double chance;
    private final String message;
    private final int modelData;
    private final String name;
    private final String lore;

    public DropData(String type, String drop, double chance, String message, int modelData, String name, String lore) {
        this.type = type;
        this.drop = drop;
        this.chance = chance;
        this.message = message;
        this.modelData = modelData;
        this.name = name;
        this.lore = lore;
    }

    public String getType() { return type; }
    public String getDrop() { return drop; }
    public double getChance() { return chance; }
    public String getMessage() { return message; }
    public int getModelData() { return modelData; }
    public String getName() { return name; }
    public String getLore() { return lore; }
}
