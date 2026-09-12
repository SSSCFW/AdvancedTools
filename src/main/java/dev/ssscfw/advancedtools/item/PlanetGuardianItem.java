package dev.ssscfw.advancedtools.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public final class PlanetGuardianItem extends ChargedSpecialSwordItem {
    public PlanetGuardianItem() {
        super(Tiers.DIAMOND, 1200, 4, "Ground Banish", 1);
    }

    @Override
    protected void performAbility(Level level, Player player, ItemStack stack, float power, boolean fullyCharged) {
        if (power < 0.1F) return;
        HitResult hit = AbilityUtil.blockRay(level, player, 4.0D);
        Vec3 center = hit.getLocation();
        AABB box = new AABB(center, center).inflate(5.0D);
        for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box, entity -> entity != player && entity.onGround())) {
            target.push(0.0D, 1.35D * power, 0.0D);
        }
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.EXPLOSION, center.x, center.y, center.z, 40, 2.5D, 0.15D, 2.5D, 0.0D);
        }
    }
}
