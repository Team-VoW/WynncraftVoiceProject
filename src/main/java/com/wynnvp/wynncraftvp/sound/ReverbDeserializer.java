/*
 * Copyright © Team-VoW 2024-2025.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

/**
 * Custom Gson deserializer for Reverb enum that supports both integer and string values.
 * Integer values are mapped to enum ordinals (0-9).
 */
public class ReverbDeserializer implements JsonDeserializer<Reverb> {
    @Override
    public Reverb deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        if (json.isJsonNull()) {
            return Reverb.OUTSIDE; // Default fallback
        }

        // Handle integer values (ordinal)
        if (json.isJsonPrimitive() && json.getAsJsonPrimitive().isNumber()) {
            int ordinal = json.getAsInt();
            Reverb[] values = Reverb.values();

            if (ordinal >= 0 && ordinal < values.length) {
                return values[ordinal];
            } else {
                throw new JsonParseException(
                        "Invalid reverb ordinal: " + ordinal + ". Must be between 0 and " + (values.length - 1));
            }
        }

        // Handle string values (enum name) for backwards compatibility
        if (json.isJsonPrimitive() && json.getAsJsonPrimitive().isString()) {
            String name = json.getAsString();
            try {
                return Reverb.valueOf(name);
            } catch (IllegalArgumentException e) {
                throw new JsonParseException("Invalid reverb name: " + name, e);
            }
        }

        throw new JsonParseException("Reverb must be an integer (0-9) or string enum name");
    }
}
