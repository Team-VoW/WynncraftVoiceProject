/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wynnvp.wynncraftvp.utils.LineFormatter;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class SoundsHandlerTest {
    @Test
    void silentDuplicateCannotOverwriteVoicedEntry() {
        SoundsHandler handler = load("""
                [
                  {"line":"Guide: It's ready.","file":"guide-ready"},
                  {"line":"Guide: Its ready."}
                ]
                """);

        SoundObject sound = handler.get(key("Guide: It's ready.")).orElseThrow();
        assertEquals("guide-ready", sound.getId());
    }

    @Test
    void voicedEntryReplacesEarlierSilentDuplicate() {
        SoundsHandler handler = load("""
                [
                  {"line":"Guide: Its ready."},
                  {"line":"Guide: It's ready.","file":"guide-ready"}
                ]
                """);

        SoundObject sound = handler.get(key("Guide: It's ready.")).orElseThrow();
        assertEquals("guide-ready", sound.getId());
    }

    @Test
    void silentLineMakesSharedEarlyPrefixAmbiguous() {
        SoundsHandler handler = load("""
                [
                  {"line":"Guide: The eastern road is open.","file":"road-open"},
                  {"line":"Guide: The eastern road is closed."}
                ]
                """);

        assertTrue(handler.findEarlyMatch(key("Guide: The eastern road is ")).isEmpty());
        assertEquals(
                "road-open",
                handler.findEarlyMatch(key("Guide: The eastern road is op"))
                        .orElseThrow()
                        .getValue()
                        .getId());
    }

    @Test
    void exactSilentLineIsRegisteredWithoutAudio() {
        SoundsHandler handler = load("""
                [{"line":"Guide: This line is intentionally silent."}]
                """);

        SoundObject sound =
                handler.get(key("Guide: This line is intentionally silent.")).orElseThrow();
        assertNull(sound.getId());
        assertFalse(sound.shouldReport());
        assertTrue(sound.canEarlyPlay());
    }

    @Test
    void reportingBlockerIsSilentButRemainsReportable() {
        SoundsHandler handler = load("""
                [{"line":"Guide: This line still needs reports.","report":true}]
                """);

        SoundObject sound =
                handler.get(key("Guide: This line still needs reports.")).orElseThrow();
        assertNull(sound.getId());
        assertTrue(sound.shouldReport());
        assertFalse(sound.canEarlyPlay());
    }

    private static SoundsHandler load(String json) {
        SoundsHandler handler = new SoundsHandler(false);
        handler.loadSoundsFromJson(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)));
        return handler;
    }

    private static String key(String line) {
        return LineFormatter.formatToLineData(line).getSoundLine();
    }
}
