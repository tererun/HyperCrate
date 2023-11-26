package run.tere.plugin.hypercrate.guis.holder;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import run.tere.plugin.hypercrate.HyperCrate;
import run.tere.plugin.hypercrate.apis.ItemStackAPI;
import run.tere.plugin.hypercrate.guis.HyperCrateInventoryHolder;
import run.tere.plugin.hypercrate.guis.HyperCrateSettingsGUI;

public class SettingsGUI implements HyperCrateInventoryHolder {

    private Inventory inventory;

    public SettingsGUI() {
        this.inventory = Bukkit.createInventory(this, 9, HyperCrate.getLanguage().get("GUI_Settings"));
        inventory.setItem(2, ItemStackAPI.getItemStack(Material.BOOK, 1, HyperCrate.getLanguage().get("Crate_List"), null, HyperCrateSettingsGUI.key, "list"));
        inventory.setItem(6, ItemStackAPI.getItemStack(Material.PAPER, 1, HyperCrate.getLanguage().get("Create_Crate"), null, HyperCrateSettingsGUI.key, "create"));
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }

}
