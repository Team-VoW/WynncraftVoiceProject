/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.physics;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * The acoustic material table, as data.
 *
 * <p>Deliberately keyed on strings rather than {@code BlockState} so the whole precedence chain can be
 * tested without Minecraft. {@link AcousticMaterialTable} is the thin adapter that turns a block state
 * into the two strings this needs.
 *
 * <p>Lookup order is load-bearing. The transparent set is consulted before anything else because
 * barriers are a fully solid, fully opaque block that has to behave as though it were not there --
 * Wynncraft builds its invisible walls out of them, and any shape or occlusion test applied first
 * would let them bounce sound.
 */
public final class MaterialDefinitions {
    private final AcousticMaterial defaults;
    private final Map<String, AcousticMaterial> surfaces;
    private final Map<String, AcousticMaterial> blocks;
    private final Set<String> transparent;
    private final List<String> warnings;

    private MaterialDefinitions(
            AcousticMaterial defaults,
            Map<String, AcousticMaterial> surfaces,
            Map<String, AcousticMaterial> blocks,
            Set<String> transparent,
            List<String> warnings) {
        this.defaults = defaults;
        this.surfaces = surfaces;
        this.blocks = blocks;
        this.transparent = transparent;
        this.warnings = warnings;
    }

    /**
     * Anything suspect found while parsing.
     *
     * <p>Reported rather than thrown so one bad entry cannot cost the player the whole table, and
     * collected rather than logged directly so this class stays free of mod and Minecraft imports.
     */
    public List<String> warnings() {
        return List.copyOf(warnings);
    }

    /**
     * The last-resort table, used when the bundled resource is missing or unreadable.
     *
     * <p>It carries only what the mod cannot function without: a plausible average surface, the
     * blocks that must never reflect, and the fluids that would otherwise be read as stone. Everything
     * else falls back to the average, which sounds bland rather than wrong.
     */
    public static MaterialDefinitions builtIn() {
        AcousticMaterial defaults = new AcousticMaterial(0.78f, 0.85f, 0.40f, 0.90f, AcousticMaterial.Mode.REFLECTOR);
        Map<String, AcousticMaterial> blocks = new HashMap<>();
        AcousticMaterial water = new AcousticMaterial(0f, 0.90f, 0f, 0.20f, AcousticMaterial.Mode.PASS_THROUGH);
        AcousticMaterial lava = new AcousticMaterial(0f, 0.70f, 0f, 0.35f, AcousticMaterial.Mode.PASS_THROUGH);
        blocks.put("minecraft:water", water);
        blocks.put("minecraft:flowing_water", water);
        blocks.put("minecraft:lava", lava);
        blocks.put("minecraft:flowing_lava", lava);
        return new MaterialDefinitions(defaults, Map.of(), blocks, defaultTransparent(), List.of());
    }

    private static Set<String> defaultTransparent() {
        return new HashSet<>(Set.of(
                "minecraft:air",
                "minecraft:cave_air",
                "minecraft:void_air",
                "minecraft:barrier",
                "minecraft:light",
                "minecraft:structure_void",
                "minecraft:moving_piston"));
    }

    /** Parses the table. Throws only on genuinely unreadable input; the caller decides what to do. */
    public static MaterialDefinitions parse(Reader reader) {
        JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
        List<String> warnings = new ArrayList<>();

        AcousticMaterial defaults = readMaterial(
                warnings,
                "defaults",
                root.getAsJsonObject("defaults"),
                new AcousticMaterial(0.78f, 0.85f, 0.40f, 0.90f, AcousticMaterial.Mode.REFLECTOR));

        Map<String, AcousticMaterial> surfaces = new HashMap<>();
        JsonObject surfaceJson = root.getAsJsonObject("surfaces");
        if (surfaceJson != null) {
            for (Map.Entry<String, JsonElement> entry : surfaceJson.entrySet()) {
                surfaces.put(
                        entry.getKey(),
                        readMaterial(
                                warnings,
                                "surface " + entry.getKey(),
                                entry.getValue().getAsJsonObject(),
                                defaults));
            }
        }

        Map<String, AcousticMaterial> blocks = new HashMap<>();
        JsonObject blockJson = root.getAsJsonObject("blocks");
        if (blockJson != null) {
            for (Map.Entry<String, JsonElement> entry : blockJson.entrySet()) {
                JsonObject value = entry.getValue().getAsJsonObject();
                // A block entry may start from a named surface rather than the global average, so
                // "ice behaves like glass" does not mean restating four numbers.
                AcousticMaterial base = defaults;
                if (value.has("surface")) {
                    base = surfaces.getOrDefault(value.get("surface").getAsString(), defaults);
                }
                blocks.put(entry.getKey(), readMaterial(warnings, "block " + entry.getKey(), value, base));
            }
        }

        Set<String> transparent = new HashSet<>();
        if (root.has("transparent")) {
            root.getAsJsonArray("transparent").forEach(element -> transparent.add(element.getAsString()));
        }

        return new MaterialDefinitions(defaults, surfaces, blocks, transparent, warnings);
    }

    private static AcousticMaterial readMaterial(
            List<String> warnings, String where, JsonObject json, AcousticMaterial base) {
        if (json == null) {
            return base;
        }
        AcousticMaterial.Mode mode = base.mode();
        if (json.has("mode")) {
            String name = json.get("mode").getAsString();
            switch (name) {
                case "transparent" -> mode = AcousticMaterial.Mode.TRANSPARENT;
                case "pass_through" -> mode = AcousticMaterial.Mode.PASS_THROUGH;
                case "reflector" -> mode = AcousticMaterial.Mode.REFLECTOR;
                // Never quietly assume "reflector": a typo on a block meant to be transparent
                // would turn it into a wall, which is the one mistake this system cannot afford.
                default ->
                    warnings.add("Unknown sound material mode \"" + name + "\" on " + where + ", keeping "
                            + base.mode().name().toLowerCase(Locale.ROOT));
            }
        }
        if (mode == AcousticMaterial.Mode.TRANSPARENT) {
            return AcousticMaterial.TRANSPARENT;
        }
        // A pass-through never bounces anything, so only its occlusion is meaningful.
        float reflectivity =
                mode == AcousticMaterial.Mode.PASS_THROUGH ? 0f : read(json, "reflectivity", base.reflectivity());
        float scatter = mode == AcousticMaterial.Mode.PASS_THROUGH ? 0f : read(json, "scatter", base.scatter());
        return new AcousticMaterial(
                reflectivity,
                read(json, "hfDamping", base.hfDamping()),
                scatter,
                read(json, "occlusion", base.occlusion()),
                mode);
    }

    private static float read(JsonObject json, String key, float fallback) {
        return json.has(key) ? json.get(key).getAsFloat() : fallback;
    }

    /**
     * @param blockId namespaced block id, for example {@code minecraft:oak_planks}
     * @param surfaceKey the surface group its sound type belongs to, or {@code null} if unknown
     */
    public AcousticMaterial resolve(String blockId, String surfaceKey) {
        if (transparent.contains(blockId)) {
            return AcousticMaterial.TRANSPARENT;
        }
        AcousticMaterial block = blocks.get(blockId);
        if (block != null) {
            return block;
        }
        if (surfaceKey != null) {
            AcousticMaterial surface = surfaces.get(surfaceKey);
            if (surface != null) {
                return surface;
            }
        }
        return defaults;
    }

    public boolean isTransparent(String blockId) {
        return transparent.contains(blockId);
    }

    public AcousticMaterial defaults() {
        return defaults;
    }

    public Set<String> surfaceKeys() {
        return Set.copyOf(surfaces.keySet());
    }
}
