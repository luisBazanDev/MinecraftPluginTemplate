package pe.bazan.luis.plugins.minecraftplugintemplate;

import org.bukkit.plugin.java.JavaPlugin;
import pe.bazan.luis.plugins.minecraftplugintemplate.commands.CommandHandler;
import pe.bazan.luis.plugins.minecraftplugintemplate.configs.MainConfig;
import pe.bazan.luis.plugins.minecraftplugintemplate.configs.MessagesConfig;
import pe.bazan.luis.plugins.minecraftplugintemplate.events.JoinEvent;
import pe.bazan.luis.plugins.minecraftplugintemplate.utils.UpdateChecker;

public final class MinecraftPluginTemplate extends JavaPlugin {
    private static MinecraftPluginTemplate instance;
    public static String pluginId = "MinecraftPluginTemplate";
    private UpdateChecker updateChecker = new UpdateChecker();
    private MainConfig mainConfig;
    private MessagesConfig messagesConfig;
    private CommandHandler commandHandler;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        loadConfigs();
        setUpCommandHandler();

        updateChecker.check();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    // -- Plugin methods

    /**
     * Load all configs
     */
    private void loadConfigs() {
        mainConfig = new MainConfig(this);
        messagesConfig = new MessagesConfig(this);
    }

    /**
     * Start a command handler
     */
    private void setUpCommandHandler() {
        commandHandler = new CommandHandler(this);
        getCommand("plugin-command").setExecutor(commandHandler);
        getCommand("plugin-command").setTabCompleter(commandHandler);
    }

    /**
     * Register events of the plugin
     */
    private void registerEvents() {
        getServer().getPluginManager().registerEvents(updateChecker, this);
        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
    }

    // --- Getters and Setters

    /**
     * Singleton pattern
     * @return Unique instance of the plugin
     */
    public static MinecraftPluginTemplate getInstance() {
        return instance;
    }

    public MainConfig getMainConfig() {
        return mainConfig;
    }

    public MessagesConfig getMessagesConfig() {
        return messagesConfig;
    }
}
