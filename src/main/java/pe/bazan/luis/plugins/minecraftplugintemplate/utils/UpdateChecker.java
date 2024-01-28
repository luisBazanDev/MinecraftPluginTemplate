package pe.bazan.luis.plugins.minecraftplugintemplate.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pe.bazan.luis.plugins.minecraftplugintemplate.MinecraftPluginTemplate;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class UpdateChecker implements Listener {
    private String url = "https://api.spigotmc.org/legacy/update.php?resource=";
    private String id = "resourceId";
    private boolean isAvailable;

    public boolean isAvailable() {
        return isAvailable;
    }

    @EventHandler
    public void on(PlayerJoinEvent event) {
        if(event.getPlayer().isOp() && isAvailable)
            event.getPlayer().sendMessage(String.format("[%s] Update available message", MinecraftPluginTemplate.getInstance().getName()));
    }

    public void check() {
        isAvailable = checkUpdate();
    }

    private boolean checkUpdate() {
        MinecraftPluginTemplate.getInstance().getLogger().info("Check for updates...");
        try {
            String localVersion = MinecraftPluginTemplate.getInstance().getPluginMeta().getVersion();
            HttpsURLConnection connection = (HttpsURLConnection) new URL(url + id).openConnection();
            connection.setRequestMethod("GET");
            String raw = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();

            String remoteVersion;
            if(raw.contains("-")) {
                remoteVersion = raw.split("-")[0].trim();
            } else {
                remoteVersion = raw;
            }

            if(!localVersion.equalsIgnoreCase(remoteVersion))
                return true;
        } catch (IOException e) {
            return false;
        }
        return false;
    }
}
