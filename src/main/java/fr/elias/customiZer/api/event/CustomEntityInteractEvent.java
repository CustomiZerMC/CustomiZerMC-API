package fr.elias.customiZer.api.event;

import fr.elias.customiZer.api.entity.CustomEntity;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class CustomEntityInteractEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final CustomEntity entity;
    private final org.bukkit.entity.Player player;

    public CustomEntityInteractEvent(CustomEntity entity, org.bukkit.entity.Player player) {
        this.entity = entity;
        this.player = player;
    }

    public CustomEntity getEntity() {
        return entity;
    }

    public org.bukkit.entity.Player getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
