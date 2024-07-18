package xyz.yyg0725.potioncore.commands;

import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.LiteralText;
import xyz.yyg0725.potioncore.PotionCoreReloaded;

public class PotionCoreCheckpack {
    public PotionCoreCheckpack(){

    }
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> dispatcher.register(CommandManager.literal("checkbackpack")
                .executes(context -> {
                    PlayerEntity player = context.getSource().getPlayer();
                    checkBackpack(player);
                    return 1;
                })));
    }

    public static void checkBackpack(PlayerEntity player) {
        // 遍历玩家背包中的物品
        for (ItemStack stack : player.inventory.main) {
            if (stack.getItem().getGroup() == PotionCoreReloaded.POTIONCORE_TAB) {
                // 清除该类物品
                stack.setCount(0);
                // 发送提示消息给玩家
                player.sendMessage(new LiteralText("This item cannot be gave/used."), false);
            }
        }
    }
}
