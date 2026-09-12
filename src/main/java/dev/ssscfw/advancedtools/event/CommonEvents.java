package dev.ssscfw.advancedtools.event;

import dev.ssscfw.advancedtools.item.AreaMiningItem;
import dev.ssscfw.advancedtools.item.MagnetItem;
import dev.ssscfw.advancedtools.item.SolidifierItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public final class CommonEvents {
    private static final int SOLIDIFIER_INTERVAL_TICKS = 5;

    private CommonEvents() {
    }

    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getAction() != PlayerInteractEvent.LeftClickBlock.Action.START) {
            return;
        }
        ItemStack stack = event.getItemStack();
        Direction face = event.getFace();
        if (face != null && stack.getItem() instanceof AreaMiningItem item) {
            item.advancedTools$recordMiningFace(stack, face);
        }
    }

    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        collectWithMagnet(player);

        if (player.tickCount % SOLIDIFIER_INTERVAL_TICKS == 0) {
            solidifyFluids(player);
        }
    }

    private static void collectWithMagnet(ServerPlayer player) {
        int range = activeMagnetRange(player);
        if (range <= 0) {
            return;
        }

        AABB area = new AABB(
                player.getX() - range, player.getY() - range, player.getZ() - range,
                player.getX() + range, player.getY() + range, player.getZ() + range);

        for (ItemEntity item : player.serverLevel().getEntitiesOfClass(ItemEntity.class, area, ItemEntity::isAlive)) {
            item.setNoPickUpDelay();
            item.playerTouch(player);
        }

        for (ExperienceOrb orb : player.serverLevel().getEntitiesOfClass(ExperienceOrb.class, area, ExperienceOrb::isAlive)) {
            player.takeXpDelay = 0;
            orb.playerTouch(player);
        }
    }

    private static void solidifyFluids(ServerPlayer player) {
        int range = activeSolidifierRange(player);
        if (range <= 0) {
            return;
        }

        ServerLevel level = player.serverLevel();
        BlockPos base = player.blockPosition().below();
        int size = range * 2 + 1;
        int minX = base.getX() - range;
        int maxX = base.getX() + range;
        int minZ = base.getZ() - range;
        int maxZ = base.getZ() + range;
        int minY = Math.max(base.getY(), level.getMinBuildHeight());
        int maxY = Math.min(base.getY() + size - 1, level.getMaxBuildHeight() - 1);

        for (BlockPos cursor : BlockPos.betweenClosed(minX, minY, minZ, maxX, maxY, maxZ)) {
            if (!level.hasChunkAt(cursor)) {
                continue;
            }

            BlockState state = level.getBlockState(cursor);
            if (!state.is(Blocks.WATER) && !state.is(Blocks.LAVA)) {
                continue;
            }

            FluidState fluid = state.getFluidState();
            if (fluid.isEmpty()) {
                continue;
            }

            BlockState replacement = fluid.isSource()
                    ? Blocks.OBSIDIAN.defaultBlockState()
                    : Blocks.COBBLESTONE.defaultBlockState();
            level.setBlock(cursor, replacement, Block.UPDATE_ALL);
        }
    }

    private static int activeMagnetRange(ServerPlayer player) {
        int maxRange = 0;
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (stack.getItem() instanceof MagnetItem && MagnetItem.isEnabled(stack)) {
                maxRange = Math.max(maxRange, MagnetItem.getRange(stack));
            }
        }
        return maxRange;
    }

    private static int activeSolidifierRange(ServerPlayer player) {
        int maxRange = 0;
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (stack.getItem() instanceof SolidifierItem && SolidifierItem.isEnabled(stack)) {
                maxRange = Math.max(maxRange, SolidifierItem.getRange(stack));
            }
        }
        return maxRange;
    }
}
