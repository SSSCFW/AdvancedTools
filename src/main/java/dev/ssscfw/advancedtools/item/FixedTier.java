package dev.ssscfw.advancedtools.item;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

/** Tier wrapper used to preserve the fixed max-damage values of the legacy special weapons. */
public record FixedTier(Tier delegate, int uses) implements Tier {
    @Override
    public int getUses() {
        return Math.max(1, uses);
    }

    @Override
    public float getSpeed() {
        return delegate.getSpeed();
    }

    @Override
    public float getAttackDamageBonus() {
        return delegate.getAttackDamageBonus();
    }

    @Override
    public TagKey<Block> getIncorrectBlocksForDrops() {
        return delegate.getIncorrectBlocksForDrops();
    }

    @Override
    public int getEnchantmentValue() {
        return delegate.getEnchantmentValue();
    }

    @Override
    public Ingredient getRepairIngredient() {
        return delegate.getRepairIngredient();
    }
}
