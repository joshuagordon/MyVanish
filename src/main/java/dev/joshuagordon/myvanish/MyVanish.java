package dev.joshuagordon.myvanish;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import dev.joshuagordon.myvanish.commands.*;
import dev.joshuagordon.myvanish.events.*;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MyVanish extends JavaPlugin {

    // Minecraft Logger
    public final Logger logger = Logger.getLogger("Minecraft");
    // ArrayList for Vanished Players
    private final List<Player> vanishedPlayers = new ArrayList<>();
    // Obtain plugin prefix from config
    private final String pluginPrefix = getConfig().getString("pluginPrefix");

    // Getter and setters
    public List<Player> getVanishedPlayers () { return vanishedPlayers; }
    public void addVanishedPlayer(Player player) { vanishedPlayers.add(player); }
    public void removeVanishedPlayer(Player player) { vanishedPlayers.remove(player); }

    // Vanish list string builder
    public String vanishList() {
        StringBuilder list = new StringBuilder();

        list.append(ChatColor.translateAlternateColorCodes('&',
                pluginPrefix + "&7 Vanished Players: &f"));

        // Concatenate player usernames
        for (Player player : getVanishedPlayers()) {
            list.append(player.getName());
            list.append(" ");
        }
        return list.toString();
    }

    @Override
    public void onEnable() {
        // Register Commands
        Objects.requireNonNull(getCommand("v")).setExecutor(new jVanish(this));
        Objects.requireNonNull(getCommand("vanish")).setExecutor(new jVanish(this));
        Objects.requireNonNull(getCommand("fakejoin")).setExecutor(new FakeJoin(this));
        Objects.requireNonNull(getCommand("fakeleave")).setExecutor(new FakeJoin(this));
        Objects.requireNonNull(getCommand("myv")).setExecutor(new BaseCommands(this));

        // Config
        setupConfig(); // Custom method below

        // Register Event Listeners
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new AttackEvents(this), this);
        pm.registerEvents(new BlockEvents(this), this);
        pm.registerEvents(new ContainerEvents(this), this);
        pm.registerEvents(new ItemEvents(this), this);
        pm.registerEvents(new JoinEvents(this), this);
    }

    public void setupConfig() {
        // Create strings
        String fakejoinMessage = ChatColor.translateAlternateColorCodes('&',
                "&e%player% joined the game");
        String fakeleaveMessage = ChatColor.translateAlternateColorCodes('&',
                "&e%player% left the game");
        String pluginPrefix = ChatColor.translateAlternateColorCodes('&',
                "&8&l[&3&lMy&b&lVanish&8&l]&r");

        // Add default configs
        getConfig().addDefault("fakejoinMessage", fakejoinMessage);
        getConfig().addDefault("fakeleaveMessage", fakeleaveMessage);
        getConfig().addDefault("pluginPrefix", pluginPrefix);

        // Copy Defaults
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

}