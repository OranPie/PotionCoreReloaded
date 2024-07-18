package xyz.yyg0725.potioncore.utils;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class TitleUtils {

    // 显示标题
    public static void sendTitle(ServerPlayerEntity player, String title, String subtitle) {
        Text titleText = new LiteralText(title);
        Text subtitleText = new LiteralText(subtitle);

        // 设置标题和副标题
        player.networkHandler.sendPacket(new net.minecraft.network.packet.s2c.play.TitleS2CPacket(
                net.minecraft.network.packet.s2c.play.TitleS2CPacket.Action.TITLE, titleText));
        player.networkHandler.sendPacket(new net.minecraft.network.packet.s2c.play.TitleS2CPacket(
                net.minecraft.network.packet.s2c.play.TitleS2CPacket.Action.SUBTITLE, subtitleText));
    }

    // 清除标题
    public static void clearTitle(ServerPlayerEntity player) {
        player.networkHandler.sendPacket(new net.minecraft.network.packet.s2c.play.TitleS2CPacket(
                net.minecraft.network.packet.s2c.play.TitleS2CPacket.Action.CLEAR, null));
    }
}
