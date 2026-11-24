package fr.elias.customiZer.api.event;

import fr.elias.customiZer.api.entity.CustomEntity;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class CustomEntityDespawnEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final CustomEntity entity;

    public CustomEntityDespawnEvent(CustomEntity entity) {
        this.entity = entity;
    }

    public CustomEntity getEntity() {
        return entity;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
