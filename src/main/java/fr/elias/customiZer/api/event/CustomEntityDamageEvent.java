package fr.elias.customiZer.api.event;

import fr.elias.customiZer.api.entity.CustomEntity;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class CustomEntityDamageEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final CustomEntity entity;
    private final double damage;

    public CustomEntityDamageEvent(CustomEntity entity, double damage) {
        this.entity = entity;
        this.damage = damage;
    }

    public CustomEntity getEntity() {
        return entity;
    }

    public double getDamage() {
        return damage;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
