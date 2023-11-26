package run.tere.plugin.hypercrate.guis.holder;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import run.tere.plugin.hypercrate.HyperCrate;
import run.tere.plugin.hypercrate.apis.ItemStackAPI;
import run.tere.plugin.hypercrate.consts.crates.Crate;
import run.tere.plugin.hypercrate.guis.HyperCrateInventoryHolder;
import run.tere.plugin.hypercrate.guis.HyperCrateSettingsGUI;

import java.util.Arrays;

public class CrateSettingsGUI implements HyperCrateInventoryHolder {

    private Inventory inventory;

    public CrateSettingsGUI(Crate crate) {
        this.inventory = Bukkit.createInventory(null, 27, HyperCrate.getLanguage().get("GUI_CrateSettings"));
        inventory.setItem(0, ItemStackAPI.getItemStack(Material.BARRIER, 1, HyperCrate.getLanguage().get("Delete_Crate"), null, HyperCrateSettingsGUI.key, "delete"));
        inventory.setItem(8, ItemStackAPI.getItemStack(Material.BOOK, 1, "§2§lINFO", Arrays.asList("§aCrateName§f:", crate.getCrateSettings().getCrateName(), "§aUUID§f:", crate.getCrateSettings().getCrateKey()), HyperCrateSettingsGUI.key, "info"));
        inventory.setItem(13, ItemStackAPI.getItemStack(Material.CHEST, 1, HyperCrate.getLanguage().get("Change_ItemRewards"), null, HyperCrateSettingsGUI.key, "items"));
        inventory.setItem(18, ItemStackAPI.getItemStack(Material.GRASS_BLOCK, 1, HyperCrate.getLanguage().get("Get_CrateBlock"), null, HyperCrateSettingsGUI.key, "getCrateBlock"));
        inventory.setItem(19, ItemStackAPI.getItemStack(Material.TRIPWIRE_HOOK, 1, HyperCrate.getLanguage().get("Get_CrateKey"), null, HyperCrateSettingsGUI.key, "getCrateKey"));
        inventory.setItem(21, ItemStackAPI.getItemStack(Material.NAME_TAG, 1, HyperCrate.getLanguage().get("Change_Name"), null, HyperCrateSettingsGUI.key, "name"));
        inventory.setItem(22, ItemStackAPI.getItemStack(Material.NOTE_BLOCK, 1, HyperCrate.getLanguage().get("Change_Sound"), null, HyperCrateSettingsGUI.key, "sound"));
        inventory.setItem(23, ItemStackAPI.getItemStack(Material.SPRUCE_SIGN, 1, HyperCrate.getLanguage().get("Change_Holographics"), null, HyperCrateSettingsGUI.key, "holo"));
        inventory.setItem(24, ItemStackAPI.getItemStack(Material.BEACON, 1, HyperCrate.getLanguage().get("Change_DisplayItem"), null, HyperCrateSettingsGUI.key, "displayItem"));
        inventory.setItem(25, ItemStackAPI.getItemStack(Material.IRON_PICKAXE, 1, HyperCrate.getLanguage().get("Change_Material"), null, HyperCrateSettingsGUI.key, "material"));
        inventory.setItem(26, ItemStackAPI.getItemStack(Material.PAPER, 1, HyperCrate.getLanguage().get("Change_CrateKey_Item"), null, HyperCrateSettingsGUI.key, "keyItem"));
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }

}
