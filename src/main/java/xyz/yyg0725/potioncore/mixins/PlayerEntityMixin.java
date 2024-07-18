package xyz.yyg0725.potioncore.mixins;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.yyg0725.potioncore.PotionCoreReloaded;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(method = "initDataTracker", at = @At("HEAD"))
    private void initDataTracker(CallbackInfo ci) {
        DataTracker dataTracker = ((PlayerEntity) (Object) this).getDataTracker();
        dataTracker.startTracking(PotionCoreReloaded.SPIN_PITCH, 0.0f);
        dataTracker.startTracking(PotionCoreReloaded.SPIN_YAW, 0.0f);
        dataTracker.startTracking(PotionCoreReloaded.DROWN_AIR, 150);
        // System.out.println(dataTracker.get(PotionCoreReloaded.SPIN_PITCH));
    }

    @Inject(method = "damage", at = @At("HEAD"))
    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (((Object) this) instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) (Object) this;
            // Example: Apply recoil effect if the player has a specific status effect
            if (source.getAttacker() instanceof LivingEntity) {
                LivingEntity attacker = (LivingEntity) source.getAttacker();
                // System.out.println(player.hasStatusEffect(PotionCoreReloaded.RECOIL_EFFECT));
                if (player.hasStatusEffect(PotionCoreReloaded.RECOIL_EFFECT)) {
                    int amplifier = player.getStatusEffect(PotionCoreReloaded.RECOIL_EFFECT).getAmplifier();
                    float recoilDamage = amplifier * 1.2f;
                    // System.out.println(recoilDamage);
                    attacker.damage(DamageSource.MAGIC, recoilDamage);
                }
            }
        }
    }

    @Inject(method = "remove", at = @At("HEAD"), cancellable = true)
    private void onRemove(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (player.hasStatusEffect(PotionCoreReloaded.REVIVAL_EFFECT)) {
            Style style = Style.EMPTY.withColor(Formatting.GOLD).withBold(true); // 设置字体颜色为绿色，加粗，并增加字号
            LiteralText text = (LiteralText) new LiteralText("YOU'LL BE ALIVE!").setStyle(style);
            player.removeStatusEffect(PotionCoreReloaded.REVIVAL_EFFECT);
            player.sendMessage(text, true);
            ci.cancel();
        }
    }
    /*
    @Inject(method = "jump", at = @At("HEAD"), cancellable = true)
    private void onJump(CallbackInfo info) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (player.hasStatusEffect(PotionCoreReloaded.STEP_UP_EFFECT)) {
            int amplifier = player.getStatusEffect(PotionCoreReloaded.STEP_UP_EFFECT).getAmplifier();
            float stepHeight = 0.6f + amplifier * 0.5f; // 计算踏步高度
            player.stepHeight = stepHeight;
        }
    }
     */

    /*
    @Inject(method = "tickMovement", at = @At("HEAD"), cancellable = true)
    private void onTickMovement(CallbackInfo info) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (!player.world.isClient) {
            if (player.horizontalCollision && player.hasStatusEffect(PotionCoreReloaded.CLIMB_EFFECT)) {
                System.out.println("Handle it.");
                player.setVelocity(player.getVelocity().x, 0.2, player.getVelocity().z);
                info.cancel();
            }
        }
    }
     */
}
