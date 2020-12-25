package run.tere.plugin.hypercrate.consts.crates;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import run.tere.plugin.hypercrate.HyperCrate;
import run.tere.plugin.hypercrate.apis.InventoryAPI;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Crate {
    private HashSet<CrateLocation> crateLocations;
    private CrateItems crateItems;
    private CrateSettings crateSettings;

    public Crate(CrateSettings crateSettings) {
        this.crateLocations = new HashSet<>();
        this.crateItems = new CrateItems();
        this.crateSettings = crateSettings;
    }

    public CrateItems getCrateItems() {
        return this.crateItems;
    }

    public CrateSettings getCrateSettings() {
        return this.crateSettings;
    }

    public boolean roll(Player player) {
        Inventory playerInv = player.getInventory();
        List<ItemStack> crateItemStacks = this.crateItems.getCrateItems();
        if (crateItemStacks.isEmpty()) {
            player.sendMessage(HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("Crate_Empty_Error"));
            return false;
        }
        Collections.shuffle(crateItemStacks);
        player.playSound(player.getLocation(), this.crateSettings.getRollFinishSound(), 1F, 1F);
        if (InventoryAPI.isInventoryFull(playerInv)) {
            player.sendMessage(HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("Inventory_Full"));
            player.getWorld().dropItemNaturally(player.getLocation(), crateItemStacks.get(0));
        } else {
            player.sendMessage(HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("Gave_Item"));
            player.getInventory().addItem(crateItemStacks.get(0));
        }
        return true;
    }

    public void giveCrateKey(Player player) {
        Inventory playerInv = player.getInventory();
        ItemStack crateKeyItem = this.crateSettings.getCrateKeyItem();
        if (InventoryAPI.isInventoryFull(playerInv)) {
            player.sendMessage(HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("Inventory_Full"));
            player.getWorld().dropItemNaturally(player.getLocation(), crateKeyItem);
        } else {
            player.sendMessage(HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("Gave_Item"));
            player.getInventory().addItem(crateKeyItem);
        }
    }

    public void createBlock(Location location) {
        addCrateLocation(location);
        if (HyperCrate.isUseHolographicDisplays()) {
            Hologram hologram = HologramsAPI.createHologram(HyperCrate.getPlugin(), location.clone().add(0.5, 2.5, 0.5));
            for (int i = 0; i < crateSettings.getHolographics().size(); i++) {
                hologram.insertTextLine(i, crateSettings.getHolographics().get(i));
            }
            if ((!this.crateSettings.getDisplayItem().getType().equals(Material.BARRIER)) && (this.crateSettings.getDisplayItem().getType().isItem()) && (this.crateSettings.getDisplayItemString() != null)) {
                hologram.insertItemLine(hologram.size(), this.crateSettings.getDisplayItem());
            }
        }
    }

    public void breakBlock(Location location) {
        removeCrateLocation(location);
        if (HyperCrate.isUseHolographicDisplays()) {
            Location addedLocation = location.clone().add(0.5, 2.5, 0.5);
            for (Hologram hologram : HologramsAPI.getHolograms(HyperCrate.getPlugin())) {
                if ((addedLocation.getWorld().equals(hologram.getWorld())) && (addedLocation.getX() == hologram.getX()) && (addedLocation.getY() == hologram.getY()) && (addedLocation.getZ() == hologram.getZ())) {
                    hologram.delete();
                }
            }
        }
    }

    private void addCrateLocation(Location location) {
        if (containsCrateLocationByLocation(location)) return;
        CrateLocation crateLocation = CrateLocation.getCrateLocationFromLocation(location);
        this.crateLocations.add(crateLocation);
        HyperCrate.getCrateHandler().saveCrateHandler();
    }

    private void removeCrateLocation(Location location) {
        CrateLocation crateLocation = getCrateLocationByLocation(location);
        this.crateLocations.remove(crateLocation);
        HyperCrate.getCrateHandler().saveCrateHandler();
    }

    public CrateLocation getCrateLocationByLocation(Location location) {
        if (this.crateLocations.isEmpty()) return null;
        for (CrateLocation crateLocation : this.crateLocations) {
            if ((crateLocation.getWorld().equalsIgnoreCase(location.getWorld().getUID().toString())) && (crateLocation.getX() == location.getBlockX()) && (crateLocation.getY() == location.getBlockY()) && (crateLocation.getZ() == location.getBlockZ())) {
                return crateLocation;
            }
        }
        return null;
    }

    public HashSet<CrateLocation> getCrateLocations() {
        return crateLocations;
    }

    public boolean containsCrateLocationByLocation(Location location) {
        return (getCrateLocationByLocation(location) != null);
    }

    public void delete() {
        HashSet<CrateLocation> deleteList = new HashSet<>(crateLocations);
        for (CrateLocation crateLocation : deleteList) {
            breakBlock(crateLocation.getLocation());
            crateLocation.getLocation().getBlock().setType(Material.AIR);
        }
        HyperCrate.getCrateHandler().removeCrate(this);
    }

    public void updateHolographics() {
        if (HyperCrate.isUseHolographicDisplays()) {
            for (CrateLocation crateLocation : this.crateLocations) {
                Location addedLocation = crateLocation.getLocation().clone().add(0.5, 2.5, 0.5);
                for (Hologram hologram : HologramsAPI.getHolograms(HyperCrate.getPlugin())) {
                    if ((addedLocation.getWorld().equals(hologram.getWorld())) && (addedLocation.getX() == hologram.getX()) && (addedLocation.getY() == hologram.getY()) && (addedLocation.getZ() == hologram.getZ())) {
                        hologram.clearLines();
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < crateSettings.getHolographics().size(); i++) {
                                    hologram.insertTextLine(i, crateSettings.getHolographics().get(i));
                                }
                                if ((!crateSettings.getDisplayItem().getType().equals(Material.BARRIER)) && (crateSettings.getDisplayItem().getType().isItem()) && (crateSettings.getDisplayItemString() != null)) {
                                    hologram.insertItemLine(hologram.size(), crateSettings.getDisplayItem());
                                }
                            }
                        }.runTask(HyperCrate.getPlugin());
                    }
                }
            }
        }
    }

    public void updateBlockMaterial() {
        for (CrateLocation crateLocation : this.crateLocations) {
            crateLocation.getLocation().getBlock().setType(this.crateSettings.getBlockMaterial());
        }
    }

}