# AdvancedTools

A Minecraft tools and mobs mod originally written for Forge 1.12.2.

## Minecraft 1.21.1 NeoForge port

The `port/1.21.1-neoforge` branch contains the Java 21 / NeoForge 21.1 port.

### Requirements

- Minecraft 1.21.1
- NeoForge 21.1.x
- Java 21
- Gradle 9.2.1 (the original repository does not include a Gradle Wrapper)

### Ported systems

- Upgraded shovels, pickaxes and axes, including area/connected mining and range switching
- Red/Blue Enhancers and legacy durability multipliers
- Infinite tools and weapons
- Special weapons and their active/passive abilities
- Throwing Knife / Poison Knife projectile behavior
- Automatic Crossbow
- Six high-grade mobs, custom textures, attributes, drops and natural spawning
- Legacy crafting recipes converted to the 1.21.1 data-pack recipe format
- NeoForge biome modifiers and 1.21.1 item/entity registration
- Legacy common settings for mining range, dig-under offset, connected-block lists, drop gathering and mob spawn toggles

The original 1.12.2 sources remain under `src/main/java/Nanashi` as a porting reference and are excluded from the 1.21.1 compilation.

### Configuration

NeoForge generates `config/advancedtools-common.toml`. It contains the migrated common options, including the initial upgraded-tool range, `digUnder`, connected-destruction block lists, `dropGather`, the master high-grade-mob spawn switch and per-mob spawn switches.

### Build

With Java 21 and Gradle 9.2.1 installed:

```bash
gradle build
```

The CI workflow also starts a NeoForge dedicated server and requires it to reach the ready state, so registry/data-pack loading problems are caught in addition to Java compile errors.

### Compatibility note

Registry IDs for the user-facing items and the six high-grade mobs are preserved. Some short-lived 1.12.2 helper/projectile internals were reimplemented with modern 1.21.1 APIs rather than preserving their old entity implementation. In particular, Ice Hold uses the modern freezing mechanism instead of temporarily replacing a mob's AI task list, which avoids interfering with AI supplied by other mods.
