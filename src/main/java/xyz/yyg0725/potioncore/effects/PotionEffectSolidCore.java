package xyz.yyg0725.potioncore.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public class PotionEffectSolidCore extends StatusEffect {

    public PotionEffectSolidCore() {
        super(StatusEffectType.BENEFICIAL, 0xFFD700); // BENEFICIAL type and color (Gold)
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        // 可以留空，因为该药水效果不需要周期性更新
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true; // 持续应用效果
    }

}
