package xyz.yyg0725.potioncore.effects;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;
import xyz.yyg0725.potioncore.PotionCoreReloaded;
import xyz.yyg0725.potioncore.events.RevivalEventHandler;

public class PotionEffectDrown extends StatusEffect {

    public PotionEffectDrown() {
        super(StatusEffectType.BENEFICIAL, 0xE6F0BC); // BENEFICIAL type and color (Light Goldenrod Yellow)
        RevivalEventHandler.register(); // Registering the event handler
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;

            // Retrieve drown air from DataTracker or set default
            int air = player.getDataTracker().get(PotionCoreReloaded.DROWN_AIR);

            // Decrease drown air if not submerged in water
            if (!player.isSubmergedInWater()) {
                int respiration = EnchantmentHelper.getEquipmentLevel(Enchantments.RESPIRATION, entity);
                // System.out.println(respiration);
                if (respiration > 0 && player.getRandom().nextInt(respiration + 1) > 0) {
                    air = Math.min(air + 1, player.getMaxAir());
                } else {
                    air -= 2;
                }
            } else {
                if (!player.hasStatusEffect(StatusEffects.WATER_BREATHING)) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 1, 255));
                }
                air += 3;
            }

            // Apply drown damage if air runs out
            if (air <= 0) {
                air = 70;
                if (!player.world.isClient) {
                    player.damage(DamageSource.DROWN, 1.0F);
                }
            }

            // Store updated drown air in DataTracker
            player.getDataTracker().set(PotionCoreReloaded.DROWN_AIR, air);
            // System.out.println(air);
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

}
