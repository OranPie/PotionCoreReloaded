package xyz.yyg0725.potioncore.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class PotionEffectFire extends StatusEffect {

    public PotionEffectFire() {
        super(StatusEffectType.BENEFICIAL, 0x666666); // BENEFICIAL type and color (Purple)
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) entity;
            World world = player.world;

            // Get the bounding box around the player
            Box boundingBox = new Box(player.getBlockPos()).expand(amplifier * 2.5, 2.5, amplifier * 2.5);

            // Get all entities within the bounding box
            List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, boundingBox, null);

            for (LivingEntity target : entities) {
                if (target != player) {
                    // Apply fire effect to the entity
                    target.setOnFireFor(1); // Adjust the duration as needed
                }
            }
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
