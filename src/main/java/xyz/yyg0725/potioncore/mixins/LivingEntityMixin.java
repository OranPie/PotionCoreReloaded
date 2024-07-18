package xyz.yyg0725.potioncore.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.yyg0725.potioncore.PotionCoreReloaded;

import java.util.Objects;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Unique
    private boolean isHandlingDamage = false;

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (isHandlingDamage) {
            return; // Avoid recursion
        }

        LivingEntity entity = (LivingEntity) (Object) this;
        LivingEntity attacker = (LivingEntity) source.getAttacker();
        float taked = amount;

        // bad_effects
        if (attacker != null && attacker.hasStatusEffect(PotionCoreReloaded.KLUTZ_EFFECT) && source.isProjectile()) {
            int klutzAmplifier = Objects.requireNonNull(attacker.getStatusEffect(PotionCoreReloaded.KLUTZ_EFFECT)).getAmplifier() + 1;
            float klutzReduction = klutzAmplifier * 2.0f;
            taked -= klutzReduction;
        }

        if (attacker != null && attacker.hasStatusEffect(PotionCoreReloaded.MAGIC_INHIBITION_EFFECT) && source.isMagic()) {
            int magicInhibitionAmplifier = Objects.requireNonNull(attacker.getStatusEffect(PotionCoreReloaded.MAGIC_INHIBITION_EFFECT)).getAmplifier() + 1;
            float magicInhibitionReduction = taked * (magicInhibitionAmplifier * 0.3f);
            taked -= magicInhibitionReduction;
        }
        if (attacker != null && attacker.hasStatusEffect(PotionCoreReloaded.MAGIC_FOCUS_EFFECT) && source.isMagic()) {
            int magicInhibitionAmplifier = Objects.requireNonNull(attacker.getStatusEffect(PotionCoreReloaded.MAGIC_INHIBITION_EFFECT)).getAmplifier() + 1;
            float magicInhibitionReduction = taked * (magicInhibitionAmplifier * 0.75f);
            taked += magicInhibitionReduction;
        }

        if (entity.hasStatusEffect(PotionCoreReloaded.VULNERABLE_EFFECT)) {
            int vulnerableAmplifier = Objects.requireNonNull(entity.getStatusEffect(PotionCoreReloaded.VULNERABLE_EFFECT)).getAmplifier() + 1;
            float vulnerableAddition = taked * (vulnerableAmplifier * 0.8f);
            taked += vulnerableAddition;
        }

        if (entity.hasStatusEffect(PotionCoreReloaded.REVIVAL_EFFECT) && entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            if (amount > player.getMaxHealth() && source == DamageSource.OUT_OF_WORLD){
                Style style = Style.EMPTY.withColor(Formatting.GOLD).withBold(true); // 设置字体颜色为绿色，加粗，并增加字号
                LiteralText text = (LiteralText) new LiteralText("YOU'LL BE ALIVE!").setStyle(style);
                player.sendMessage(text, true);
                player.setHealth(player.getMaxHealth());
                entity.removeStatusEffect(PotionCoreReloaded.REVIVAL_EFFECT);
                cir.cancel();
                return;
            } else if (amount > player.getMaxHealth() && source == DamageSource.GENERIC) {
                Style style = Style.EMPTY.withColor(Formatting.GOLD).withBold(true); // 设置字体颜色为绿色，加粗，并增加字号
                LiteralText text = (LiteralText) new LiteralText("YOU'LL BE ALIVE!").setStyle(style);
                player.sendMessage(text, true);
                player.setHealth(player.getMaxHealth());
                entity.removeStatusEffect(PotionCoreReloaded.REVIVAL_EFFECT);
                cir.cancel();
                return;
            } else if (amount > player.getHealth()) {
                Style style = Style.EMPTY.withColor(Formatting.GOLD).withBold(true); // 设置字体颜色为绿色，加粗，并增加字号
                LiteralText text = (LiteralText) new LiteralText("YOU'LL BE ALIVE!").setStyle(style);
                player.sendMessage(text, true);
                player.setHealth(player.getMaxHealth());
                entity.removeStatusEffect(PotionCoreReloaded.REVIVAL_EFFECT);
                cir.cancel();
                return;
            }
        }

        if (taked < 0) {
            taked = 0;
        }

        // System.out.println(taked);
        if (taked != amount) {
            isHandlingDamage = true;
            boolean result = entity.damage(source, taked);
            isHandlingDamage = false;
            cir.setReturnValue(result);
        }
    }
    @Inject(method = "setHealth", at = @At("HEAD"), cancellable = true)
    private void onSetHealth(float health, CallbackInfo ci){
        if (((LivingEntity) (Object)this).hasStatusEffect(PotionCoreReloaded.REVIVAL_EFFECT) && health < 1){
            if ((LivingEntity) (Object)this instanceof PlayerEntity){
                Style style = Style.EMPTY.withColor(Formatting.GOLD).withBold(true); // 设置字体颜色为绿色，加粗，并增加字号
                LiteralText text = (LiteralText) new LiteralText("YOU'LL BE ALIVE!").setStyle(style);
                ((PlayerEntity) (Object)this).sendMessage(text, true);
            }
            ((LivingEntity) (Object)this).setHealth(((LivingEntity) (Object)this).getMaxHealth());
            ((LivingEntity) (Object)this).removeStatusEffect(PotionCoreReloaded.REVIVAL_EFFECT);
            ci.cancel();
        }

    }

    /*
    @Inject(method = "applyDamage", at = @At("HEAD"), cancellable = true)
    private void applyDamage(DamageSource source, float amount, CallbackInfo ci) {
        // Check if the attacker is a player with the specified potion effect
        if (source.getAttacker() instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) source.getAttacker();
            if (player.hasStatusEffect(PotionCoreReloaded.LAUNCH_EFFECT)) {
                // Get the amplifier level of the potion effect
                int amplifier = Objects.requireNonNull(player.getStatusEffect(PotionCoreReloaded.LAUNCH_EFFECT)).getAmplifier();

                // Calculate launch height based on amplifier level
                int launchHeight = 6 + (amplifier * 6);

                // Get the entity being damaged
                LivingEntity entity = (LivingEntity) (Object) this;

                // Launch the entity vertically
                entity.setVelocity(entity.getVelocity().x, launchHeight / 20.0D, entity.getVelocity().z);

                // Cancel the original damage application
                ci.cancel();
            }
        }
    }
    */
    @Inject(method = "takeKnockback", at = @At("HEAD"), cancellable = true)
    private void takeKnockback(float f, double d, double e, CallbackInfo ci) {
        // 检查实体是否为玩家
        if ((Object)this instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) (Object) this;
            // 检查是否有免疫击退效果的状态
            if (entity.hasStatusEffect(PotionCoreReloaded.SOLID_CORE_EFFECT)) {
                // 减少击退效果
                ci.cancel();
                return;
            }
        }
    }


}
