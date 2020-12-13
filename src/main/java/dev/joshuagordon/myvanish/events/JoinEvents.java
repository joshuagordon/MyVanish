package dev.joshuagordon.myvanish.events;

import dev.joshuagordon.myvanish.MyVanish;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvents implements Listener {

    private final MyVanish plugin;
    public JoinEvents(MyVanish instance) { plugin = instance; }

    @EventHandler
    // Listen for Player Join Event
    public void onJoin(PlayerJoinEvent event) {
        // Hide vanished players from newly joined players
        for (Player vanishedPlayer : plugin.getVanishedPlayers()) {
            event.getPlayer().hidePlayer(plugin, vanishedPlayer);
        }
    }
}