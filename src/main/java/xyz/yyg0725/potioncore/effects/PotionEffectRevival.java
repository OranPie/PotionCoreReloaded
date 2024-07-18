package xyz.yyg0725.potioncore.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import xyz.yyg0725.potioncore.events.RevivalEventHandler;

import static net.minecraft.entity.effect.StatusEffectType.*;

public class PotionEffectRevival extends StatusEffect {

    public PotionEffectRevival() {
        super(BENEFICIAL, 0xF0E68C); // BENEFICIAL type and color (Light Goldenrod Yellow)
        RevivalEventHandler.register(); // Registering the event handler
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        // Lock health to maximum health
        // return void;
        if (!entity.world.isClient) entity.setHealth(entity.getHealth());
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        // Continue updating effect over time
        return true;
    }

    public static class PotionEffectRepair extends StatusEffect {

        public PotionEffectRepair() {
            super(BENEFICIAL, 0xE6F0BC); // BENEFICIAL type and color (Light Goldenrod Yellow)
            RevivalEventHandler.register(); // Registering the event handler
        }

        @Override
        public void applyUpdateEffect(LivingEntity entity, int amplifier) {
            if (entity instanceof PlayerEntity){
                double maxMotion = -0.4D / (amplifier + 1);
                // if (entity.spe)
            }
        }


        @Override
        public boolean canApplyUpdateEffect(int duration, int amplifier) {
            // Continue updating effect over time
            return true;
        }
    }

    public static class PotionEffectTeleport extends StatusEffect {

        public PotionEffectTeleport() {
            super(BENEFICIAL, 0xE6F0BC); // BENEFICIAL type and color (Light Goldenrod Yellow)
            RevivalEventHandler.register(); // Registering the event handler
        }

        @Override
        public void applyUpdateEffect(LivingEntity entity, int amplifier) {
            if (entity instanceof PlayerEntity){
                double maxMotion = -0.4D / (amplifier + 1);
                // if (entity.spe)
            }
        }


        @Override
        public boolean canApplyUpdateEffect(int duration, int amplifier) {
            // Continue updating effect over time
            return true;
        }
    }
}
