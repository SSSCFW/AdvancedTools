package dev.ssscfw.advancedtools.item;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

/** A vanilla tier with the durability multiplier used by the 1.12.2 AdvancedTools tools. */
public record ScaledTier(Tier delegate, float durabilityMultiplier) implements Tier {
    @Override
    public int getUses() {
        return Math.max(1, (int) Math.floor(delegate.getUses() * durabilityMultiplier));
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
