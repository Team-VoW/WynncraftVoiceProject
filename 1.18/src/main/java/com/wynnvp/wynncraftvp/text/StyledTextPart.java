/*
 * Copyright © Team-VoW 2023-2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;

/*
 * This file originates from © Wynntils 2023 https://github.com/Wynntils/Artemis/ but was modified to fit this project
 */
public final class StyledTextPart {
    private final String text;
    private final PartStyle style;

    private final StyledText parent;

    public StyledTextPart(String text, Style style, StyledText parent, Style parentStyle) {
        this.parent = parent;
        this.text = text;

        // Must be done last
        this.style = PartStyle.fromStyle(style, this, parentStyle);
    }

    StyledTextPart(StyledTextPart part, StyledText parent) {
        this.text = part.text;
        this.style = new PartStyle(part.style, this);
        this.parent = parent;
    }

    // This factory is used to create a StyledTextPart from a component that has formatting codes
    // It is separate from the constructor because this only needs to be applied in cases there the text could have
    // formatting codes
    static List<StyledTextPart> fromCodedString(String codedString, Style style, StyledText parent, Style parentStyle) {
        // When we have a style, but the text has formatting codes,
        // we need to apply the formatting codes to the style
        // This means that the actual style applies first; then the formatting codes
        List<StyledTextPart> parts = new ArrayList<>();

        Style currentStyle = style;
        StringBuilder currentString = new StringBuilder();

        boolean nextIsFormatting = false;

        for (char current : codedString.toCharArray()) {
            if (nextIsFormatting) {
                nextIsFormatting = false;

                ChatFormatting formatting = ChatFormatting.getByCode(current);

                if (formatting == null) {
                    currentString.append(ChatFormatting.PREFIX_CODE);
                    currentString.append(current);
                    continue;
                }

                // We already had some text with the current style
                // Append it before modifying the style
                if (!currentString.isEmpty()) {
                    // We might have lost an event, so we need to add it back
                    currentStyle =
                            currentStyle.withClickEvent(style.getClickEvent()).withHoverEvent(style.getHoverEvent());

                    parts.add(new StyledTextPart(currentString.toString(), currentStyle, null, parentStyle));

                    // reset string
                    // style is not reset, because we want to keep the formatting
                    currentString = new StringBuilder();
                }

                // Color formatting resets the style
                if (formatting.isColor()) {
                    currentStyle = Style.EMPTY.withColor(formatting);
                    continue;
                }

                currentStyle = currentStyle.applyFormat(formatting);

                continue;
            }

            if (current == ChatFormatting.PREFIX_CODE) {
                nextIsFormatting = true;
                continue;
            }

            currentString.append(current);
        }

        // Check if we have some text left
        if (!currentString.isEmpty()) {
            // We might have lost an event, so we need to add it back
            currentStyle = currentStyle.withClickEvent(style.getClickEvent()).withHoverEvent(style.getHoverEvent());
            parts.add(new StyledTextPart(currentString.toString(), currentStyle, null, parentStyle));
        }

        return parts;
    }

    public String getString(PartStyle previousStyle, PartStyle.StyleType type) {
        return style.asString(previousStyle, type) + text;
    }

    public StyledText getParent() {
        return parent;
    }

    public PartStyle getPartStyle() {
        return style;
    }

    boolean isEmpty() {
        return text.isEmpty();
    }

    @Override
    public String toString() {
        return "StyledTextPart[" + "text=" + text + ", " + "style=" + style + ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StyledTextPart that = (StyledTextPart) o;
        return Objects.equals(text, that.text) && Objects.equals(style, that.style);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, style);
    }
}
