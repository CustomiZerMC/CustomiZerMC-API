package fr.elias.customiZer.api.event;

import fr.elias.customiZer.api.entity.CustomEntity;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class CustomAnimationStartEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final CustomEntity entity;
    private final String animation;

    public CustomAnimationStartEvent(CustomEntity entity, String animation) {
        this.entity = entity;
        this.animation = animation;
    }

    public CustomEntity getEntity() {
        return entity;
    }

    public String getAnimation() {
        return animation;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
