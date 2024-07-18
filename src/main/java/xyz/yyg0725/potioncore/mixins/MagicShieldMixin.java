package xyz.yyg0725.potioncore.mixins;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.yyg0725.potioncore.effects.PotionEffectMagicshield;

@Mixin(LivingEntity.class)
public class MagicShieldMixin {

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {
        // 在这里处理实体受伤事件
        if (((LivingEntity)(Object)this).world.isClient) {
            return;
        }

        if ((Object)this instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) (Object) this;
            amount = PotionEffectMagicshield.handleDamage(playerEntity, source, amount);
            // playerEntity.sendMessage(new LiteralText(String.format("[DEBUG] DAMAGE %.2f.", amount)), false);
            playerEntity.setHealth(playerEntity.getHealth() - amount);
            // 设置处理后的伤害值
            info.setReturnValue(true); // 表示伤害已经被处理，取消原始方法的执行
        }
    }
}
