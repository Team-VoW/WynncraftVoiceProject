/*
 * Copyright © Team-VoW 2023-2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.text;

import com.google.common.collect.Iterables;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

public final class StyledText implements Iterable<StyledTextPart> {
    // High surrogate characters for the positive and negative space characters
    // These can be used to trim unicode spacers from StyledTexts
    private static final char POSITIVE_SPACE_HIGH_SURROGATE = '\uDB00';
    private static final char NEGATIVE_SPACE_HIGH_SURROGATE = '\uDAFF';

    public static final StyledText EMPTY = new StyledText(List.of(), List.of(), List.of());

    private final List<StyledTextPart> parts;

    private final List<ClickEvent> clickEvents;
    private final List<HoverEvent> hoverEvents;

    /**
     * Note: All callers of this constructor should ensure that the event lists are collected from the parts.
     * Additionally, they should ensure that the events are distinct.
     */
    private StyledText(List<StyledTextPart> parts, List<ClickEvent> clickEvents, List<HoverEvent> hoverEvents) {
        this.parts = parts.stream()
                .filter(styledTextPart -> !styledTextPart.isEmpty())
                .map(styledTextPart -> new StyledTextPart(styledTextPart, this))
                .collect(Collectors.toList());
        this.clickEvents = Collections.unmodifiableList(clickEvents);
        this.hoverEvents = Collections.unmodifiableList(hoverEvents);
    }

    public static StyledText fromComponent(Component component) {
        List<StyledTextPart> parts = new ArrayList<>();

        // Walk the component tree using DFS
        // Component#visit behaves weirdly, so we do it manually
        // Save the style of the parent component so we can inherit it
        Deque<Pair<Component, Style>> deque = new LinkedList<>();

        deque.add(new Pair<>(component, Style.EMPTY));

        while (!deque.isEmpty()) {
            Pair<Component, Style> currentPair = deque.pop();
            Component current = currentPair.key();
            Style parentStyle = currentPair.value();

            // We use getContents here to get this and only this component's string.
            String componentString =
                    MutableComponent.create(current.getContents()).getString();

            List<StyledTextPart> styledTextParts =
                    StyledTextPart.fromCodedString(componentString, current.getStyle(), null, parentStyle);

            // Only actual styles are inherited, string formatting codes are not
            Style styleToFollowForChildren = current.getStyle().applyTo(parentStyle);

            List<Pair<Component, Style>> siblingPairs = current.getSiblings().stream()
                    .map(sibling -> new Pair<>(sibling, styleToFollowForChildren))
                    .collect(Collectors.toList());

            Collections.reverse(siblingPairs);
            siblingPairs.forEach(deque::addFirst);

            // Disallow empty parts
            parts.addAll(
                    styledTextParts.stream().filter(part -> !part.isEmpty()).toList());
        }

        return fromParts(parts);
    }

    public static StyledText fromString(String codedString) {
        return new StyledText(
                StyledTextPart.fromCodedString(codedString, Style.EMPTY, null, Style.EMPTY), List.of(), List.of());
    }

    public static StyledText fromModifiedString(String codedString, StyledText styledText) {
        List<HoverEvent> hoverEvents = List.copyOf(styledText.hoverEvents);
        List<ClickEvent> clickEvents = List.copyOf(styledText.clickEvents);

        return new StyledText(
                StyledTextPart.fromCodedString(codedString, Style.EMPTY, styledText, Style.EMPTY),
                clickEvents,
                hoverEvents);
    }

    public static StyledText fromUnformattedString(String unformattedString) {
        StyledTextPart part = new StyledTextPart(unformattedString, Style.EMPTY, null, Style.EMPTY);
        return new StyledText(List.of(part), List.of(), List.of());
    }

    public static StyledText fromPart(StyledTextPart part) {
        return fromParts(List.of(part));
    }

    public static StyledText fromParts(List<StyledTextPart> parts) {
        // Collect the events
        List<ClickEvent> clickEvents = new ArrayList<>();
        List<HoverEvent> hoverEvents = new ArrayList<>();

        for (StyledTextPart part : parts) {
            ClickEvent clickEvent = part.getPartStyle().getClickEvent();
            if (clickEvent != null && !clickEvents.contains(clickEvent)) {
                clickEvents.add(clickEvent);
            }

            HoverEvent hoverEvent = part.getPartStyle().getHoverEvent();
            if (hoverEvent != null && !hoverEvents.contains(hoverEvent)) {
                hoverEvents.add(hoverEvent);
            }
        }

        return new StyledText(parts, clickEvents, hoverEvents);
    }

    // We don't want to expose the actual string to the outside world
    // If you need to do an operation with this string, implement it as a method
    public String getString(PartStyle.StyleType type) {
        StringBuilder builder = new StringBuilder();

        PartStyle previousStyle = null;
        for (StyledTextPart part : parts) {
            builder.append(part.getString(previousStyle, type));
            previousStyle = part.getPartStyle();
        }

        return builder.toString();
    }

    /**
     * @return The string representation of this {@link StyledText} with default formatting codes.
     */
    public String getString() {
        return getString(PartStyle.StyleType.DEFAULT);
    }

    public String getStringWithoutFormatting() {
        return getString(PartStyle.StyleType.NONE);
    }

    public int length() {
        return parts.stream().mapToInt(StyledTextPart::length).sum();
    }

    public int length(PartStyle.StyleType styleType) {
        return getString(styleType).length();
    }

    public static StyledText join(StyledText styledTextSeparator, StyledText... texts) {
        List<StyledTextPart> parts = new ArrayList<>();

        final int length = texts.length;
        for (int i = 0; i < length; i++) {
            StyledText text = texts[i];
            parts.addAll(text.parts);

            if (i != length - 1) {
                parts.addAll(styledTextSeparator.parts);
            }
        }

        return fromParts(parts);
    }

    public static StyledText join(StyledText styledTextSeparator, Iterable<StyledText> texts) {
        return join(styledTextSeparator, Iterables.toArray(texts, StyledText.class));
    }

    public static StyledText join(String codedStringSeparator, StyledText... texts) {
        return join(StyledText.fromString(codedStringSeparator), texts);
    }

    public static StyledText join(String codedStringSeparator, Iterable<StyledText> texts) {
        return join(StyledText.fromString(codedStringSeparator), Iterables.toArray(texts, StyledText.class));
    }

    public static StyledText concat(StyledText... texts) {
        return fromParts(Arrays.stream(texts)
                .map(text -> text.parts)
                .flatMap(List::stream)
                .toList());
    }

    public static StyledText concat(Iterable<StyledText> texts) {
        return concat(Iterables.toArray(texts, StyledText.class));
    }

    public boolean isEmpty() {
        return parts.isEmpty();
    }

    public boolean isBlank() {
        return parts.stream().allMatch(StyledTextPart::isBlank);
    }

    public boolean contains(String codedString) {
        return contains(codedString, PartStyle.StyleType.DEFAULT);
    }

    public boolean contains(StyledText styledText) {
        return contains(styledText.getString(PartStyle.StyleType.DEFAULT), PartStyle.StyleType.DEFAULT);
    }

    public boolean contains(String codedString, PartStyle.StyleType styleType) {
        return getString(styleType).contains(codedString);
    }

    public boolean contains(StyledText styledText, PartStyle.StyleType styleType) {
        return contains(styledText.getString(styleType), styleType);
    }

    public boolean startsWith(String codedString) {
        return startsWith(codedString, PartStyle.StyleType.DEFAULT);
    }

    public boolean startsWith(StyledText styledText) {
        return startsWith(styledText.getString(PartStyle.StyleType.DEFAULT), PartStyle.StyleType.DEFAULT);
    }

    public boolean startsWith(String codedString, PartStyle.StyleType styleType) {
        return getString(styleType).startsWith(codedString);
    }

    public boolean startsWith(StyledText styledText, PartStyle.StyleType styleType) {
        return startsWith(styledText.getString(styleType), styleType);
    }

    public boolean endsWith(String codedString) {
        return endsWith(codedString, PartStyle.StyleType.DEFAULT);
    }

    public boolean endsWith(StyledText styledText) {
        return endsWith(styledText.getString(PartStyle.StyleType.DEFAULT), PartStyle.StyleType.DEFAULT);
    }

    public boolean endsWith(String codedString, PartStyle.StyleType styleType) {
        return getString(styleType).endsWith(codedString);
    }

    public boolean endsWith(StyledText styledText, PartStyle.StyleType styleType) {
        return endsWith(styledText.getString(styleType), styleType);
    }

    public Matcher getMatcher(Pattern pattern) {
        return getMatcher(pattern, PartStyle.StyleType.DEFAULT);
    }

    public Matcher getMatcher(Pattern pattern, PartStyle.StyleType styleType) {
        return pattern.matcher(getString(styleType));
    }

    public boolean matches(Pattern pattern) {
        return matches(pattern, PartStyle.StyleType.DEFAULT);
    }

    public boolean matches(Pattern pattern, PartStyle.StyleType styleType) {
        return pattern.matcher(getString(styleType)).matches();
    }

    public boolean find(Pattern pattern) {
        return find(pattern, PartStyle.StyleType.DEFAULT);
    }

    public boolean find(Pattern pattern, PartStyle.StyleType styleType) {
        return pattern.matcher(getString(styleType)).find();
    }

    public StyledText append(StyledText styledText) {
        return concat(this, styledText);
    }

    public StyledText append(String codedString) {
        return append(StyledText.fromString(codedString));
    }

    public StyledText appendPart(StyledTextPart part) {
        List<StyledTextPart> newParts = new ArrayList<>(parts);
        newParts.add(part);
        return fromParts(newParts);
    }

    public StyledText prepend(StyledText styledText) {
        return concat(styledText, this);
    }

    public StyledText prepend(String codedString) {
        return prepend(StyledText.fromString(codedString));
    }

    public StyledText prependPart(StyledTextPart part) {
        List<StyledTextPart> newParts = new ArrayList<>(parts);
        newParts.addFirst(part);
        return fromParts(newParts);
    }

    public boolean equalsString(String string) {
        return equalsString(string, PartStyle.StyleType.DEFAULT);
    }

    public boolean equalsString(String string, PartStyle.StyleType styleType) {
        return getString(styleType).equals(string);
    }

    public StyledTextPart getFirstPart() {
        if (parts.isEmpty()) {
            return null;
        }

        return parts.getFirst();
    }

    public StyledTextPart getLastPart() {
        if (parts.isEmpty()) {
            return null;
        }

        return parts.getLast();
    }

    public int getPartCount() {
        return parts.size();
    }

    /**
     * Returns the first part of this {@link StyledText} that matches the given event.
     *
     * @param clickEvent the event to find
     * @return the 1-based index, or -1
     */
    public int getClickEventIndex(ClickEvent clickEvent) {
        // Check if the event is already in the list
        for (int i = 0; i < clickEvents.size(); i++) {
            ClickEvent event = clickEvents.get(i);
            if (event.equals(clickEvent)) {
                return i + 1;
            }
        }

        return -1;
    }

    ClickEvent getClickEvent(int index) {
        return Iterables.get(clickEvents, index - 1, null);
    }

    /**
     * Returns the first part of this {@link StyledText} that matches the given event.
     *
     * @param hoverEvent the event to find
     * @return the 1-based index, or -1
     */
    public int getHoverEventIndex(HoverEvent hoverEvent) {
        // Check if the event is already in the list
        for (int i = 0; i < hoverEvents.size(); i++) {
            HoverEvent event = hoverEvents.get(i);
            if (event.equals(hoverEvent)) {
                return i + 1;
            }
        }

        return -1;
    }

    HoverEvent getHoverEvent(int index) {
        return Iterables.get(hoverEvents, index - 1, null);
    }

    private StyledTextPart getPartBefore(StyledTextPart part) {
        int index = parts.indexOf(part);
        if (index == 0) {
            return null;
        }

        return parts.get(index - 1);
    }

    @Override
    public Iterator<StyledTextPart> iterator() {
        return parts.iterator();
    }

    @Override
    public String toString() {
        return "StyledText{'" + getString(PartStyle.StyleType.INCLUDE_EVENTS) + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StyledText that = (StyledText) o;
        return Objects.deepEquals(parts, that.parts)
                && Objects.deepEquals(clickEvents, that.clickEvents)
                && Objects.deepEquals(hoverEvents, that.hoverEvents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parts, clickEvents, hoverEvents);
    }

    public static class StyledTextSerializer implements JsonSerializer<StyledText>, JsonDeserializer<StyledText> {
        @Override
        public StyledText deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return StyledText.fromString(json.getAsString());
        }

        @Override
        public JsonElement serialize(StyledText src, Type typeOfSrc, JsonSerializationContext context) {
            return context.serialize(src.getString());
        }
    }
}
