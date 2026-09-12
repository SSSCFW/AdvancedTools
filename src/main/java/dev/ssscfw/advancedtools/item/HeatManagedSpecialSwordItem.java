package dev.ssscfw.advancedtools.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

/**
 * Recreates the legacy Blaze Blade / Storm Bringer rapid-use heat mechanic.
 * Heat is stored per stack instead of on the singleton Item instance, avoiding
 * the old cross-player shared-state bug while keeping the gameplay behavior.
 */
public abstract class HeatManagedSpecialSwordItem extends ChargedSpecialSwordItem {
    private static final String HEAT_KEY = "advancedtools_heat";
    private static final String SAFETY_KEY = "advancedtools_heat_safety";

    protected HeatManagedSpecialSwordItem(Tier tier, int durability, int attackDamage, String abilityDescription) {
        super(tier, durability, attackDamage, abilityDescription, 0);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);
        if (level.isClientSide) {
            return;
        }
        int heat = getInt(stack, HEAT_KEY);
        if (heat > 0) {
            setInt(stack, HEAT_KEY, heat - 1);
        }
    }

    @Override
    protected void afterAbility(Level level, Player player, ItemStack stack, float power, boolean fullyCharged) {
        if (player.hasInfiniteMaterials()) {
            return;
        }

        int heat = Math.min(500, getInt(stack, HEAT_KEY) + 20);
        int safety = getInt(stack, SAFETY_KEY);
        if (heat > 100) {
            if (heat > 350) {
                safety += 3;
            } else if (heat > 200) {
                safety += 2;
            } else {
                safety += 1;
            }
            if (safety >= 3) {
                player.getFoodData().eat(-1, 0.0F);
                safety = 0;
            }
        } else {
            safety = 0;
        }

        setInt(stack, HEAT_KEY, heat);
        setInt(stack, SAFETY_KEY, safety);
    }

    private static int getInt(ItemStack stack, String key) {
        CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        return tag.getInt(key);
    }

    private static void setInt(ItemStack stack, String key, int value) {
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> {
            if (value == 0) {
                tag.remove(key);
            } else {
                tag.putInt(key, value);
            }
        });
    }
}
