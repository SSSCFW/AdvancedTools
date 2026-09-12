package dev.ssscfw.advancedtools.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public final class StormBringerItem extends ChargedSpecialSwordItem {
    public StormBringerItem() {
        super(Tiers.DIAMOND, 1200, 4, "Gale Impact", 0);
    }

    @Override
    protected void performAbility(Level level, Player player, ItemStack stack, float power, boolean fullyCharged) {
        if (fullyCharged) {
            AABB box = player.getBoundingBox().inflate(8.0D, 2.0D, 8.0D);
            for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box, entity -> entity != player)) {
                Vec3 away = target.position().subtract(player.position());
                Vec3 horizontal = new Vec3(away.x, 0.0D, away.z);
                if (horizontal.lengthSqr() > 1.0E-6D) {
                    Vec3 push = horizontal.normalize().scale(4.0D);
                    target.push(push.x, 0.25D, push.z);
                }
            }
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.CLOUD, player.getX(), player.getY() + 1.0D, player.getZ(), 100, 4.0D, 1.0D, 4.0D, 0.1D);
            }
            return;
        }

        EntityHitResult hit = AbilityUtil.entityRay(level, player, 27.0D, 1.0D);
        if (hit != null && hit.getEntity() instanceof LivingEntity target) {
            AbilityUtil.extraDamage(target, player, 2.0F);
            Vec3 look = player.getLookAngle();
            target.push(look.x * 0.8D, 1.4D, look.z * 0.8D);
        }
    }
}
