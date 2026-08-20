/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.physics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.StringReader;
import org.junit.jupiter.api.Test;

class MaterialDefinitionsTest {
    private static final String TABLE = """
            {
              "blocks": {
                "minecraft:bookshelf": { "reflectivity": 0.35, "scatter": 0.8 },
                "minecraft:ice": { "surface": "glass" },
                "minecraft:water": { "mode": "pass_through", "occlusion": 0.2 }
              },
              "defaults": { "hfDamping": 0.85, "occlusion": 0.9, "reflectivity": 0.78, "scatter": 0.4 },
              "surfaces": {
                "glass": { "hfDamping": 1.0, "occlusion": 0.55, "reflectivity": 0.9, "scatter": 0.05 },
                "stone": { "hfDamping": 0.95, "occlusion": 1.0, "reflectivity": 0.92, "scatter": 0.25 }
              },
              "transparent": ["minecraft:barrier", "minecraft:light"]
            }
            """;

    private static MaterialDefinitions parse() {
        return MaterialDefinitions.parse(new StringReader(TABLE));
    }

    @Test
    void aSurfaceGroupSuppliesTheNumbersForItsSoundType() {
        AcousticMaterial stone = parse().resolve("minecraft:cobblestone", "stone");

        assertEquals(0.92f, stone.reflectivity(), 1e-6);
        assertEquals(0.25f, stone.scatter(), 1e-6);
        assertTrue(stone.isReflector());
    }

    @Test
    void aBlockEntryBeatsItsSurfaceGroup() {
        AcousticMaterial bookshelf = parse().resolve("minecraft:bookshelf", "stone");

        assertEquals(0.35f, bookshelf.reflectivity(), 1e-6);
        assertEquals(0.8f, bookshelf.scatter(), 1e-6);
        // Unstated fields fall back to the defaults rather than to the surface group.
        assertEquals(0.9f, bookshelf.occlusion(), 1e-6);
    }

    @Test
    void aBlockEntryCanStartFromANamedSurface() {
        AcousticMaterial ice = parse().resolve("minecraft:ice", "stone");

        assertEquals(0.9f, ice.reflectivity(), 1e-6);
        assertEquals(0.55f, ice.occlusion(), 1e-6);
    }

    /** The barrier rule, at the level where it is actually decided. */
    @Test
    void theTransparentSetBeatsEverythingElse() {
        MaterialDefinitions definitions = parse();

        assertSame(AcousticMaterial.TRANSPARENT, definitions.resolve("minecraft:barrier", "stone"));
        assertTrue(definitions.isTransparent("minecraft:light"));
    }

    @Test
    void anUnknownBlockAndSurfaceFallsBackToTheDefaults() {
        MaterialDefinitions definitions = parse();

        assertEquals(definitions.defaults(), definitions.resolve("somemod:strange_block", null));
        assertEquals(definitions.defaults(), definitions.resolve("somemod:strange_block", "no_such_surface"));
    }

    @Test
    void aPassThroughNeverReflectsWhateverElseItSays() {
        AcousticMaterial water = parse().resolve("minecraft:water", "stone");

        assertEquals(AcousticMaterial.Mode.PASS_THROUGH, water.mode());
        assertEquals(0f, water.reflectivity(), 1e-6);
        assertEquals(0f, water.scatter(), 1e-6);
        assertEquals(0.2f, water.occlusion(), 1e-6);
    }

    /** Fluids report SoundType.STONE, so without an override a pond would read as a stone wall. */
    @Test
    void theBuiltInFallbackStillKnowsFluidsAreNotStone() {
        MaterialDefinitions builtIn = MaterialDefinitions.builtIn();

        assertEquals(
                AcousticMaterial.Mode.PASS_THROUGH,
                builtIn.resolve("minecraft:water", "stone").mode());
        assertEquals(
                AcousticMaterial.Mode.PASS_THROUGH,
                builtIn.resolve("minecraft:lava", "stone").mode());
        assertSame(AcousticMaterial.TRANSPARENT, builtIn.resolve("minecraft:barrier", "stone"));
    }

    /**
     * A typo on a block meant to be invisible must not quietly turn it into a wall. That is the one
     * mistake this system cannot afford, so it is reported and the safer base mode is kept.
     */
    @Test
    void anUnknownModeIsReportedRatherThanTreatedAsAWall() {
        String table = """
                {
                  "blocks": { "minecraft:glass_pane": { "mode": "pass-through", "occlusion": 0.1 } },
                  "defaults": { "mode": "transparent" },
                  "surfaces": {},
                  "transparent": []
                }
                """;

        MaterialDefinitions definitions = MaterialDefinitions.parse(new StringReader(table));

        assertSame(AcousticMaterial.TRANSPARENT, definitions.resolve("minecraft:glass_pane", null));
        assertEquals(1, definitions.warnings().size());
        assertTrue(
                definitions.warnings().get(0).contains("pass-through"),
                "the warning should name the offending value: " + definitions.warnings());
    }

    @Test
    void aWellFormedTableReportsNothing() {
        assertTrue(parse().warnings().isEmpty());
    }

    @Test
    void geometryScalingWeakensPartialBlocks() {
        AcousticMaterial stone = parse().resolve("minecraft:cobblestone", "stone");

        AcousticMaterial slab = stone.asReflector(0.5f);
        assertEquals(0.92f, slab.reflectivity(), 1e-6);
        assertEquals(0.5f, slab.occlusion(), 1e-6);

        AcousticMaterial pane = stone.asPassThrough(0.25f);
        assertEquals(AcousticMaterial.Mode.PASS_THROUGH, pane.mode());
        assertEquals(0f, pane.reflectivity(), 1e-6);
        assertEquals(0.25f, pane.occlusion(), 1e-6);
    }
}
