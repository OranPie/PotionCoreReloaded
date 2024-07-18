package xyz.yyg0725.potioncore.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import xyz.yyg0725.potioncore.PotionCoreReloaded;

import java.util.Random;

public class PotionEffectRecoil extends StatusEffect {

    public PotionEffectRecoil() {
        super(StatusEffectType.BENEFICIAL, 0xE6F0BC); // BENEFICIAL type and color (Light Goldenrod Yellow)
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        // This method can be used to apply ongoing effects, but in this case, it might be empty
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true; // Continuously apply this effect
    }

}
