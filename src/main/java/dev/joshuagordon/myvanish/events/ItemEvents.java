package dev.joshuagordon.myvanish.events;

import dev.joshuagordon.myvanish.MyVanish;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ItemEvents implements Listener {

    private final MyVanish plugin;
    public ItemEvents (MyVanish instance) { plugin = instance; }

    @EventHandler
    // Listen for Entity Item Pickup Event
    public void onPickup(EntityPickupItemEvent event) {
        // Check if entity is player
        if (event.getEntity() instanceof Player) {
            // Cast entity to player
            Player player = (Player) event.getEntity();

            // Cancel event for vanished players
            if (plugin.getVanishedPlayers().contains(player))
                event.setCancelled(true);
        }
    }

    @EventHandler
    // Listen for Entity Item Drop Event
    public void onDrop(PlayerDropItemEvent event) {
        // Cancel event for vanished players
        if (plugin.getVanishedPlayers().contains(event.getPlayer()))
            event.setCancelled(true);
    }
}
