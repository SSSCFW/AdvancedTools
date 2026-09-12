package dev.ssscfw.advancedtools.entity;

import dev.ssscfw.advancedtools.registry.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public final class FireZombieEntity extends Zombie {
    public FireZombieEntity(EntityType<? extends FireZombieEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.30D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        super.populateDefaultEquipmentSlots(random, difficulty);
        setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.STONE_AXE));
    }

    @Override
    public void aiStep() {
        if (!level().isClientSide) {
            if (isInWaterRainOrBubble()) {
                hurt(damageSources().drown(), 2.0F);
            } else {
                igniteForSeconds(2.0F);
            }
        }
        super.aiStep();
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean result = super.doHurtTarget(target);
        if (result) {
            target.igniteForSeconds(5.0F);
        }
        return result;
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource source, boolean recentlyHit) {
        super.dropCustomDeathLoot(level, source, recentlyHit);
        int looting = MobDropUtil.lootingLevel(level, source);
        if (random.nextFloat() <= Math.min(1.0F, 0.20F + looting * 0.10F)) {
            spawnAtLocation(ModItems.RED_ENHANCER.get());
        }
        if (source.getEntity() instanceof Player && random.nextFloat() < 0.05F) {
            ItemStack axe = new ItemStack(Items.STONE_AXE);
            if (random.nextBoolean()) {
                MobDropUtil.addEnchant(level, axe, Enchantments.EFFICIENCY, 1 + random.nextInt(4));
            }
            if (random.nextBoolean()) {
                MobDropUtil.addEnchant(level, axe, Enchantments.UNBREAKING, 1);
            }
            spawnAtLocation(axe);
        }
    }
}
