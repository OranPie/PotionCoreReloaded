package xyz.yyg0725.potioncore.effects;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.registry.Registry;
import xyz.yyg0725.potioncore.events.RevivalEventHandler;

import static net.minecraft.entity.effect.StatusEffectType.BENEFICIAL;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class PotionEffectExtension extends StatusEffect {

    private final Random random = new Random();

    public PotionEffectExtension() {
        super(BENEFICIAL, 0xE6F0BC); // BENEFICIAL type and color (Light Goldenrod Yellow)
        RevivalEventHandler.register(); // Registering the event handler
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.world.getTime() % 10 == 0) {
            if (entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entity;

                // 遍历玩家背包中的所有物品
                for (ItemStack itemStack : player.inventory.main) {
                    // 获取所有非诅咒附魔列表
                    List<Enchantment> enchantments = Registry.ENCHANTMENT.stream()
                            .filter(enchantment -> !enchantment.isCursed())
                            .collect(Collectors.toList());

                    Enchantment enchantment = enchantments.get(random.nextInt(enchantments.size()));

                    // 获取当前附魔等级
                    int currentLevel = EnchantmentHelper.getLevel(enchantment, itemStack);

                    // 计算下一个附魔等级（每次增加1级，但不超过最大等级）
                    int nextLevel = currentLevel + amplifier;
                    try{
                    // 获取物品的 NBT 数据
                    NbtCompound nbt = itemStack.getOrCreateTag();
                    NbtList enchantmentList = nbt.getList("Enchantments", 10); // 10 表示 NBT.TAG_Compound

                    // 移除现有的同类型附魔数据
                    for (int i = 0; i < enchantmentList.size(); i++) {
                        NbtCompound tag = enchantmentList.getCompound(i);
                        String id = tag.getString("id");
                        if (id.equals(String.valueOf(Registry.ENCHANTMENT.getId(enchantment)))) {
                            enchantmentList.remove(i);
                            break;
                        }
                    }

                    // 创建新的附魔数据
                    NbtCompound enchantmentData = new NbtCompound();
                    enchantmentData.putString("id", String.valueOf(Registry.ENCHANTMENT.getId(enchantment)));
                    enchantmentData.putInt("lvl", nextLevel);

                    // 将新的附魔数据添加到列表中
                    enchantmentList.add(enchantmentData);

                    // 更新物品的 NBT 数据
                    nbt.put("Enchantments", enchantmentList);

                        itemStack.writeNbt(nbt);
                    } catch (NullPointerException e) {
                        System.out.println("Error in PotionEffectExtension Applying effect!");
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
