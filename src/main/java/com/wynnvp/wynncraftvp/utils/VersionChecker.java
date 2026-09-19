/*
 * Copyright © Team-VoW 2024-2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.utils;

import static com.wynnvp.wynncraftvp.ModCore.config;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.api.ApiHttpException;
import com.wynnvp.wynncraftvp.api.VoWApiClient;
import com.wynnvp.wynncraftvp.config.BetaConfig;
import com.wynnvp.wynncraftvp.sound.SoundPlayer;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.Minecraft;

public final class VersionChecker {
    private static final VoWApiClient API = new VoWApiClient();

    private VersionChecker() {}

    public static void checkVersion() {
        String clientId =
                Utils.sha256(Minecraft.getInstance().getUser().getProfileId().toString());
        String modVersion = withoutLeadingV(ModCore.instance.getVersion());
        try {
            apply(API.bootup(clientId, modVersion), BetaConfig.isBetaBuild(), config.isSendFunFact(), modVersion);
        } catch (ApiHttpException exception) {
            ModCore.LOGGER.error("Bootup request failed with HTTP {}.", exception.statusCode());
        } catch (IOException exception) {
            ModCore.LOGGER.error("Bootup request failed.", exception);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            ModCore.LOGGER.warn("Bootup request was interrupted.", exception);
        }
    }

    static void apply(VoWApiClient.BootupResponse response, boolean betaBuild, boolean showFunFact, String version) {
        BootupPlan plan = plan(response, betaBuild, showFunFact);
        if (plan.audioBaseUrl() != null) {
            config.soundsBaseUrl = plan.audioBaseUrl();
            if (!plan.audioMirrorUrls().isEmpty()) config.urls = plan.audioMirrorUrls();
            config.save();
        }

        if (plan.disablesMod()) {
            SoundPlayer.stopMod = true;
            Utils.sendMessage(
                    "§8A game breaking bug was found on your version of §5Voices of Wynn§8 so the mod was disabled. Please update it");
        }
        if (plan.showsUpdate()) showUpdate(response, version);
        if (plan.funFact() != null) Utils.sendMessage("§9Fun fact: " + plan.funFact());

        for (String message : plan.broadcasts()) {
            if (message != null && !message.isBlank()) Utils.sendMessage("§d" + message);
        }
    }

    static BootupPlan plan(VoWApiClient.BootupResponse response, boolean betaBuild, boolean showFunFact) {
        boolean disablesMod = "disable".equals(response.update().action());
        boolean showsUpdate = disablesMod || "notify".equals(response.update().action());
        List<String> mirrors = betaBuild
                ? List.of()
                : response.audio().mirrorUrls().stream()
                        .filter(url -> url != null && !url.isBlank())
                        .toList();
        String fact = "none".equals(response.update().action()) && showFunFact ? response.funFact() : null;
        return new BootupPlan(
                response.update().action(),
                disablesMod,
                showsUpdate,
                fact,
                betaBuild ? null : response.audio().baseUrl(),
                mirrors,
                response.broadcasts().stream()
                        .filter(message -> message != null && !message.isBlank())
                        .toList());
    }

    private static void showUpdate(VoWApiClient.BootupResponse response, String version) {
        Utils.sendMessage("§9A new version of §5Voices of Wynn§9 is available! You are using version: §4" + version
                + " §9and the newest version is: §2" + response.update().latestVersion() + ".");
        Utils.appendMessageWithLinkAndSend(
                "§9To download it directly, click ", response.update().downloadUrl(), "§b§nhere");
        Utils.appendMessageWithLinkAndSend(
                "§9To see the changelog, click ", response.update().changelogUrl(), "§b§nhere");
    }

    static String withoutLeadingV(String version) {
        return version != null && version.startsWith("v") ? version.substring(1) : version;
    }

    record BootupPlan(
            String action,
            boolean disablesMod,
            boolean showsUpdate,
            String funFact,
            String audioBaseUrl,
            List<String> audioMirrorUrls,
            List<String> broadcasts) {}
}
