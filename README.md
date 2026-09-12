# AdvancedTools

A Minecraft tools and mobs mod originally written for Forge 1.12.2.

## Minecraft 1.21.1 NeoForge port

The `port/1.21.1-neoforge` branch contains the Java 21 / NeoForge 21.1 port.

### Requirements

- Minecraft 1.21.1
- NeoForge 21.1.x
- Java 21

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

The original 1.12.2 sources remain under `src/main/java/Nanashi` as a porting reference and are excluded from the 1.21.1 compilation.

### Build

```bash
gradle build
```

The CI workflow also starts a NeoForge dedicated server and requires it to reach the ready state, so registry/data-pack loading problems are caught in addition to Java compile errors.
