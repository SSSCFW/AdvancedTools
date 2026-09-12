package dev.ssscfw.advancedtools.registry;

import dev.ssscfw.advancedtools.AdvancedTools;
import dev.ssscfw.advancedtools.item.AsmoSlasherItem;
import dev.ssscfw.advancedtools.item.AutomaticCrossbowItem;
import dev.ssscfw.advancedtools.item.BlazeBladeItem;
import dev.ssscfw.advancedtools.item.DevilSwordItem;
import dev.ssscfw.advancedtools.item.EnhancerItem;
import dev.ssscfw.advancedtools.item.HolySaberItem;
import dev.ssscfw.advancedtools.item.IceHoldItem;
import dev.ssscfw.advancedtools.item.InfiniteHoeItem;
import dev.ssscfw.advancedtools.item.LuckySwordItem;
import dev.ssscfw.advancedtools.item.NegiItem;
import dev.ssscfw.advancedtools.item.PlanetGuardianItem;
import dev.ssscfw.advancedtools.item.SmashBatItem;
import dev.ssscfw.advancedtools.item.SpecialSwordItem;
import dev.ssscfw.advancedtools.item.StormBringerItem;
import dev.ssscfw.advancedtools.item.ThrowingKnifeItem;
import dev.ssscfw.advancedtools.item.UpgradedAxeItem;
import dev.ssscfw.advancedtools.item.UpgradedPickaxeItem;
import dev.ssscfw.advancedtools.item.UpgradedShovelItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tiers;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AdvancedTools.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AdvancedTools.MOD_ID);

    public static final DeferredItem<EnhancerItem> RED_ENHANCER = ITEMS.register("redenhancer", () -> new EnhancerItem(Rarity.UNCOMMON));
    public static final DeferredItem<EnhancerItem> BLUE_ENHANCER = ITEMS.register("blueenhancer", () -> new EnhancerItem(Rarity.RARE));

    public static final DeferredItem<UpgradedShovelItem> UG_WOOD_SHOVEL = shovel("ugwoodshovel", Tiers.WOOD, 1.0F, false);
    public static final DeferredItem<UpgradedShovelItem> UG_STONE_SHOVEL = shovel("ugstoneshovel", Tiers.STONE, 1.5F, false);
    public static final DeferredItem<UpgradedShovelItem> UG_IRON_SHOVEL = shovel("ugironshovel", Tiers.IRON, 2.0F, false);
    public static final DeferredItem<UpgradedShovelItem> UG_DIAMOND_SHOVEL = shovel("ugdiamondshovel", Tiers.DIAMOND, 3.0F, false);
    public static final DeferredItem<UpgradedShovelItem> UG_GOLD_SHOVEL = shovel("uggoldshovel", Tiers.GOLD, 2.5F, false);

    public static final DeferredItem<UpgradedPickaxeItem> UG_WOOD_PICKAXE = pickaxe("ugwoodpickaxe", Tiers.WOOD, 1.0F, false);
    public static final DeferredItem<UpgradedPickaxeItem> UG_STONE_PICKAXE = pickaxe("ugstonepickaxe", Tiers.STONE, 1.5F, false);
    public static final DeferredItem<UpgradedPickaxeItem> UG_IRON_PICKAXE = pickaxe("ugironpickaxe", Tiers.IRON, 2.0F, false);
    public static final DeferredItem<UpgradedPickaxeItem> UG_DIAMOND_PICKAXE = pickaxe("ugdiamondpickaxe", Tiers.DIAMOND, 3.0F, false);
    public static final DeferredItem<UpgradedPickaxeItem> UG_GOLD_PICKAXE = pickaxe("uggoldpickaxe", Tiers.GOLD, 2.5F, false);

    public static final DeferredItem<UpgradedAxeItem> UG_WOOD_AXE = axe("ugwoodaxe", Tiers.WOOD, 1.0F, false);
    public static final DeferredItem<UpgradedAxeItem> UG_STONE_AXE = axe("ugstoneaxe", Tiers.STONE, 1.5F, false);
    public static final DeferredItem<UpgradedAxeItem> UG_IRON_AXE = axe("ugironaxe", Tiers.IRON, 2.0F, false);
    public static final DeferredItem<UpgradedAxeItem> UG_DIAMOND_AXE = axe("ugdiamondaxe", Tiers.DIAMOND, 3.0F, false);
    public static final DeferredItem<UpgradedAxeItem> UG_GOLD_AXE = axe("uggoldaxe", Tiers.GOLD, 2.5F, false);

    public static final DeferredItem<BlazeBladeItem> BLAZE_BLADE = ITEMS.register("blazeblade", BlazeBladeItem::new);
    public static final DeferredItem<IceHoldItem> ICE_HOLD = ITEMS.register("icehold", IceHoldItem::new);
    public static final DeferredItem<AsmoSlasherItem> ASMO_SLASHER = ITEMS.register("asmoslasher", AsmoSlasherItem::new);
    public static final DeferredItem<PlanetGuardianItem> PLANET_GUARDIAN = ITEMS.register("planetguardian", PlanetGuardianItem::new);
    public static final DeferredItem<StormBringerItem> STORM_BRINGER = ITEMS.register("stormbringer", StormBringerItem::new);
    public static final DeferredItem<NegiItem> NEGI = ITEMS.register("negi", NegiItem::new);
    public static final DeferredItem<LuckySwordItem> LUCK_LUCK = ITEMS.register("luckluck", LuckySwordItem::new);
    public static final DeferredItem<SmashBatItem> SMASH_BAT = ITEMS.register("smashbat", SmashBatItem::new);
    public static final DeferredItem<DevilSwordItem> DEVIL_SWORD = ITEMS.register("devilsword", DevilSwordItem::new);
    public static final DeferredItem<HolySaberItem> HOLY_SABER = ITEMS.register("holysaber", HolySaberItem::new);
    public static final DeferredItem<ThrowingKnifeItem> THROWING_KNIFE = ITEMS.register("throwingknife", () -> new ThrowingKnifeItem(false));
    public static final DeferredItem<ThrowingKnifeItem> POISON_KNIFE = ITEMS.register("poisonknife", () -> new ThrowingKnifeItem(true));
    public static final DeferredItem<AutomaticCrossbowItem> CROSSBOW = ITEMS.register("crossbow", AutomaticCrossbowItem::new);

    public static final DeferredItem<SpecialSwordItem> INFINITE_SWORD = specialSword("infinitesword", Tiers.GOLD, 1, 8, true);
    public static final DeferredItem<UpgradedPickaxeItem> INFINITE_PICKAXE = pickaxe("infinitepickaxe", Tiers.DIAMOND, 1.0F, true);
    public static final DeferredItem<UpgradedAxeItem> INFINITE_AXE = axe("infiniteaxe", Tiers.GOLD, 1.0F, true);
    public static final DeferredItem<UpgradedShovelItem> INFINITE_SHOVEL = shovel("infiniteshovel", Tiers.GOLD, 1.0F, true);
    public static final DeferredItem<InfiniteHoeItem> INFINITE_HOE = ITEMS.register("infinitehoe", () -> new InfiniteHoeItem(Tiers.GOLD));
    public static final DeferredItem<SpecialSwordItem> GENOCIDE_BLADE = specialSword("genocideblade", Tiers.DIAMOND, 1, 10000, true);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ADVANCED_TOOLS_TAB = CREATIVE_TABS.register("advancedtools", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.advancedtools"))
            .withTabsAfter(CreativeModeTabs.SPAWN_EGGS)
            .icon(() -> BLUE_ENHANCER.get().getDefaultInstance())
            .displayItems((parameters, output) -> ITEMS.getEntries().forEach(entry -> output.accept(entry.get())))
            .build());

    private ModItems() {
    }

    private static DeferredItem<UpgradedShovelItem> shovel(String id, net.minecraft.world.item.Tier tier, float durability, boolean infinite) {
        return ITEMS.register(id, () -> new UpgradedShovelItem(tier, durability, infinite));
    }

    private static DeferredItem<UpgradedPickaxeItem> pickaxe(String id, net.minecraft.world.item.Tier tier, float durability, boolean infinite) {
        return ITEMS.register(id, () -> new UpgradedPickaxeItem(tier, durability, infinite));
    }

    private static DeferredItem<UpgradedAxeItem> axe(String id, net.minecraft.world.item.Tier tier, float durability, boolean infinite) {
        return ITEMS.register(id, () -> new UpgradedAxeItem(tier, durability, infinite));
    }

    private static DeferredItem<SpecialSwordItem> specialSword(String id, net.minecraft.world.item.Tier tier, int durability, int attackDamage, boolean infinite) {
        return ITEMS.register(id, () -> new SpecialSwordItem(tier, durability, attackDamage, infinite));
    }
}
