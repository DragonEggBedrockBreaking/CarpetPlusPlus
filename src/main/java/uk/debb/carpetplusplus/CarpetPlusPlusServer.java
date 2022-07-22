package uk.debb.carpetplusplus;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CarpetPlusPlusServer implements CarpetExtension {
    public static void loadExtension() {
        CarpetServer.manageExtension(new CarpetPlusPlusServer());
    }

    @Override
    public String version() {
        return "carpetplusplus";
    }

    @Override
    public void onGameStarted() {
        CarpetServer.settingsManager.parseSettingsClass(CarpetPlusPlusSettings.class);
    }
}
