package xyz.yyg0725.potioncore.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import xyz.yyg0725.potioncore.events.RevivalEventHandler;

import static net.minecraft.entity.effect.StatusEffectType.BENEFICIAL;

public class PotionEffectRepair extends StatusEffect {

    public PotionEffectRepair() {
        super(BENEFICIAL, 0xE6F0BC); // BENEFICIAL type and color (Light Goldenrod Yellow)
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;

            // 遍历玩家背包中的所有物品
            for (ItemStack itemStack : player.inventory.main) {
                // 检查物品是否损坏并且需要修复
                if (itemStack.isDamageable() && itemStack.getDamage() > 0) {
                    // 每10 tick自动修复1点耐久
                    if (player.world.getTime() % 20 == 0) {
                        itemStack.setDamage(itemStack.getDamage() - amplifier - 1);
                    }
                }
            }
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
