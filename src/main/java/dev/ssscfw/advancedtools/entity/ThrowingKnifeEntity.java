package dev.ssscfw.advancedtools.entity;

import dev.ssscfw.advancedtools.item.ThrowingKnifeItem;
import dev.ssscfw.advancedtools.registry.ModEntities;
import dev.ssscfw.advancedtools.registry.ModItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public final class ThrowingKnifeEntity extends ThrowableItemProjectile {
    public ThrowingKnifeEntity(EntityType<? extends ThrowingKnifeEntity> type, Level level) {
        super(type, level);
    }

    public ThrowingKnifeEntity(LivingEntity owner, Level level) {
        super(ModEntities.THROWING_KNIFE.get(), owner, level);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.THROWING_KNIFE.get();
    }

    @Override
    protected double getDefaultGravity() {
        return 0.01D;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        ItemStack projectileStack = getItem();
        boolean poison = projectileStack.getItem() instanceof ThrowingKnifeItem knife && knife.isPoison();
        result.getEntity().hurt(damageSources().thrown(this, getOwner()), poison ? 2.0F : 4.0F);
        if (poison && result.getEntity() instanceof LivingEntity living) {
            living.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 1));
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!level().isClientSide) {
            spawnAtLocation(getItem().copyWithCount(1));
            discard();
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide && tickCount > 200) {
            spawnAtLocation(getItem().copyWithCount(1));
            discard();
        }
    }
}
