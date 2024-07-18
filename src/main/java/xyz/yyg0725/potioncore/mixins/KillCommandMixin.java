package xyz.yyg0725.potioncore.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.KillCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.yyg0725.potioncore.PotionCoreReloaded;

import java.util.Collection;

@Mixin(KillCommand.class)
public abstract class KillCommandMixin {

    @Inject(method = "execute", at = @At("HEAD"), cancellable = true)
    private static void execute(ServerCommandSource source, Collection<? extends Entity> targets, CallbackInfoReturnable<Integer> cir) {
        for (Entity entity : targets) {
            if (entity instanceof LivingEntity) {
                if (((LivingEntity) entity).hasStatusEffect(PotionCoreReloaded.REVIVAL_EFFECT)) {
                    System.out.println("Handle it. Kill command.");
                    if (entity instanceof PlayerEntity) {
                        Style style = Style.EMPTY.withColor(Formatting.GOLD).withBold(true); // 设置字体颜色为绿色，加粗，并增加字号
                        LiteralText text = (LiteralText) new LiteralText("YOU'LL BE ALIVE!").setStyle(style);
                        ((PlayerEntity)entity).sendMessage(text, true);
                    }
                    cir.setReturnValue(0); // 设置返回值为0，取消执行杀死命令
                    ((LivingEntity) entity).removeStatusEffect(PotionCoreReloaded.REVIVAL_EFFECT);
                    cir.cancel(); // 取消方法继续执行
                    return; // 立即返回，不再处理其他实体
                }
            }
        }
    }
}
