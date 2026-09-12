package dev.ssscfw.advancedtools.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public final class BlazeBladeFireball extends SmallFireball {
    private final boolean fullyCharged;
    private boolean detonated;

    public BlazeBladeFireball(Level level, LivingEntity owner, Vec3 direction, boolean fullyCharged) {
        super(level, owner, direction.normalize());
        this.fullyCharged = fullyCharged;
        this.setDeltaMovement(direction.normalize().scale(1.8D));
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        detonate();
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        detonate();
    }

    private void detonate() {
        if (detonated || level().isClientSide) {
            return;
        }
        detonated = true;
        double radius = fullyCharged ? 1.8D : 1.5D;
        level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(radius), entity -> entity != getOwner())
                .forEach(entity -> entity.igniteForSeconds(5.0F));
        if (fullyCharged) {
            level().explode(this, getX(), getY(), getZ(), 1.5F, true, Level.ExplosionInteraction.MOB);
        }
    }
}
