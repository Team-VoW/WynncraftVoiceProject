package com.wynnvp.wynncraftvp.utils.reflection;

import net.minecraft.entity.Entity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;

import java.util.List;

public class ReflectionUtils {

    private static final DataParameter<String> NAME_KEY = ReflectionFields.Entity_CUSTOM_NAME.getValue(Entity.class);
    private static final DataParameter<Boolean> NAME_VISIBLE_KEY = ReflectionFields.Entity_CUSTOM_NAME_VISIBLE.getValue(Entity.class);

    public static String getNameFromMetadata(List<EntityDataManager.DataEntry<?>> dataManagerEntries) {
        assert NAME_KEY != null;
        if (dataManagerEntries != null) {
            for (EntityDataManager.DataEntry<?> entry : dataManagerEntries) {
                if (NAME_KEY.equals(entry.getKey())) {
                    return (String) entry.getValue();
                }
            }
        }
        return null;
    }

    public static boolean isNameVisibleFromMetadata(List<EntityDataManager.DataEntry<?>> dataManagerEntries) {
        assert NAME_VISIBLE_KEY != null;
        if (dataManagerEntries != null) {
            for (EntityDataManager.DataEntry<?> entry : dataManagerEntries) {
                if (NAME_VISIBLE_KEY.equals(entry.getKey())) {
                    return (Boolean) entry.getValue();
                }
            }
        }
        return false;
    }

}
