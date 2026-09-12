package dev.ssscfw.advancedtools.item;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

final class AbilityUtil {
    private AbilityUtil() {
    }

    static HitResult blockRay(Level level, Player player, double distance) {
        Vec3 start = player.getEyePosition();
        Vec3 end = start.add(player.getLookAngle().scale(distance));
        return level.clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, player));
    }

    static EntityHitResult entityRay(Level level, Player player, double distance, double inflate) {
        Vec3 start = player.getEyePosition();
        Vec3 look = player.getLookAngle();
        Vec3 end = start.add(look.scale(distance));
        AABB search = player.getBoundingBox().expandTowards(look.scale(distance)).inflate(inflate);
        return ProjectileUtil.getEntityHitResult(level, player, start, end, search,
                entity -> entity instanceof LivingEntity && entity.isPickable() && entity != player);
    }

    static void lightning(Level level, BlockPos position, Player cause) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }
        BlockPos ground = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, position);
        LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(serverLevel);
        if (bolt != null) {
            bolt.moveTo(Vec3.atBottomCenterOf(ground));
            if (cause instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
                bolt.setCause(serverPlayer);
            }
            serverLevel.addFreshEntity(bolt);
        }
    }

    static void extraDamage(LivingEntity target, LivingEntity attacker, float amount) {
        if (amount <= 0.0F) {
            return;
        }
        if (attacker instanceof Player player) {
            target.hurt(target.damageSources().playerAttack(player), amount);
        } else {
            target.hurt(target.damageSources().mobAttack(attacker), amount);
        }
    }
}
