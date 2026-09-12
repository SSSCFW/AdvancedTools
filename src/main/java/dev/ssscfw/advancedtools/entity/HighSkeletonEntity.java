package dev.ssscfw.advancedtools.entity;

import dev.ssscfw.advancedtools.registry.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public final class HighSkeletonEntity extends Skeleton {
    public HighSkeletonEntity(EntityType<? extends HighSkeletonEntity> type, Level level) {
        super(type, level);
        this.xpReward = 10;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Skeleton.createAttributes()
                .add(Attributes.MAX_HEALTH, 25.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.30D);
    }

    @Override
    protected int getAttackInterval() {
        return 20;
    }

    @Override
    protected int getHardAttackInterval() {
        return 20;
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource source, boolean recentlyHit) {
        super.dropCustomDeathLoot(level, source, recentlyHit);
        int looting = MobDropUtil.lootingLevel(level, source);
        if (random.nextFloat() <= Math.min(1.0F, 0.10F + looting * 0.10F)) {
            spawnAtLocation(ModItems.BLUE_ENHANCER.get());
        }
        if (source.getEntity() instanceof Player player) {
            boolean lucky = MobDropUtil.isHoldingLucky(player) && random.nextFloat() < 0.50F;
            if (lucky || random.nextFloat() < 0.05F) {
                spawnAtLocation(ModItems.CROSSBOW.get());
            }
        }
    }
}
