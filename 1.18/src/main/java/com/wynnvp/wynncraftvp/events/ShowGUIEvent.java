package com.wynnvp.wynncraftvp.events;

import com.terraformersmc.modmenu.util.mod.Mod;
import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.config.VOWAutoConfig;
import com.wynnvp.wynncraftvp.gui.LineReportGUI;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class ShowGUIEvent {


    public static void onGUIOpen(Screen screen, CallbackInfo ci) {

        System.out.println("Is using cloth api: " + ModCore.isUsingClothApi);
        if (screen instanceof TitleScreen && ModCore.isUsingClothApi && !ModCore.config.isHasChosenLineReport()) {
            LineReportGUI.OpenGui();
            ci.cancel();
        }


    }
}
