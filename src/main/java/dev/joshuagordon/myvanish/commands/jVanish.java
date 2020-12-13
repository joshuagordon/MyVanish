package dev.joshuagordon.myvanish.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.joshuagordon.myvanish.MyVanish;

public class jVanish implements CommandExecutor {

    private final MyVanish plugin;

    public jVanish(MyVanish instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        String pluginPrefix = plugin.getConfig().getString("pluginPrefix");
        Server server = plugin.getServer();

        if(sender instanceof Player) {

            Player player = (Player) sender;

            // Check if the /v or /vanish commands were used
            if(     cmd.getName().equalsIgnoreCase("v") ||
                    cmd.getName().equalsIgnoreCase("vanish")) {

                // Checks that no arguments were specified
                if(args.length == 0) {
                    // Check if the player is vanished
                    if (plugin.getVanishedPlayers().contains(player)) {

                        // unvanish the player to all online players
                        for (Player allPlayers : Bukkit.getServer().getOnlinePlayers()) {
                            allPlayers.showPlayer(plugin, player);
                        }

                        // Send message to player
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                pluginPrefix + "&r&a You appeared out of thin air!"));

                        // Exit god and fly mode
                        player.setAllowFlight(false);
                        player.setFlying(false);
                        player.setInvulnerable(false);

                        // Remove player from vanish list
                        plugin.removeVanishedPlayer(player);
                    }
                    else {
                        // If player is not in vanish list, hide them from all online players
                        for (Player allPlayers : Bukkit.getServer().getOnlinePlayers()) {
                            allPlayers.hidePlayer(plugin, player);
                        }

                        // Send player a message
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                pluginPrefix + "&r&a You disappeared!"));

                        // Enter god and fly mode
                        player.setAllowFlight(true);
                        player.setFlying(true);
                        player.setInvulnerable(true);

                        // Add player to vanish list
                        plugin.addVanishedPlayer(player);

                        // Action Bar Message
                        String message = "§a§lYou are invisible to other players!";

                        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, (runnable) -> {
                            if (plugin.getVanishedPlayers().contains(player)) {
                                player.sendActionBar(message);
                            }
                            else { // no longer vanished
                                runnable.cancel();
                                return;
                            }
                        }, 0, 40);
                    }
                }

                // If command had argument and it was "list"
                else if(args.length == 1 && args[0].equalsIgnoreCase("list")) {
                    // Send player the vanish list /v list (or /vanish list)
                    player.sendMessage(plugin.vanishList());
                }
            }

            return true;
        }
        else {
            if(args.length == 0) {
                // Sender was console, this command was not supported for console use.
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        pluginPrefix + "&r&c This command &c&lcannot&r&c be used from the console."));

                return false;
            }
            else if(args.length == 1 && args[0].equalsIgnoreCase("list")) {
                // Send console the vanish list /v list (or /vanish list)
                sender.sendMessage(plugin.vanishList());

                return true;
            }
        }
        return false;
    }

}
