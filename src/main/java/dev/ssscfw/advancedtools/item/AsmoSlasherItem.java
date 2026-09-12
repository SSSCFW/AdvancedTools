package dev.ssscfw.advancedtools.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public final class AsmoSlasherItem extends ChargedSpecialSwordItem {
    public AsmoSlasherItem() {
        super(Tiers.DIAMOND, 1200, 4, "Lightning Caller", 1);
    }

    @Override
    protected void performAbility(Level level, Player player, ItemStack stack, float power, boolean fullyCharged) {
        if (fullyCharged && player.getFoodData().getFoodLevel() > 7) {
            for (int i = 0; i < 8; i++) {
                double angle = Math.PI * 2.0D * i / 8.0D;
                BlockPos pos = BlockPos.containing(player.getX() + Math.cos(angle) * 5.5D, player.getY(), player.getZ() + Math.sin(angle) * 5.5D);
                AbilityUtil.lightning(level, pos, player);
            }
        } else {
            Vec3 target = player.position().add(player.getLookAngle().scale(6.0D));
            AbilityUtil.lightning(level, BlockPos.containing(target), player);
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof Player player && (target instanceof Creeper || target instanceof Pig)) {
            AbilityUtil.lightning(target.level(), target.blockPosition(), player);
        }
        return true;
    }
}
