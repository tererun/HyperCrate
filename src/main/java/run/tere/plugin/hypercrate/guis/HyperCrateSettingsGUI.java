package run.tere.plugin.hypercrate.guis;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import run.tere.plugin.hypercrate.HyperCrate;
import run.tere.plugin.hypercrate.apis.ItemStackAPI;
import run.tere.plugin.hypercrate.consts.crates.Crate;
import run.tere.plugin.hypercrate.guis.holder.*;

import java.util.Arrays;

public class HyperCrateSettingsGUI {
    public static String key = "HyperCrateGUIItem";

    public static Inventory getSettingsGUI() {
        return new SettingsGUI().getInventory();
    }

    public static Inventory getCrateListGUI() {
        return new ListGUI().getInventory();
    }

    public static Inventory getCrateSettingGUI(Crate crate) {
        return new CrateSettingsGUI(crate).getInventory();
    }

    public static Inventory getCrateItemSettingsGUI(Crate crate) {
        return new CrateItemSettingsGUI(crate).getInventory();
    }

    public static Inventory getCrateItemRewardsGUI(Crate crate) {
        return new CrateItemRewardsGUI(crate).getInventory();
    }

}
