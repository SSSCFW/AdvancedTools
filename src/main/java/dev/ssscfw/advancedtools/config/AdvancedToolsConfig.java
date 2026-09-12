package dev.ssscfw.advancedtools.config;

import java.util.List;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ModConfigSpec;

/** NeoForge equivalent of the legacy 1.12.2 AdvancedTools common config. */
public final class AdvancedToolsConfig {
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.IntValue DESTROY_RANGE_LEVEL;
    public static final ModConfigSpec.IntValue SAFETY_COUNTER;
    public static final ModConfigSpec.BooleanValue SPAWN_HIGH_GRADE_MOBS;
    public static final ModConfigSpec.BooleanValue DROP_GATHER;
    public static final ModConfigSpec.IntValue DIG_UNDER;

    public static final ModConfigSpec.ConfigValue<List<? extends String>> PICKAXE_CHAIN_BLOCKS;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> SHOVEL_CHAIN_BLOCKS;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> AXE_CHAIN_BLOCKS;

    public static final ModConfigSpec.BooleanValue SPAWN_FIRE_ZOMBIE;
    public static final ModConfigSpec.BooleanValue SPAWN_GOLD_CREEPER;
    public static final ModConfigSpec.BooleanValue SPAWN_HIGH_SKELETON;
    public static final ModConfigSpec.BooleanValue SPAWN_HIGH_SPEED_CREEPER;
    public static final ModConfigSpec.BooleanValue SPAWN_SKELETON_SNIPER;
    public static final ModConfigSpec.BooleanValue SPAWN_ZOMBIE_WARRIOR;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("general");
        DESTROY_RANGE_LEVEL = builder
                .comment("Initial upgraded-tool range level. 0 means one block; 1 means 3x3. The value is clamped to the tool's maximum range.")
                .defineInRange("destroyRangeLevel", 1, 0, 9);
        SAFETY_COUNTER = builder
                .comment("Legacy compatibility setting. Connected-block search is now bounded by selected range.")
                .defineInRange("safetyCounter", 100, 1, 100000);
        SPAWN_HIGH_GRADE_MOBS = builder
                .comment("Master switch for natural spawning of AdvancedTools high-grade mobs.")
                .define("spawnHighGradeMobs", true);
        DROP_GATHER = builder
                .comment("Move drops created by area mining to the player's feet, matching legacy dropGather.")
                .define("dropGather", false);
        DIG_UNDER = builder
                .comment("How many blocks below the aimed block are included when mining a vertical face.")
                .defineInRange("digUnder", 1, 0, 256);

        PICKAXE_CHAIN_BLOCKS = builder
                .comment("Exact block registry IDs that use connected destruction for upgraded pickaxes.")
                .defineList("pickaxeChainBlocks", List.of(
                        "minecraft:diamond_ore", "minecraft:deepslate_diamond_ore",
                        "minecraft:gold_ore", "minecraft:deepslate_gold_ore",
                        "minecraft:iron_ore", "minecraft:deepslate_iron_ore",
                        "minecraft:coal_ore", "minecraft:deepslate_coal_ore",
                        "minecraft:lapis_ore", "minecraft:deepslate_lapis_ore",
                        "minecraft:redstone_ore", "minecraft:deepslate_redstone_ore",
                        "minecraft:nether_quartz_ore"),
                        () -> "minecraft:diamond_ore", AdvancedToolsConfig::isString);
        SHOVEL_CHAIN_BLOCKS = builder
                .comment("Exact block registry IDs that use connected destruction for upgraded shovels.")
                .defineList("shovelChainBlocks", List.of("minecraft:clay", "minecraft:gravel"),
                        () -> "minecraft:gravel", AdvancedToolsConfig::isString);
        AXE_CHAIN_BLOCKS = builder
                .comment("Exact block registry IDs that use connected destruction for upgraded axes.")
                .defineList("axeChainBlocks", List.of(
                        "minecraft:oak_log", "minecraft:spruce_log", "minecraft:birch_log", "minecraft:jungle_log",
                        "minecraft:acacia_log", "minecraft:dark_oak_log", "minecraft:mangrove_log", "minecraft:cherry_log"),
                        () -> "minecraft:oak_log", AdvancedToolsConfig::isString);
        builder.pop();

        builder.push("mobSpawnSetting");
        SPAWN_FIRE_ZOMBIE = builder.define("fireZombie", true);
        SPAWN_GOLD_CREEPER = builder.define("goldCreeper", true);
        SPAWN_HIGH_SKELETON = builder.define("highSkeleton", true);
        SPAWN_HIGH_SPEED_CREEPER = builder.define("highSpeedCreeper", true);
        SPAWN_SKELETON_SNIPER = builder.define("skeletonSniper", true);
        SPAWN_ZOMBIE_WARRIOR = builder.define("zombieWarrior", true);
        builder.pop();

        SPEC = builder.build();
    }

    private AdvancedToolsConfig() {}

    private static boolean isString(Object value) {
        return value instanceof String string && !string.isBlank();
    }

    public static int initialRange(int maxRange) {
        return Math.clamp(safeGet(DESTROY_RANGE_LEVEL), 0, maxRange);
    }

    public static int digUnder() { return safeGet(DIG_UNDER); }
    public static boolean dropGather() { return safeGet(DROP_GATHER); }
    public static boolean highGradeMobsEnabled() { return safeGet(SPAWN_HIGH_GRADE_MOBS); }
    public static boolean fireZombieEnabled() { return highGradeMobsEnabled() && safeGet(SPAWN_FIRE_ZOMBIE); }
    public static boolean goldCreeperEnabled() { return highGradeMobsEnabled() && safeGet(SPAWN_GOLD_CREEPER); }
    public static boolean highSkeletonEnabled() { return highGradeMobsEnabled() && safeGet(SPAWN_HIGH_SKELETON); }
    public static boolean highSpeedCreeperEnabled() { return highGradeMobsEnabled() && safeGet(SPAWN_HIGH_SPEED_CREEPER); }
    public static boolean skeletonSniperEnabled() { return highGradeMobsEnabled() && safeGet(SPAWN_SKELETON_SNIPER); }
    public static boolean zombieWarriorEnabled() { return highGradeMobsEnabled() && safeGet(SPAWN_ZOMBIE_WARRIOR); }

    public static boolean isPickaxeChainBlock(BlockState state) { return containsBlock(PICKAXE_CHAIN_BLOCKS, state); }
    public static boolean isShovelChainBlock(BlockState state) { return containsBlock(SHOVEL_CHAIN_BLOCKS, state); }
    public static boolean isAxeChainBlock(BlockState state) { return containsBlock(AXE_CHAIN_BLOCKS, state); }

    private static boolean containsBlock(ModConfigSpec.ConfigValue<List<? extends String>> value, BlockState state) {
        String id = BuiltInRegistries.BLOCK.getKey(state.getBlock()).toString();
        return safeGet(value).contains(id);
    }

    public static <T> T safeGet(ModConfigSpec.ConfigValue<T> value) {
        try {
            return value.get();
        } catch (IllegalStateException exception) {
            return value.getDefault();
        }
    }
}
