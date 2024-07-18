package xyz.yyg0725.potioncore.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class KillTesterItem extends Item {

    private final KillEffect effect;

    public KillTesterItem(Settings settings, KillEffect effect) {
        super(settings);
        this.effect = effect;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (!world.isClient) {
            switch (effect) {
                case REMOVE:
                    player.sendMessage(new LiteralText("If you used, Minecraft will stop work or your use /kill kill yourself"), true);
                    player.remove();
                    player.sendMessage(new LiteralText("Successfully used!"), true);
                    break;
                case SET_REMOVED:
                    player.removed = true;
                    break;
                case KILL:
                    if (player instanceof ServerPlayerEntity) {
                        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                        ServerCommandSource source = serverPlayer.getCommandSource();
                        source.getMinecraftServer().getCommandManager().execute(source, "/kill @s");
                    }
                    break;
                case SET_HEALTH:
                    player.setHealth(0);
                    break;
                default:
                    break;
            }
        }
        return TypedActionResult.success(stack);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) attacker;
            World world = player.getEntityWorld();
            if (!world.isClient) {
                switch (effect) {
                    case REMOVE:
                        target.remove();
                        break;
                    case SET_REMOVED:
                        target.removed = true;
                        break;
                    case KILL:
                        ServerCommandSource source = player.getCommandSource();
                        source.getMinecraftServer().getCommandManager().execute(source, "/kill " + target.getEntityName());
                        break;
                    case SET_HEALTH:
                        target.setHealth(0);
                        break;
                    default:
                        break;
                }
            }
        }
        return true;
    }

    public enum KillEffect {
        REMOVE,
        SET_REMOVED,
        KILL,
        SET_HEALTH
    }
}
