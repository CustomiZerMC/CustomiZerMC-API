package fr.elias.customiZer.api.event;

import fr.elias.customiZer.api.RewardData;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Fired when a player breaks a block that has CustomiZer reward data.
 *
 * <p>Cancelling this event prevents the rewards (XP, money, drops) from
 * being distributed, but the block break itself is not cancelled.
 *
 * <p>The XP/money/points values reflect per-block amounts before WorldGuard
 * multipliers; the final amounts already include multipliers by the time
 * this event fires. Changing the values on this event overrides what gets paid out.
 */
public class CustomBlockRewardEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Block block;
    private final RewardData rewardData;
    private double jobsXP;
    private double money;
    private double points;
    private boolean cancelled = false;

    public CustomBlockRewardEvent(@NotNull Player player,
                                  @NotNull Block block,
                                  @NotNull RewardData rewardData,
                                  double jobsXP,
                                  double money,
                                  double points) {
        super(player);
        this.block = block;
        this.rewardData = rewardData;
        this.jobsXP = jobsXP;
        this.money = money;
        this.points = points;
    }

    /** The block that was broken. */
    @NotNull public Block getBlock() { return block; }

    /** The reward configuration for this block type. */
    @NotNull public RewardData getRewardData() { return rewardData; }

    /** Jobs XP that will be awarded. Can be modified. */
    public double getJobsXP() { return jobsXP; }
    public void setJobsXP(double jobsXP) { this.jobsXP = jobsXP; }

    /** Money that will be awarded. Can be modified. */
    public double getMoney() { return money; }
    public void setMoney(double money) { this.money = money; }

    /** Points that will be awarded. Can be modified. */
    public double getPoints() { return points; }
    public void setPoints(double points) { this.points = points; }

    @Override public boolean isCancelled() { return cancelled; }
    @Override public void setCancelled(boolean cancel) { this.cancelled = cancel; }
    @Override @NotNull public HandlerList getHandlers() { return HANDLERS; }
    @NotNull public static HandlerList getHandlerList() { return HANDLERS; }
}
