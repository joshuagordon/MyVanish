package dev.joshuagordon.myvanish.commands;

// Import main class
import dev.joshuagordon.myvanish.MyVanish;
// Bukkit Imports
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BaseCommands implements CommandExecutor {

    // Construct class with instance of Main class for access to methods
    private final MyVanish plugin;
    public BaseCommands(MyVanish instance) { plugin = instance; }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        // Obtain plugin prefix from config
        String pluginPrefix = plugin.getConfig().getString("pluginPrefix");

        // Check command
        if (cmd.getName().equalsIgnoreCase("myv")) {
            // If 1 argument & that argument is "r" or "reload"
            if (args.length == 1 && (
                args[0].equalsIgnoreCase("reload") ||
                args[0].equalsIgnoreCase("r"))
                ) {

                // Reload config
                plugin.reloadConfig();

                // Notify sender of reload
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        pluginPrefix + "&r&a Reload successful."));

                return true;
            }

            return false;
        }

        return false;
    }

}
