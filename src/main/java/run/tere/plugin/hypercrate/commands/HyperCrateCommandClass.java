package run.tere.plugin.hypercrate.commands;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import run.tere.plugin.hypercrate.HyperCrate;
import run.tere.plugin.hypercrate.consts.crates.Crate;
import run.tere.plugin.hypercrate.consts.languages.Language;
import run.tere.plugin.hypercrate.guis.HyperCrateSettingsGUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HyperCrateCommandClass implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("hypercrate")) {
            if (args.length == 0) {
                if (!sender.hasPermission("hypercrate.commands.help")) {
                    sender.sendMessage(HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("Permission_Error"));
                    return true;
                }
                sendHelpMessage(sender);
            } else {
                if (args[0].equalsIgnoreCase("givekey")) {
                    if (!sender.hasPermission("hypercrate.commands.givekey")) {
                        sender.sendMessage(HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("Permission_Error"));
                        return true;
                    }
                    if (args.length < 4 || !NumberUtils.isDigits(args[2])) {
                        sender.sendMessage(HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("Wrong_Command"));
                        return true;
                    }
                    int amount = Integer.parseInt(args[2]);
                    StringBuilder crateName = new StringBuilder();
                    for (int i = 3; i<args.length; i++) {
                        crateName.append(args[i]);
                        if (i != (args.length - 1)) {
                            crateName.append(" ");
                        }
                    }
                    String crateNameString = ChatColor.translateAlternateColorCodes('&', crateName.toString());
                    if (!HyperCrate.getCrateHandler().containsCrate(crateNameString)) {
                        sender.sendMessage(HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("Crate_Not_Found"));
                        return true;
                    }
                    Crate crate = HyperCrate.getCrateHandler().getCrate(crateNameString);
                    for (Entity entity : Bukkit.selectEntities(sender, args[1])) {
                        if (entity instanceof Player) {
                            Player player = (Player) entity;
                            crate.giveCrateKey(player, amount);
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
                } else if (args[0].equalsIgnoreCase("help")) {
                    if (!sender.hasPermission("hypercrate.commands.help")) {
                        sender.sendMessage(HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("Permission_Error"));
                        return true;
                    }
                    sendHelpMessage(sender);
                } else if (args[0].equalsIgnoreCase("reload")) {
                    if (!sender.hasPermission("hypercrate.commands.reload")) {
                        sender.sendMessage(HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("Permission_Error"));
                        return true;
                    }
                    sender.sendMessage(HyperCrate.getLanguage().get("Prefix") + " §7§oReloading...");
                    HyperCrate.getPlugin().reloadConfig();
                    HyperCrate.setConfigruation(HyperCrate.getPlugin().getConfig());
                    HyperCrate.getConfigLanguage().reloadConfig();
                    HyperCrate.setLanguage(new Language());
                    sender.sendMessage(HyperCrate.getLanguage().get("Prefix") + " §a§oReload Complete!");
                } else {
                    sender.sendMessage(HyperCrate.getLanguage().get("Prefix") + " " + HyperCrate.getLanguage().get("Wrong_Command"));
                }
            }
            return true;
        }
        return false;
    }

    private void sendHelpMessage(CommandSender sender) {
        sender.sendMessage("§7======= §f§l<§6HyperCrate§f§l> §7=======");
        sender.sendMessage("§e§l" + HyperCrate.getLanguage().get("CommandHelp_Author") + "§f: tererun");
        sender.sendMessage("§e§l" + HyperCrate.getLanguage().get("CommandHelp_Version") + "§f: " + HyperCrate.getPlugin().getDescription().getVersion());
        sender.sendMessage("§e§l" + HyperCrate.getLanguage().get("CommandHelp_Commands") + "§f:");
        sender.sendMessage("§a /hypercrate§f: "  + HyperCrate.getLanguage().get("CommandHelp_Help"));
        sender.sendMessage("§a /hypercrate help§f: " + HyperCrate.getLanguage().get("CommandHelp_Help"));
        sender.sendMessage("§a /hypercrate givekey <Player> <Amount> <CrateName>§f: " + HyperCrate.getLanguage().get("CommandHelp_GiveKey"));
        sender.sendMessage("§a /hypercrate settings§f: " + HyperCrate.getLanguage().get("CommandHelp_Settings"));
        sender.sendMessage("§a /hypercrate reload§f: " + HyperCrate.getLanguage().get("CommandHelp_Reload"));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!command.getName().equalsIgnoreCase("hypercrate")) {
            return HyperCrate.getPlugin().onTabComplete(sender, command, alias, args);
        }
        List<String> tabList = new ArrayList<>();
        if (sender.hasPermission("hypercrate.commands.help")) tabList.add("help");
        if (sender.hasPermission("hypercrate.commands.givekey")) tabList.add("givekey");
        if (sender.hasPermission("hypercrate.commands.settings")) tabList.add("settings");
        if (sender.hasPermission("hypercrate.commands.reload")) tabList.add("reload");
        if (args.length == 1) {
            if (args[0].isEmpty()) {
                List<String> list = new ArrayList<>();
                if (sender.hasPermission("hypercrate.commands.help")) list.add("help");
                if (sender.hasPermission("hypercrate.commands.givekey")) list.add("givekey");
                if (sender.hasPermission("hypercrate.commands.settings")) list.add("settings");
                if (sender.hasPermission("hypercrate.commands.reload")) list.add("reload");
                return list;
            }
            List<String> list = new ArrayList<>();
            for (String name : tabList) {
                if (name.startsWith(args[0])) {
                    list.add(name);
                }
            }
            return list;
        } else {
            if (tabList.contains(args[0])) {
                if (args[0].equalsIgnoreCase("givekey")) {
                    if (sender.hasPermission("hypercrate.commands.givekey")) {
                        if (args.length == 2) {
                            List<String> playerNames = new ArrayList<>();
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                playerNames.add(player.getName());
                            }
                            if (args[1].isEmpty()) {
                                List<String> tabListArgsOne = new ArrayList<>();
                                tabListArgsOne.add("@a");
                                tabListArgsOne.add("@e");
                                tabListArgsOne.add("@p");
                                tabListArgsOne.add("@r");
                                tabListArgsOne.add("@s");
                                tabListArgsOne.addAll(playerNames);
                                return tabListArgsOne;
                            } else {
                                if ("@a".startsWith(args[1])) {
                                    return Collections.singletonList("@a");
                                } else if ("@e".startsWith(args[1])) {
                                    return Collections.singletonList("@e");
                                } else if ("@p".startsWith(args[1])) {
                                    return Collections.singletonList("@p");
                                } else if ("@r".startsWith(args[1])) {
                                    return Collections.singletonList("@r");
                                } else if ("@s".startsWith(args[1])) {
                                    return Collections.singletonList("@s");
                                } else {
                                    for (String playerName : playerNames) {
                                        if (playerName.startsWith(args[1])) {
                                            return Collections.singletonList(playerName);
                                        }
                                    }
                                }
                            }
                        } else if (args.length == 3) {
                            return Arrays.asList("1", "64");
                        } else if (args.length == 4) {
                            List<String> crateNames = new ArrayList<>();
                            for (Crate crate : HyperCrate.getCrateHandler().getCrateList()) {
                                crateNames.add(translateAlternateColorCodes(crate.getCrateSettings().getCrateName()));
                            }
                            if (args[3].isEmpty()) {
                                return crateNames;
                            } else {
                                for (String crateName : crateNames) {
                                    if (crateName.startsWith(args[3])) {
                                        return Collections.singletonList(crateName);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return HyperCrate.getPlugin().onTabComplete(sender, command, alias, args);
    }

    public static String translateAlternateColorCodes(String textToTranslate) {
        Validate.notNull(textToTranslate, "Cannot translate null text");
        char altColorChar = '§';
        char[] b = textToTranslate.toCharArray();
        for (int i = 0; i < b.length - 1; i++) {
            if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx".indexOf(b[i + 1]) > -1) {
                b[i] = '&';
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }
        return new String(b);
    }
}
