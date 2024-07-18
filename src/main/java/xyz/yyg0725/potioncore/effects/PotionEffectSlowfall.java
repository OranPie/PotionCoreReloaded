package xyz.yyg0725.potioncore.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.system.CallbackI;
import xyz.yyg0725.potioncore.PotionCoreReloaded;
import xyz.yyg0725.potioncore.events.RevivalEventHandler;

import static net.minecraft.entity.effect.StatusEffectType.BENEFICIAL;

public class PotionEffectSlowfall extends StatusEffect {

    public PotionEffectSlowfall() {
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
