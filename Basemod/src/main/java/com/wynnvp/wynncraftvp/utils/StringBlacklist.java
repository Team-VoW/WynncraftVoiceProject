package com.wynnvp.wynncraftvp.utils;

import net.minecraft.util.text.TextFormatting;

import java.util.HashSet;
import java.util.Set;

public class StringBlacklist {

    private static final Set<String> blacklist = new HashSet<>();

    public static void namesDefault() {
        blacklist.add("tower of ascension");
        blacklist.add("lv");
        blacklist.add("❤");
        for (int i = 0; i <= 9; i++) {
            blacklist.add(""+i);
        }
        for (TextFormatting textFormatting : TextFormatting.values()) {
            if (textFormatting.equals(TextFormatting.DARK_GREEN)) continue;
            blacklist.add(textFormatting.toString());
        }
    }

    public static boolean has(String name) {
        return blacklist.stream().anyMatch(name::contains);
    }

}
