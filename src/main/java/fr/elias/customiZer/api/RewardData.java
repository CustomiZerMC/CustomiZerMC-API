package fr.elias.customiZer.api;

import java.util.List;

/**
 * Reward configuration for a block type registered in blocks.yml.
 */
public class RewardData {
    private final String name;
    private final String type;
    private final List<String> lore;
    private final int modelData;
    private final double jobsxp;
    private final String job;
    private final double money;
    private final double points;
    private final List<DropData> drops;
    private final boolean cancelVanillaDrops;
    private final boolean cancelIADrops;
    private final String soundEffectOnMined;
    private final String visualEffectOnMined;
    private final List<String> bannedTools;

    public RewardData(String name, String type, List<String> lore, int modelData, double jobsxp, String job,
                      double money, double points, List<DropData> drops, boolean cancelVanillaDrops,
                      boolean cancelIADrops, String soundEffectOnMined, String visualEffectOnMined,
                      List<String> bannedTools) {
        this.name = name;
        this.type = type;
        this.lore = lore;
        this.modelData = modelData;
        this.jobsxp = jobsxp;
        this.job = job;
        this.money = money;
        this.points = points;
        this.drops = drops;
        this.cancelVanillaDrops = cancelVanillaDrops;
        this.cancelIADrops = cancelIADrops;
        this.soundEffectOnMined = soundEffectOnMined;
        this.visualEffectOnMined = visualEffectOnMined;
        this.bannedTools = bannedTools;
    }

    public String getName() { return name; }
    public String getType() { return type; }
    public List<String> getLore() { return lore; }
    public int getModelData() { return modelData; }
    public double getJobsxp() { return jobsxp; }
    public String getJob() { return job; }
    public double getMoney() { return money; }
    public double getPoints() { return points; }
    public List<DropData> getDrops() { return drops; }
    public boolean isCancelVanillaDrops() { return cancelVanillaDrops; }
    public boolean isCancelIADrops() { return cancelIADrops; }
    public String getSoundEffectOnMined() { return soundEffectOnMined; }
    public String getVisualEffectOnMined() { return visualEffectOnMined; }
    public List<String> getBannedTools() { return bannedTools; }
}
