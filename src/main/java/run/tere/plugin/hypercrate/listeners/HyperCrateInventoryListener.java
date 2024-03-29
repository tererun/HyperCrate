package run.tere.plugin.hypercrate.listeners;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import run.tere.plugin.hypercrate.HyperCrate;
import run.tere.plugin.hypercrate.apis.InventoryAPI;
import run.tere.plugin.hypercrate.apis.ItemStackAPI;
import run.tere.plugin.hypercrate.consts.crates.Crate;
import run.tere.plugin.hypercrate.consts.crates.CrateSettings;
import run.tere.plugin.hypercrate.consts.itemclick.ItemClick;
import run.tere.plugin.hypercrate.consts.itemclick.ItemClickType;
import run.tere.plugin.hypercrate.consts.typechat.TypeChat;
import run.tere.plugin.hypercrate.consts.typechat.TypeChatType;
import run.tere.plugin.hypercrate.guis.HyperCrateInventoryHolder;
import run.tere.plugin.hypercrate.guis.HyperCrateSettingsGUI;
import run.tere.plugin.hypercrate.guis.holder.*;
import run.tere.plugin.hypercrate.utils.ChatUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class HyperCrateInventoryListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Inventory inventory = e.getInventory();
        InventoryHolder inventoryHolder = inventory.getHolder();
        if (inventoryHolder instanceof CrateItemRewardsGUI) {
            e.setCancelled(true);
            return;
        }
        if (!(inventoryHolder instanceof HyperCrateInventoryHolder)) return;
        Player player = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem != null) {
            if (!NBTEditor.contains(clickedItem, "HyperCrateGUIItem")) {
                if (!(inventoryHolder instanceof CrateItemSettingsGUI)) e.setCancelled(true);
                return;
            }
            String itemType = NBTEditor.getString(clickedItem, "HyperCrateGUIItem");
            if (inventoryHolder instanceof SettingsGUI) {
                e.setCancelled(true);
                if (itemType.equalsIgnoreCase("list")) {
                    player.openInventory(HyperCrateSettingsGUI.getCrateListGUI());
                } else if (itemType.equalsIgnoreCase("create")) {
                    String crateName = "New Crate";
                    if (HyperCrate.getCrateHandler().getCrateSizeFromStartWithName(crateName) != 0) {
                        crateName += " (" + HyperCrate.getCrateHandler().getCrateNameSizeFromStartWithName(crateName) + ")";
                    }
                    String crateKey = UUID.randomUUID().toString();
                    CrateSettings crateSettings = new CrateSettings(Material.CHEST, crateName, crateKey, ItemStackAPI.getDefaultCrateKey(crateName, crateKey), "minecraft:block.chest.open", InventoryAPI.getStringFromItemStack(new ItemStack(Material.DROPPER, 1)), new ArrayList<>(Arrays.asList("§b" + crateName)));
                    Crate crate = new Crate(crateSettings);
                    HyperCrate.getCrateHandler().addCrate(crate);
                    ChatUtil.sendMessage(player, HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("Created_Crate"));
                    player.openInventory(HyperCrateSettingsGUI.getCrateSettingGUI(crate));
                }
            } else if (inventoryHolder instanceof ListGUI) {
                e.setCancelled(true);
                player.openInventory(HyperCrateSettingsGUI.getCrateSettingGUI(HyperCrate.getCrateHandler().getCrateFromKey(itemType)));
            } else if (inventoryHolder instanceof CrateSettingsGUI) {
                e.setCancelled(true);
                ItemStack crateInfo = e.getInventory().getItem(8);
                if ((crateInfo == null) || (!crateInfo.hasItemMeta()) || (!crateInfo.getItemMeta().hasLore())) return;
                Crate crate = HyperCrate.getCrateHandler().getCrateFromKey(crateInfo.getItemMeta().getLore().get(3));
                if (itemType.equalsIgnoreCase("delete")) {
                    crate.delete();
                    player.closeInventory();
                    ChatUtil.sendMessage(player, HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("Deleted_Crate"));
                } else if (itemType.equalsIgnoreCase("items")) {
                    player.openInventory(HyperCrateSettingsGUI.getCrateItemSettingsGUI(crate));
                } else if (itemType.equalsIgnoreCase("material")) {
                    player.closeInventory();
                    ItemClick itemClick = new ItemClick(player.getUniqueId(), crate, ItemClickType.MATERIAL, 600L);
                    HyperCrate.getItemClickHandler().addItemClick(itemClick);
                } else if (itemType.equalsIgnoreCase("displayItem")) {
                    player.closeInventory();
                    ItemClick itemClick = new ItemClick(player.getUniqueId(), crate, ItemClickType.DISPLAY_ITEM, 600L);
                    HyperCrate.getItemClickHandler().addItemClick(itemClick);
                } else if (itemType.equalsIgnoreCase("keyItem")) {
                    player.closeInventory();
                    ItemClick itemClick = new ItemClick(player.getUniqueId(), crate, ItemClickType.KEY_ITEM, 600L);
                    HyperCrate.getItemClickHandler().addItemClick(itemClick);
                } else if (itemType.equalsIgnoreCase("holo")) {
                    player.closeInventory();
                    ChatUtil.sendMessage(player, HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("TypeChat_Holo_Message"));
                    TypeChat typeChat = new TypeChat(player.getUniqueId(), crate, TypeChatType.HOLO, 600L);
                    HyperCrate.getTypeChatHandler().addTypeChat(typeChat);
                } else if (itemType.equalsIgnoreCase("sound")) {
                    player.closeInventory();
                    ChatUtil.sendMessage(player, HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("TypeChat_Sound_Message"));
                    TypeChat typeChat = new TypeChat(player.getUniqueId(), crate, TypeChatType.SOUND, 600L);
                    HyperCrate.getTypeChatHandler().addTypeChat(typeChat);
                } else if (itemType.equalsIgnoreCase("name")) {
                    player.closeInventory();
                    ChatUtil.sendMessage(player, HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("TypeChat_Message"));
                    TypeChat typeChat = new TypeChat(player.getUniqueId(), crate, TypeChatType.NAME, 600L);
                    HyperCrate.getTypeChatHandler().addTypeChat(typeChat);
                } else if (itemType.equalsIgnoreCase("getCrateBlock")) {
                    ChatUtil.sendMessage(player, HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("Gave_Item"));
                    player.closeInventory();
                    player.getInventory().addItem(crate.getCrateSettings().getCrateBlock());
                } else if (itemType.equalsIgnoreCase("getCrateKey")) {
                    player.closeInventory();
                    crate.giveCrateKey(player, 1);
                }
            }
        }

    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        InventoryHolder inventoryHolder = e.getInventory().getHolder();
        if (!(inventoryHolder instanceof CrateItemSettingsGUI)) return;
        CrateItemSettingsGUI crateItemSettingsGUI = (CrateItemSettingsGUI) inventoryHolder;
        Player player = (Player) e.getPlayer();
        Crate crate = HyperCrate.getCrateHandler().getCrateFromKey(crateItemSettingsGUI.getCrateKey());
        crate.getCrateItems().clearAll();
        for (ItemStack itemStack : e.getInventory().getContents()) {
            if (itemStack == null) continue;
            crate.getCrateItems().addCrateItem(itemStack);
        }
        HyperCrate.getCrateHandler().saveCrateHandler();
        ChatUtil.sendMessage(player, HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("Set_Item"));
    }
}
