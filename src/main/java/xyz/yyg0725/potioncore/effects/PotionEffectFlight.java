package xyz.yyg0725.potioncore.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.player.PlayerEntity;

public class PotionEffectFlight extends StatusEffect {

    public PotionEffectFlight() {
        super(StatusEffectType.BENEFICIAL, 0xADD8E6); // BENEFICIAL type and color (Light Blue)
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            if (!player.abilities.allowFlying) {
                player.abilities.allowFlying = true;
                player.sendAbilitiesUpdate();
            }
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }


    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            if (!player.isCreative() && !player.isSpectator()) {
                player.abilities.allowFlying = false;
                player.abilities.flying = false;
                player.sendAbilitiesUpdate();
            }
            super.onRemoved(entity, attributes, amplifier);
        } else {
            super.onRemoved(entity, attributes, amplifier);
        }
    }
}
