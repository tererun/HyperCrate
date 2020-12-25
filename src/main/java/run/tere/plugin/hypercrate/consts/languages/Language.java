package run.tere.plugin.hypercrate.consts.languages;

import org.bukkit.configuration.file.FileConfiguration;
import run.tere.plugin.hypercrate.HyperCrate;

import java.util.HashMap;

public class Language {
    private HashMap<String, String> languages;

    public Language() {
        this.languages = new HashMap<>();
        FileConfiguration config = HyperCrate.getConfigLanguage().getConfig();
        for (String key : config.getKeys(false)) {
            this.languages.put(key, config.getString(key));
        }
    }

    public void addTranslation(String placeHolder) {
        String translatedText = HyperCrate.getConfigLanguage().getConfig().getString(placeHolder);
        languages.put(placeHolder, translatedText);
    }

    public String get(String placeHolder) {
        if (languages.containsKey(placeHolder)) {
            return languages.get(placeHolder);
        }
        return placeHolder;
    }
}
