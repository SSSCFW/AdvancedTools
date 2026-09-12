package dev.ssscfw.advancedtools.item;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public final class IceHoldItem extends ChargedSpecialSwordItem {
    public IceHoldItem() {
        super(Tiers.DIAMOND, 1200, 4, "Ice Coffin", 1);
    }

    @Override
    protected void performAbility(Level level, Player player, ItemStack stack, float power, boolean fullyCharged) {
        if (fullyCharged) {
            AABB box = player.getBoundingBox().inflate(5.0D, 1.0D, 5.0D);
            for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box, entity -> entity != player)) {
                freezeOrDamage(target, player);
            }
            snowPatch(level, player.blockPosition(), 6);
            return;
        }

        EntityHitResult entityHit = AbilityUtil.entityRay(level, player, 12.0D, 1.0D);
        if (entityHit != null && entityHit.getEntity() instanceof LivingEntity living) {
            freezeOrDamage(living, player);
            return;
        }

        HitResult hit = AbilityUtil.blockRay(level, player, 12.0D);
        snowPatch(level, BlockPos.containing(hit.getLocation()), 3);
    }

    private static void freezeOrDamage(LivingEntity target, Player player) {
        if (target instanceof EnderMan) {
            AbilityUtil.extraDamage(target, player, 12.0F);
        } else {
            target.setTicksFrozen(400);
        }
    }

    private static void snowPatch(Level level, BlockPos center, int radius) {
        if (!(level instanceof ServerLevel)) {
            return;
        }
        int radiusSq = radius * radius;
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                if (x * x + z * z > radiusSq) continue;
                BlockPos base = center.offset(x, 0, z);
                BlockState state = level.getBlockState(base);
                if (state.getFluidState().is(FluidTags.WATER) && state.getFluidState().isSource()) {
                    level.setBlockAndUpdate(base, Blocks.ICE.defaultBlockState());
                    continue;
                }
                BlockPos above = base.above();
                if (level.isEmptyBlock(above) && Blocks.SNOW.defaultBlockState().canSurvive(level, above)) {
                    level.setBlockAndUpdate(above, Blocks.SNOW.defaultBlockState());
                }
            }
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target instanceof EnderMan) {
            AbilityUtil.extraDamage(target, attacker, 8.0F);
        }
        return true;
    }
}
