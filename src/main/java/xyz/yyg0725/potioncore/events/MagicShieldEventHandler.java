package xyz.yyg0725.potioncore.events;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import xyz.yyg0725.potioncore.PotionCoreReloaded;
import xyz.yyg0725.potioncore.effects.PotionEffectMagicshield;

public class MagicShieldEventHandler {
    public static void register() {
        // Registering HUD render callback
        HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player != null && client.player.hasStatusEffect(PotionCoreReloaded.MAGIC_SHIELD_EFFECT)) {
                int amplifier = client.player.getStatusEffect(PotionCoreReloaded.MAGIC_SHIELD_EFFECT).getAmplifier();
                int screenWidth = client.getWindow().getScaledWidth();
                int screenHeight = client.getWindow().getScaledHeight();
                int x = screenWidth / 2 - 91;
                int y = screenHeight - 39; // Adjust this value to fit your needs
                // PotionEffectMagicshield.renderMagicShieldEffect(matrixStack, x, y, amplifier);
            }
        });

        // Registering server tick event to handle damage
        /*
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            world.getPlayers().forEach(player -> {
                if (player.hasStatusEffect(PotionCoreReloaded.MAGICSHIELD_EFFECT)) {
                    PotionEffectMagicshield.handleDamage(player, DamageSource.MAGIC, 0);
                }
            });
        });
        */
    }
}
