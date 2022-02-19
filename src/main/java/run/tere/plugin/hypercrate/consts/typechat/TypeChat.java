package run.tere.plugin.hypercrate.consts.typechat;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;
import run.tere.plugin.hypercrate.HyperCrate;
import run.tere.plugin.hypercrate.consts.crates.Crate;

import java.util.UUID;

public class TypeChat {
    private TypeChat typeChat;
    private UUID uuid;
    private Crate crate;
    private TypeChatType typeChatType;
    private BukkitRunnable bukkitRunnable;
    private long later;

    public TypeChat(UUID uuid, Crate crate, TypeChatType typeChatType, long later) {
        this.typeChat = this;
        this.uuid = uuid;
        this.crate = crate;
        this.typeChatType = typeChatType;
        this.bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                HyperCrate.getTypeChatHandler().removeTypeChat(typeChat);
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
                if (offlinePlayer.isOnline()) {
                    Bukkit.getPlayer(uuid).sendMessage(HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("Set_TypeChat_Timeup"));
                }
            }
        };
        this.later = later;
        run();
    }

    public void run() {
        this.bukkitRunnable.runTaskLater(HyperCrate.getPlugin(), this.later);
    }

    public void cancel() {
        this.bukkitRunnable.cancel();
    }

    public boolean isCancelled() {
        return this.bukkitRunnable.isCancelled();
    }

    public UUID getUUID() {
        return uuid;
    }

    public Crate getCrate() {
        return crate;
    }

    public TypeChatType getTypeChatType() {
        return typeChatType;
    }
}
