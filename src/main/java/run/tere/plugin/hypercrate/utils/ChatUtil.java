package run.tere.plugin.hypercrate.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import run.tere.plugin.hypercrate.HyperCrate;

public class ChatUtil {

    public static void sendMessage(Player player, String message) {
        player.sendMessage(getPlaceholderMessage(player, message));
    }

    public static String getPlaceholderMessage(Player player, String message) {
        String replacedMessage = ChatColor.translateAlternateColorCodes('&', message);
        if (HyperCrate.isUsePlaceholderAPI()) {
            replacedMessage = PlaceholderAPI.setPlaceholders(player, replacedMessage);
        }
        return replacedMessage;
    }

}
