package run.tere.plugin.hypercrate.listeners;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import run.tere.plugin.hypercrate.HyperCrate;
import run.tere.plugin.hypercrate.consts.crates.Crate;
import run.tere.plugin.hypercrate.utils.ChatUtil;

public class HyperCrateBlockListener implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        Block block = e.getBlock();
        Location blockLocation = block.getLocation();
        ItemStack itemStack = e.getItemInHand();
        if (NBTEditor.contains(itemStack, "HyperCrateKey")) {
            e.setCancelled(true);
            ChatUtil.sendMessage(player, HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("Cannot_Place_Key"));
            return;
        }
        if ((!NBTEditor.contains(itemStack, "HyperCrateBlock")) || (!HyperCrate.getCrateHandler().containsCrateFromKey(NBTEditor.getString(itemStack, "HyperCrateBlock")))) return;
        if (!player.hasPermission("hypercrate.actions.place")) {
            e.setCancelled(true);
            ChatUtil.sendMessage(player, HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("Permission_Error"));
            return;
        }
        Crate crate = HyperCrate.getCrateHandler().getCrateFromKey(NBTEditor.getString(itemStack, "HyperCrateBlock"));
        crate.createBlock(blockLocation);
        ChatUtil.sendMessage(player, HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("Create_Block"));
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Block block = e.getBlock();
        Location blockLocation = block.getLocation();
        if (!HyperCrate.getCrateHandler().containsCrateFromLocation(blockLocation)) return;
        Crate crate = HyperCrate.getCrateHandler().getCrateFromLocation(blockLocation);
        if (!player.hasPermission("hypercrate.actions.break")) {
            e.setCancelled(true);
            ChatUtil.sendMessage(player, HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("Permission_Error"));
            return;
        }
        if (!player.getGameMode().equals(GameMode.CREATIVE)) {
            e.setCancelled(true);
            ChatUtil.sendMessage(player, HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("Not_Creative_Error"));
            return;
        }
        if (!player.isSneaking()) {
            e.setCancelled(true);
            ChatUtil.sendMessage(player, HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("Sneak_Break"));
            return;
        }
        crate.breakBlock(blockLocation);
        ChatUtil.sendMessage(player, HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("Break_Block"));
    }
}
