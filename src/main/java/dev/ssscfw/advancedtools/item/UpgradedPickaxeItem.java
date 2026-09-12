package dev.ssscfw.advancedtools.item;

import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;

public class UpgradedPickaxeItem extends PickaxeItem implements AreaMiningItem {
    private final int maxRange;
    private final boolean infinite;

    public UpgradedPickaxeItem(Tier baseTier, float durabilityMultiplier, boolean infinite) {
        this(new ScaledTier(baseTier, durabilityMultiplier), rangeFor(baseTier), infinite);
    }

    private UpgradedPickaxeItem(Tier tier, int maxRange, boolean infinite) {
        super(tier, new Item.Properties().attributes(PickaxeItem.createAttributes(tier, 1.0F, -2.8F)));
        this.maxRange = maxRange;
        this.infinite = infinite;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return AreaMiningSupport.cycleRange(level, player, hand, maxRange);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miner) {
        boolean result = infinite || super.mineBlock(stack, level, state, pos, miner);
        AreaMiningSupport.mineArea(stack, level, state, pos, miner, maxRange, AreaMiningSupport.Kind.PICKAXE);
        return result;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        AreaMiningSupport.appendTooltip(stack, maxRange, tooltip);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return !infinite && super.isBarVisible(stack);
    }

    private static int rangeFor(Tier tier) {
        if (tier == Tiers.WOOD) return 2;
        if (tier == Tiers.STONE) return 4;
        if (tier == Tiers.IRON) return 7;
        return 9;
    }
}
