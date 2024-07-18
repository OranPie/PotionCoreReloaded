package xyz.yyg0725.potioncore.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import xyz.yyg0725.potioncore.events.RevivalEventHandler;

public class PotionEffectWeight extends StatusEffect {

    public PotionEffectWeight() {
        super(StatusEffectType.HARMFUL, 0x6A5ACD); // HARMFUL type and color (Slate Blue)

    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;

            // Reduce movement speed
            EntityAttributeInstance movementSpeed = player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
            if (movementSpeed != null) {
                double currentSpeed = movementSpeed.getValue();
                double reducedSpeed = (255 - amplifier) * 0.0001; // Reduce by 10% per level
                movementSpeed.setBaseValue(reducedSpeed);
            }

            // Reduce jump height
            // player.setJumpVelocity(0.05 * (1.0 - 0.1 * amplifier)); // Reduce by 10% per level
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
