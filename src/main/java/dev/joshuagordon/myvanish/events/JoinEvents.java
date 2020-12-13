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
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        // Hide vanished players from newly joined players
        for (Player vanishedPlayer : plugin.getVanishedPlayers()) {
            player.hidePlayer(plugin, vanishedPlayer);
        }
    }
}