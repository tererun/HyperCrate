package run.tere.plugin.hypercrate.guis.holder;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import run.tere.plugin.hypercrate.HyperCrate;
import run.tere.plugin.hypercrate.consts.crates.Crate;
import run.tere.plugin.hypercrate.guis.HyperCrateInventoryHolder;

public class CrateItemSettingsGUI implements HyperCrateInventoryHolder {

    private Inventory inventory;
    private String crateKey;

    public CrateItemSettingsGUI(Crate crate) {
        this.crateKey = crate.getCrateSettings().getCrateKey();
        this.inventory = Bukkit.createInventory(null, 54, HyperCrate.getLanguage().get("GUI_CrateRewardsSettings"));
        for (ItemStack crateItem : crate.getCrateItems().getCrateItems()) {
            inventory.addItem(crateItem);
        }
    }

    public String getCrateKey() {
        return crateKey;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }

}
