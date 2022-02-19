package run.tere.plugin.hypercrate.listeners;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import run.tere.plugin.hypercrate.HyperCrate;
import run.tere.plugin.hypercrate.consts.crates.Crate;
import run.tere.plugin.hypercrate.guis.HyperCrateSettingsGUI;
import run.tere.plugin.hypercrate.utils.ChatUtil;

import java.util.List;

public class HyperCrateInteractListener implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack itemStack = player.getInventory().getItem(player.getInventory().getHeldItemSlot());
        Block block = e.getClickedBlock();
        if ((e.useInteractedBlock().equals(Event.Result.DENY)) || (e.useInteractedBlock().equals(Event.Result.DENY))) return;
        if ((e.getHand() == null) || (!e.getHand().equals(EquipmentSlot.HAND)) || (block == null)) return;
        if (!HyperCrate.getCrateHandler().containsCrateFromLocation(block.getLocation())) {
            if (itemStack != null) {
                if (NBTEditor.contains(itemStack, "HyperCrateKey")) {
                    e.setCancelled(true);
                }
            }
            return;
        }
        Crate crate = HyperCrate.getCrateHandler().getCrateFromLocation(block.getLocation());
        if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if (player.isSneaking()) return;
            e.setCancelled(true);
            List<ItemStack> crateItemStacks = crate.getCrateItems().getCrateItems();
            if (crateItemStacks.isEmpty()) {
                ChatUtil.sendMessage(player, HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("Crate_Empty_Error"));
                return;
            }
            player.openInventory(HyperCrateSettingsGUI.getCrateItemRewardsGUI(crate));
        } else if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            e.setCancelled(true);
            if ((itemStack == null) || (!NBTEditor.contains(itemStack, "HyperCrateKey") || (!NBTEditor.getString(itemStack, "HyperCrateKey").equalsIgnoreCase(crate.getCrateSettings().getCrateKey())))) {
                ChatUtil.sendMessage(player, HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("CrateKey_Error"));
                return;
            }
            if (crate.roll(player)) {
                itemStack.setAmount(itemStack.getAmount() - 1);
            }
        }
    }
}
