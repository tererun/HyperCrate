package run.tere.plugin.hypercrate.apis;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import run.tere.plugin.hypercrate.HyperCrate;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ConfigLanguage {
    private Plugin plugin;

    private FileConfiguration config = null;
    private File langFile;
    private String langFileName;

    public ConfigLanguage(String langFileName) {
        this.plugin = HyperCrate.getPlugin();
        this.langFileName = langFileName;
        new File(this.plugin.getDataFolder() + File.separator + "lang").mkdir();
        this.langFile = new File(this.plugin.getDataFolder() + File.separator + "lang", this.langFileName + ".yml");
    }

    public void saveDefaultConfig() {
        if (!this.langFile.exists()) {
            this.plugin.saveResource("lang" + File.separator + this.langFileName + ".yml", false);
        }
    }

    public void reloadConfig() {
        this.config = YamlConfiguration.loadConfiguration(this.langFile);
        InputStream defConfigStream = this.plugin.getResource(this.langFileName + ".yml");
        if (defConfigStream == null) {
            return;
        }
        this.config.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, StandardCharsets.UTF_8)));
    }

    public FileConfiguration getConfig() {
        if (this.config == null) {
            this.reloadConfig();
        }
        return this.config;
    }

    public void saveConfig() {
        if (this.config == null) {
            return;
        }
        try {
            getConfig().save(langFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
