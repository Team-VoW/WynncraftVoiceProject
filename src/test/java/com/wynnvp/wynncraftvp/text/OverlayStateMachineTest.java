/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wynnvp.wynncraftvp.utils.LineFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OverlayStateMachineTest {
    // -------------------------------------------------------------------------
    // Test infrastructure
    // -------------------------------------------------------------------------

    // Fake tick clock — set tick[0] to control time.
    private final long[] tick = {1};

    // Recording listener
    private final List<FiredEvent> fired = new ArrayList<>();
    private final List<AlreadyPlayedEvent> alreadyPlayed = new ArrayList<>();
    private boolean stopCalled = false;
    private final List<String> earlyPlayAttempts = new ArrayList<>();

    // Control what tryEarlyPlay returns (simulate manifest lookup result)
    private String earlyPlayReturn = null;

    private OverlayStateMachine machine;

    /** Fired when the machine decides to play a new sound. */
    private record FiredEvent(String combined, String formattedLine, String finalKey) {}

    /** Fired when early play was correct and no re-play is needed. */
    private record AlreadyPlayedEvent(String combined, String formattedLine) {}

    @BeforeEach
    void setUp() {
        fired.clear();
        alreadyPlayed.clear();
        stopCalled = false;
        earlyPlayAttempts.clear();
        earlyPlayReturn = null;
        // Start at 1 to mirror realistic Minecraft game time (worlds effectively start > 0).
        tick[0] = 1;

        machine = buildMachine(Function.identity()); // no player-name substitution in tests
    }

    private OverlayStateMachine buildMachine(Function<String, String> nameReplacer) {
        OverlayDialogueListener listener = new OverlayDialogueListener() {
            @Override
            public void onDialogueFired(String combined, String formattedLine, String finalKey) {
                fired.add(new FiredEvent(combined, formattedLine, finalKey));
            }

            @Override
            public void onDialogueAlreadyPlayed(String combined, String formattedLine) {
                alreadyPlayed.add(new AlreadyPlayedEvent(combined, formattedLine));
            }

            @Override
            public void onWrongEarlyPlay() {
                stopCalled = true;
            }

            @Override
            public String tryEarlyPlay(String rawCombined, String excludeKey) {
                earlyPlayAttempts.add(rawCombined);
                return earlyPlayReturn;
            }
        };
        return new OverlayStateMachine(() -> tick[0], listener, () -> true, nameReplacer);
    }

    // Helpers to reduce boilerplate in scenario tests
    private void feed(long t, String body, String npc) {
        tick[0] = t;
        machine.onTextReceived(body, npc);
    }

    private void advanceTo(long t) {
        tick[0] = t;
        machine.onTick();
    }

    /**
     * Feeds the same body/npc OVERLAY_STABILITY_REPEATS more times at the current tick.
     * Simulates the completed overlay staying visible (duplicate packets) so the repeat
     * threshold is satisfied before calling advanceTo to trigger the stability fire.
     */
    private void saturate(String body, String npc) {
        for (int i = 0; i < OverlayStateMachine.OVERLAY_STABILITY_REPEATS; i++) {
            machine.onTextReceived(body, npc);
        }
    }

    /** Returns what LineFormatter produces for a given line — useful when setting earlyPlayReturn. */
    private static String key(String line) {
        return LineFormatter.formatToLineData(line).getSoundLine();
    }

    // -------------------------------------------------------------------------
    // Scenario tests — feed real packet sequences, assert audio outcomes
    // -------------------------------------------------------------------------

    /**
     * Real in-game packet capture: Tasim's "It's been a while…" line, logged as [PLAYED].
     *
     * <p>Ticks are normalised by subtracting 7736779714 so the first packet lands at tick 1.
     * Last body change: tick 48 ("right?" completes). Stability fires at tick 53 (48 + 5).
     *
     * <p>[PLAYED] means early play returned the correct manifest key → {@code
     * onDialogueAlreadyPlayed} must fire and audio must NOT be stopped.
     */
    @Test
    void scenario_realCapture_tasim_itHasBeenAWhile_earlyPlayCorrect() {
        String fullLine =
                "Tasim: It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?";
        earlyPlayReturn = key(fullLine);

        // All 160 packets from the in-game capture (ticks normalised: subtract 7736779714).
        feed(1L, "I", "Tasim");
        feed(2L, "I", "Tasim");
        feed(2L, "I", "Tasim");
        feed(3L, "It", "Tasim");
        feed(3L, "It'", "Tasim");
        feed(3L, "It's", "Tasim");
        feed(4L, "It's", "Tasim");
        feed(4L, "It's", "Tasim");
        feed(5L, "It's b", "Tasim");
        feed(5L, "It's be", "Tasim");
        feed(5L, "It's bee", "Tasim");
        feed(6L, "It's been", "Tasim");
        feed(6L, "It's been", "Tasim");
        feed(7L, "It's been", "Tasim");
        feed(7L, "It's been a", "Tasim");
        feed(7L, "It's been a", "Tasim");
        feed(8L, "It's been a w", "Tasim");
        feed(8L, "It's been a w", "Tasim");
        feed(9L, "It's been a wh", "Tasim");
        feed(9L, "It's been a whi", "Tasim");
        feed(9L, "It's been a whil", "Tasim");
        feed(10L, "It's been a while", "Tasim");
        feed(10L, "It's been a while", "Tasim");
        feed(11L, "It's been a while,", "Tasim");
        feed(11L, "It's been a while,", "Tasim");
        feed(11L, "It's been a while, h", "Tasim");
        feed(12L, "It's been a while, ha", "Tasim");
        feed(12L, "It's been a while, ha", "Tasim");
        feed(13L, "It's been a while, has", "Tasim");
        feed(13L, "It's been a while, hasn", "Tasim");
        feed(14L, "It's been a while, hasn'", "Tasim");
        feed(14L, "It's been a while, hasn't", "Tasim");
        feed(14L, "It's been a while, hasn't", "Tasim");
        feed(14L, "It's been a while, hasn't", "Tasim");
        feed(15L, "It's been a while, hasn't i", "Tasim");
        feed(16L, "It's been a while, hasn't it", "Tasim");
        feed(16L, "It's been a while, hasn't it?", "Tasim");
        feed(16L, "It's been a while, hasn't it?", "Tasim");
        feed(16L, "It's been a while, hasn't it?", "Tasim");
        feed(17L, "It's been a while, hasn't it? W", "Tasim");
        feed(18L, "It's been a while, hasn't it? We", "Tasim");
        feed(18L, "It's been a while, hasn't it? We", "Tasim");
        feed(19L, "It's been a while, hasn't it? We", "Tasim");
        feed(19L, "It's been a while, hasn't it? We s", "Tasim");
        feed(19L, "It's been a while, hasn't it? We sh", "Tasim");
        feed(19L, "It's been a while, hasn't it? We sho", "Tasim");
        feed(20L, "It's been a while, hasn't it? We shou", "Tasim");
        feed(20L, "It's been a while, hasn't it? We shou", "Tasim");
        feed(21L, "It's been a while, hasn't it? We shoul", "Tasim");
        feed(21L, "It's been a while, hasn't it? We should", "Tasim");
        feed(21L, "It's been a while, hasn't it? We should", "Tasim");
        feed(22L, "It's been a while, hasn't it? We should c", "Tasim");
        feed(22L, "It's been a while, hasn't it? We should c", "Tasim");
        feed(23L, "It's been a while, hasn't it? We should ca", "Tasim");
        feed(23L, "It's been a while, hasn't it? We should cat", "Tasim");
        feed(23L, "It's been a while, hasn't it? We should catc", "Tasim");
        feed(24L, "It's been a while, hasn't it? We should catch", "Tasim");
        feed(25L, "It's been a while, hasn't it? We should catch", "Tasim");
        feed(25L, "It's been a while, hasn't it? We should catch", "Tasim");
        feed(25L, "It's been a while, hasn't it? We should catch u", "Tasim");
        feed(25L, "It's been a while, hasn't it? We should catch up", "Tasim");
        feed(26L, "It's been a while, hasn't it? We should catch up!", "Tasim");
        feed(26L, "It's been a while, hasn't it? We should catch up!", "Tasim");
        feed(27L, "It's been a while, hasn't it? We should catch up!", "Tasim");
        feed(27L, "It's been a while, hasn't it? We should catch up! Y", "Tasim");
        feed(27L, "It's been a while, hasn't it? We should catch up! Yo", "Tasim");
        feed(28L, "It's been a while, hasn't it? We should catch up! You", "Tasim");
        feed(28L, "It's been a while, hasn't it? We should catch up! You", "Tasim");
        feed(29L, "It's been a while, hasn't it? We should catch up! You'", "Tasim");
        feed(29L, "It's been a while, hasn't it? We should catch up! You'v", "Tasim");
        feed(29L, "It's been a while, hasn't it? We should catch up! You've", "Tasim");
        feed(30L, "It's been a while, hasn't it? We should catch up! You've", "Tasim");
        feed(30L, "It's been a while, hasn't it? We should catch up! You've", "Tasim");
        feed(31L, "It's been a while, hasn't it? We should catch up! You've p", "Tasim");
        feed(31L, "It's been a while, hasn't it? We should catch up! You've pr", "Tasim");
        feed(31L, "It's been a while, hasn't it? We should catch up! You've pro", "Tasim");
        feed(32L, "It's been a while, hasn't it? We should catch up! You've prob", "Tasim");
        feed(32L, "It's been a while, hasn't it? We should catch up! You've prob", "Tasim");
        feed(33L, "It's been a while, hasn't it? We should catch up! You've proba", "Tasim");
        feed(33L, "It's been a while, hasn't it? We should catch up! You've probab", "Tasim");
        feed(33L, "It's been a while, hasn't it? We should catch up! You've probabl", "Tasim");
        feed(34L, "It's been a while, hasn't it? We should catch up! You've probably", "Tasim");
        feed(34L, "It's been a while, hasn't it? We should catch up! You've probably", "Tasim");
        feed(35L, "It's been a while, hasn't it? We should catch up! You've probably", "Tasim");
        feed(35L, "It's been a while, hasn't it? We should catch up! You've probably h", "Tasim");
        feed(35L, "It's been a while, hasn't it? We should catch up! You've probably ha", "Tasim");
        feed(36L, "It's been a while, hasn't it? We should catch up! You've probably had", "Tasim");
        feed(36L, "It's been a while, hasn't it? We should catch up! You've probably had", "Tasim");
        feed(37L, "It's been a while, hasn't it? We should catch up! You've probably had", "Tasim");
        feed(36L, "It's been a while, hasn't it? We should catch up! You've probably had l", "Tasim");
        feed(36L, "It's been a while, hasn't it? We should catch up! You've probably had lo", "Tasim");
        feed(37L, "It's been a while, hasn't it? We should catch up! You've probably had lot", "Tasim");
        feed(37L, "It's been a while, hasn't it? We should catch up! You've probably had lot", "Tasim");
        feed(38L, "It's been a while, hasn't it? We should catch up! You've probably had lots", "Tasim");
        feed(38L, "It's been a while, hasn't it? We should catch up! You've probably had lots", "Tasim");
        feed(38L, "It's been a while, hasn't it? We should catch up! You've probably had lots o", "Tasim");
        feed(39L, "It's been a while, hasn't it? We should catch up! You've probably had lots of", "Tasim");
        feed(39L, "It's been a while, hasn't it? We should catch up! You've probably had lots of", "Tasim");
        feed(40L, "It's been a while, hasn't it? We should catch up! You've probably had lots of", "Tasim");
        feed(40L, "It's been a while, hasn't it? We should catch up! You've probably had lots of a", "Tasim");
        feed(40L, "It's been a while, hasn't it? We should catch up! You've probably had lots of ad", "Tasim");
        feed(41L, "It's been a while, hasn't it? We should catch up! You've probably had lots of adv", "Tasim");
        feed(41L, "It's been a while, hasn't it? We should catch up! You've probably had lots of adv", "Tasim");
        feed(42L, "It's been a while, hasn't it? We should catch up! You've probably had lots of adve", "Tasim");
        feed(42L, "It's been a while, hasn't it? We should catch up! You've probably had lots of adven", "Tasim");
        feed(42L, "It's been a while, hasn't it? We should catch up! You've probably had lots of advent", "Tasim");
        feed(43L, "It's been a while, hasn't it? We should catch up! You've probably had lots of adventu", "Tasim");
        feed(43L, "It's been a while, hasn't it? We should catch up! You've probably had lots of adventu", "Tasim");
        feed(44L, "It's been a while, hasn't it? We should catch up! You've probably had lots of adventur", "Tasim");
        feed(44L, "It's been a while, hasn't it? We should catch up! You've probably had lots of adventure", "Tasim");
        feed(44L, "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures", "Tasim");
        feed(45L, "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures,", "Tasim");
        feed(45L, "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures,", "Tasim");
        feed(46L, "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures,", "Tasim");
        feed(
                46L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, r",
                "Tasim");
        feed(
                47L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, ri",
                "Tasim");
        feed(
                47L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, rig",
                "Tasim");
        feed(
                47L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, rig",
                "Tasim");
        feed(
                48L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, righ",
                "Tasim");
        feed(
                48L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right",
                "Tasim");
        feed(
                48L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                49L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                49L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                50L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                50L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                50L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                51L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                51L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                52L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                52L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                53L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                53L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                53L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                54L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                54L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                54L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                55L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                55L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                56L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                56L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                56L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                57L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                57L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                57L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                58L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                58L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                58L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                59L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                59L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                59L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                60L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                60L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                60L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                61L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                61L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                62L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                62L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                63L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                63L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                63L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                64L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                64L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                65L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                65L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                65L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                66L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                66L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                67L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                67L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                68L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                68L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");
        feed(
                68L,
                "It's been a while, hasn't it? We should catch up! You've probably had lots of adventures, right?",
                "Tasim");

        // Stability reached at tick 48 + OVERLAY_STABILITY_TICKS = 53; trigger the onTick fire.
        advanceTo(53L);

        assertFalse(stopCalled, "Early play key was correct — must NOT stop audio");
        assertTrue(fired.isEmpty(), "Sound already playing correctly — must NOT re-fire");
        assertEquals(1, alreadyPlayed.size());
        assertEquals(fullLine, alreadyPlayed.get(0).combined());
    }

    @Test
    void scenario_earlyPlay_correctKey_doesNotStopAudio() {
        // Manifest matches exactly when body is complete; early play returns that key.
        // Expected: onDialogueAlreadyPlayed fires, stopCalled stays false.
        String fullLine = "Commander: Soldier, I hope you know";
        earlyPlayReturn = key(fullLine); // simulate manifest returning the correct key

        // Typewriter animation arriving one tick at a time
        feed(10, "Soldier,", "Commander");
        feed(11, "Soldier, I hope", "Commander");
        feed(12, "Soldier, I hope you", "Commander");
        feed(13, "Soldier, I hope you know", "Commander");
        saturate("Soldier, I hope you know", "Commander");
        // Stable — fire at 13 + 5 = 18
        advanceTo(18);

        assertFalse(stopCalled, "Correct early play key — must NOT stop audio");
        assertTrue(fired.isEmpty(), "Sound already playing correctly — must NOT re-fire");
        assertEquals(1, alreadyPlayed.size());
        assertEquals("Commander: Soldier, I hope you know", alreadyPlayed.get(0).combined());
    }

    @Test
    void scenario_earlyPlay_wrongKey_stopsAndReplays() {
        // Early play starts a sound, but the typewriter completes to a DIFFERENT line
        // (e.g. the player guessed wrong because text was ambiguous mid-stream).
        // Expected: onWrongEarlyPlay fires, then onDialogueFired fires.
        earlyPlayReturn = "commander3awrongsound"; // wrong key — won't match final

        feed(10, "Soldier,", "Commander");
        feed(11, "Soldier, I hope", "Commander");
        feed(12, "Soldier, I hope you know", "Commander");
        saturate("Soldier, I hope you know", "Commander");
        advanceTo(17); // 12 + 5

        assertTrue(stopCalled, "Wrong early play key — must stop audio");
        assertEquals(1, fired.size(), "Must re-fire with correct sound after stopping");
        // The correct key is the formatter output for the full line
        assertEquals(key("Commander: Soldier, I hope you know"), fired.get(0).finalKey());
        assertTrue(alreadyPlayed.isEmpty());
    }

    @Test
    void scenario_noEarlyPlay_normalFire() {
        // Early play feature off (or no manifest match) — just fires when stable.
        earlyPlayReturn = null; // no match

        feed(10, "Soldier,", "Commander");
        feed(11, "Soldier, I hope you know", "Commander");
        saturate("Soldier, I hope you know", "Commander");
        advanceTo(16); // 11 + 5

        assertFalse(stopCalled);
        assertEquals(1, fired.size());
        assertEquals("Commander: Soldier, I hope you know", fired.get(0).combined());
        assertTrue(alreadyPlayed.isEmpty());
    }

    // -------------------------------------------------------------------------
    // Tick stability
    // -------------------------------------------------------------------------

    @Test
    void doesNotFireBeforeStabilityThreshold() {
        machine.onTextReceived("Hello world", "Bob");
        for (int i = 1; i < OverlayStateMachine.OVERLAY_STABILITY_TICKS; i++) {
            tick[0] = 1 + i;
            machine.onTick();
        }
        assertTrue(fired.isEmpty(), "Should not fire before stability threshold");
    }

    @Test
    void firesExactlyAtStabilityThreshold() {
        machine.onTextReceived("Hello world", "Bob"); // tick = 1, lastBodyChangeTick = 1
        saturate("Hello world", "Bob");
        advanceTo(1 + OverlayStateMachine.OVERLAY_STABILITY_TICKS);

        assertEquals(1, fired.size() + alreadyPlayed.size(), "Should fire exactly once");
        String combined =
                fired.isEmpty() ? alreadyPlayed.get(0).combined() : fired.get(0).combined();
        assertEquals("Bob: Hello world", combined);
    }

    @Test
    void textChangeResetsStabilityCounter() {
        machine.onTextReceived("Hello", "Bob"); // lastBodyChangeTick = 1
        feed(3, "Hello world", "Bob"); // resets counter to tick 3
        saturate("Hello world", "Bob");

        advanceTo(7); // 3 + 4 — not yet stable
        assertEquals(0, fired.size() + alreadyPlayed.size());

        advanceTo(8); // 3 + 5 — stable
        assertEquals(1, fired.size() + alreadyPlayed.size());
        assertEquals("Bob: Hello world", fired.get(0).combined());
    }

    @Test
    void packetStabilityAlsoTriggersFire() {
        machine.onTextReceived("Hello", "Bob");
        for (long t = 2; t < 1 + OverlayStateMachine.OVERLAY_STABILITY_TICKS; t++) {
            tick[0] = t;
            machine.onTextReceived("Hello", "Bob"); // body unchanged, refreshes packet tick
        }
        saturate("Hello", "Bob"); // ensure repeat threshold is met
        assertTrue(fired.isEmpty() && alreadyPlayed.isEmpty());

        advanceTo(OverlayStateMachine.OVERLAY_STABILITY_TICKS * 2L);
        assertEquals(1, fired.size() + alreadyPlayed.size());
    }

    // -------------------------------------------------------------------------
    // State transitions
    // -------------------------------------------------------------------------

    @Test
    void blankBodyClearsVoiceDialogActive() {
        machine.onTextReceived("Hello", "Bob");
        assertTrue(machine.isVoiceDialogActive());
        machine.onTextReceived("", "Bob");
        assertFalse(machine.isVoiceDialogActive());
    }

    @Test
    void npcChangeFiresCurrentPendingFirst() {
        machine.onTextReceived("Line one", "Alice");
        machine.onTextReceived("Line two", "Bob"); // NPC changed → fires Alice first

        assertEquals(1, fired.size() + alreadyPlayed.size());
        assertEquals("Alice: Line one", fired.get(0).combined());
    }

    @Test
    void bodyShortenerFiresCurrentPending() {
        machine.onTextReceived("A very long line of text", "Alice");
        machine.onTextReceived("Hi", "Alice"); // <50% length → fire previous

        assertEquals(1, fired.size() + alreadyPlayed.size());
        assertEquals("Alice: A very long line of text", fired.get(0).combined());
    }

    @Test
    void duplicateCombinedTextIsIgnored() {
        machine.onTextReceived("Hello", "Bob"); // tick = 1
        saturate("Hello", "Bob");
        advanceTo(1 + OverlayStateMachine.OVERLAY_STABILITY_TICKS);
        assertEquals(1, fired.size() + alreadyPlayed.size());

        // Same text arrives again later
        feed(100, "Hello", "Bob");
        saturate("Hello", "Bob");
        advanceTo(100 + OverlayStateMachine.OVERLAY_STABILITY_TICKS);
        assertEquals(1, fired.size() + alreadyPlayed.size(), "Duplicate must be ignored");
    }

    // -------------------------------------------------------------------------
    // Timeout
    // -------------------------------------------------------------------------

    @Test
    void voiceDialogActiveClearsAfterTimeout() {
        machine.onTextReceived("Hello", "Bob"); // lastReceivedOverlayTick = 1
        assertTrue(machine.isVoiceDialogActive());

        advanceTo(1 + OverlayStateMachine.VOICE_DIALOG_TIMEOUT_TICKS); // tick 201
        assertFalse(machine.isVoiceDialogActive());
    }

    @Test
    void timeoutDoesNotTriggerBeforeThreshold() {
        machine.onTextReceived("Hello", "Bob"); // lastReceivedOverlayTick = 1
        advanceTo(OverlayStateMachine.VOICE_DIALOG_TIMEOUT_TICKS); // tick 200, need 201
        assertTrue(machine.isVoiceDialogActive());
    }

    // -------------------------------------------------------------------------
    // Early play
    // -------------------------------------------------------------------------

    @Test
    void earlyPlayNotCalledWhenDisabled() {
        OverlayStateMachine disabledMachine = new OverlayStateMachine(
                () -> tick[0],
                new OverlayDialogueListener() {
                    @Override
                    public void onDialogueFired(String combined, String formattedLine, String finalKey) {
                        fired.add(new FiredEvent(combined, formattedLine, finalKey));
                    }

                    @Override
                    public void onDialogueAlreadyPlayed(String combined, String formattedLine) {}

                    @Override
                    public void onWrongEarlyPlay() {}

                    @Override
                    public String tryEarlyPlay(String rawCombined, String excludeKey) {
                        earlyPlayAttempts.add(rawCombined);
                        return null;
                    }
                },
                () -> false, // early play disabled
                Function.identity());

        disabledMachine.onTextReceived("Hello", "Bob");
        assertTrue(earlyPlayAttempts.isEmpty(), "tryEarlyPlay must not be called when feature is disabled");
    }

    @Test
    void earlyPlayCalledWithRawCombined() {
        machine.onTextReceived("Hello world", "Bob");
        assertEquals(1, earlyPlayAttempts.size());
        assertEquals("Bob: Hello world", earlyPlayAttempts.get(0));
    }

    @Test
    void earlyPlayNotCalledAgainAfterSuccessfulMatch() {
        earlyPlayReturn = "some/key";
        machine.onTextReceived("Hello", "Bob");
        assertEquals(1, earlyPlayAttempts.size());

        // Body grows — earlyPlayed is already true, no second attempt
        machine.onTextReceived("Hello world", "Bob");
        assertEquals(1, earlyPlayAttempts.size());
    }

    // -------------------------------------------------------------------------
    // Reset
    // -------------------------------------------------------------------------

    @Test
    void resetClearsAllState() {
        machine.onTextReceived("Hello", "Bob"); // tick = 1
        assertTrue(machine.isVoiceDialogActive());

        machine.reset();
        assertFalse(machine.isVoiceDialogActive());

        // After reset, same text should fire again (lastFiredText cleared)
        feed(50, "Hello", "Bob");
        saturate("Hello", "Bob");
        advanceTo(50 + OverlayStateMachine.OVERLAY_STABILITY_TICKS);
        assertEquals(1, fired.size() + alreadyPlayed.size());
    }

    // -------------------------------------------------------------------------
    // Edge cases
    // -------------------------------------------------------------------------

    @Test
    void nullBodyIsIgnoredGracefully() {
        machine.onTextReceived(null, "Bob");
        assertFalse(machine.isVoiceDialogActive());
        assertTrue(fired.isEmpty() && alreadyPlayed.isEmpty());
    }

    @Test
    void nullNpcFallsBackToPreviousNpc() {
        machine.onTextReceived("Hello", "Bob"); // tick = 1, pendingNpc = "Bob"
        feed(2, "World", null); // null npc → reuse "Bob", body changed
        saturate("World", null);

        assertTrue(fired.isEmpty() && alreadyPlayed.isEmpty()); // no immediate fire
        advanceTo(2 + OverlayStateMachine.OVERLAY_STABILITY_TICKS);
        assertEquals(1, fired.size() + alreadyPlayed.size());
        assertEquals("Bob: World", fired.get(0).combined());
    }

    @Test
    void playerNameReplacerAppliedBeforeKeyLookup() {
        machine = buildMachine(text -> text.replace("Alice", "soldier"));
        feed(1, "Alice told me", "Bob");
        saturate("Alice told me", "Bob");
        advanceTo(1 + OverlayStateMachine.OVERLAY_STABILITY_TICKS);

        assertEquals(1, fired.size());
        assertEquals("Bob: Alice told me", fired.get(0).combined()); // combined is raw
        assertEquals("Bob: soldier told me", fired.get(0).formattedLine()); // replacer applied
        assertEquals(key("Bob: soldier told me"), fired.get(0).finalKey());
    }

    @Test
    void narrationLineUsesBodyOnlyForKeyNotCombined() {
        // When npc is null, combined = "//body" but playback key is derived from body alone
        machine.onTextReceived("A narrator speaks", null);
        saturate("A narrator speaks", null);
        advanceTo(1 + OverlayStateMachine.OVERLAY_STABILITY_TICKS);

        assertEquals(1, fired.size());
        assertEquals("//A narrator speaks", fired.get(0).combined());
        // formattedLine is body only (no "//" prefix) — important for correct manifest lookup
        assertEquals("A narrator speaks", fired.get(0).formattedLine());
        assertEquals(key("A narrator speaks"), fired.get(0).finalKey()); // no NPC prefix in manifest key
    }
}
