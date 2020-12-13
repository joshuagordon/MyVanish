package dev.joshuagordon.myvanish.events;

// Import Main Class
import dev.joshuagordon.myvanish.MyVanish;
// Bukkit Imports
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

public class AttackEvents implements Listener {

    // Construct class with instance of Main class for access to Main methods
    private final MyVanish plugin;
    public AttackEvents(MyVanish instance) { plugin = instance; }

    @EventHandler
    // Listen for entity damage entity event
    public void onAttack(EntityDamageByEntityEvent event) {

        // Obtain plugin prefix from config
        String pluginPrefix = plugin.getConfig().getString("pluginPrefix");

        // Check if damager entity is player
        if(event.getDamager() instanceof Player) {

            // Store and cast damager entity to Player
            Player damager = (Player) event.getDamager();

            // Check if player is vanished
            if (plugin.getVanishedPlayers().contains(damager)) {

                // Cancel event
                event.setCancelled(true);

                // Send player message
                damager.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    pluginPrefix + "&r&c You &c&lcannot&r&c attack mobs / players in vanish."));
            }
        }
    }

    @EventHandler
    // Listen for Bow Shoot event
    public void onShoot(EntityShootBowEvent event) {

        // Obtain plugin prefix from config
        String pluginPrefix = plugin.getConfig().getString("pluginPrefix");

        // Check if shooter entity is player
        if(event.getEntity() instanceof Player) {

            // Store and cast shooter entity to Player
            Player shooter = (Player) event.getEntity();

            // Check if player is vanished
            if (plugin.getVanishedPlayers().contains(shooter)) {

                // Cancel event
                event.setCancelled(true);

                // Send player message
                shooter.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        pluginPrefix + "&r&c You &c&lcannot&r&c shoot bows in vanish."));
            }
        }
    }

    @EventHandler
    // Listen for mobs targetting vanished players
    public void onTarget(EntityTargetLivingEntityEvent event) {
        // Check if target is a player
        if (event.getTarget() instanceof Player) {
            // Cast target to player
            Player player = (Player) event.getTarget();

            // Check if player is vanished
            if(plugin.getVanishedPlayers().contains(player)) {
                // Cancel event
                event.setCancelled(true);
            }
        }
    }
}
