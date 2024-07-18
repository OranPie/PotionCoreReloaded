package xyz.yyg0725.potioncore.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public class PotionEffectStepUp extends StatusEffect {

    public float STEP_HEIGHT_INIT = 0;
    public PotionEffectStepUp() {
        super(StatusEffectType.BENEFICIAL, 0x00FF00); // BENEFICIAL type and color (Green)
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true; // 每个tick更新
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        STEP_HEIGHT_INIT = entity.stepHeight;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.stepHeight = 0.6f + amplifier * 0.5f;
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier){
        entity.stepHeight = STEP_HEIGHT_INIT;
    }
}
