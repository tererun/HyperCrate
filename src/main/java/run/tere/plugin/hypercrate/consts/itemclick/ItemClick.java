package run.tere.plugin.hypercrate.consts.itemclick;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;
import run.tere.plugin.hypercrate.HyperCrate;
import run.tere.plugin.hypercrate.consts.crates.Crate;

import java.util.UUID;

public class ItemClick {
    private ItemClick itemClick;
    private UUID uuid;
    private Crate crate;
    private ItemClickType itemClickType;
    private BukkitRunnable bukkitRunnable;
    private long later;

    public ItemClick(UUID uuid, Crate crate, ItemClickType itemClickType, long later) {
        this.itemClick = this;
        this.uuid = uuid;
        this.crate = crate;
        this.itemClickType = itemClickType;
        if (itemClickType.equals(ItemClickType.DISPLAY_ITEM)) {
            Bukkit.getPlayer(uuid).sendMessage(HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("ItemClick_DisplayItem_Message"));
        } else {
            Bukkit.getPlayer(uuid).sendMessage(HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("ItemClick_Message"));
        }
        this.bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                HyperCrate.getItemClickHandler().removeItemClick(itemClick);
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
                if (offlinePlayer.isOnline()) {
                    Bukkit.getPlayer(uuid).sendMessage(HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("Set_Item_Timeup"));
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

    public ItemClickType getItemClickType() {
        return itemClickType;
    }
}
