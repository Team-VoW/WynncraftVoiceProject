package com.wynnvp.wynncraftvp.npc;

import java.util.HashMap;
import java.util.Map;

public class QuestMarkHandler {

    private static final Map<String, String> wichQuest = new HashMap<>();

    public static void put(String questName) {
        wichQuest.put("???", questName);
    }

    public static Map<String, String> getWichQuest() {
        return wichQuest;
    }
}
