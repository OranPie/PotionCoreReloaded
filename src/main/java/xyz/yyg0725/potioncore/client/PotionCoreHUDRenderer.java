package xyz.yyg0725.potioncore.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import xyz.yyg0725.potioncore.PotionCoreReloaded;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class PotionCoreHUDRenderer {

    private static final MinecraftClient client = MinecraftClient.getInstance();

    public static void render(MatrixStack matrices, float tickDelta) {
        PlayerEntity player = client.player;
        if (player != null && player.hasStatusEffect(PotionCoreReloaded.DROWN_EFFECT)) {
            int air = player.getDataTracker().get(PotionCoreReloaded.DROWN_AIR);
            if (player.hasStatusEffect(PotionCoreReloaded.MAGIC_SHIELD_EFFECT)){
                renderMagicShield(matrices, (Objects.requireNonNull(player.getStatusEffect(PotionCoreReloaded.MAGIC_SHIELD_EFFECT)).getAmplifier() + 1) * 1.5);
            }
            // Render drown air on the HUD
            renderDrownAir(matrices, air);
        }
    }

    private static void renderDrownAir(MatrixStack matrices, int air) {
        TextRenderer textRenderer = client.textRenderer;
        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        // Calculate position for the drown air text
        int posX = screenWidth - 130; // 调整 X 位置
        int posY = screenHeight - 30; // 调整 Y 位置

        // Render the drown air text
        String drownAirText = String.format("%d", air);
        Text text = new TranslatableText("gui.potioncore.drown_air", drownAirText); // Example translation key
        textRenderer.draw(matrices, text, posX, posY, 0x0077FF); // Blue color

        // You can customize the rendering further, such as adding shadow or centering the text
    }

    private static void renderMagicShield(MatrixStack matrices, double magic) {
        TextRenderer textRenderer = client.textRenderer;
        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        // Calculate position for the drown air text
        int posX = screenWidth - 130; // 调整 X 位置
        int posY = screenHeight - 50; // 调整 Y 位置

        // Render the drown air text
        String MagicShieldText = String.format("%.1f", magic);
        Text text = new TranslatableText("gui.potioncore.magic_shield", MagicShieldText); // Example translation key
        textRenderer.draw(matrices, text, posX, posY, 0x0077FF); // Blue color

        // You can customize the rendering further, such as adding shadow or centering the text
    }
}
