package xyz.yyg0725.potioncore.effects;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public class PotionEffectReach extends StatusEffect {

    public PotionEffectReach() {
        super(StatusEffectType.BENEFICIAL, 0x98D982); // 绿色代表益处效果
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        // 获取并修改触及距离和攻击范围
        EntityAttributeInstance reachAttribute = entity.getAttributeInstance(ReachEntityAttributes.REACH);
        EntityAttributeInstance attackRangeAttribute = entity.getAttributeInstance(ReachEntityAttributes.ATTACK_RANGE);

        if (reachAttribute != null && attackRangeAttribute != null) {
            reachAttribute.setBaseValue(4.5 + amplifier);
            attackRangeAttribute.setBaseValue(3.0 + amplifier);
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true; // 每tick应用效果
    }
}
