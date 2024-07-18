package xyz.yyg0725.potioncore.events;

import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import xyz.yyg0725.potioncore.PotionCoreReloaded;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class NoItemPickupHandler {

    // 注册事件处理器
    // 注册事件处理器
    public static void register() {
        UseItemCallback.EVENT.register(NoItemPickupHandler::onItemUse);
    }

    // 拦截玩家使用物品的事件，并取消使用该物品
    private static TypedActionResult<ItemStack> onItemUse(PlayerEntity player, World world, Hand hand) {
        ItemStack heldItem = player.getStackInHand(hand);

        // 检查玩家使用的物品是否属于模组的 Creative Tab
        if (heldItem.getItem().getGroup() == PotionCoreReloaded.POTIONCORE_TAB) {
            // 如果是创造模式且尝试使用属于该 Creative Tab 的物品，则取消使用操作
            player.sendMessage(new LiteralText("This item cannot be gave/used."), false); // 发送提示消息给玩家
            heldItem.setCount(0);
            return TypedActionResult.fail(heldItem);
        }

        // 允许正常使用其他物品
        return TypedActionResult.pass(heldItem);
    }
}
