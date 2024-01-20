package pe.bazan.luis.plugins.minecraftplugintemplate.commands.test;


import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pe.bazan.luis.plugins.minecraftplugintemplate.MinecraftPluginTemplate;
import pe.bazan.luis.plugins.minecraftplugintemplate.commands.Command;
import pe.bazan.luis.plugins.minecraftplugintemplate.commands.CommandInfo;

import java.util.ArrayList;
import java.util.List;

@CommandInfo(
        name = "test",
        pattern = "test",
        usage = "/plugin-command test <player>",
        desc = "Only a test command",
        permission = "MinecraftPluginTemplate.commands.test.test"
)
public class TestCommand implements Command {
    @Override
    public boolean execute(MinecraftPluginTemplate plugin, CommandSender sender, String... args) {
        if (args.length == 0) {
            sender.sendMessage("Hello world");
        } else {
            sender.sendMessage("Hello " + args[0]);
        }
        return true;
    }

    @Override
    public List<String> tab(MinecraftPluginTemplate plugin, Player player, String... args) {
        List<String> complete = new ArrayList<>();
        if (args.length == 1) {
            for (String string : Bukkit.getOnlinePlayers().stream().map(x -> x.getName().toLowerCase()).toList()) {
                if (string.startsWith(args[0])) complete.add(string);
            }
            return complete;
        }
        return complete;
    }
}
