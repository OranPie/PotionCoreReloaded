package xyz.yyg0725.potioncore.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.player.PlayerEntity;
import xyz.yyg0725.potioncore.PotionCoreReloaded;

import java.util.Objects;

public class PotionEffectMagicshield extends StatusEffect {
    // IMPROVE TEXTURES AND DAMAGE SOURCE FIX
    public PotionEffectMagicshield() {
        super(StatusEffectType.BENEFICIAL, 0x800080); // BENEFICIAL type and color (Purple)
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        // This method can be left empty if no periodic update is needed
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    // Method to render the magic shield effect around the armor bar
    /*
    public static void renderMagicShieldEffect(MatrixStack matrices, int x, int y, int amplifier) {
        // Render the shield icon based on the amplifier level
        for (int i = 0; i <= amplifier; i++) {
            DrawableHelper.drawTexture(matrices, x + i * 8, y, 0, 0, 9, 9, 9, 9);
        }
    }
    */

    // Method to handle damage and apply magic shield effect
    public static float handleDamage(PlayerEntity entity, DamageSource source, float amount) {
        if (entity instanceof PlayerEntity) {
            if (source.isMagic()) {
                // Check if the player has the MagicShield effect
                if (entity.hasStatusEffect(PotionCoreReloaded.MAGIC_SHIELD_EFFECT)) { // Ensure ModEffects.MAGICSHIELD is defined
                    // entity.sendMessage(new LiteralText(String.format("[DEBUG] entity effects: %s.", entity.getStatusEffects())), false);
                    int amplifier = Objects.requireNonNull(entity.getStatusEffect(PotionCoreReloaded.MAGIC_SHIELD_EFFECT)).getAmplifier();
                    int shieldLevel = amplifier + 1;
                    double damageReduction = shieldLevel * 1.5;

                    // Reduce the damage

                    return (float) Math.max(0, amount - damageReduction);
                }
            }
            // Apply the original damage if no shield is present
            return amount;
        }
        return amount;
    }
}
