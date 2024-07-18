package xyz.yyg0725.potioncore.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.system.CallbackI;
import xyz.yyg0725.potioncore.PotionCoreReloaded;

import java.util.Objects;

public class PotionEffectTeleportSpawn extends StatusEffect {
    private Vec3d lastPlayerPosition;
    private int notMovedTicks;

    public PotionEffectTeleportSpawn() {
        super(StatusEffectType.BENEFICIAL, 0xFF4500); // OrangeRed color
        this.lastPlayerPosition = Vec3d.ZERO;
        this.notMovedTicks = 0;
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) entity;
            if (player.getSpawnPointPosition() == null) {
                player.setSpawnPoint(player.getServerWorld().getRegistryKey(), new BlockPos(player.getPos()), player.yaw, true, true);
            }
        }
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) entity;
            ServerWorld world = player.getServerWorld();

            // Check if player has moved
            Vec3d currentPlayerPos = player.getPos();
            if (currentPlayerPos.equals(this.lastPlayerPosition)) {
                // Player has not moved
                this.notMovedTicks += 2;

                // Check if player has not moved for at least 10 seconds (200 ticks)
                if (this.notMovedTicks >= 200) {
                    if (player.getSpawnPointPosition() != null) {
                        // Calculate teleport target position (e.g., slightly above spawn point)
                        Vec3d targetPos = new Vec3d(player.getSpawnPointPosition().getX() + 0.5,
                                player.getSpawnPointPosition().getY() + 1.1,
                                player.getSpawnPointPosition().getZ() + 0.5);


                        // Check if teleport target is in a different dimension
                        if (player.world.getRegistryKey() != player.getServerWorld().getRegistryKey()) {
                            // Teleport to a different dimension
                            player.teleport(Objects.requireNonNull(player.getServer()).getWorld(player.getSpawnPointDimension()), targetPos.x, targetPos.y, targetPos.z, player.yaw, player.pitch);
                        } else {
                            // Teleport within the same dimension
                            player.teleport(targetPos.x, targetPos.y, targetPos.z);
                        }

                        player.removeStatusEffect(PotionCoreReloaded.TELEPORT_SPAWN_EFFECT);
                    }

                    // Reset notMovedTicks counter
                    this.notMovedTicks = 0;
                }
            } else {
                // Player has moved, reset notMovedTicks counter
                this.notMovedTicks = 0;
            }

            // Update lastPlayerPosition to current position
            this.lastPlayerPosition = currentPlayerPos;
            // Spawn dragon breath particles around the player during teleport

            for (int i = 0; i < 40; i++) {
                double offsetX = player.getRandom().nextGaussian() * 0.2;
                double offsetY = player.getRandom().nextGaussian() * 0.2;
                double offsetZ = player.getRandom().nextGaussian() * 0.2;
                world.spawnParticles(ParticleTypes.DRAGON_BREATH,
                        player.getX() + offsetX,
                        player.getY() + player.getHeight() / 2.0 + offsetY,
                        player.getZ() + offsetZ,
                        1,
                        0.0, 0.0, 0.0,
                        0.0); // Adjust parameters as needed
            }
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true; // Continue applying update effect
    }
}
