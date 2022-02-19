package run.tere.plugin.hypercrate.apis;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class ItemStackAPI {
    public static ItemStack getItemStack(Material material, int amount, String displayName, List<String> lore, String key, String value) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        if (lore != null) {
            itemMeta.setLore(lore);
        }
        itemStack.setItemMeta(itemMeta);
        itemStack = NBTEditor.set(itemStack, value, key);
        return itemStack;
    }

    public static String getDefaultCrateKey(String crateName, String crateKey) {
        ItemStack itemStack = new ItemStack(Material.PAPER, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§6§l" + crateName + " Key");
        itemMeta.setLore(Arrays.asList("§aYou can pull the crate by holding this key", "§aand right-clicking towards the crate!"));
        itemStack.setItemMeta(itemMeta);
        itemStack = NBTEditor.set(itemStack, crateKey, "HyperCrateKey");
        return InventoryAPI.getStringFromItemStack(itemStack);
    }
}
