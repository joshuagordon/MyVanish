package dev.joshuagordon.myvanish.events;

import dev.joshuagordon.myvanish.MyVanish;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockEvents implements Listener {

    private final MyVanish plugin;
    public BlockEvents(MyVanish instance) { plugin = instance; }

    @EventHandler
    // Listen for block break events, cancel for vanished players
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        String pluginPrefix = plugin.getConfig().getString("pluginPrefix");

        // Check if player is vanished
        if(plugin.getVanishedPlayers().contains(player)) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    pluginPrefix + "&r&c You &c&lcannot&r&c break blocks in vanish."));
        }
    }

    @EventHandler
    // Listen for block place events, cancel for vanished players
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        String pluginPrefix = plugin.getConfig().getString("pluginPrefix");

        // Check if player is vanished
        if(plugin.getVanishedPlayers().contains(player)) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    pluginPrefix + "&r&c You &c&lcannot&r&c place blocks in vanish."));
        }
    }

    @EventHandler
    // Listen for interactions with doors / pressure plates / buttons / fence gates / levers and cancel
    // for vanished players.
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        String pluginPrefix = plugin.getConfig().getString("pluginPrefix");
        Block clickedBlock = event.getClickedBlock();

        // Check if player vanished
        if(plugin.getVanishedPlayers().contains(player)) {
            // Make sure air was not clicked
            if (clickedBlock != null) {
                Material clickedBlockType = clickedBlock.getType();
                // Check if door / pressure plate / button / fence / lever was clicked
                if (clickedBlockType.name().endsWith("DOOR") ||
                    clickedBlockType.name().endsWith("BUTTON") ||
                    clickedBlockType.name().endsWith("FENCE_GATE") ||
                    clickedBlockType == Material.LEVER) {

                    // Cancel event
                    event.setCancelled(true);
                    // Send message
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            pluginPrefix + "&r&c You &c&lcannot&r&c interact with this."));
                }
                // Same logic except no message for pressure plates, due to spam in chat
                else if (clickedBlockType.name().endsWith("PRESSURE_PLATE")) {
                    // Cancel event
                    event.setCancelled(true);
                }
            }
        }
    }
}
