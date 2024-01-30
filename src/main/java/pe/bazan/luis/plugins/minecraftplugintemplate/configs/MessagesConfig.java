package pe.bazan.luis.plugins.minecraftplugintemplate.configs;

import pe.bazan.luis.plugins.minecraftplugintemplate.MinecraftPluginTemplate;

import javax.annotation.Nullable;

public class MessagesConfig {
    private CustomConfig configFile;
    private MinecraftPluginTemplate plugin;

    public MessagesConfig(MinecraftPluginTemplate plugin) {
        this.plugin = plugin;
        registerConfig();
    }

    /**
     * Register configuration file or create it based on plugin resources
     */
    private void registerConfig() {
        configFile = new CustomConfig("configs/config.yml", plugin);
        configFile.registerConfig();
    }

    /**
     * Method for reload this config
     */
    public void reloadConfig() {
        configFile.reloadConfig();
    }

    public String get(String key) {
        return this.configFile.getConfig().getString(key, "ERROR TO GET MESSAGE: " + key);
    }
}
