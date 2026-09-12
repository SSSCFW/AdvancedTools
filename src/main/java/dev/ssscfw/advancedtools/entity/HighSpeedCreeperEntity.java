package dev.ssscfw.advancedtools.entity;

import dev.ssscfw.advancedtools.registry.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;

public final class HighSpeedCreeperEntity extends Creeper {
    public HighSpeedCreeperEntity(EntityType<? extends HighSpeedCreeperEntity> type, Level level) {
        super(type, level);
        this.xpReward = 15;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Creeper.createAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.365D);
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide && isAlive() && getHealth() <= 10.0F) {
            addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20, 1, false, false));
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getDirectEntity() instanceof AbstractArrow) {
            return false;
        }
        return super.hurt(source, amount);
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource source, boolean recentlyHit) {
        super.dropCustomDeathLoot(level, source, recentlyHit);
        int looting = MobDropUtil.lootingLevel(level, source);
        if (random.nextFloat() <= Math.min(1.0F, 0.25F + looting * 0.10F)) {
            spawnAtLocation(ModItems.RED_ENHANCER.get());
        }
        if (random.nextFloat() <= Math.min(1.0F, 0.10F + looting * 0.10F)) {
            spawnAtLocation(ModItems.BLUE_ENHANCER.get());
        }
    }
}
