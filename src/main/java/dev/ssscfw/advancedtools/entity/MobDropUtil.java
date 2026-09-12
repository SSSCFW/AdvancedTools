package dev.ssscfw.advancedtools.entity;

import java.util.List;

import dev.ssscfw.advancedtools.registry.ModItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;

final class MobDropUtil {
    private MobDropUtil() {
    }

    static int lootingLevel(ServerLevel level, DamageSource source) {
        if (!(source.getEntity() instanceof Player player)) {
            return 0;
        }
        var looting = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.LOOTING);
        return player.getMainHandItem().getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY).getLevel(looting);
    }

    static boolean isHoldingLucky(Player player) {
        return player.getMainHandItem().is(ModItems.LUCK_LUCK.get());
    }

    static Item randomGoldCreeperReward(ServerLevel level) {
        List<Item> items = List.of(
                ModItems.RED_ENHANCER.get(), ModItems.BLUE_ENHANCER.get(),
                ModItems.UG_WOOD_SHOVEL.get(), ModItems.UG_STONE_SHOVEL.get(), ModItems.UG_IRON_SHOVEL.get(), ModItems.UG_DIAMOND_SHOVEL.get(), ModItems.UG_GOLD_SHOVEL.get(),
                ModItems.UG_WOOD_PICKAXE.get(), ModItems.UG_STONE_PICKAXE.get(), ModItems.UG_IRON_PICKAXE.get(), ModItems.UG_DIAMOND_PICKAXE.get(), ModItems.UG_GOLD_PICKAXE.get(),
                ModItems.UG_WOOD_AXE.get(), ModItems.UG_STONE_AXE.get(), ModItems.UG_IRON_AXE.get(), ModItems.UG_DIAMOND_AXE.get(), ModItems.UG_GOLD_AXE.get(),
                ModItems.BLAZE_BLADE.get(), ModItems.ICE_HOLD.get(), ModItems.ASMO_SLASHER.get(), ModItems.PLANET_GUARDIAN.get(), ModItems.STORM_BRINGER.get(),
                ModItems.NEGI.get(), ModItems.LUCK_LUCK.get(), ModItems.SMASH_BAT.get(), ModItems.DEVIL_SWORD.get(), ModItems.HOLY_SABER.get(),
                ModItems.THROWING_KNIFE.get(), ModItems.POISON_KNIFE.get());
        return items.get(level.random.nextInt(items.size()));
    }

    static void addEnchant(ServerLevel level, ItemStack stack, net.minecraft.resources.ResourceKey<net.minecraft.world.item.enchantment.Enchantment> enchantment, int levelValue) {
        var holder = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(enchantment);
        stack.enchant(holder, levelValue);
    }
}
