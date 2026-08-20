/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.physics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

/** Guards the shipped table itself, which no compiler checks. */
class MaterialsResourceTest {
    private static final String RESOURCE = "assets/wynnvp/sound_physics/materials.json";
    private static final Pattern BLOCK_ID = Pattern.compile("[a-z0-9_.-]+:[a-z0-9_./-]+");

    private static MaterialDefinitions load() throws IOException {
        try (InputStream stream = MaterialsResourceTest.class.getClassLoader().getResourceAsStream(RESOURCE)) {
            assertNotNull(stream, "the bundled materials table is missing from the jar");
            try (Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
                return MaterialDefinitions.parse(reader);
            }
        }
    }

    @Test
    void theBundledTableParses() throws IOException {
        MaterialDefinitions definitions = load();

        assertTrue(definitions.surfaceKeys().size() >= 10, "the table lost most of its surface groups");
        assertTrue(definitions.defaults().isReflector());
    }

    /** The single rule the whole feature is built around. */
    @Test
    void barriersAreShippedAsAcousticallyInvisible() throws IOException {
        MaterialDefinitions definitions = load();

        for (String id : List.of(
                "minecraft:barrier", "minecraft:light", "minecraft:structure_void", "minecraft:moving_piston")) {
            assertSame(AcousticMaterial.TRANSPARENT, definitions.resolve(id, "stone"), id + " must not reflect sound");
        }
    }

    @Test
    void fluidsAreShippedAsPassThroughRatherThanStone() throws IOException {
        MaterialDefinitions definitions = load();

        for (String id : List.of("minecraft:water", "minecraft:flowing_water", "minecraft:lava")) {
            assertEquals(
                    AcousticMaterial.Mode.PASS_THROUGH,
                    definitions.resolve(id, "stone").mode(),
                    id + " reports SoundType.STONE and needs an override");
        }
    }

    @Test
    void woodIsClearlySofterThanStone() throws IOException {
        MaterialDefinitions definitions = load();

        AcousticMaterial wood = definitions.resolve("x:x", "wood");
        AcousticMaterial stone = definitions.resolve("x:x", "stone");

        // The row that decides whether a Wynncraft inn sounds like an inn or like a dungeon.
        assertTrue(wood.reflectivity() < stone.reflectivity() - 0.15f);
        assertTrue(wood.hfDamping() < stone.hfDamping());
    }

    @Test
    void everyValueIsInAPlausibleRange() throws IOException {
        MaterialDefinitions definitions = load();

        for (String key : definitions.surfaceKeys()) {
            AcousticMaterial material = definitions.resolve("x:x", key);
            assertInUnit(key + ".reflectivity", material.reflectivity());
            assertInUnit(key + ".hfDamping", material.hfDamping());
            assertInUnit(key + ".scatter", material.scatter());
            assertTrue(
                    material.occlusion() >= 0f && material.occlusion() <= 2f,
                    key + ".occlusion was " + material.occlusion());
        }
    }

    @Test
    void everyBlockIdIsWellFormed() throws IOException {
        String json = Files.readString(Path.of("src/main/resources").resolve(RESOURCE), StandardCharsets.UTF_8);
        var matcher = Pattern.compile("\"(minecraft:[^\"]+)\"").matcher(json);

        int found = 0;
        while (matcher.find()) {
            String id = matcher.group(1);
            assertTrue(BLOCK_ID.matcher(id).matches(), "malformed block id: " + id);
            found++;
        }
        assertTrue(found > 10, "expected the table to name a good number of blocks, found " + found);
    }

    private static void assertInUnit(String name, float value) {
        assertTrue(value >= 0f && value <= 1f, name + " was " + value + ", outside [0, 1]");
    }
}
