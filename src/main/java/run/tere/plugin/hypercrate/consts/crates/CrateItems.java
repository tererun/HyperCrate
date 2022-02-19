package run.tere.plugin.hypercrate.consts.crates;

import org.bukkit.inventory.ItemStack;
import run.tere.plugin.hypercrate.apis.InventoryAPI;

import java.util.ArrayList;
import java.util.List;

public class CrateItems {
    private List<String> crateItems;

    public CrateItems() {
        crateItems = new ArrayList<>();
    }

    public void addCrateItem(ItemStack itemStack) {
        crateItems.add(InventoryAPI.getStringFromItemStack(itemStack));
    }

    public List<ItemStack> getCrateItems() {
        List<ItemStack> itemStacks = new ArrayList<>();
        for (String crateItem : crateItems) {
            itemStacks.add(InventoryAPI.getItemStackFromString(crateItem));
        }
        return itemStacks;
    }

    public void clearAll() {
        this.crateItems.clear();
    }
}
