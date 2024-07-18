package xyz.yyg0725.potioncore;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import xyz.yyg0725.potioncore.commands.PotionCoreCheckpack;
import xyz.yyg0725.potioncore.effects.*;
import xyz.yyg0725.potioncore.events.MagicShieldEventHandler;
import xyz.yyg0725.potioncore.items.CorePotionItem;
import xyz.yyg0725.potioncore.events.NoItemPickupHandler;
import xyz.yyg0725.potioncore.items.KillTesterItem;

public class PotionCoreReloaded implements ModInitializer {

    // Custom creative tab for PotionCore added items
    public static final ItemGroup POTIONCORE_TAB = new ItemGroup(9,"potioncore_tab") {
        @Override
        public ItemStack createIcon() {
            // Return an ItemStack representing the icon of your creative tab
            return new ItemStack(Registry.ITEM.get(new Identifier("potioncore", "revival_potion")));
        }
    };

    // effects
    public static final StatusEffect REVIVAL_EFFECT = new PotionEffectRevival();
    public static final StatusEffect MAGIC_SHIELD_EFFECT = new PotionEffectMagicshield();
    public static final StatusEffect FLIGHT_EFFECT = new PotionEffectFlight();
    public static final StatusEffect SPIN_EFFECT = new PotionEffectSpin();
    public static final StatusEffect SLOWFALL_EFFECT = new PotionEffectSlowfall();
    public static final StatusEffect LOVE_EFFECT = new PotionEffectLove();
    public static final StatusEffect REPAIR_EFFECT = new PotionEffectRepair();
    public static final StatusEffect DISORGANIZATION_EFFECT = new PotionEffectDisorganization();
    public static final StatusEffect EXTENSION_EFFECT = new PotionEffectExtension();
    public static final StatusEffect DROWN_EFFECT = new PotionEffectDrown();
    public static final StatusEffect FIRE_EFFECT = new PotionEffectFire();
    public static final StatusEffect RECOIL_EFFECT = new PotionEffectRecoil();
    public static final StatusEffect EXPLODE_EFFECT = new PotionEffectExplode();
    public static final StatusEffect KLUTZ_EFFECT = new PotionEffectKlutz();
    public static final StatusEffect RUST_EFFECT = new PotionEffectRust();
    public static final StatusEffect MAGIC_INHIBITION_EFFECT = new PotionEffectMagicInhibition();
    public static final StatusEffect VULNERABLE_EFFECT = new PotionEffectVulnerable();
    public static final StatusEffect WEIGHT_EFFECT = new PotionEffectWeight();
    public static final StatusEffect LIGHTNING_EFFECT = new PotionEffectLightning();
    public static final StatusEffect SOLID_CORE_EFFECT = new PotionEffectSolidCore();
    public static final StatusEffect PURITY_EFFECT = new PotionEffectPurity();
    public static final StatusEffect MAGIC_FOCUS_EFFECT = new PotionEffectMagicFocus();
    public static final StatusEffect REACH_EFFECT = new PotionEffectReach();
    public static final StatusEffect STEP_UP_EFFECT = new PotionEffectStepUp();
    public static final StatusEffect TELEPORT_SPAWN_EFFECT = new PotionEffectTeleportSpawn();
    public static final StatusEffect TELEPORT_EFFECT = new PotionEffectTeleport();


    // disabled effects
    // public static final StatusEffect FUCK_EFFECT = new PotionEffectFuck();
    // public static final StatusEffect LAUNCH_EFFECT = new PotionEffectLaunch();
    // public static final StatusEffect CLIMB_EFFECT = new PotionEffectClimb();
    // public static final StatusEffect PERPLEXITY_EFFECT = new PotionEffectPerplexity();

    // commands
    public final PotionCoreCheckpack CHECKPACK_COMMAND = new PotionCoreCheckpack();

    // data trackers
    public static final TrackedData<Float> SPIN_PITCH = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.FLOAT);
    public static final TrackedData<Float> SPIN_YAW = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.FLOAT);
    public static final TrackedData<Integer> DROWN_AIR = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.INTEGER);

    // items

    public static final Item KILL_TESTER_REMOVE = new KillTesterItem(new Item.Settings().group(ItemGroup.MISC), KillTesterItem.KillEffect.REMOVE);
    public static final Item KILL_TESTER_SET_REMOVED_TRUE = new KillTesterItem(new Item.Settings().group(ItemGroup.MISC), KillTesterItem.KillEffect.SET_REMOVED);
    public static final Item KILL_TESTER_KILL_COMMAND = new KillTesterItem(new Item.Settings().group(ItemGroup.MISC), KillTesterItem.KillEffect.KILL);
    public static final Item KILL_TESTER_SET_HEALTH_0 = new KillTesterItem(new Item.Settings().group(ItemGroup.MISC), KillTesterItem.KillEffect.SET_HEALTH);



    @Override
    public void onInitialize() {
        // Register your custom potion effects
        registerPotionEffect("revival", REVIVAL_EFFECT);
        // TODO: KILL COMMAND TEST PASSED !!!!!!
        registerPotionEffect("magic_shield", MAGIC_SHIELD_EFFECT);
        // TODO: TEST PASSED + NEW FEATURE
        registerPotionEffect("flight", FLIGHT_EFFECT);
        // TODO: TEST PASSED
        // registerPotionEffect("spin", SPIN_EFFECT);
        // registerPotionEffect("slowfall", SLOWFALL_EFFECT);
        registerPotionEffect("love", LOVE_EFFECT);
        // TODO: TEST PASSED
        registerPotionEffect("disorganization", DISORGANIZATION_EFFECT);
        // TODO: TEST PASSED
        registerPotionEffect("repair", REPAIR_EFFECT);
        // TODO: TEST PASSED + NEW FEATURE edited? [
        registerPotionEffect("extension", EXTENSION_EFFECT);
        // TODO: TEST PASSED + NEW FEATURE
        registerPotionEffect("drown", DROWN_EFFECT);
        // TODO: TEST PASSED
        registerPotionEffect("fire", FIRE_EFFECT);
        // TODO: TEST PASSED + NEW FEATURE
        registerPotionEffect("recoil", RECOIL_EFFECT);
        // TODO: TEST PASSED + NEW FEATURE
        registerPotionEffect("explode", EXPLODE_EFFECT);
        // TODO: TEST PASSED + NEW FEATURE
        registerPotionEffect("klutz", KLUTZ_EFFECT);
        // TODO: TEST PASSED + NEW FEATURE
        registerPotionEffect("rust", RUST_EFFECT);
        // TODO: TEST PASSED + NEW FEATURE
        registerPotionEffect("vulnerable", VULNERABLE_EFFECT);
        // TODO: TEST PASSED + NF
        registerPotionEffect("weight", WEIGHT_EFFECT);
        // TODO: TEST PASSED + NF
        registerPotionEffect("lightning", LIGHTNING_EFFECT);
        // TODO: TEST PASSED + NF
        registerPotionEffect("purity", PURITY_EFFECT);
        // TODO: TEST PASSED
        registerPotionEffect("reach", REACH_EFFECT);
        // TODO: TEST PASSED + NF
        registerPotionEffect("step_up", STEP_UP_EFFECT);
        // TODO: TEST PASSED + NF
        registerPotionEffect("teleport_spawn", TELEPORT_SPAWN_EFFECT);
        // TODO: TEST PASSED
        registerPotionEffect("teleport", TELEPORT_EFFECT);
        // TODO: TEST PASSED

        registerPotionEffect("magic_focus", MAGIC_FOCUS_EFFECT);
        // TODO: TEST [UNUSABLE - ATTACKER NOT GET]
        registerPotionEffect("magic_inhibition", MAGIC_INHIBITION_EFFECT);
        // TODO: TEST [UNUSABLE - ATTACKER NOT GET]
        // registerPotionEffect("fuck", FUCK_EFFECT);
        // registerPotionEffect("perplexity", PERPLEXITY_EFFECT);
        // registerPotionEffect("climb", CLIMB_EFFECT);
        // registerPotionEffect("launch", LAUNCH_EFFECT);
        // TODO: DONE IT

        // EXPLAIN: NEW FEATURE = USED AMPLIFIER


        // EFFECTS TODO: ADD MORE EFFECTS
        // EFFECTS TODO: ADD TRADE EFFECT- NO
        registerItemTester();
        registerCheckBackpackCommand();
        registerTickEvent();
        registerMagicShield();
        registerRecoil();
        NoItemPickupHandler.register();
    }

    private void registerPotionEffect(String id, StatusEffect effect) {
        Registry.register(Registry.STATUS_EFFECT, new Identifier("potioncore", id), effect);
        CorePotionItem.register(id + "_potion", REVIVAL_EFFECT);
    }

    private void registerItemTester() {
        Registry.register(Registry.ITEM, new Identifier("potioncore", "kill_tester_remove.json"), KILL_TESTER_REMOVE);
        Registry.register(Registry.ITEM, new Identifier("potioncore", "kill_tester_set_removed_true"), KILL_TESTER_SET_REMOVED_TRUE);
        Registry.register(Registry.ITEM, new Identifier("potioncore", "kill_tester_command"), KILL_TESTER_KILL_COMMAND);
        Registry.register(Registry.ITEM, new Identifier("potioncore", "kill_tester_set_health_0"), KILL_TESTER_SET_HEALTH_0);
    }

    private void registerCheckBackpackCommand() {
        PotionCoreCheckpack.register();
    }
    private void registerTickEvent() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (server.getTicks() % 20 == 0) {
                // 每20 tick执行一次背包检查
                server.getPlayerManager().getPlayerList().forEach(PotionCoreCheckpack::checkBackpack);
            }
        });
    }

    private void registerMagicShield() {
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (entity instanceof PlayerEntity) {
                PotionEffectMagicshield.handleDamage((PlayerEntity) entity, DamageSource.MAGIC, 0);
            }
            return ActionResult.PASS;
        });

        MagicShieldEventHandler.register();
    }

    private void registerRecoil() {
    }



}
