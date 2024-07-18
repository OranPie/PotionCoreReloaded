package xyz.yyg0725.potioncore.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public class PotionEffectKlutz extends StatusEffect {
    public PotionEffectKlutz() {
        super(StatusEffectType.BENEFICIAL, 0x800080); // BENEFICIAL type and color (Purple)
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        // No periodic update needed
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
