package run.tere.plugin.hypercrate.guis;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import run.tere.plugin.hypercrate.HyperCrate;
import run.tere.plugin.hypercrate.apis.ItemStackAPI;
import run.tere.plugin.hypercrate.consts.crates.Crate;

import java.util.Arrays;

public class HyperCrateSettingsGUI {
    private static String key = "HyperCrateGUIItem";

    public static Inventory getSettingsGUI() {
        Inventory inventory = Bukkit.createInventory(null, 9, "§6§lHyperCrate Settings");
        inventory.setItem(2, ItemStackAPI.getItemStack(Material.BOOK, 1, HyperCrate.getLanguage().get("Crate_List"), null, key, "list"));
        inventory.setItem(6, ItemStackAPI.getItemStack(Material.PAPER, 1, HyperCrate.getLanguage().get("Create_Crate"), null, key, "create"));
        return inventory;
    }

    public static Inventory getCrateListGUI() {
        Inventory inventory = Bukkit.createInventory(null, 54, "§6§lHyperCrate Crate List");
        for (Crate crate : HyperCrate.getCrateHandler().getCrateList()) {
            inventory.addItem(ItemStackAPI.getItemStack(crate.getCrateSettings().getBlockMaterial(), 1, crate.getCrateSettings().getCrateName(), null, key, crate.getCrateSettings().getCrateKey()));
        }
        return inventory;
    }

    public static Inventory getCrateSettingGUI(Crate crate) {
        Inventory inventory = Bukkit.createInventory(null, 27, "§6§lHyperCrate Crate Settings");
        inventory.setItem(0, ItemStackAPI.getItemStack(Material.BARRIER, 1, HyperCrate.getLanguage().get("Delete_Crate"), null, key, "delete"));
        inventory.setItem(8, ItemStackAPI.getItemStack(Material.BOOK, 1, "§2§lINFO", Arrays.asList("§aCrateName§f:", crate.getCrateSettings().getCrateName(), "§aUUID§f:", crate.getCrateSettings().getCrateKey()), key, "info"));
        inventory.setItem(13, ItemStackAPI.getItemStack(Material.CHEST, 1, HyperCrate.getLanguage().get("Change_ItemRewards"), null, key, "items"));
        inventory.setItem(18, ItemStackAPI.getItemStack(Material.GRASS_BLOCK, 1, HyperCrate.getLanguage().get("Get_CrateBlock"), null, key, "getCrateBlock"));
        inventory.setItem(19, ItemStackAPI.getItemStack(Material.TRIPWIRE_HOOK, 1, HyperCrate.getLanguage().get("Get_CrateKey"), null, key, "getCrateKey"));
        inventory.setItem(21, ItemStackAPI.getItemStack(Material.NAME_TAG, 1, HyperCrate.getLanguage().get("Change_Name"), null, key, "name"));
        inventory.setItem(22, ItemStackAPI.getItemStack(Material.NOTE_BLOCK, 1, HyperCrate.getLanguage().get("Change_Sound"), null, key, "sound"));
        inventory.setItem(23, ItemStackAPI.getItemStack(Material.SPRUCE_SIGN, 1, HyperCrate.getLanguage().get("Change_Holographics"), null, key, "holo"));
        inventory.setItem(24, ItemStackAPI.getItemStack(Material.BEACON, 1, HyperCrate.getLanguage().get("Change_DisplayItem"), null, key, "displayItem"));
        inventory.setItem(25, ItemStackAPI.getItemStack(Material.IRON_PICKAXE, 1, HyperCrate.getLanguage().get("Change_Material"), null, key, "material"));
        inventory.setItem(26, ItemStackAPI.getItemStack(Material.PAPER, 1, HyperCrate.getLanguage().get("Change_CrateKey_Item"), null, key, "keyItem"));
        return inventory;
    }

    public static Inventory getCrateItemSettingsGUI(Crate crate) {
        Inventory inventory = Bukkit.createInventory(null, 54, "§6§lHyperCrate ItemRewards Settings§7 " + crate.getCrateSettings().getCrateKey());
        for (ItemStack crateItem : crate.getCrateItems().getCrateItems()) {
            inventory.addItem(crateItem);
        }
        return inventory;
    }

    public static Inventory getCrateItemRewardsGUI(Crate crate) {
        Inventory inventory = Bukkit.createInventory(null, 54, "§6§l" + crate.getCrateSettings().getCrateName() + "§6§l HC ItemRewards");
        for (ItemStack crateItem : crate.getCrateItems().getCrateItems()) {
            inventory.addItem(crateItem);
        }
        return inventory;
    }

}
