package dev.ssscfw.advancedtools.entity;

import dev.ssscfw.advancedtools.registry.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public final class ZombieWarriorEntity extends Zombie {
    public ZombieWarriorEntity(EntityType<? extends ZombieWarriorEntity> type, Level level) {
        super(type, level);
        this.xpReward = 10;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.32D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        super.populateDefaultEquipmentSlots(random, difficulty);
        setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.DEVIL_SWORD.get()));
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
                spawnAtLocation(ModItems.DEVIL_SWORD.get());
            }
        }
    }
}
