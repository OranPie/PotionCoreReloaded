package xyz.yyg0725.potioncore.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import xyz.yyg0725.potioncore.PotionCoreReloaded;
import xyz.yyg0725.potioncore.events.RevivalEventHandler;

import static net.minecraft.entity.effect.StatusEffectType.BENEFICIAL;

public class PotionEffectSpin extends StatusEffect {

    public PotionEffectSpin() {
        super(BENEFICIAL, 0xE6F0BC); // BENEFICIAL type and color (Light Goldenrod Yellow)
        RevivalEventHandler.register(); // Registering the event handler
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        // wip
        if (!entity.world.isClient && entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;

            float maxRotation = 8.0F;
            float maxRand = maxRotation * (amplifier + 1);
            float pitch = player.getDataTracker().get(PotionCoreReloaded.SPIN_PITCH);
            float yaw = player.getDataTracker().get(PotionCoreReloaded.SPIN_YAW);
            float randPitch = player.getRandom().nextFloat() * maxRand * 2.0F - maxRand;
            float randYaw = player.getRandom().nextFloat() * maxRand * 2.0F - maxRand;

            if (MathHelper.abs(pitch + randPitch) > maxRotation) {
                pitch -= randPitch;
            } else {
                pitch += randPitch;
            }

            if (MathHelper.abs(yaw + randYaw) > maxRotation) {
                yaw -= randYaw;
            } else {
                yaw += randYaw;
            }

            player.setHeadYaw(player.headYaw + yaw);
            player.setBodyYaw(player.bodyYaw + yaw);
            player.yaw = player.yaw + yaw;
            player.pitch = player.getPitch(0) + pitch;
            // Calculate new pitch angle (in radians)
            float newPitchRadians = (float) Math.toRadians(pitch);  // Example: 45 degrees pitch

            // Calculate new direction vector
            Vec3d lookVector = entity.getRotationVector();  // Get current look vector
            double distance = lookVector.length();  // Get the distance
            double x = -Math.sin(newPitchRadians) * distance;  // Calculate new x coordinate
            double y = Math.cos(newPitchRadians) * distance;   // Calculate new y coordinate

            // Set new look vector
            entity.setVelocity(x, y, lookVector.z);
            player.prevPitch = player.pitch;
            player.prevYaw = player.yaw;
            player.prevBodyYaw = player.bodyYaw;
            player.prevHeadYaw = player.headYaw;

            player.getDataTracker().set(PotionCoreReloaded.SPIN_PITCH, pitch);
            player.getDataTracker().set(PotionCoreReloaded.SPIN_YAW, yaw);
        }
    }


    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        // Continue updating effect over time
        return true;
    }
}
