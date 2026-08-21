/*
 * Copyright © Team-VoW 2024-2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.npc;

import static com.wynnvp.wynncraftvp.ModCore.config;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class NPCHandler {
    /** How far from the nametag, in XZ, the body of an NPC is allowed to be. */
    private static final double HEAD_SEARCH_RADIUS_SQ = 1.3;

    /** How far above the nametag a body may be. A label sits over what it labels, so barely at all. */
    private static final double HEAD_MAX_ABOVE = 0.4;

    /**
     * How far below the nametag a body may be.
     *
     * <p>Enough for a tall mob whose tag floats well clear of it, and no more. Without a floor here
     * the search happily reaches through the deck of a ship and speaks from an armour stand twenty
     * blocks down in the hull, which sounds exactly like being buried alive, because it is.
     */
    private static final double HEAD_MAX_BELOW = 4.0;

    /**
     * How far from the player nametags are searched for.
     *
     * <p>Comfortably past the range the server keeps entities loaded at, so it costs nothing to be
     * generous here, but not so wide that a failed search has to walk half the world to find that out.
     * A speaker who was found and then wandered further off stays cached regardless; this only bounds
     * where a new search looks.
     */
    private static final double SEARCH_RADIUS = 50;

    private static final double SEARCH_DIAMETER = SEARCH_RADIUS * 2;

    /** How far a shortened-name guess may reach. Beyond conversation range it is not worth guessing. */
    private static final double SHORTENED_NAME_RADIUS_SQ = 32 * 32;

    public static CachedEntity findEntity(String rawName) {
        rawName = SpeakerNames.clean(rawName);

        CachedEntity c = findNPC(rawName);
        if (!isCachedValid(c)) {
            return null;
        }

        return c;
    }

    /**
     * Explains what {@link #findEntity(String)} sees, for {@code /vow-debug npc}.
     *
     * <p>Mirrors the real search rather than re-implementing it: the nametag comes from
     * {@link #findClosestTextDisplay}, the head from {@link #getHead}, and every entity the head
     * search considered is listed alongside the reason it was or was not eligible.
     */
    public static String describeSearch(String rawName) {
        Player player = Minecraft.getInstance().player;
        if (player == null || Minecraft.getInstance().level == null) {
            return "No world loaded.";
        }

        String cleaned = SpeakerNames.clean(rawName);
        Entity nameEntity = findClosestTextDisplay(cleaned, player);
        if (nameEntity == null) {
            return "No text display matching \"" + cleaned + "\".";
        }

        Vec3 npcEyePos = nameEntity.getEyePosition().add(0, -0.2, 0);
        Entity head = getHead(nameEntity);
        Entity chosen = head == null ? nameEntity : head;

        StringBuilder out = new StringBuilder();
        out.append("[NPC] \"").append(cleaned).append("\"\n");
        out.append("  nametag: ").append(describeEntity(nameEntity, npcEyePos)).append('\n');
        out.append("  head:    ")
                .append(head == null ? "none (falling back to the nametag)" : describeEntity(head, npcEyePos))
                .append('\n');
        out.append("  source:  ")
                .append(format(chosen.getEyePosition()))
                .append(" d(player)=")
                .append(String.format("%.2f", chosen.getEyePosition().distanceTo(player.getEyePosition())))
                .append('\n');
        out.append("  candidates in the nametag's column:");

        // An NPC's model is dozens of item displays stacked on one spot, so listing them one per line
        // buries everything else. Identical rows are counted instead.
        Map<String, Integer> rows = new LinkedHashMap<>();
        for (Entity e : Minecraft.getInstance().level.entitiesForRendering()) {
            if (squaredXZDistance(e.getEyePosition(), npcEyePos) <= HEAD_SEARCH_RADIUS_SQ) {
                rows.merge(describeEntity(e, npcEyePos), 1, Integer::sum);
            }
        }
        if (rows.isEmpty()) {
            out.append(" none");
        } else {
            rows.forEach((row, count) -> out.append("\n    ").append(row).append(count > 1 ? " x" + count : ""));
        }
        return out.toString();
    }

    /** The cleaned text a nametag actually renders, which is not its entity display name. */
    private static String tagText(Entity entity) {
        if (!(entity instanceof Display.TextDisplay display) || display.textRenderState() == null) {
            return "";
        }
        return SpeakerNames.nametagName(display.textRenderState().text().getString());
    }

    /**
     * Every text display in range and whether the nametag filter keeps it, for {@code /vow-debug tags}.
     *
     * <p>{@link SpeakerNames#nametagName} decides what counts as a nametag from the shape of the text
     * alone, which is a guess about how Wynncraft builds its world. This is how to check that guess
     * against the world itself rather than reasoning about it.
     */
    public static String describeNametags() {
        Player player = Minecraft.getInstance().player;
        if (player == null || Minecraft.getInstance().level == null) {
            return "No world loaded.";
        }

        Vec3 eye = player.getEyePosition();
        Map<String, Integer> kept = new LinkedHashMap<>();
        Map<String, Integer> dropped = new LinkedHashMap<>();

        for (Display.TextDisplay display : Minecraft.getInstance()
                .level
                .getEntitiesOfClass(
                        Display.TextDisplay.class,
                        AABB.ofSize(eye, SEARCH_DIAMETER, SEARCH_DIAMETER, SEARCH_DIAMETER))) {
            if (display.textRenderState() == null) continue;

            String raw = display.textRenderState().text().getString();
            String name = SpeakerNames.nametagName(raw);
            String row = String.format(
                    "%.0fm \"%s\"%s",
                    Math.sqrt(display.position().distanceToSqr(eye)),
                    raw.replace("\n", "\\n"),
                    name.isEmpty() ? "" : " -> " + name);
            (name.isEmpty() ? dropped : kept).merge(row, 1, Integer::sum);
        }

        StringBuilder out = new StringBuilder();
        out.append("[NPC] text displays within ").append((int) SEARCH_RADIUS).append(" blocks");
        appendRows(out, "kept as nametags", kept);
        appendRows(out, "dropped, not nametag shaped", dropped);
        return out.toString();
    }

    private static void appendRows(StringBuilder out, String heading, Map<String, Integer> rows) {
        out.append("\n  ").append(heading).append(" (").append(rows.size()).append("):");
        if (rows.isEmpty()) {
            out.append(" none");
            return;
        }
        rows.forEach((row, count) -> out.append("\n    ").append(row).append(count > 1 ? " x" + count : ""));
    }

    private static String describeEntity(Entity e, Vec3 npcEyePos) {
        Vec3 eye = e.getEyePosition();
        return String.format(
                "%s %s dXZ=%.2f dY=%+.2f%s%s%s",
                e.getType().toShortString(),
                format(eye),
                Math.sqrt(squaredXZDistance(eye, npcEyePos)),
                eye.y - npcEyePos.y,
                e.isInvisible() ? " invisible" : "",
                isArmourStand(e) ? " armourstand" : "",
                rejection(e, npcEyePos));
    }

    /** Why {@link #getHead} would not take this entity, or the empty string if it would. */
    private static String rejection(Entity e, Vec3 npcEyePos) {
        double dy = e.getEyePosition().y - npcEyePos.y;
        if (e instanceof Display.TextDisplay) {
            return " REJECTED: a label, not a body";
        }
        if (dy > HEAD_MAX_ABOVE) {
            return " REJECTED: above the nametag";
        }
        if (dy < -HEAD_MAX_BELOW) {
            return " REJECTED: too far below the nametag";
        }
        if (e.isInvisible() && !isArmourStand(e)) {
            return " REJECTED: an invisible marker";
        }
        return "";
    }

    private static String format(Vec3 v) {
        return String.format("(%.2f, %.2f, %.2f)", v.x, v.y, v.z);
    }

    // Standard find function
    private static CachedEntity findNPC(String rawName) {
        Player player = Minecraft.getInstance().player;
        assert player != null;
        Entity entity = findClosestTextDisplay(rawName, player);
        // If no entity was found
        if (entity == null) return new CachedEntity();

        Entity childEntity = getHead(entity);
        if (childEntity == null) {
            childEntity = entity;
        }

        CachedEntity cachedEntity = new CachedEntity(entity, childEntity);

        return cachedEntity;
    }

    /**
     * The entity a nametag is labelling, which is what the voice should come out of.
     *
     * <p>Wynncraft does not build NPCs out of one entity. A modern one is a stack of item displays
     * for the model, an invisible slime for the hitbox, and a text display floating over the top;
     * older ones are an invisible armour stand wearing the armour. So this looks for whatever sits in
     * the same column as the nametag, within a vertical band tight enough that only the thing being
     * labelled can be in it.
     *
     * <p>The column is what decides it. Wynncraft hangs a nametag exactly above the model it belongs
     * to, so a candidate at zero horizontal distance is almost certainly the right one, and a
     * candidate half a block off is quite likely to be a neighbour's. Height only breaks ties.
     */
    private static Entity getHead(Entity entity) {
        Entity childEntity = null;
        double closestXzDistanceSq = Double.MAX_VALUE;
        double closestVerticalDistanceSq = Double.MAX_VALUE;
        Vec3 npcEyePos = entity.getEyePosition().add(0, -0.2, 0);

        for (Entity entityInWorld : Minecraft.getInstance().level.entitiesForRendering()) {
            // A text display is a label, not a body. The nametag being searched below is itself one,
            // and it sits at zero horizontal distance from itself, so leaving text displays in means
            // the search can never get past its own starting point. Item and block displays are the
            // opposite case: on a modern Wynncraft NPC they are the model, and the best thing here.
            if (entityInWorld instanceof Display.TextDisplay) {
                continue;
            }

            Vec3 eye = entityInWorld.getEyePosition();
            double dy = eye.y - npcEyePos.y;
            if (dy > HEAD_MAX_ABOVE || dy < -HEAD_MAX_BELOW) {
                continue;
            }

            double xzDistanceSq = squaredXZDistance(eye, npcEyePos);
            if (xzDistanceSq > HEAD_SEARCH_RADIUS_SQ) {
                continue;
            }

            // Something invisible that is not an armour stand is a marker, not a body. This is the
            // same rule isCachedValid enforces, applied before rather than after picking.
            if (entityInWorld.isInvisible() && !isArmourStand(entityInWorld)) {
                continue;
            }

            double verticalDistanceSq = dy * dy;
            if (xzDistanceSq < closestXzDistanceSq
                    || (xzDistanceSq == closestXzDistanceSq && verticalDistanceSq < closestVerticalDistanceSq)) {
                closestXzDistanceSq = xzDistanceSq;
                closestVerticalDistanceSq = verticalDistanceSq;
                childEntity = entityInWorld;
            }
        }

        return childEntity;
    }

    private static double squaredXZDistance(Vec3 first, Vec3 second) {
        double d = first.x - second.x;
        double f = first.z - second.z;
        return d * d + f * f;
    }

    public static boolean isArmourStand(Entity entity) {
        return entity instanceof net.minecraft.world.entity.decoration.ArmorStand;
    }

    /**
     * The nametag most likely to belong to the speaker.
     *
     * <p>Two ways a nametag can match, tried in that order within a single pass over the displays.
     * The first is the real one: the tag contains the speaker's name, which covers "Captain Fuhue"
     * being addressed as "Fuhue".
     *
     * <p>The second runs the containment the other way round, for when the tag is a shortening of the
     * speaker rather than an extension of them: "Sovereign Tasim" speaks over a nametag that just
     * reads "Tasim". That direction is a guess rather than a match, so it is fenced in three ways. It
     * is only consulted when nothing matched properly; the tag has to be long enough not to be a
     * coincidence; and it only looks at NPCs you are more or less standing with, because a guess made
     * across fifty blocks of world is not worth making. Among those, the longest tag wins, so a
     * bare three letters can never outrank the real name.
     *
     * @param rawName the speaker's name, already through {@link SpeakerNames#clean}
     */
    public static Entity findClosestTextDisplay(String rawName, Player player) {
        Vec3 playerEyePos = player.getEyePosition();

        Entity closest = null;
        double closestDistance = Double.MAX_VALUE;

        Entity fallback = null;
        int fallbackLength = 0;
        double fallbackDistance = Double.MAX_VALUE;

        for (Display.TextDisplay textDisplay : Minecraft.getInstance()
                .level
                .getEntitiesOfClass(
                        Display.TextDisplay.class,
                        AABB.ofSize(playerEyePos, SEARCH_DIAMETER, SEARCH_DIAMETER, SEARCH_DIAMETER))) {
            if (textDisplay.textRenderState() == null) continue;

            String name = SpeakerNames.nametagName(
                    textDisplay.textRenderState().text().getString());
            if (name.isEmpty()) continue;

            double distance = textDisplay.position().distanceToSqr(playerEyePos);

            if (SpeakerNames.names(name, rawName)) {
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closest = textDisplay;
                }
                continue;
            }

            if (distance > SHORTENED_NAME_RADIUS_SQ || !SpeakerNames.isShorteningOf(name, rawName)) {
                continue;
            }
            if (name.length() > fallbackLength || (name.length() == fallbackLength && distance < fallbackDistance)) {
                fallback = textDisplay;
                fallbackLength = name.length();
                fallbackDistance = distance;
            }
        }

        return closest != null ? closest : fallback;
    }

    public static boolean isCachedValid(CachedEntity c) {
        if (c == null || c.child == null || c.name == null) {
            return false;
        }

        if (!c.name.isAlive()) { // is alive
            return false;
        }
        if (!c.child.isAlive()) { // is alive
            return false;
        }
        if (c.child.isInvisible() && !c.isArmourStand) {
            return false;
        }
        // is closer than 200 meters/blocks/generic measurement unit
        if (c.name.getEyePosition().distanceTo(Minecraft.getInstance().player.getEyePosition()) > 200) {
            return false;
        }
        // distance change?
        return !(Math.abs(c.name.distanceTo(c.child) - c.distance)
                > config.getNpcFinderThingMaxDistanceChangeBeforeCacheInvalid());
    }
}
