package dev.ssscfw.advancedtools.entity;

import dev.ssscfw.advancedtools.registry.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public final class SkeletonSniperEntity extends Skeleton {
    public SkeletonSniperEntity(EntityType<? extends SkeletonSniperEntity> type, Level level) {
        super(type, level);
        this.xpReward = 7;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Skeleton.createAttributes()
                .add(Attributes.MAX_HEALTH, 25.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.30D);
    }

    @Override
    protected int getAttackInterval() {
        return 30;
    }

    @Override
    protected int getHardAttackInterval() {
        return 30;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!level().isClientSide && tickCount % 10 == 0 && getTarget() != null) {
            boolean melee = distanceToSqr(getTarget()) < 16.0D;
            if (melee && !getMainHandItem().is(ModItems.SMASH_BAT.get())) {
                ItemStack bat = new ItemStack(ModItems.SMASH_BAT.get());
                if (level() instanceof ServerLevel serverLevel) {
                    MobDropUtil.addEnchant(serverLevel, bat, Enchantments.KNOCKBACK, 10);
                }
                setItemSlot(EquipmentSlot.MAINHAND, bat);
            } else if (!melee && !getMainHandItem().is(Items.BOW)) {
                setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
            }
        }
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource source, boolean recentlyHit) {
        super.dropCustomDeathLoot(level, source, recentlyHit);
        int looting = MobDropUtil.lootingLevel(level, source);
        if (random.nextFloat() <= Math.min(1.0F, 0.20F + looting * 0.10F)) {
            spawnAtLocation(ModItems.RED_ENHANCER.get());
        }
        if (source.getEntity() instanceof Player && random.nextFloat() < 0.05F) {
            ItemStack bat = new ItemStack(ModItems.SMASH_BAT.get());
            MobDropUtil.addEnchant(level, bat, Enchantments.KNOCKBACK, 5 + random.nextInt(5));
            if (random.nextBoolean()) {
                MobDropUtil.addEnchant(level, bat, Enchantments.SMITE, 1 + random.nextInt(2));
            }
            spawnAtLocation(bat);
        }
    }
}
