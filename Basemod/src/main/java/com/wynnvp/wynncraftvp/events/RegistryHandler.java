package com.wynnvp.wynncraftvp.events;

import com.wynnvp.wynncraftvp.sound.SoundsHandler;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class RegistryHandler {

    public static void initRegistries() {
        SoundsHandler.registerSounds();
    }


}
