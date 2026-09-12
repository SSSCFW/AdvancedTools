package dev.ssscfw.advancedtools.event;

import dev.ssscfw.advancedtools.entity.FireZombieEntity;
import dev.ssscfw.advancedtools.entity.GoldCreeperEntity;
import dev.ssscfw.advancedtools.entity.HighSkeletonEntity;
import dev.ssscfw.advancedtools.entity.HighSpeedCreeperEntity;
import dev.ssscfw.advancedtools.entity.SkeletonSniperEntity;
import dev.ssscfw.advancedtools.entity.ZombieWarriorEntity;
import dev.ssscfw.advancedtools.registry.ModEntities;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

public final class ModEntityEvents {
    private ModEntityEvents() {
    }

    public static void onAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.HIGH_SKELETON.get(), HighSkeletonEntity.createAttributes().build());
        event.put(ModEntities.SKELETON_SNIPER.get(), SkeletonSniperEntity.createAttributes().build());
        event.put(ModEntities.ZOMBIE_WARRIOR.get(), ZombieWarriorEntity.createAttributes().build());
        event.put(ModEntities.FIRE_ZOMBIE.get(), FireZombieEntity.createAttributes().build());
        event.put(ModEntities.HIGH_SPEED_CREEPER.get(), HighSpeedCreeperEntity.createAttributes().build());
        event.put(ModEntities.GOLD_CREEPER.get(), GoldCreeperEntity.createAttributes().build());
    }

    public static void onSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(ModEntities.HIGH_SKELETON.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (type, level, reason, pos, random) -> pos.getY() < 50 && Monster.checkMonsterSpawnRules(type, level, reason, pos, random),
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ModEntities.SKELETON_SNIPER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ModEntities.ZOMBIE_WARRIOR.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (type, level, reason, pos, random) -> pos.getY() < 50 && Monster.checkMonsterSpawnRules(type, level, reason, pos, random),
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ModEntities.FIRE_ZOMBIE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ModEntities.HIGH_SPEED_CREEPER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ModEntities.GOLD_CREEPER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }
}
