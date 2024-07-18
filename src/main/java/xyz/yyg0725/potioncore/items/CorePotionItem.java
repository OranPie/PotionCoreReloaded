package xyz.yyg0725.potioncore.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import xyz.yyg0725.potioncore.PotionCoreReloaded;

public class CorePotionItem extends Item {

    private final StatusEffect effect;



    public CorePotionItem(StatusEffect effect, Settings settings) {
        super(settings.maxCount(1));
        this.effect = effect;
    }

    // @Override
    // public int getMaxCount()
    // Register the item with a specific identifier
    public static void register(String id, StatusEffect effect) {
        CorePotionItem potionItem = new CorePotionItem(effect, new Item.Settings().group(PotionCoreReloaded.POTIONCORE_TAB));
        Registry.register(Registry.ITEM, new Identifier("potioncore", id), potionItem);
    }

    // Apply the potion effect on use
    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (!user.world.isClient) {
            applyEffect(entity);
            stack.decrement(1);
        }
        return ActionResult.SUCCESS;
    }

    private void applyEffect(LivingEntity target) {
        StatusEffectInstance effectInstance = new StatusEffectInstance(effect, 200, 0); // Adjust duration and amplifier as needed
        target.addStatusEffect(effectInstance);
    }

}
