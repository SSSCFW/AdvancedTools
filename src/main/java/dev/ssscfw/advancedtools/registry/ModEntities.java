package dev.ssscfw.advancedtools.registry;

import dev.ssscfw.advancedtools.AdvancedTools;
import dev.ssscfw.advancedtools.entity.FireZombieEntity;
import dev.ssscfw.advancedtools.entity.GoldCreeperEntity;
import dev.ssscfw.advancedtools.entity.HighSkeletonEntity;
import dev.ssscfw.advancedtools.entity.HighSpeedCreeperEntity;
import dev.ssscfw.advancedtools.entity.SkeletonSniperEntity;
import dev.ssscfw.advancedtools.entity.ThrowingKnifeEntity;
import dev.ssscfw.advancedtools.entity.ZombieWarriorEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, AdvancedTools.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<ThrowingKnifeEntity>> THROWING_KNIFE =
            register("throwingknife", MobCategory.MISC, 0.25F, 0.25F, ThrowingKnifeEntity::new);
    public static final DeferredHolder<EntityType<?>, EntityType<HighSkeletonEntity>> HIGH_SKELETON =
            register("highskeleton", MobCategory.MONSTER, 0.6F, 1.99F, HighSkeletonEntity::new);
    public static final DeferredHolder<EntityType<?>, EntityType<SkeletonSniperEntity>> SKELETON_SNIPER =
            register("skeletonsniper", MobCategory.MONSTER, 0.6F, 1.99F, SkeletonSniperEntity::new);
    public static final DeferredHolder<EntityType<?>, EntityType<ZombieWarriorEntity>> ZOMBIE_WARRIOR =
            register("zombiewarrior", MobCategory.MONSTER, 0.6F, 1.95F, ZombieWarriorEntity::new);
    public static final DeferredHolder<EntityType<?>, EntityType<FireZombieEntity>> FIRE_ZOMBIE =
            ENTITY_TYPES.register("firezombie", () -> EntityType.Builder
                    .<FireZombieEntity>of(FireZombieEntity::new, MobCategory.MONSTER)
                    .fireImmune().sized(0.6F, 1.95F).clientTrackingRange(8).build("firezombie"));
    public static final DeferredHolder<EntityType<?>, EntityType<HighSpeedCreeperEntity>> HIGH_SPEED_CREEPER =
            register("highspeedcreeper", MobCategory.MONSTER, 0.6F, 1.7F, HighSpeedCreeperEntity::new);
    public static final DeferredHolder<EntityType<?>, EntityType<GoldCreeperEntity>> GOLD_CREEPER =
            register("goldcreeper", MobCategory.MONSTER, 0.6F, 1.7F, GoldCreeperEntity::new);

    private ModEntities() {
    }

    private static <T extends net.minecraft.world.entity.Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(
            String id, MobCategory category, float width, float height, EntityType.EntityFactory<T> factory) {
        return ENTITY_TYPES.register(id, () -> EntityType.Builder.of(factory, category)
                .sized(width, height)
                .clientTrackingRange(8)
                .build(id));
    }
}
