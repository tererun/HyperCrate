package run.tere.plugin.hypercrate;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import run.tere.plugin.hypercrate.apis.ConfigLanguage;
import run.tere.plugin.hypercrate.apis.JsonAPI;
import run.tere.plugin.hypercrate.commands.HyperCrateCommandClass;
import run.tere.plugin.hypercrate.consts.crates.Crate;
import run.tere.plugin.hypercrate.consts.crates.CrateHandler;
import run.tere.plugin.hypercrate.consts.crates.CrateLocation;
import run.tere.plugin.hypercrate.consts.languages.Language;
import run.tere.plugin.hypercrate.handlers.ItemClickHandler;
import run.tere.plugin.hypercrate.handlers.TypeChatHandler;
import run.tere.plugin.hypercrate.listeners.HyperCrateBlockListener;
import run.tere.plugin.hypercrate.listeners.HyperCrateInteractListener;
import run.tere.plugin.hypercrate.listeners.HyperCrateInventoryListener;
import run.tere.plugin.hypercrate.update.UpdateChecker;

import java.io.File;

public final class HyperCrate extends JavaPlugin {

    private static Plugin plugin;

    private static boolean useHolographicDisplays;
    private static boolean usePlaceholderAPI;

    private static FileConfiguration config;
    private static ConfigLanguage configLanguage;
    private static Language language;

    private static CrateHandler crateHandler;
    private static ItemClickHandler itemClickHandler;
    private static TypeChatHandler typeChatHandler;

    @Override
    public void onEnable() {
        pluginSetup();
    }

    @Override
    public void onDisable() {
        removeCrates();
    }

    private void removeCrates() {
        for (Crate crate : crateHandler.getCrateList()) {
            for (CrateLocation crateLocation : crate.getCrateLocations()) {
                Location addedLocation = crateLocation.getLocation().clone().add(0.5, 2.5, 0.5);
                for (Hologram hologram : HologramsAPI.getHolograms(HyperCrate.getPlugin())) {
                    if ((addedLocation.getWorld().equals(hologram.getWorld())) && (addedLocation.getX() == hologram.getX()) && (addedLocation.getY() == hologram.getY()) && (addedLocation.getZ() == hologram.getZ())) {
                        hologram.delete();
                    }
                }
            }
        }
    }

    private void pluginSetup() {
        new UpdateChecker(this, 87060, "https://www.spigotmc.org/resources/hypercrate.87060/");
        useHolographicDisplays = Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays");
        usePlaceholderAPI = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
        plugin = this;
        itemClickHandler = new ItemClickHandler();
        typeChatHandler = new TypeChatHandler();
        setConfig();
        setLanguage();
        loadCrates();
        registerCommands();
        registerEvents();
    }

    private void registerCommands() {
        getCommand("hypercrate").setExecutor(new HyperCrateCommandClass());
        getCommand("hypercrate").setTabCompleter(new HyperCrateCommandClass());
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new HyperCrateBlockListener(), this);
        getServer().getPluginManager().registerEvents(new HyperCrateInteractListener(), this);
        getServer().getPluginManager().registerEvents(new HyperCrateInventoryListener(), this);
    }

    private void setConfig() {
        saveDefaultConfig();
        config = getConfig();
        configLanguage = new ConfigLanguage(config.getString("Language"));
        configLanguage.saveDefaultConfig();
        ConfigLanguage enConfig = new ConfigLanguage("en");
        ConfigLanguage jaConfig = new ConfigLanguage("ja");
        enConfig.saveDefaultConfig();
        jaConfig.saveDefaultConfig();
    }

    private void setLanguage() {
        language = new Language();
    }

    private void loadCrates() {
        crateHandler = new CrateHandler();
        String fileName = "data";
        File file = new File(getDataFolder(), fileName + ".json");
        if (file.exists()) {
            crateHandler = JsonAPI.read(fileName);
        }
        if ((crateHandler != null) && (crateHandler.getCrateList() != null)) {
            for (Crate crate : crateHandler.getCrateList()) {
                for (CrateLocation crateLocation : crate.getCrateLocations()) {
                    crate.createBlock(crateLocation.getLocation());
                }
            }
        }
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    public static FileConfiguration getConfigruation() {
        return config;
    }

    public static void setConfigruation(FileConfiguration config) {
        HyperCrate.config = config;
    }

    public static Language getLanguage() {
        return language;
    }

    public static void setLanguage(Language language) {
        HyperCrate.language = language;
    }

    public static CrateHandler getCrateHandler() {
        return crateHandler;
    }

    public static ConfigLanguage getConfigLanguage() {
        return configLanguage;
    }

    public static void setConfigLanguage(ConfigLanguage configLanguage) {
        HyperCrate.configLanguage = configLanguage;
    }

    public static boolean isUseHolographicDisplays() {
        return useHolographicDisplays;
    }

    public static boolean isUsePlaceholderAPI() {
        return usePlaceholderAPI;
    }

    public static ItemClickHandler getItemClickHandler() {
        return itemClickHandler;
    }

    public static TypeChatHandler getTypeChatHandler() {
        return typeChatHandler;
    }
}
