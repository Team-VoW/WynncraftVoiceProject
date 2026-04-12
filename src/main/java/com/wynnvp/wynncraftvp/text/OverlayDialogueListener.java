/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.text;

/**
 * Callbacks invoked by {@link OverlayStateMachine} when dialogue events occur.
 * Implementations handle the Minecraft-dependent side effects (sound playback,
 * logging, config reads) while the state machine stays pure and testable.
 *
 * <p>Firing sequence for a completed line:
 * <ol>
 *   <li>If early play played the <em>wrong</em> key: {@link #onWrongEarlyPlay()} is called first.
 *   <li>If early play played the <em>correct</em> key: {@link #onDialogueAlreadyPlayed} is called
 *       (no audio action needed).
 *   <li>Otherwise: {@link #onDialogueFired} is called to play the sound.
 * </ol>
 */
public interface OverlayDialogueListener {
    /**
     * Called when a dialogue line is ready for playback (no early play, or wrong early play key).
     *
     * @param combined      display string — {@code "NpcName: body"} or {@code "//body"} for
     *                      narration-only lines
     * @param formattedLine player-name-substituted playback line (use this for sound lookup)
     * @param finalKey      the manifest lookup key derived from {@code formattedLine}
     */
    void onDialogueFired(String combined, String formattedLine, String finalKey);

    /**
     * Called when early play already played the correct sound — no audio action needed.
     * Implementations should just log the line.
     *
     * @param combined      display string (for chat logging)
     * @param formattedLine player-name-substituted line (for file logging)
     */
    void onDialogueAlreadyPlayed(String combined, String formattedLine);

    /**
     * Called when early play played the <em>wrong</em> sound and it must be stopped.
     * {@link #onDialogueFired} will be called immediately after to start the correct sound.
     */
    void onWrongEarlyPlay();

    /**
     * Attempt to pre-play audio based on the current partial text.
     *
     * <p>The implementation is responsible for formatting the raw combined string,
     * checking the minimum-character threshold, looking up a manifest match, and
     * playing it. If the resolved key equals {@code excludeKey}, no audio is played
     * and {@code null} is returned (dedup guard).
     *
     * @param rawCombined the raw {@code "npc: body"} string (no player-name substitution applied)
     * @param excludeKey  a previously matched key to skip, or {@code null}
     * @return the manifest key of the sound that was played, or {@code null} if nothing was played
     */
    String tryEarlyPlay(String rawCombined, String excludeKey);
}
