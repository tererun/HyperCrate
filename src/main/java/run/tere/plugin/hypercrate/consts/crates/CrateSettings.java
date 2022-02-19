package run.tere.plugin.hypercrate.consts.crates;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import run.tere.plugin.hypercrate.HyperCrate;
import run.tere.plugin.hypercrate.apis.InventoryAPI;

import java.util.ArrayList;
import java.util.List;

public class CrateSettings {
    private Material blockMaterial;
    private String crateName;
    private String crateKey;
    private String crateKeyItem;
    private String rollFinishSound;
    private String displayItem;
    private List<String> holographics;

    public CrateSettings(Material blockMaterial, String crateName, String crateKey, String crateKeyItem, String rollFinishSound, String displayItem, List<String> holographics) {
        this.blockMaterial = blockMaterial;
        this.crateName = crateName;
        this.crateKey = crateKey;
        this.crateKeyItem = crateKeyItem;
        this.rollFinishSound = rollFinishSound;
        this.displayItem = displayItem;
        this.holographics = holographics;
    }

    public String getCrateName() {
        return crateName;
    }

    public String getCrateKey() {
        return crateKey;
    }

    public String getRollFinishSound() {
        return rollFinishSound;
    }

    public List<String> getHolographics() {
        return holographics;
    }

    public Material getBlockMaterial() {
        return blockMaterial;
    }

    public ItemStack getDisplayItem() {
        return InventoryAPI.getItemStackFromString(this.displayItem);
    }

    public String getDisplayItemString() {
        return this.displayItem;
    }

    public ItemStack getCrateKeyItem() {
        ItemStack itemStack = InventoryAPI.getItemStackFromString(this.crateKeyItem);
        itemStack = NBTEditor.set(itemStack, this.crateKey, "HyperCrateKey");
        return itemStack;
    }

    public void setBlockMaterial(Material blockMaterial) {
        this.blockMaterial = blockMaterial;
        Crate crate = HyperCrate.getCrateHandler().getCrateFromKey(crateKey);
        crate.updateBlockMaterial();
    }

    public void setCrateName(String crateName) {
        this.crateName = crateName;
    }

    public void setDisplayItem(ItemStack displayItemStack) {
        this.displayItem = InventoryAPI.getStringFromItemStack(displayItemStack);
        Crate crate = HyperCrate.getCrateHandler().getCrateFromKey(crateKey);
        crate.updateHolographics();
    }

    public void setCrateKeyItem(ItemStack itemStack) {
        this.crateKeyItem = InventoryAPI.getStringFromItemStack(itemStack);
    }

    public boolean isDeletePlaceHolder(String text) {
        return ((text.equalsIgnoreCase(" ")) || (text.equalsIgnoreCase("")));
    }

    public void setHolographic(int line, String text) {
        if (this.holographics.size() > line) {
            if (line < 0) {
                if (!isDeletePlaceHolder(text)) {
                    this.holographics.add(0, text);
                }
            } else {
                if (isDeletePlaceHolder(text)) {
                    if (this.holographics.size() == 1) {
                        this.holographics = new ArrayList<>();
                    } else {
                        this.holographics.remove(line);
                    }
                } else {
                    this.holographics.set(line, text);
                }
            }
        } else {
            if (!isDeletePlaceHolder(text)) {
                this.holographics.add(text);
            }
        }
        Crate crate = HyperCrate.getCrateHandler().getCrateFromKey(crateKey);
        crate.updateHolographics();
    }

    public void addHolographic(String text) {
        this.holographics.add(text);
        Crate crate = HyperCrate.getCrateHandler().getCrateFromKey(crateKey);
        crate.updateHolographics();
    }

    public void setHolographics(List<String> holographics) {
        this.holographics = holographics;
        Crate crate = HyperCrate.getCrateHandler().getCrateFromKey(crateKey);
        crate.updateHolographics();
    }

    public void setRollFinishSound(String rollFinishSound) {
        this.rollFinishSound = rollFinishSound;
    }

    public ItemStack getCrateBlock() {
        ItemStack itemStack = new ItemStack(this.blockMaterial, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§6§lCrate Block");
        itemStack.setItemMeta(itemMeta);
        itemStack = NBTEditor.set(itemStack, crateKey, "HyperCrateBlock");
        return itemStack;
    }
}
