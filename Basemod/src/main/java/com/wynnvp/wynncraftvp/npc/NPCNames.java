package com.wynnvp.wynncraftvp.npc;

import java.util.Arrays;

public enum NPCNames {

    // Class to add the new NPCs, the raw name is his name!
    // Write it as it is in green on
    // top of his head, the id is in case the name in
    // SoundHandler is archiving it differently

    NONE("null"),

    //KING'S RECRUIT
    TASIM("tasim"),
    CARAVAN_DRIVER("caravandriver"),
    GUARD("guard"),
    SOLDIER("soldier"),
    ALEDAR("aledar"),
    RAGNIS_KING("ragnisking"),

    //ENZAN'S BROTHER
    ENZAN("enzan"),
    THERCK("therck"),

    //POISONING THE PEST
    FARMER_CEVALUS("farmercevalus"),

    //COOK ASSISTANT
    THE_COOK("thecook"),
    BORED_FARMER("boredfarmer"),

    //INFESTED PLANT
    OPE("ope"),

    //THE SEWERS OF RAGNI
    JENPREST("jenprest"),
    QUESTIONMARK("questionmark"),

    //TUNNEL TROUBLE
    BYLVIS("bylvis"),
    CAPTAIN_FENOR("captainfenor"),
    MINER_LINTON(""),

    ANKOU("ankou"),
    TYKO("tyko"),
    LAEN("laen"),
    LEUCSAA("leucsaa"),
    MYLO("mylo"),

    TALKING_MUSHROOM("talkingmushroom"),
    SEASKIPPER_CAPTAIN("seaskippercaptain"),
    JAVIER("javier"),
    DECEASED_BUCCANEER("deceasedbuccaneer"),
    DALBEN("dalben"),
    SEASUM("seasum"),
    BOAT_CAPTAIN("boatcaptain"),
    MAMUI("mamui"),
    STUDENT("student"),
    CIDRE("cidre"),
    UNA("una"),
    HEADMASTER("headmaster"),
    GHOST_GUIDE("ghostguide"),
    TEACHER("teacher"),

    //DECREPIT SEWERS
    WITHERHEAD("witherhead");

    private final String rawName;

    public String getRawName() {
        return rawName;
    }

    NPCNames(String rawName) {
        this.rawName = rawName;
    }

    public static boolean contains(String rawName) {
        return Arrays.stream(NPCNames.values()).anyMatch(f -> f.getRawName().equalsIgnoreCase(rawName));
    }

}
