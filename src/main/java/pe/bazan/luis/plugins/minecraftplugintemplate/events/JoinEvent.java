package pe.bazan.luis.plugins.minecraftplugintemplate.events;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.getPlayer().sendMessage("Hello from the plugin!");
    }
}
