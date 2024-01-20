package pe.bazan.luis.plugins.minecraftplugintemplate.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import pe.bazan.luis.plugins.minecraftplugintemplate.MinecraftPluginTemplate;
import pe.bazan.luis.plugins.minecraftplugintemplate.commands.test.TestCommand;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandHandler implements CommandExecutor, TabCompleter {
    private final MinecraftPluginTemplate plugin;
    private Map<String, Command> commands;

    public CommandHandler(MinecraftPluginTemplate plugin) {
        this.plugin = plugin;
        registerCommands();
    }


    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        // Grab the base and arguments.
        String base = (args.length > 0 ? args[0] : "").toLowerCase();
        String last = (args.length > 0 ? args[args.length - 1] : "").toLowerCase();

        if (base.equals("help") || base.equals("h")) {
            showHelp(sender);
            return true;
        }

        // If there's no base argument, show a helpful message.
        if (base.equals("")) {
            return true;
        }

        // Get all commands that match the base.
        List<Command> matches = getMatchingCommands(base);

        // If there's more than one match, display them.
        if (matches.size() < 1) {
            showHelp(sender);
            return true;
        }

        // Grab the only match.
        Command command = matches.get(0);
        CommandInfo info = command.getClass().getAnnotation(CommandInfo.class);

        // First check if the sender has permission.
        if (!sender.hasPermission(info.permission())) {
            sender.sendMessage(info.permission());
            return true;
        }

        // Otherwise, execute the command!
        String[] params = trimFirstArg(args);
        if (!command.execute(plugin, sender, params)) {
            showUsage(command, sender);
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        String base = (args.length > 0 ? args[0] : "").toLowerCase();

        if (base.equals("help")) {
            return null;
        }

        // List all commands
        if (base.equals("")) {
            return Stream.concat(
                        commands.values()
                            .stream()
                            .map(cmd -> cmd.getClass().getAnnotation(CommandInfo.class))
                            .filter(info -> info != null && sender.hasPermission(info.permission()))
                            .map(CommandInfo::name),
                        Stream.of("help")
                    )
                    .sorted()
                    .collect(Collectors.toList());
        }

        // Filter subcommands
        if (args.length == 1) {
            return commands.values().stream()
                    .map(cmd -> cmd.getClass().getAnnotation(CommandInfo.class))
                    .filter(info -> info.name().startsWith(base))
                    .map(CommandInfo::name)
                    .sorted()
                    .collect(Collectors.toList());
        }

        if (
                args.length > 1
                        && commands.get(args[0]) != null
                        && sender instanceof Player
        ) {
            return commands.get(args[0]).tab(plugin, (Player) sender, Arrays.copyOfRange(args, 1, args.length));
        }

        return null;
    }

    private String[] trimFirstArg(String[] args) {
        return Arrays.copyOfRange(args, 1, args.length);
    }

    private void showHelp(CommandSender sender) {
        StringBuilder test = new StringBuilder();
        StringBuilder user = new StringBuilder();

        for (Command command : commands.values()) {
            CommandInfo info = command.getClass().getAnnotation(CommandInfo.class);
            if (!sender.hasPermission(info.permission())) continue;

            StringBuilder builder;
            if (info.permission().startsWith(MinecraftPluginTemplate.pluginId + ".commands.test")) {
                builder = test;
            } else {
                builder = user;
            }

            builder.append("\n")
                    .append(ChatColor.RESET).append(info.usage()).append(" ")
                    .append(ChatColor.YELLOW).append(info.desc());
        }

        if (test.length() == 0) {
            sender.sendMessage("Commands:" + user);
        } else {
            sender.sendMessage("User commands:" + user);
            if (test.length() > 1) sender.sendMessage("Test commands:" + test);
        }
    }

    private List<Command> getMatchingCommands(String arg) {
        List<Command> result = new ArrayList<>();

        // Grab the commands that match the argument.
        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            if (arg.matches(entry.getKey())) {
                result.add(entry.getValue());
            }
        }

        return result;
    }

    private void showUsage(Command command, CommandSender sender) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                String.format("Usage: ", command.getClass().getAnnotation(CommandInfo.class).usage())
        ));
    }

    private void registerCommands() {
        commands = new HashMap<>();
        register(TestCommand.class);
    }

    /**
     * Register a command.
     *
     * @param cmd a Command
     */
    private void register(Class<? extends Command> cmd) {
        try {
            Command command = cmd.newInstance();
            register(command);
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException("Failed to instantiate Command class: " + cmd.getName(), e);
        }
    }

    /**
     * Register a command
     *
     * @param cmd a Command
     */
    public void register(Command cmd) {
        Class<?> cls = cmd.getClass();
        CommandInfo info = cls.getAnnotation(CommandInfo.class);
        if (info == null) {
            throw new IllegalArgumentException("Missing CommandInfo annotation on class " + cls.getName());
        }

        commands.put(info.pattern(), cmd);
    }
}
