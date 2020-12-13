package dev.joshuagordon.myvanish.commands;

import dev.joshuagordon.myvanish.MyVanish;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FakeJoin implements CommandExecutor {

    // Construct class with instance of Main for access to methods
    private final MyVanish plugin;
    public FakeJoin(MyVanish instance) { plugin = instance; }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        // Obtaining join message format from config
        String joinMessage = plugin.getConfig().getString("fakejoinMessage");
        String leaveMessage = plugin.getConfig().getString("fakeleaveMessage");
        String pluginPrefix = plugin.getConfig().getString("pluginPrefix");

        // No changes made, but save anyway just in case
        plugin.saveConfig();

        // Check command
        if (cmd.getName().equalsIgnoreCase("fakejoin")) {
            // Check if sender is player
            if (sender instanceof Player) {
                // Sender is player, cast to Player
                Player player = (Player) sender;
                // Player - assume the player wants to perform fake-join on themselves, unless otherwise specified.
                if (args.length == 0) {
                    // Username not specified, obtain sender's user
                    if(joinMessage != null) {
                        // Replace %player% with actual player username
                        joinMessage = joinMessage.replace("%player%", player.getName());
                        // Send message to sender
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                pluginPrefix + "&r&a Join message broadcasted."));
                        // Broadcast fake join message
                        Bukkit.broadcastMessage(joinMessage);
                        return true;
                    }
                    else {
                        // Notify sender that join message was null
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                pluginPrefix + "&r&c Join message null."));
                    }
                }
                else if (args.length == 1) {
                    // Username specified, use provided username
                    if(joinMessage != null) {
                        // Replace %player% with specified player username
                        joinMessage = joinMessage.replace("%player%", args[0]);
                        // Send message to sender
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                pluginPrefix + "&r&a Join message broadcasted."));
                        // Broadcast fake join message
                        Bukkit.broadcastMessage(joinMessage);
                        return true;
                    }
                    else {
                        // Notify sender that join message was null
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                pluginPrefix + "&r&c Join message null."));
                        return false;
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
                        // Replace %player% with specified player username
                        joinMessage = joinMessage.replace("%player%", args[0]);
                        // Send message to sender
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                pluginPrefix + "&r&a Join message broadcasted."));
                        // Broadcast fake join message
                        Bukkit.broadcastMessage(joinMessage);
                        return true;
                    }
                    else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                pluginPrefix + "&r&c Join message null."));
                        return false;
                    }
                }
            }
        }

        // If command was /fakeleave
        if (cmd.getName().equalsIgnoreCase("fakeleave")) {
            // Check if sender is player
            if (sender instanceof Player) {
                // Sender is player, cast to Player
                Player player = (Player) sender;
                // Player - assume the player wants to perform fake-join on themselves, unless otherwise specified.
                if (args.length == 0) {
                    // Username not specified, obtain sender's user
                    if(leaveMessage != null) {
                        // Replace %player% with specified player username
                        leaveMessage = leaveMessage.replace("%player%", player.getName());
                        // Send message to player
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                pluginPrefix + "&r&a Leave message broadcasted."));
                        // Broadcast fake join message
                        Bukkit.broadcastMessage(leaveMessage);
                        return true;
                    }
                    else {
                        // Notify player that leave message is null
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                pluginPrefix + "&r&c Leave message null."));
                        return false;
                    }
                }
                else if (args.length == 1) {
                    // Username specified, use provided username
                    if(leaveMessage != null) {
                        // Replace %player% with specified player username
                        leaveMessage = leaveMessage.replace("%player%", args[0]);
                        // Send message to player
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                pluginPrefix + "&r&a Leave message broadcasted."));
                        // Broadcast fake join message
                        Bukkit.broadcastMessage(leaveMessage);
                        return true;
                    }
                    else {
                        // Notify player that leave message is null
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                pluginPrefix + "&r&c Leave message null."));
                        return false;
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
                        // Replace %player% with specified player username
                        leaveMessage = leaveMessage.replace("%player%", args[0]);
                        // Send message to sender
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                pluginPrefix + "&r&c Leave message broadcasted."));
                        // Broadcast fake join message
                        Bukkit.broadcastMessage(leaveMessage);
                        return true;
                    }
                    else {
                        // Notify sender that message was null
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                pluginPrefix + "&r&c Leave message null."));
                    }
                }
            }
        }

        return false;
    }
}
