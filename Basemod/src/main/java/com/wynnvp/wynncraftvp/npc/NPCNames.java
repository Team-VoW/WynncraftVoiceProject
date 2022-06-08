package com.wynnvp.wynncraftvp.npc;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum NPCNames {

    // Class to add the new NPCs, the raw name is his name!
    // Write it as it is in green on
    // top of his head, the id is in case the name in
    // SoundHandler is archiving it differently

    NONE("null", null),
    ANKOU("ankou", null),
    TYKO("tyko", null),
    LAEN("laen", null),
    LEUCSAA("leucsaa", null),
    MYLO("mylo", null),
    ENZAN("enzan", null),
    TASIM("tasim", null),
    CARAVAN_DRIVER("caravan driver", "caravandriver"),
    GUARD("guard", null),
    SOLDIER("soldier", null),
    ALEDAR("aledar", null),
    RAGNI_KING("ragni's king", "ragnisking");

    private final String rawName;
    private final String id;

    public static boolean contains(String rawName) {
        return Arrays.stream(NPCNames.values()).anyMatch(f -> f.getRawName().equalsIgnoreCase(rawName) || (f.getId() != null && f.getId().equalsIgnoreCase(rawName)));
    }

    public static NPCNames get(String name) {
        return Arrays.stream(NPCNames.values()).filter(f -> f.getRawName().equalsIgnoreCase(name) || (f.getId() != null && f.getId().equalsIgnoreCase(name))).findAny().orElse(NPCNames.NONE);
    }

}
