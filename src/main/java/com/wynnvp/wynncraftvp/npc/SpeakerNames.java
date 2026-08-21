/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.npc;

/**
 * The rules for deciding whether a nametag belongs to whoever is speaking.
 *
 * <p>Kept apart from {@link NPCHandler} and free of any world types so the matching can be tested
 * without a client: the awkward cases here are all about names, not about entities.
 */
public final class SpeakerNames {
    /** Shorter than this, a nametag is too generic to guess a speaker from. */
    public static final int MIN_SHORTENED_LENGTH = 3;

    /** The most text a nametag can carry before it is plainly something other than a name. */
    public static final int MAX_RAW_LENGTH = 64;

    /** The most name a nametag can carry once the decoration is off it. */
    public static final int MAX_NAME_LENGTH = 32;

    private SpeakerNames() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Strips a name down to the letters and digits that identify it, so colour codes, spacing,
     * titles' punctuation and decoration cannot come between a speaker and their nametag.
     *
     * <p>Done by hand rather than with a pair of {@code replaceAll} calls because this runs once per
     * nametag in range every time a line has to be relocated, and {@link String#replaceAll} compiles
     * its pattern afresh on every call.
     */
    public static String clean(String name) {
        StringBuilder cleaned = new StringBuilder(name.length());
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            // A section sign takes the character after it with it, so the code's letter is not left
            // behind in the name.
            if (c == '§') {
                i++;
                continue;
            }
            c = Character.toLowerCase(c);
            if ((c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || c == '?') {
                cleaned.append(c);
            }
        }
        return cleaned.toString();
    }

    /**
     * The name a text display carries, or the empty string when it is not a nametag at all.
     *
     * <p>A Wynncraft nametag is one text display holding two lines: the name, and under it a badge
     * row drawn out of private-use font glyphs. What the game shows as
     *
     * <pre>
     *   Captain Rurie
     *      [NPC]
     * </pre>
     *
     * is stored as {@code "Captain Rurie\n..."}. So the name is the first line and
     * nothing else, and the glyphs on the second would only ever be noise in a comparison.
     *
     * <p>A length cap then drops what is plainly not a name: holograms, signposts and quest boards,
     * which run to sentences where a name runs to a word or two. It is deliberately generous, and it
     * is not trying to tell an NPC's tag from a player's — those are dropped by not matching a
     * speaker, not by their shape. The cap earns its keep on the shortened-name guess in
     * {@link #isShorteningOf}, the one match loose enough that a stray line of world text could
     * otherwise satisfy it.
     */
    public static String nametagName(String text) {
        int lineEnd = text.indexOf('\n');
        String firstLine = lineEnd < 0 ? text : text.substring(0, lineEnd);
        // Checked before cleaning, so a long hologram costs a length read rather than a walk over
        // every character in it.
        if (firstLine.isEmpty() || firstLine.length() > MAX_RAW_LENGTH) {
            return "";
        }
        String name = clean(firstLine);
        return name.length() > MAX_NAME_LENGTH ? "" : name;
    }

    /**
     * Whether a nametag names the speaker outright.
     *
     * <p>The tag is allowed to say more than the speaker does, which is the usual shape of it:
     * "Captain Fuhue" stands under a nametag while the dialogue calls them "Fuhue".
     */
    public static boolean names(String tag, String speaker) {
        return !tag.isEmpty() && tag.contains(speaker);
    }

    /**
     * Whether a nametag is a shortening of the speaker rather than an extension of them.
     *
     * <p>The other direction of {@link #names}, for NPCs whose dialogue carries a title their nametag
     * leaves off: "Sovereign Tasim" speaks over a nametag reading only "Tasim". A guess rather than a
     * match, so the tag has to be long enough that it cannot be a coincidence, and callers should
     * only reach for it once {@link #names} has found nothing.
     */
    public static boolean isShorteningOf(String tag, String speaker) {
        return tag.length() >= MIN_SHORTENED_LENGTH && speaker.contains(tag);
    }
}
