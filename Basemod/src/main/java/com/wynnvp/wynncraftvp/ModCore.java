package com.wynnvp.wynncraftvp;

import com.wynnvp.wynncraftvp.coords.CoordsHandler;
import com.wynnvp.wynncraftvp.sound.SoundsHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = ModCore.MODID,
        name = ModCore.NAME,
        version = ModCore.VERSION,
        clientSideOnly = true
)
public class ModCore {
    public static final String MODID = "wynnvp";
    public static final String NAME = "Wynncraft Voice Project";
    public static final String VERSION = "0.1";
    public SoundsHandler soundsHandler;
    public CoordsHandler coordsHandler;
    public static ModCore instance;

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        soundsHandler = new SoundsHandler();
        coordsHandler = new CoordsHandler();
        instance = this;
    }

}
