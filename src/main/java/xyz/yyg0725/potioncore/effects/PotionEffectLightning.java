package xyz.yyg0725.potioncore.effects;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import xyz.yyg0725.potioncore.events.RevivalEventHandler;

public class PotionEffectLightning extends StatusEffect {

    public PotionEffectLightning() {
        super(StatusEffectType.BENEFICIAL, 0xFFFACD); // BENEFICIAL type and color (Lemon Chiffon)
        RevivalEventHandler.register(); // Registering the event handler
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity && !entity.world.isClient) {
            PlayerEntity player = (PlayerEntity) entity;
            ServerWorld world = (ServerWorld) player.world;
            BlockPos pos = player.getBlockPos();

            // Generate lightning at player's position
            LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(world);
            if (lightning != null) {
                lightning.refreshPositionAfterTeleport(pos.getX(), pos.getY(), pos.getZ());
                world.spawnEntity(lightning);
            }
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
