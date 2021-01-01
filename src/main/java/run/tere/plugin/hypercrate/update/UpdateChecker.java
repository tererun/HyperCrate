package run.tere.plugin.hypercrate.update;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import run.tere.plugin.hypercrate.HyperCrate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class UpdateChecker implements Listener {
    private Plugin plugin;
    private int resourceId;
    private boolean newVersionBool;
    private String url;

    public UpdateChecker(Plugin plugin, int resourceId, String url) {
        this.plugin = plugin;
        this.resourceId = resourceId;
        this.url = url;
        confirmNewVersion();
        if (this.newVersionBool) {
            Bukkit.getPluginManager().registerEvents(this, this.plugin);
        }
    }

    public void confirmNewVersion() {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream(); Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {
                    String nowVersion = this.plugin.getDescription().getVersion();
                    String newVersion = scanner.next();
                    newVersionBool = !nowVersion.equalsIgnoreCase(newVersion);
                    if (newVersionBool) {
                        this.plugin.getLogger().info(HyperCrate.getLanguage().get("Prefix") + " §aA new version has been released!");
                        this.plugin.getLogger().info(HyperCrate.getLanguage().get("Prefix") + " §aDownload it here >");
                        this.plugin.getLogger().info(HyperCrate.getLanguage().get("Prefix") + " §e" + this.url);
                    }
                }
            } catch (IOException exception) {
                newVersionBool = false;
            }
        });
    }

    public boolean hasNewVersion() {
        return newVersionBool;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (!hasNewVersion()) return;
        if (player.isOp()) {
            player.sendMessage(HyperCrate.getLanguage().get("Prefix") + " §aA new version has been released!");
            player.sendMessage(HyperCrate.getLanguage().get("Prefix") + " §aDownload it here >");
            player.sendMessage(HyperCrate.getLanguage().get("Prefix") + " §e" + this.url);
        }
    }
}
