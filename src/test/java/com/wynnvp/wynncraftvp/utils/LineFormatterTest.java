/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.wynnvp.wynncraftvp.sound.line.LineData;
import org.junit.jupiter.api.Test;

class LineFormatterTest {
    // Processing order inside formatToLineData:
    //   1. trim()
    //   2. remove quote characters
    //   3. HTTPEncode (URLEncoder.encode) — percent-encodes non-ASCII and special chars
    //   4. toLowerCase
    //   5. strip §[0-9a-r] sequences (but § is already encoded by step 3, so this is a no-op)
    //   6. strip everything not in [a-z0-9?.!/]  — this removes '%' and '+' from encoding
    //
    // Consequence: characters like ',', '!', '?', '/' and non-ASCII are first percent-encoded
    // then the '%' is stripped, leaving their hex digits in the output.

    @Test
    void realLineIsPreservedBeforeTransformation() {
        LineData result = LineFormatter.formatToLineData("Hello World");
        assertEquals("Hello World", result.getRealLine());
    }

    @Test
    void outputIsLowercase() {
        LineData result = LineFormatter.formatToLineData("HELLO");
        assertEquals("hello", result.getSoundLine());
    }

    @Test
    void leadingAndTrailingWhitespaceStripped() {
        LineData result = LineFormatter.formatToLineData("  hello  ");
        assertEquals("hello", result.getSoundLine());
        assertEquals("hello", result.getRealLine());
    }

    @Test
    void spacesEncodedToPlus_thenPlusStripped() {
        // Space → '+' after URLEncode → stripped (not in allowed set) → no separator in output
        LineData result = LineFormatter.formatToLineData("one two three");
        assertEquals("onetwothree", result.getSoundLine());
    }

    @Test
    void quotesAreIgnoredBeforeEncoding() {
        LineData result = LineFormatter.formatToLineData("\"Hello\" 'world' \u201CHi\u201D \u2018there\u2019");
        assertEquals("helloworldhithere", result.getSoundLine());
    }

    @Test
    void quoteStylesProduceSameKey() {
        String asciiDouble = LineFormatter.formatToLineData("Lecade: Mister \"Gren\" needs help").getSoundLine();
        String smartDouble = LineFormatter.formatToLineData("Lecade: Mister \u201CGren\u201D needs help").getSoundLine();
        String closingSmartDouble = LineFormatter.formatToLineData("Lecade: Mister \u201DGren\u201D needs help").getSoundLine();
        String doubledSingle = LineFormatter.formatToLineData("Lecade: Mister ''Gren'' needs help").getSoundLine();

        assertEquals(asciiDouble, smartDouble);
        assertEquals(asciiDouble, closingSmartDouble);
        assertEquals(asciiDouble, doubledSingle);
    }

    @Test
    void smartSingleQuoteIgnored() {
        LineData result = LineFormatter.formatToLineData("It\u2019s fine");
        assertEquals("itsfine", result.getSoundLine());
    }

    @Test
    void smartDoubleQuoteIgnored() {
        LineData result = LineFormatter.formatToLineData("\u201CHello\u201D");
        assertEquals("hello", result.getSoundLine());
    }

    @Test
    void minecraftSectionSignEncodedNotStripped() {
        // '§' (U+00A7, UTF-8 C2 A7) → '%C2%A7' via HTTPEncode → '%' stripped → 'c2a7'
        // The §-stripping regex runs after HTTPEncode so it never matches
        LineData result = LineFormatter.formatToLineData("\u00A7aGreen\u00A7r text");
        assertEquals("c2a7agreenc2a7rtext", result.getSoundLine());
    }

    @Test
    void dotPreservedAsciiAlreadySafe() {
        // '.' is not encoded by URLEncoder (it's in the "safe" set) and is in the allowed chars
        LineData result = LineFormatter.formatToLineData("3.14");
        assertEquals("3.14", result.getSoundLine());
    }

    @Test
    void commaEncodedToHexDigits() {
        // ',' → '%2C' → '2c' (digits+letters, both kept)
        LineData result = LineFormatter.formatToLineData("hello,world");
        assertEquals("hello2cworld", result.getSoundLine());
    }

    @Test
    void exclamationMarkEncodedToHexDigits() {
        // '!' → '%21' → '21'
        // Note: '!' IS in the allowed set, but URLEncoder encodes it first,
        // so '%' is stripped and only '21' remains
        LineData result = LineFormatter.formatToLineData("yes!");
        assertEquals("yes21", result.getSoundLine());
    }

    @Test
    void plainAlphanumericUnchanged() {
        LineData result = LineFormatter.formatToLineData("abc123");
        assertEquals("abc123", result.getSoundLine());
    }
}
