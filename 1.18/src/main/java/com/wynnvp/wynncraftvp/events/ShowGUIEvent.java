package com.wynnvp.wynncraftvp.events;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.gui.LineReportGUI;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class ShowGUIEvent {


    public static void onGUIOpen(Screen screen, CallbackInfo ci) {

        if (screen instanceof TitleScreen && ModCore.isUsingClothApi && !ModCore.config.isHasChosenLineReport()) {
            LineReportGUI.OpenGui();
            ci.cancel();
        }


    }
}
