package com.wynnvp.wynncraftvp.events;



import com.terraformersmc.modmenu.util.mod.Mod;
import com.wynnvp.wynncraftvp.ModCore;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class ShowGUIEvent {

    private static boolean hasOpened = false;
    public static void onGUIOpen(Screen screen, CallbackInfo ci) {

        if (screen instanceof TitleScreen) {
            hasOpened = true;
            ModCore.instance.audioDownloader.checkToDownload();
        }


    }
}
