package xyz.yyg0725.potioncore.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.player.PlayerEntity;

public class PotionEffectPerplexity extends StatusEffect {

    public PotionEffectPerplexity() {
        super(StatusEffectType.NEUTRAL, 0xF0E68C); // NEUTRAL type and color (Light Goldenrod Yellow)
    }
    // todo: fix
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;

            // Invert movement controls
            float moveSpeed = 0.2f * amplifier;

            // Invert left and right movement
            if (player.forwardSpeed > 0.0F) {
                player.sidewaysSpeed = -moveSpeed;
            } else if (player.forwardSpeed < 0.0F) {
                player.sidewaysSpeed = moveSpeed;
            }

            // Invert forward and backward movement
            if (player.sidewaysSpeed > 0.0F) {
                player.forwardSpeed = -moveSpeed;
            } else if (player.sidewaysSpeed < 0.0F) {
                player.forwardSpeed = moveSpeed;
            }

            // Invert sneaking and jumping
            if (player.isSneaking()) {
                player.setJumping(true);
            } else if (!player.isSneaking()) {
                player.setSneaking(true);
            }
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true; // Continue updating effect over time
    }
}
