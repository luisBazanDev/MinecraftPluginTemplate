package pe.bazan.luis.plugins.minecraftplugintemplate.configs;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public class CustomConfig {
    private final String fileName;
    private FileConfiguration fileConfiguration = null;
    private File file = null;
    private final JavaPlugin plugin;
    private boolean firstTime;

    public CustomConfig(String fileName, JavaPlugin plugin){
        this.fileName = fileName;
        this.plugin = plugin;
        this.firstTime = false;
    }

    public void registerConfig(){
        file = new File(plugin.getDataFolder(), fileName);
        if(!file.exists()){
            firstTime = true;
            this.getConfig().options().copyDefaults(true);
            saveConfig();
        }
    }

    public void saveConfig(){
        try{
            fileConfiguration.save(file);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig(){
        if(fileConfiguration == null){
            reloadConfig();
        }
        return fileConfiguration;
    }

    public void reloadConfig(){
        if(fileConfiguration == null){
            file = new File(plugin.getDataFolder(), fileName);
        }
        fileConfiguration = YamlConfiguration.loadConfiguration(file);

        if(firstTime){
            Reader defConfigStream;
            try{
                defConfigStream = new InputStreamReader(plugin.getResource(fileName), "UTF8");
                if(defConfigStream != null){
                    YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                    fileConfiguration.setDefaults(defConfig);
                }
            }catch(UnsupportedEncodingException e){
                e.printStackTrace();
            }
        }
    }
}
