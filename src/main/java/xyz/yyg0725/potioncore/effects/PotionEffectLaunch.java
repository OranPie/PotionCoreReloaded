package xyz.yyg0725.potioncore.effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.text.LiteralText;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.yyg0725.potioncore.PotionCoreReloaded;

public class PotionEffectLaunch extends StatusEffect {

    public PotionEffectLaunch() {
        super(StatusEffectType.BENEFICIAL, 0x00FF00); // BENEFICIAL type and color (Green)
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        // No periodic effect update needed for this potion effect
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true; // Keep applying the effect
    }

}