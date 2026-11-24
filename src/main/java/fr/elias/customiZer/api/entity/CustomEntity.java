package fr.elias.customiZer.api.entity;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.UUID;

/** A loaded entity. Mutating operations require the server thread. */
public interface CustomEntity {
    UUID id();

    String definitionId();

    boolean playAnimation(String clip);

    /** Invoked on natural completion of this one-shot, on the server thread. */
    boolean playAnimation(String clip, Runnable onComplete);

    void replaceModel(String modelId);

    void stopAnimation();

    void setTarget(LivingEntity target);

    void setHealth(double health);

    void setVisible(Player viewer, boolean visible);

    void despawn();
}
