/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.physics;

import com.wynnvp.wynncraftvp.ModCore;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.IdentityHashMap;
import java.util.Map;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Turns a {@link BlockState} into the acoustic material the tracer works with. */
public final class AcousticMaterialTable {
    private static final String RESOURCE = "assets/wynnvp/sound_physics/materials.json";
    private static final String OVERRIDE_FILE = "wynnvp-materials.json";

    /** Below this share of its cube, a block is something sound leaks past rather than bounces off. */
    private static final float PASS_THROUGH_OCCUPANCY = 0.35f;

    /** Block states are canonical singletons, so identity is the right key and the cheapest one. */
    private static final int CACHE_LIMIT = 4096;

    private final MaterialDefinitions definitions;
    private final Map<SoundType, String> surfaceKeys;
    private final Map<BlockState, AcousticMaterial> cache = new IdentityHashMap<>();

    /**
     * Parsed once per game session. A sound engine reload rebuilds the player, but the material table
     * is immutable data that has not changed, and re-reading it would put file I/O and JSON parsing on
     * the render thread every time the audio device changes or a resource pack reloads.
     */
    private static MaterialDefinitions sharedDefinitions;

    private AcousticMaterialTable(MaterialDefinitions definitions) {
        this.definitions = definitions;
        this.surfaceKeys = buildSurfaceKeys();
    }

    /**
     * Loads the bundled table, preferring a file in the config directory if the player has dropped one
     * there. A broken table degrades to {@link MaterialDefinitions#builtIn()} rather than stopping
     * playback: bland acoustics beat no dialogue.
     */
    public static synchronized AcousticMaterialTable load() {
        if (sharedDefinitions == null) {
            sharedDefinitions = readDefinitions();
            sharedDefinitions.warnings().forEach(ModCore::warn);
        }
        return new AcousticMaterialTable(sharedDefinitions);
    }

    private static MaterialDefinitions readDefinitions() {
        Path override = FabricLoader.getInstance().getConfigDir().resolve(OVERRIDE_FILE);
        if (Files.isReadable(override)) {
            try (Reader reader = Files.newBufferedReader(override, StandardCharsets.UTF_8)) {
                MaterialDefinitions definitions = MaterialDefinitions.parse(reader);
                ModCore.info("Loaded sound physics materials from " + override);
                return definitions;
            } catch (Exception e) {
                ModCore.warn("Could not read " + override + ", falling back to the bundled materials", e);
            }
        }

        try (InputStream stream = AcousticMaterialTable.class.getClassLoader().getResourceAsStream(RESOURCE)) {
            if (stream == null) {
                ModCore.warn("Bundled sound physics materials are missing, using built-in defaults");
                return MaterialDefinitions.builtIn();
            }
            try (Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
                return MaterialDefinitions.parse(reader);
            }
        } catch (Exception e) {
            ModCore.error("Could not read the bundled sound physics materials, using built-in defaults", e);
            return MaterialDefinitions.builtIn();
        }
    }

    /** For tests and for the debug command, which want a table without touching the file system. */
    public static AcousticMaterialTable of(MaterialDefinitions definitions) {
        return new AcousticMaterialTable(definitions);
    }

    /**
     * Only safe on the client thread: a cache miss reads the block's collision shape.
     */
    public AcousticMaterial lookup(BlockState state, BlockGetter level, BlockPos pos) {
        AcousticMaterial cached = cache.get(state);
        if (cached != null) {
            return cached;
        }
        AcousticMaterial material = resolve(state, level, pos);
        if (cache.size() >= CACHE_LIMIT) {
            // Start over rather than stop caching. Leaving it full would mean recomputing a collision
            // shape on every single lookup from here on, which is far worse than an occasional reset.
            cache.clear();
        }
        cache.put(state, material);
        return material;
    }

    /** Block states are shared across worlds, but the shapes behind them are not. */
    public void clearCache() {
        cache.clear();
    }

    private AcousticMaterial resolve(BlockState state, BlockGetter level, BlockPos pos) {
        if (state.isAir()) {
            return AcousticMaterial.TRANSPARENT;
        }

        String blockId = String.valueOf(BuiltInRegistries.BLOCK.getKey(state.getBlock()));
        // Before any shape test, because a barrier is a fully solid block that has to behave as
        // though it were not there at all.
        if (definitions.isTransparent(blockId)) {
            return AcousticMaterial.TRANSPARENT;
        }

        SoundType soundType = state.getSoundType();
        if (soundType == SoundType.EMPTY) {
            return AcousticMaterial.TRANSPARENT;
        }

        AcousticMaterial base = definitions.resolve(blockId, surfaceKeys.get(soundType));
        if (base.isTransparent() || base.mode() == AcousticMaterial.Mode.PASS_THROUGH) {
            return base;
        }

        // A full cube is the common case and the only one worth a fast path; everything else is scaled
        // by how much of its cube it actually fills, which covers slabs, stairs, panes, bars, fences,
        // carpets and plants without naming any of them.
        //
        // Note this asks about the collision shape, not canOcclude(): the latter is a rendering
        // property that slabs and stairs inherit from the block they are cut from, so it answers true
        // for them and would charge a half-height block for a whole one.
        if (state.isCollisionShapeFullBlock(level, pos)) {
            return base;
        }
        VoxelShape shape = state.getCollisionShape(level, pos);
        if (shape.isEmpty()) {
            return AcousticMaterial.TRANSPARENT;
        }
        float occupancy = boundsVolume(shape.bounds());
        if (occupancy < PASS_THROUGH_OCCUPANCY) {
            return base.asPassThrough(occupancy * 2f);
        }
        return base.asReflector(occupancy);
    }

    private static float boundsVolume(AABB bounds) {
        double volume = (bounds.maxX - bounds.minX) * (bounds.maxY - bounds.minY) * (bounds.maxZ - bounds.minZ);
        return (float) Math.clamp(volume, 0.0, 1.0);
    }

    /**
     * Sound types are mapped by direct field reference, never by reflection on their names: in a
     * production build the fields are remapped to intermediary names and a lookup by "STONE" would
     * find nothing.
     */
    private static Map<SoundType, String> buildSurfaceKeys() {
        Map<SoundType, String> keys = new IdentityHashMap<>();

        put(keys, "metal", SoundType.METAL, SoundType.NETHERITE_BLOCK, SoundType.ANVIL, SoundType.CHAIN);
        put(keys, "metal", SoundType.LODESTONE, SoundType.COPPER, SoundType.COPPER_BULB, SoundType.COPPER_GRATE);
        put(keys, "metal", SoundType.IRON, SoundType.COPPER_GOLEM_STATUE, SoundType.HEAVY_CORE, SoundType.VAULT);

        put(keys, "polished_stone", SoundType.POLISHED_DEEPSLATE, SoundType.POLISHED_TUFF, SoundType.TUFF_BRICKS);
        put(keys, "polished_stone", SoundType.DEEPSLATE_BRICKS, SoundType.DEEPSLATE_TILES, SoundType.AMETHYST);
        put(keys, "polished_stone", SoundType.CALCITE, SoundType.RESIN_BRICKS);

        put(keys, "stone", SoundType.STONE, SoundType.ANCIENT_DEBRIS, SoundType.SPAWNER, SoundType.TRIAL_SPAWNER);

        put(keys, "rough_stone", SoundType.DEEPSLATE, SoundType.TUFF, SoundType.BASALT, SoundType.NETHERRACK);
        put(keys, "rough_stone", SoundType.NETHER_BRICKS, SoundType.DRIPSTONE_BLOCK, SoundType.POINTED_DRIPSTONE);
        put(keys, "rough_stone", SoundType.MUD_BRICKS, SoundType.NETHER_ORE, SoundType.NETHER_GOLD_ORE);
        put(keys, "rough_stone", SoundType.GILDED_BLACKSTONE, SoundType.PACKED_MUD);

        put(keys, "glass", SoundType.GLASS);

        put(keys, "wood", SoundType.WOOD, SoundType.CHERRY_WOOD, SoundType.BAMBOO_WOOD, SoundType.NETHER_WOOD);
        put(keys, "wood", SoundType.LADDER, SoundType.STEM, SoundType.WART_BLOCK, SoundType.HANGING_SIGN);
        put(keys, "wood", SoundType.NETHER_WOOD_HANGING_SIGN, SoundType.BAMBOO_WOOD_HANGING_SIGN);
        put(keys, "wood", SoundType.CHERRY_WOOD_HANGING_SIGN, SoundType.SHELF, SoundType.CREAKING_HEART);

        put(keys, "light_wood", SoundType.BAMBOO, SoundType.BAMBOO_SAPLING, SoundType.SCAFFOLDING);
        put(keys, "light_wood", SoundType.CHISELED_BOOKSHELF, SoundType.MANGROVE_ROOTS);
        put(keys, "light_wood", SoundType.MUDDY_MANGROVE_ROOTS);

        put(keys, "organic", SoundType.CORAL_BLOCK, SoundType.SHROOMLIGHT, SoundType.BONE_BLOCK);
        put(keys, "organic", SoundType.FROGLIGHT, SoundType.SCULK, SoundType.SCULK_CATALYST);
        put(keys, "organic", SoundType.SCULK_SENSOR, SoundType.SCULK_SHRIEKER, SoundType.RESIN);
        put(keys, "organic", SoundType.DRIED_GHAST);

        put(keys, "loose", SoundType.SAND, SoundType.GRAVEL, SoundType.SOUL_SAND, SoundType.SOUL_SOIL);
        put(keys, "loose", SoundType.MUD, SoundType.SUSPICIOUS_SAND, SoundType.SUSPICIOUS_GRAVEL);

        put(keys, "earth", SoundType.GRASS, SoundType.ROOTS, SoundType.NYLIUM, SoundType.MOSS);
        put(keys, "earth", SoundType.AZALEA, SoundType.FLOWERING_AZALEA, SoundType.FUNGUS);
        put(keys, "earth", SoundType.ROOTED_DIRT, SoundType.AZALEA_LEAVES, SoundType.CHERRY_LEAVES);
        put(keys, "earth", SoundType.MOSS_CARPET, SoundType.FROGSPAWN);

        put(keys, "foliage", SoundType.WET_GRASS, SoundType.LILY_PAD, SoundType.SWEET_BERRY_BUSH);
        put(keys, "foliage", SoundType.CROP, SoundType.HARD_CROP, SoundType.VINE, SoundType.NETHER_WART);
        put(keys, "foliage", SoundType.NETHER_SPROUTS, SoundType.WEEPING_VINES, SoundType.TWISTING_VINES);
        put(keys, "foliage", SoundType.HANGING_ROOTS, SoundType.PINK_PETALS, SoundType.LEAF_LITTER);
        put(keys, "foliage", SoundType.SPORE_BLOSSOM, SoundType.CAVE_VINES, SoundType.GLOW_LICHEN);
        put(keys, "foliage", SoundType.BIG_DRIPLEAF, SoundType.SMALL_DRIPLEAF, SoundType.SCULK_VEIN);
        put(keys, "foliage", SoundType.CACTUS_FLOWER, SoundType.CHERRY_SAPLING, SoundType.COBWEB);

        put(keys, "cloth", SoundType.WOOL);

        put(keys, "soft", SoundType.SLIME_BLOCK, SoundType.HONEY_BLOCK, SoundType.SPONGE, SoundType.WET_SPONGE);

        put(keys, "snow", SoundType.SNOW, SoundType.POWDER_SNOW);

        put(keys, "trinket", SoundType.CANDLE, SoundType.LANTERN, SoundType.SMALL_AMETHYST_BUD);
        put(keys, "trinket", SoundType.MEDIUM_AMETHYST_BUD, SoundType.LARGE_AMETHYST_BUD);
        put(keys, "trinket", SoundType.AMETHYST_CLUSTER, SoundType.DECORATED_POT, SoundType.DECORATED_POT_CRACKED);

        return keys;
    }

    private static void put(Map<SoundType, String> keys, String surface, SoundType... types) {
        for (SoundType type : types) {
            keys.put(type, surface);
        }
    }
}
