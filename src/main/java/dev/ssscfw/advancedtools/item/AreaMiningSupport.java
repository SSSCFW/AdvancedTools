package dev.ssscfw.advancedtools.item;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

final class AreaMiningSupport {
    enum Kind { PICKAXE, SHOVEL, AXE }

    private static final String RANGE_KEY = "advancedtools_range";
    private static final String FACE_KEY = "advancedtools_face";
    private static final int DEFAULT_RANGE = 1;
    private static final int DIG_UNDER = 1;
    private static final ThreadLocal<Boolean> AREA_BREAK = ThreadLocal.withInitial(() -> false);

    private AreaMiningSupport() {
    }

    static InteractionResultHolder<ItemStack> cycleRange(Level level, Player player, InteractionHand hand, int maxRange) {
        ItemStack stack = player.getItemInHand(hand);
        int current = getRange(stack, maxRange);
        int next = player.isShiftKeyDown()
                ? Math.floorMod(current - 1, maxRange + 1)
                : (current + 1) % (maxRange + 1);
        setRange(stack, next);

        if (!level.isClientSide) {
            Component message = next == 0
                    ? Component.literal("Range: Only one")
                    : Component.literal("Range: " + (next * 2 + 1) + "x" + (next * 2 + 1));
            player.displayClientMessage(message, true);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    static void appendTooltip(ItemStack stack, int maxRange, List<Component> tooltip) {
        int range = getRange(stack, maxRange);
        tooltip.add(Component.literal(range == 0
                ? "Range: Only one"
                : "Range: " + (range * 2 + 1) + "x" + (range * 2 + 1)).withStyle(ChatFormatting.GRAY));
    }

    static void setFace(ItemStack stack, Direction face) {
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putInt(FACE_KEY, face.ordinal()));
    }

    private static Direction getFace(ItemStack stack) {
        CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        if (!tag.contains(FACE_KEY)) {
            return Direction.UP;
        }
        int ordinal = tag.getInt(FACE_KEY);
        Direction[] values = Direction.values();
        return ordinal >= 0 && ordinal < values.length ? values[ordinal] : Direction.UP;
    }

    private static int getRange(ItemStack stack, int maxRange) {
        CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        if (!tag.contains(RANGE_KEY)) {
            int initial = Math.min(DEFAULT_RANGE, maxRange);
            setRange(stack, initial);
            return initial;
        }
        return Math.clamp(tag.getInt(RANGE_KEY), 0, maxRange);
    }

    private static void setRange(ItemStack stack, int range) {
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putInt(RANGE_KEY, range));
    }

    static void mineArea(ItemStack stack, Level level, BlockState originState, BlockPos origin,
                         LivingEntity breaker, int maxRange, Kind kind) {
        if (level.isClientSide || !(breaker instanceof ServerPlayer player) || AREA_BREAK.get()) {
            return;
        }

        int range = getRange(stack, maxRange);
        if (range <= 0) {
            return;
        }

        Direction side = getFace(stack);
        boolean chain = isChainTarget(originState, kind);
        int minX = origin.getX() - range;
        int minY = origin.getY() - range;
        int minZ = origin.getZ() - range;
        int maxX = origin.getX() + range;
        int maxY = origin.getY() + range;
        int maxZ = origin.getZ() + range;

        if (!chain) {
            if (side.getAxis() == Direction.Axis.Y) {
                minY = origin.getY();
                maxY = origin.getY();
            } else {
                int shift = range - DIG_UNDER;
                minY += shift;
                maxY += shift;
            }
            if (side.getAxis() == Direction.Axis.Z) {
                minZ = origin.getZ();
                maxZ = origin.getZ();
            } else if (side.getAxis() == Direction.Axis.X) {
                minX = origin.getX();
                maxX = origin.getX();
            }
        }

        Set<BlockPos> candidates = new HashSet<>();
        for (BlockPos cursor : BlockPos.betweenClosed(minX, minY, minZ, maxX, maxY, maxZ)) {
            BlockState state = level.getBlockState(cursor);
            if (isSimilar(originState, state)) {
                candidates.add(cursor.immutable());
            }
        }
        candidates.remove(origin);

        List<BlockPos> connected = collectConnected(origin, candidates, kind == Kind.AXE ? 3.0D : 1.0D);
        if (connected.isEmpty()) {
            return;
        }

        AREA_BREAK.set(true);
        try {
            for (BlockPos target : connected) {
                if (stack.isEmpty()) {
                    break;
                }
                BlockState targetState = level.getBlockState(target);
                if (!isSimilar(originState, targetState) || targetState.getDestroySpeed(level, target) < 0.0F) {
                    continue;
                }
                player.gameMode.destroyBlock(target);
            }
        } finally {
            AREA_BREAK.set(false);
        }
    }

    private static List<BlockPos> collectConnected(BlockPos origin, Set<BlockPos> candidates, double maxDistanceSquared) {
        List<BlockPos> result = new ArrayList<>();
        ArrayDeque<BlockPos> queue = new ArrayDeque<>();
        queue.add(origin);

        while (!queue.isEmpty() && !candidates.isEmpty()) {
            BlockPos current = queue.removeFirst();
            Iterator<BlockPos> iterator = candidates.iterator();
            while (iterator.hasNext()) {
                BlockPos candidate = iterator.next();
                if (current.distSqr(candidate) <= maxDistanceSquared) {
                    iterator.remove();
                    queue.addLast(candidate);
                    result.add(candidate);
                }
            }
        }
        return result;
    }

    private static boolean isSimilar(BlockState origin, BlockState check) {
        if (check.isAir()) {
            return false;
        }
        if (origin.is(BlockTags.REDSTONE_ORES)) {
            return check.is(BlockTags.REDSTONE_ORES);
        }
        if (isLegacyDirt(origin)) {
            return isLegacyDirt(check);
        }
        return origin.getBlock() == check.getBlock();
    }

    private static boolean isLegacyDirt(BlockState state) {
        return state.is(Blocks.DIRT) || state.is(Blocks.GRASS_BLOCK);
    }

    private static boolean isChainTarget(BlockState state, Kind kind) {
        return switch (kind) {
            case AXE -> state.is(BlockTags.LOGS);
            case SHOVEL -> state.is(Blocks.CLAY) || state.is(Blocks.GRAVEL);
            case PICKAXE -> state.is(BlockTags.COAL_ORES)
                    || state.is(BlockTags.IRON_ORES)
                    || state.is(BlockTags.GOLD_ORES)
                    || state.is(BlockTags.DIAMOND_ORES)
                    || state.is(BlockTags.LAPIS_ORES)
                    || state.is(BlockTags.REDSTONE_ORES)
                    || state.is(Blocks.NETHER_QUARTZ_ORE);
        };
    }
}
