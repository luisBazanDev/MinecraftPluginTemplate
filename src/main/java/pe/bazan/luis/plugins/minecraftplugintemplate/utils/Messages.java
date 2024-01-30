package pe.bazan.luis.plugins.minecraftplugintemplate.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import pe.bazan.luis.plugins.minecraftplugintemplate.MinecraftPluginTemplate;
import pe.bazan.luis.plugins.minecraftplugintemplate.enums.MessageKey;

public class Messages {
    public static String getMessage(MessageKey key) {
        return MinecraftPluginTemplate.getInstance().getMessagesConfig().get(key.toString());
    }

    public Component formatMsg(String text) {
        return Component.text(text);
    }

    @Deprecated
    public Component formatOldColorsMsg(String text) {
        return Component.text(ChatColor.translateAlternateColorCodes('&', text));
    }
}
