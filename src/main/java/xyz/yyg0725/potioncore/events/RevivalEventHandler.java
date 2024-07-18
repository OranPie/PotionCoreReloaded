package xyz.yyg0725.potioncore.events;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import xyz.yyg0725.potioncore.PotionCoreReloaded;

public class RevivalEventHandler {

    public static void register() {
        return;
        // Registering the event handlers
        // ((PlayerEntity) entity).sendMessage(new LiteralText("Your will being killed."), true);
        /*
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            server.getWorlds().forEach(world -> {
                world.getPlayers().forEach(player -> {
                    if (player.hasStatusEffect(StatusEffects.REGENERATION)) {
                        player.setHealth(player.getMaxHealth());
                    }
                });
            });
        });
        // */
    }
}
