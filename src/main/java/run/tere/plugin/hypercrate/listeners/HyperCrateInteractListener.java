package run.tere.plugin.hypercrate.listeners;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import run.tere.plugin.hypercrate.HyperCrate;
import run.tere.plugin.hypercrate.apis.NBTEditor;
import run.tere.plugin.hypercrate.consts.crates.Crate;

public class HyperCrateInteractListener implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack itemStack = player.getInventory().getItem(player.getInventory().getHeldItemSlot());
        Block block = e.getClickedBlock();
        if ((e.getHand() == null) || (!e.getHand().equals(EquipmentSlot.HAND)) || (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) || (block == null)) return;
        if (!HyperCrate.getCrateHandler().containsCrateFromLocation(block.getLocation())) return;
        Crate crate = HyperCrate.getCrateHandler().getCrateFromLocation(block.getLocation());
        e.setCancelled(true);
        if ((itemStack == null) || (!NBTEditor.contains(itemStack, "HyperCrateKey") || (!NBTEditor.getString(itemStack, "HyperCrateKey").equalsIgnoreCase(crate.getCrateSettings().getCrateKey())))) {
            player.sendMessage(HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("CrateKey_Error"));
            return;
        }
        if (crate.roll(player)) {
            itemStack.setAmount(itemStack.getAmount() - 1);
        }
    }
}
