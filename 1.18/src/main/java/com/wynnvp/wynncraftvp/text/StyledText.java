/*
 * This file originates from © Wynntils 2023 https://github.com/Wynntils/Artemis/
 * but was modified to fit this project
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.text;

import net.minecraft.network.chat.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class StyledText implements Iterable<StyledTextPart> {
    private final List<StyledTextPart> parts;

    private final List<ClickEvent> clickEvents;
    private final List<HoverEvent> hoverEvents;

    private StyledText(List<StyledTextPart> parts, List<ClickEvent> clickEvents, List<HoverEvent> hoverEvents) {
        this.parts = parts.stream()
                .filter(styledTextPart -> !styledTextPart.isEmpty())
                .map(styledTextPart -> new StyledTextPart(styledTextPart, this))
                .collect(Collectors.toList());
        this.clickEvents = new ArrayList<>(clickEvents);
        this.hoverEvents = new ArrayList<>(hoverEvents);
    }

    public static StyledText fromComponent(Component component) {
        List<StyledTextPart> parts = new ArrayList<>();
        List<ClickEvent> clickEvents = new ArrayList<>();
        List<HoverEvent> hoverEvents = new ArrayList<>();

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



    public boolean isEmpty() {
        return parts.isEmpty();
    }


    public boolean contains(String codedString) {
        return contains(codedString, PartStyle.StyleType.DEFAULT);
    }


    public boolean contains(String codedString, PartStyle.StyleType styleType) {
        return getString(styleType).contains(codedString);
    }



    public Matcher getMatcher(Pattern pattern) {
        return getMatcher(pattern, PartStyle.StyleType.DEFAULT);
    }

    public Matcher getMatcher(Pattern pattern, PartStyle.StyleType styleType) {
        return pattern.matcher(getString(styleType));
    }


    public boolean find(Pattern pattern) {
        return find(pattern, PartStyle.StyleType.DEFAULT);
    }

    public boolean find(Pattern pattern, PartStyle.StyleType styleType) {
        return pattern.matcher(getString(styleType)).find();
    }


    int addClickEvent(ClickEvent clickEvent) {
        // Check if the event is already in the list
        for (int i = 0; i < clickEvents.size(); i++) {
            ClickEvent event = clickEvents.get(i);
            if (event.equals(clickEvent)) {
                return i + 1;
            }
        }

        clickEvents.add(clickEvent);

        return clickEvents.size();
    }

    int addHoverEvent(HoverEvent hoverEvent) {
        // Check if the event is already in the list
        for (int i = 0; i < hoverEvents.size(); i++) {
            HoverEvent event = hoverEvents.get(i);
            if (event.equals(hoverEvent)) {
                return i + 1;
            }
        }

        hoverEvents.add(hoverEvent);

        return hoverEvents.size();
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
}
