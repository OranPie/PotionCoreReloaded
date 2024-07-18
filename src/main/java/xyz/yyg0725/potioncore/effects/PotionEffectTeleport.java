package xyz.yyg0725.potioncore.effects;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.Random;

public class PotionEffectTeleport extends StatusEffect {
    private static final int TELEPORT_RANGE = 30;

    public PotionEffectTeleport() {
        super(StatusEffectType.BENEFICIAL, 0x8A2BE2); // BlueViolet color
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        World world = entity.getEntityWorld();
        BlockPos entityPos = entity.getBlockPos();
        Random random = entity.getRandom();
        if (world.getTime() % 3 == 1){
        // Find a safe teleportation spot within TELEPORT_RANGE blocks
            for (int i = 0; i < 10; i++) { // Try 10 times to find a valid teleport location
                double dx = random.nextDouble() * TELEPORT_RANGE * 2 - TELEPORT_RANGE;
                double dy = random.nextDouble() * 5 * 2 - 3;
                double dz = random.nextDouble() * TELEPORT_RANGE * 2 - TELEPORT_RANGE;

                BlockPos teleportPos = new BlockPos(entityPos.getX() + dx, entityPos.getY() + dy, entityPos.getZ() + dz);

                // Check if the teleport position is safe
                if (isSafeTeleportLocation(world, teleportPos)) {
                    teleportEntity(entity, teleportPos.getX() + 0.5, teleportPos.getY(), teleportPos.getZ() + 0.5);
                    spawnTeleportParticles(entity, world, teleportPos);
                    return;
                }
            }
        }
    }

    private boolean isSafeTeleportLocation(World world, BlockPos pos) {
        // Check if the target position is safe for teleportation (not inside blocks)
        Box box = new Box(pos);
        return world.isSpaceEmpty(box);
    }

    private void teleportEntity(Entity entity, double x, double y, double z) {
        // Teleport the entity to the specified coordinates
        entity.updatePosition(x, y, z);
    }

    private void spawnTeleportParticles(Entity entity, World world, BlockPos pos) {
        if (world instanceof ServerWorld) {
            ServerWorld cworld = (ServerWorld) world;
            // Spawn teleport particles around the entity
            for (int i = 0; i < 20; i++) {
                double offsetX = world.random.nextGaussian() * 0.2;
                double offsetY = world.random.nextGaussian() * 0.2;
                double offsetZ = world.random.nextGaussian() * 0.2;
                cworld.spawnParticles(ParticleTypes.PORTAL,
                        pos.getX() + 0.5,
                        pos.getY() + 1.0,
                        pos.getZ() + 0.5,
                        1,
                        offsetX, offsetY, offsetZ,
                        0.0);  // Adjust particle parameters as needed
            }
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true; // Continue applying update effect
    }
}
