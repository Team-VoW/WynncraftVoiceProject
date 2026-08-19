/*
 * Copyright © Team-VoW 2024-2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.line;

public class LineData {
    /**
     * Longest speaker name seen in the manifest is 33 characters ("Uggword Pollywaggon the
     * Murderous"), so anything past this is a sentence that happens to contain a colon, not a name.
     */
    private static final int MAX_NPC_NAME_LENGTH = 40;

    /** Longest real speaker name is 4 words; the cap leaves headroom without accepting prose. */
    private static final int MAX_NPC_NAME_WORDS = 5;

    private String soundLine;
    private String realLine;
    private String npcName;

    public String getSoundLine() {
        return soundLine;
    }

    public void setSoundLine(String soundLine) {
        this.soundLine = soundLine;
    }

    public String getRealLine() {
        return realLine;
    }

    public void setRealLine(String realLine) {
        this.realLine = realLine;
    }

    /**
     * Sets the speaker explicitly. Callers that already know who is talking (the overlay handler
     * reads it off the nameplate) should use this instead of letting {@link #getNPCName()} guess
     * from the text — pass an empty string for narration, which has no speaker at all.
     */
    public void setNpcName(String npcName) {
        this.npcName = npcName;
    }

    /**
     * The speaker, normalised for entity lookup and reporting, or an empty string for narration.
     *
     * <p>When no name was set explicitly it is recovered from the {@code "Speaker: line"} prefix.
     * Narration has no such prefix, so the text before the first {@code ": "} is only accepted when
     * it actually looks like a name — otherwise a whole grey-dialogue sentence ends up reported as
     * the NPC.
     */
    public String getNPCName() {
        if (npcName != null) return normalize(npcName);
        if (realLine == null) return "";

        int separator = realLine.indexOf(": ");
        if (separator < 0) return "";

        String name = realLine.substring(0, separator);
        // Strip a leading "[Lv. 42]"-style tag if present.
        name = name.substring(name.indexOf("]") + 1).trim();

        if (name.isEmpty()) return "";
        if (name.length() > MAX_NPC_NAME_LENGTH) return "";
        if (name.split("\\s+").length > MAX_NPC_NAME_WORDS) return "";

        return normalize(name);
    }

    private static String normalize(String name) {
        return name.trim().toLowerCase().replaceAll("[^a-z\\d?]", "");
    }
}
