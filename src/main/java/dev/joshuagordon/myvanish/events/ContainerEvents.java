package dev.joshuagordon.myvanish.events;

import dev.joshuagordon.myvanish.MyVanish;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class ContainerEvents implements Listener {

    private final MyVanish plugin;
    public ContainerEvents(MyVanish instance) { plugin = instance; }
    private final ArrayList<Player> hasFakeInvOpen = new ArrayList<>();

    @EventHandler
    // Listen for player interact event, especially those with a container
    public void silentContainer(PlayerInteractEvent event) {

        // Store player and clicked block in variables for simplicity
        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();

        // Check if player vanished
        if(plugin.getVanishedPlayers().contains(player)) {
            // Check clicked block is not null
            if(clickedBlock != null) {
                Material clickedBockType = clickedBlock.getType();
                // Check if clicked block is a container
                if (clickedBockType == Material.CHEST
                    || clickedBockType == Material.TRAPPED_CHEST
                    || clickedBockType == Material.ENDER_CHEST
                    || clickedBockType.name().endsWith("SHULKER_BOX") // Should cover all shulker boxes
                    || clickedBockType == Material.BARREL) {

                    // Cancel open
                    event.setCancelled(true);

                    // Duplicate chest into separate inventory
                    Inventory inv = null;
                    Inventory sinv;

                    if (clickedBockType == Material.CHEST ||
                        clickedBockType == Material.TRAPPED_CHEST) {

                        // Create chest
                        Chest chest = (Chest) clickedBlock.getState();
                        // Set inv to chest inventory
                        inv = chest.getBlockInventory();

                        // Create duplicate inventory and sent to inv contents
                        sinv = Bukkit.createInventory(player, inv.getSize());
                        sinv.setContents(inv.getContents());

                        // Show duplicated inv to player
                        player.openInventory(sinv);
                    }
                    else if (clickedBockType == Material.ENDER_CHEST) {
                        // Show player's own enderchest to them
                        player.openInventory(player.getEnderChest());
                    }
                    else if (clickedBockType.name().endsWith("SHULKER_BOX")) {

                        // Create chest
                        ShulkerBox chest = (ShulkerBox) clickedBlock.getState();
                        // Set inv to chest inventory
                        inv = chest.getInventory();

                        // Create duplicate inventory and sent to inv contents
                        sinv = Bukkit.createInventory(player, inv.getSize());
                        sinv.setContents(inv.getContents());

                        // Show duplicated inv to player
                        player.openInventory(sinv);
                    }
                    else if (clickedBockType == Material.BARREL) {

                        // Create chest
                        Barrel chest = (Barrel) clickedBlock.getState();
                        // Set inv to chest inventory
                        inv = chest.getInventory();

                        // Create duplicate inventory and sent to inv contents
                        sinv = Bukkit.createInventory(player, inv.getSize());
                        sinv.setContents(inv.getContents());

                        // Show duplicated inv to player
                        player.openInventory(sinv);
                    }

                    if (inv != null) {

                        // Add player to ArrayList for tracking
                        hasFakeInvOpen.add(player);
                    }
                }
            }
        }
    }

    @EventHandler
    // Listen for Inventory Close Event
    public void onClose(InventoryCloseEvent event) {

        // Check if player is player
        if (event.getPlayer() instanceof Player) {
            Player player = (Player) event.getPlayer();

            // Check if player is vanished and hasFakeInvOpen
            if (    plugin.getVanishedPlayers().contains(player) &&
                    hasFakeInvOpen.contains(player)) {

                // Remove player from hasFakeInvOpen
                hasFakeInvOpen.remove(player);
            }
        }
    }

    @EventHandler
    // Listen for Inventory Click Event
    public void onClick(InventoryClickEvent event) {

        // Check if clicker is player
        if(event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();

            // Check if player is vanished and hasFakeInvOpen
            if (    plugin.getVanishedPlayers().contains(player) &&
                    hasFakeInvOpen.contains(player)) {

                // Prevent editing to avoid item duplication
                event.setCancelled(true);
            }
        }
    }
}
