package run.tere.plugin.hypercrate.guis.holder;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import run.tere.plugin.hypercrate.consts.crates.Crate;
import run.tere.plugin.hypercrate.guis.HyperCrateInventoryHolder;

public class CrateItemRewardsGUI implements HyperCrateInventoryHolder {

    private Inventory inventory;

    public CrateItemRewardsGUI(Crate crate) {
        this.inventory = Bukkit.createInventory(this, 54, "§6§l" + crate.getCrateSettings().getCrateName());
        for (ItemStack crateItem : crate.getCrateItems().getCrateItems()) {
            inventory.addItem(crateItem);
        }
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }

}
