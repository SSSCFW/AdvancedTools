package dev.ssscfw.advancedtools.item;

import java.util.List;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class AutomaticCrossbowItem extends BowItem {
    public AutomaticCrossbowItem() {
        super(new Item.Properties().durability(192));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack weapon = player.getItemInHand(hand);
        ItemStack ammo = player.getProjectile(weapon);
        if (ammo.isEmpty() && player.hasInfiniteMaterials()) {
            ammo = new ItemStack(Items.ARROW);
        }
        if (ammo.isEmpty()) {
            return InteractionResultHolder.fail(weapon);
        }

        List<ItemStack> projectiles = draw(weapon, ammo, player);
        if (level instanceof ServerLevel serverLevel && !projectiles.isEmpty()) {
            shoot(serverLevel, player, hand, weapon, projectiles, 3.0F, 1.0F, true, null);
        }
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F);
        return InteractionResultHolder.sidedSuccess(weapon, level.isClientSide);
    }
}
