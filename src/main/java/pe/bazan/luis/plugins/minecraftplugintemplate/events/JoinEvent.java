package pe.bazan.luis.plugins.minecraftplugintemplate.events;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pe.bazan.luis.plugins.minecraftplugintemplate.enums.MessageKey;
import pe.bazan.luis.plugins.minecraftplugintemplate.utils.Messages;

public class JoinEvent implements Listener {
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.getPlayer().sendMessage(Messages.getMessage(MessageKey.WELCOME_MSG));
    }
}
