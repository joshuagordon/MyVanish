package dev.joshuagordon.myvanish.commands;

import dev.joshuagordon.myvanish.MyVanish;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FakeJoin implements CommandExecutor {

    private final MyVanish plugin;

    public FakeJoin(MyVanish instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        // Obtaining join message format from config
        String joinMessage = plugin.getConfig().getString("fakejoinMessage");
        String leaveMessage = plugin.getConfig().getString("fakeleaveMessage");
        String pluginPrefix = plugin.getConfig().getString("pluginPrefix");

        // No changes made, but save anyway just in case
        plugin.saveConfig();

        if (cmd.getName().equalsIgnoreCase("fakejoin")) {

            if (sender instanceof Player) {
                Player player = (Player) sender;
                // Player - assume the player wants to perform fake-join on themselves, unless otherwise specified.
                if (args.length == 0) {
                    // Username not specified, obtain sender's user
                    if(joinMessage != null) {
                        joinMessage = joinMessage.replace("%player%", player.getName());
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                pluginPrefix + "&r&a Join message broadcasted."));
                        Bukkit.broadcastMessage(joinMessage);
                        return true;
                    }
                    else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                pluginPrefix + "&r&c Join message null."));
                    }
                }
                else if (args.length == 1) {
                    // Username specified, use provided username
                    if(joinMessage != null) {
                        joinMessage = joinMessage.replace("%player%", args[0]);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                pluginPrefix + "&r&a Join message broadcasted."));
                        Bukkit.broadcastMessage(joinMessage);
                        return true;
                    }
                    else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                pluginPrefix + "&r&c Join message null."));
                    }
                }
            }
            else {
                // Console - Must specify username
                if (args.length == 0) {
                    // Username not specified, error message
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            pluginPrefix + "&r&c Please specify a username."));
                    return false;
                }
                else if (args.length == 1) {
                    // Username specified, use provided username
                    if(joinMessage != null) {
                        joinMessage = joinMessage.replace("%player%", args[0]);
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                pluginPrefix + "&r&a Join message broadcasted."));
                        Bukkit.broadcastMessage(joinMessage);
                        return true;
                    }
                    else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                pluginPrefix + "&r&c Join message null."));
                    }
                }
            }
        }

        if (cmd.getName().equalsIgnoreCase("fakeleave")) {

            if (sender instanceof Player) {
                Player player = (Player) sender;
                // Player - assume the player wants to perform fake-join on themselves, unless otherwise specified.
                if (args.length == 0) {
                    // Username not specified, obtain sender's user
                    if(leaveMessage != null) {
                        leaveMessage = leaveMessage.replace("%player%", player.getName());
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                pluginPrefix + "&r&a Leave message broadcasted."));
                        Bukkit.broadcastMessage(leaveMessage);
                        return true;
                    }
                    else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                pluginPrefix + "&r&c Leave message null."));
                    }
                }
                else if (args.length == 1) {
                    // Username specified, use provided username
                    if(leaveMessage != null) {
                        leaveMessage = leaveMessage.replace("%player%", args[0]);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                pluginPrefix + "&r&a Leave message broadcasted."));
                        Bukkit.broadcastMessage(leaveMessage);
                        return true;
                    }
                    else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                pluginPrefix + "&r&c Leave message null."));
                    }
                }
            }
            else {
                // Console - Must specify username
                if (args.length == 0) {
                    // Username not specified, error message
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            pluginPrefix + "&r&c Please specify a username."));
                    return false;
                }
                else if (args.length == 1) {
                    // Username specified, use provided username
                    if(leaveMessage != null) {
                        leaveMessage = leaveMessage.replace("%player%", args[0]);
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                pluginPrefix + "&r&c Leave message broadcasted."));
                        Bukkit.broadcastMessage(leaveMessage);
                        return true;
                    }
                    else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                pluginPrefix + "&r&c Leave message null."));
                    }
                }
            }
        }

        return false;
    }
}
