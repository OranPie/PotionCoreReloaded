package xyz.yyg0725.potioncore.effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.List;

public class PotionEffectLove extends StatusEffect {

    public PotionEffectLove() {
        super(StatusEffectType.BENEFICIAL, 0xE6F0BC); // BENEFICIAL type and color (Light Goldenrod Yellow)
    }

    @Override
    public void applyUpdateEffect(LivingEntity targetEntity, int amplifier) {
        if (targetEntity instanceof PlayerEntity) {
            World world = targetEntity.world;
            List<LivingEntity> nearbyEntities = world.getEntitiesByClass(LivingEntity.class, targetEntity.getBoundingBox().expand(5.0), entity -> true);

            for (LivingEntity nearbyEntity : nearbyEntities) {
                if (nearbyEntity != targetEntity && nearbyEntity.isAlive()) {
                    customEffect(null, (PlayerEntity) targetEntity, nearbyEntity, amplifier, -0.4D / (amplifier + 1));
                }
            }
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        // Continue updating effect over time
        return true;
    }

    // Example custom method similar to func_180793_a
    public void customEffect(Entity thrownPotion, PlayerEntity thrower, LivingEntity affectedEntity, int amplifier, double potency) {
        if (affectedEntity instanceof AnimalEntity) {
            PlayerEntity player = thrower instanceof PlayerEntity ? thrower : null;
            ((AnimalEntity) affectedEntity).lovePlayer(player); // Replace with appropriate method call
        } else {
            effectParticles(affectedEntity, amplifier);
        }
    }

    // Example of particle effect method
    private void effectParticles(LivingEntity entity, int amplifier) {
        if (entity instanceof AnimalEntity) {
            ((AnimalEntity) entity).lovePlayer(null); // Replace with appropriate method call
        } else if (entity.world instanceof ServerWorld) {
            ServerWorld world = (ServerWorld) entity.world;
            world.spawnParticles(ParticleTypes.HEART,
                    entity.getX(), entity.getY(), entity.getZ(),
                    20, 0.5D, 2.0D, 0.5D, 0.0D);
        }
    }
}
