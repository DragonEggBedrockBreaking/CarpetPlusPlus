package uk.debb.carpetplusplus;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CarpetPlusPlusServer implements CarpetExtension {
    public static final Logger LOGGER = LoggerFactory.getLogger("CarpetPlusPlus");

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
    // UNCOMMENT TO ADD TRANSLATIONS
    /*@Override
    public Map<String, String> canHasTranslations(String lang) {
        String dataJSON;
        try {
            dataJSON = IOUtils.toString(Objects.requireNonNull(CarpetPlusPlusServer.class.getClassLoader().getResourceAsStream(String.format("assets/carpetplusplus/lang/%s.json", lang))), StandardCharsets.UTF_8);
        } catch (IOException | NullPointerException e) {
            return null;
        }
        Gson gson = (new GsonBuilder()).enableComplexMapKeySerialization().create();
        return gson.fromJson(dataJSON, (new TypeToken<Map<String, String>>() {}).getType());
    }*/
}
