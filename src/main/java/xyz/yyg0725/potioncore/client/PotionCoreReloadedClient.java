package xyz.yyg0725.potioncore.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import xyz.yyg0725.potioncore.utils.RomanNumerals;

import java.util.List;

public class PotionCoreReloadedClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // 在这里添加客户端初始化逻辑
        System.out.println("[PotionCoreReloaded] Initializing client...");
        // 可以注册渲染器、设置客户端事件监听器等
        // 调用渲染方法
        HudRenderCallback.EVENT.register(PotionCoreHUDRenderer::render);

        ItemTooltipCallback.EVENT.register(this::onItemTooltip);
        // TODO: TEST PASSED
        System.out.println("[PotionCoreReloaded] Done!");
    }
    private void onItemTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip) {
        for (int i = 0; i < tooltip.size(); i++) {
            Text line = tooltip.get(i);
            if (line instanceof TranslatableText) {
                TranslatableText translatableText = (TranslatableText) line;
                String text = translatableText.getString();
                if (text.contains("enchantment.level.")) {
                    String[] parts = text.split(" ");
                    if (parts.length >= 2) {
                        String prefix = parts[0];
                        String key = parts[1];
                        if (key.startsWith("enchantment.level.")) {
                            try {
                                int level = Integer.parseInt(key.substring("enchantment.level.".length()));
                                String romanLevel = RomanNumerals.toRoman(level);
                                String newTooltipText = prefix + " " + romanLevel;
                                tooltip.set(i, new TranslatableText(newTooltipText).formatted(Formatting.GRAY));
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }
}
