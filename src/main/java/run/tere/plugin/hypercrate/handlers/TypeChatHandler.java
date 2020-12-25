package run.tere.plugin.hypercrate.handlers;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import run.tere.plugin.hypercrate.HyperCrate;
import run.tere.plugin.hypercrate.consts.crates.Crate;
import run.tere.plugin.hypercrate.consts.typechat.TypeChat;
import run.tere.plugin.hypercrate.consts.typechat.TypeChatType;

import java.util.HashSet;
import java.util.UUID;

public class TypeChatHandler implements Listener {
    private HashSet<TypeChat> typeChats;

    public TypeChatHandler() {
        this.typeChats = new HashSet<>();
        Bukkit.getPluginManager().registerEvents(this, HyperCrate.getPlugin());
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        if (!containsTypeChatFromUUID(uuid)) return;
        TypeChat typeChat = getTypeChatFromUUID(uuid);
        TypeChatType typeChatType = typeChat.getTypeChatType();
        Crate crate = typeChat.getCrate();
        e.setCancelled(true);
        String message = ChatColor.translateAlternateColorCodes('&', e.getMessage());
        switch (typeChatType) {
            case HOLO:
                String[] texts = message.split(":", 2);
                if ((texts.length == 0) || (!NumberUtils.isDigits(texts[0]))) {
                    player.sendMessage(HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("TypeChat_Holo_Error_Message"));
                    removeTypeChat(typeChat);
                    return;
                }
                crate.getCrateSettings().setHolographic(Integer.parseInt(texts[0]) - 1, texts[1]);
                break;
            case SOUND:
                crate.getCrateSettings().setRollFinishSound(message);
                break;
            case NAME:
                if (HyperCrate.getCrateHandler().containsCrate(message)) {
                    player.sendMessage(HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("TypeChat_Already_Exists_Name_Error"));
                    removeTypeChat(typeChat);
                    return;
                }
                crate.getCrateSettings().setCrateName(message);
                break;
        }
        this.removeTypeChat(typeChat);
        HyperCrate.getCrateHandler().saveCrateHandler();
        player.sendMessage(HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("Set_TypeChat"));
    }

    public void addTypeChat(TypeChat typeChat) {
        UUID uuid = typeChat.getUUID();
        if (this.containsTypeChatFromUUID(uuid)) {
            this.removeTypeChat(this.getTypeChatFromUUID(uuid));
        }
        this.typeChats.add(typeChat);
    }

    public boolean containsTypeChatFromUUID(UUID uuid) {
        return getTypeChatFromUUID(uuid) != null;
    }

    public TypeChat getTypeChatFromUUID(UUID uuid) {
        for (TypeChat typeChat : this.typeChats) {
            if (typeChat.getUUID().equals(uuid)) {
                return typeChat;
            }
        }
        return null;
    }

    public void removeTypeChat(TypeChat typeChat) {
        typeChat.cancel();
        typeChats.remove(typeChat);
    }
}
