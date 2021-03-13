package com.wynnvp.wynncraftvp.coords;

import java.util.HashMap;

public class CoordsHandler {
    private final HashMap<Integer, FullCoordinate> coordinates;


    public CoordsHandler() {
        coordinates = new HashMap<>();
        addLocations();
    }

    private void addLocations() {
        add("Thomas", -606, 89, -1431);
        add("Talking Mushroom", 1100, 124, -4869);
    }

    private void add(String npcName, double x, double y, double z) {
        coordinates.put(npcName.hashCode(), new FullCoordinate(x, y, z));
    }

    public FullCoordinate getLocation(String npcName) {
        if (coordinates.containsKey(npcName.hashCode())){
            return coordinates.get(npcName.hashCode());
        }
        return null;
    }


}
