package lol.dimensional.cheese.listeners;

import lol.dimensional.cheese.Cheese;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (Cheese.isCheeseBoy(event.getPlayer())) {
            Cheese.getInstance().stopTheCheese();;
        }

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (Cheese.isCheeseBoy(event.getPlayer())) {
            Cheese.getInstance().startTheCheese(event.getPlayer());
        }
    }
}
