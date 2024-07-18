package xyz.yyg0725.potioncore.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PotionEffectFuck extends StatusEffect {

    public PotionEffectFuck() {
        super(StatusEffectType.BENEFICIAL, 0x00FF00); // BENEFICIAL type and color (Green)
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        World world = entity.getEntityWorld();
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            if (!world.isClient) {
                try {
                    Vec3d lookVec = player.getRotationVec(1.0f);
                    ArrowEntity arrow = new ArrowEntity(world, player);
                    arrow.setProperties(player, player.pitch, player.yaw, 0.0f, 3.0f, 1.0f);
                    world.spawnEntity(arrow);
                }catch (Exception e){
                    return;
                }
            }
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true; // 持续应用效果
    }
}
