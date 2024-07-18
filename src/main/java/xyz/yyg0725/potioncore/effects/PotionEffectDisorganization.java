package xyz.yyg0725.potioncore.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

public class PotionEffectDisorganization extends StatusEffect {

    public PotionEffectDisorganization() {
        super(StatusEffectType.BENEFICIAL, 0x666666); // BENEFICIAL type and color (Purple)
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!entity.world.isClient && entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) entity;
            PlayerInventory inventory = player.inventory;
            int inventorySize = inventory.size();

            for (int i = 0; i < inventorySize / 2; i++) {
                int slot = player.getRandom().nextInt(inventorySize / 2);
                ItemStack temp = inventory.getStack(i);
                inventory.setStack(i, inventory.getStack(inventorySize / 2 + slot));
                inventory.setStack(inventorySize / 2 + slot, temp);
            }

            player.updateCursorStack();
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
