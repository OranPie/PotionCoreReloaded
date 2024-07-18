package xyz.yyg0725.potioncore.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class PotionEffectExplode extends StatusEffect {

    public PotionEffectExplode() {
        super(StatusEffectType.BENEFICIAL, 0xE6F0BC); // BENEFICIAL type and color (Light Goldenrod Yellow)
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity) {
            World world = entity.getEntityWorld();
            if (!world.isClient && world.getTime() % 5 == 1) {
                float explosionPower = 2.0F + amplifier * 0.7F; // Adjust explosion power based on amplifier
                world.createExplosion(null, entity.getX(), entity.getY() - 2, entity.getZ(), explosionPower, Explosion.DestructionType.DESTROY);
            }
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true; // Continue updating effect over time
    }
}
