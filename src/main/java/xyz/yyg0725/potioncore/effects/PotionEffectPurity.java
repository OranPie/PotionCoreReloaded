package xyz.yyg0725.potioncore.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import xyz.yyg0725.potioncore.events.RevivalEventHandler;

import static net.minecraft.entity.effect.StatusEffectType.BENEFICIAL;

public class PotionEffectPurity extends StatusEffect {

    public PotionEffectPurity() {
        super(BENEFICIAL, 0xE6F0BC); // BENEFICIAL type and color (Light Goldenrod Yellow)
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.isAlive()){
            if (entity.hasStatusEffect(StatusEffects.WITHER)){
                entity.removeStatusEffect(StatusEffects.WITHER);

            }
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
