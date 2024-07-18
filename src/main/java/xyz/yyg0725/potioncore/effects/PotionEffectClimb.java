package xyz.yyg0725.potioncore.effects;

import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PotionEffectClimb extends StatusEffect {

    public PotionEffectClimb() {
        super(StatusEffectType.BENEFICIAL, 0xA0522D); // 棕色代表攀爬效果
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        // 每tick更新效果
        return true;
    }
}
