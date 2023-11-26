package run.tere.plugin.hypercrate.guis.holder;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import run.tere.plugin.hypercrate.HyperCrate;
import run.tere.plugin.hypercrate.apis.ItemStackAPI;
import run.tere.plugin.hypercrate.consts.crates.Crate;
import run.tere.plugin.hypercrate.guis.HyperCrateInventoryHolder;
import run.tere.plugin.hypercrate.guis.HyperCrateSettingsGUI;

public class ListGUI implements HyperCrateInventoryHolder {

    private Inventory inventory;

    public ListGUI() {
        this.inventory = Bukkit.createInventory(this, 54, HyperCrate.getLanguage().get("GUI_CrateList"));
        for (Crate crate : HyperCrate.getCrateHandler().getCrateList()) {
            inventory.addItem(ItemStackAPI.getItemStack(crate.getCrateSettings().getBlockMaterial(), 1, crate.getCrateSettings().getCrateName(), null, HyperCrateSettingsGUI.key, crate.getCrateSettings().getCrateKey()));
        }
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }

}
