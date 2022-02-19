package run.tere.plugin.hypercrate.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import run.tere.plugin.hypercrate.HyperCrate;
import run.tere.plugin.hypercrate.consts.crates.Crate;
import run.tere.plugin.hypercrate.consts.itemclick.ItemClick;
import run.tere.plugin.hypercrate.consts.itemclick.ItemClickType;

import java.util.HashSet;
import java.util.UUID;

public class ItemClickHandler implements Listener {
    private HashSet<ItemClick> itemClicks;

    public ItemClickHandler() {
        this.itemClicks = new HashSet<>();
        Bukkit.getPluginManager().registerEvents(this, HyperCrate.getPlugin());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        UUID uuid = player.getUniqueId();
        if (!containsItemClickFromUUID(uuid)) return;
        ItemClick itemClick = getItemClickFromUUID(uuid);
        ItemClickType itemClickType = itemClick.getItemClickType();
        if ((e.getCurrentItem() == null) || (e.getCurrentItem().getType().equals(Material.AIR))) return;
        Crate crate = itemClick.getCrate();
        ItemStack itemStack = e.getCurrentItem().clone();
        switch (itemClickType) {
            case MATERIAL:
                if (!itemStack.getType().isBlock()) {
                    itemStack = new ItemStack(Material.CHEST);
                }
                crate.getCrateSettings().setBlockMaterial(itemStack.getType());
                break;
            case DISPLAY_ITEM:
                if (!itemStack.getType().isItem()) {
                    itemStack = new ItemStack(Material.DROPPER, 1);
                }
                crate.getCrateSettings().setDisplayItem(itemStack);
                break;
            case KEY_ITEM:
                if (!itemStack.getType().isItem()) {
                    itemStack = new ItemStack(Material.PAPER, 1);
                }
                itemStack.setAmount(1);
                crate.getCrateSettings().setCrateKeyItem(itemStack);
                break;
        }
        this.removeItemClick(itemClick);
        HyperCrate.getCrateHandler().saveCrateHandler();
        player.sendMessage(HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("Set_Item"));
    }

    public void addItemClick(ItemClick itemClick) {
        UUID uuid = itemClick.getUUID();
        if (this.containsItemClickFromUUID(uuid)) {
            this.removeItemClick(this.getItemClickFromUUID(uuid));
        }
        this.itemClicks.add(itemClick);
    }

    public boolean containsItemClickFromUUID(UUID uuid) {
        return getItemClickFromUUID(uuid) != null;
    }

    public ItemClick getItemClickFromUUID(UUID uuid) {
        for (ItemClick itemClick : this.itemClicks) {
            if (itemClick.getUUID().equals(uuid)) {
                return itemClick;
            }
        }
        return null;
    }

    public void removeItemClick(ItemClick itemClick) {
        itemClick.cancel();
        itemClicks.remove(itemClick);
    }
}
