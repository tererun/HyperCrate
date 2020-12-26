package run.tere.plugin.hypercrate.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import run.tere.plugin.hypercrate.HyperCrate;
import run.tere.plugin.hypercrate.consts.crates.Crate;
import run.tere.plugin.hypercrate.guis.HyperCrateSettingsGUI;

public class HyperCrateCommandClass implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("hypercrate")) {
            if (args.length == 0) {
                if (!sender.hasPermission("hypercrate.commands.help")) {
                    sender.sendMessage(HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("Permission_Error"));
                    return true;
                }
                sender.sendMessage("§7======= §f§l<§6HyperCrate§f§l> §7=======");
                sender.sendMessage("§e§lAuthor§f: tererun");
                sender.sendMessage("§e§lVersion§f: " + HyperCrate.getPlugin().getDescription().getVersion());
                sender.sendMessage("§e§lCommands§f:");
                sender.sendMessage("§a /hypercrate§f: Show this help");
                sender.sendMessage("§a /hypercrate givekey <Player> <CrateName>§f: Give player crate key");
                //sender.sendMessage("§a /hypercrate rollcrate <Player> <CrateName>§f: roll the crate");
                sender.sendMessage("§a /hypercrate settings§f: Open the GUI settings");
            } else {
                if (args[0].equalsIgnoreCase("givekey")) {
                    if (!sender.hasPermission("hypercrate.commands.getkey")) {
                        sender.sendMessage(HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("Permission_Error"));
                        return true;
                    }
                    if (args.length < 3) {
                        sender.sendMessage(HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("Wrong_Command"));
                        return true;
                    }

                    StringBuilder crateName = new StringBuilder();
                    for (int i=2; i<args.length; i++) {
                        crateName.append(args[i]);
                        if (i != (args.length - 1)) {
                            crateName.append(" ");
                        }
                    }
                    if (!HyperCrate.getCrateHandler().containsCrate(crateName.toString())) {
                        sender.sendMessage(HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("Crate_Not_Found"));
                        return true;
                    }
                    Crate crate = HyperCrate.getCrateHandler().getCrate(crateName.toString());
                    for (Entity entity : Bukkit.selectEntities(sender, args[1])) {
                        if (entity instanceof Player) {
                            Player player = (Player) entity;
                            crate.giveCrateKey(player);
                        }
                    }
                } else if (args[0].equalsIgnoreCase("settings")) {
                    if (!sender.hasPermission("hypercrate.commands.settings")) {
                        sender.sendMessage(HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("Permission_Error"));
                        return true;
                    }
                    if (!(sender instanceof Player)) {
                        sender.sendMessage(HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("Player_Command"));
                        return true;
                    }
                    Player player = (Player) sender;
                    player.openInventory(HyperCrateSettingsGUI.getSettingsGUI());
                } else {
                    sender.sendMessage(HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("Wrong_Command"));
                }
            }
            return true;
        }
        return false;
    }
}
