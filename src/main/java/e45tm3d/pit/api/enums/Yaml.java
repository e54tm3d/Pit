package e45tm3d.pit.api.enums;

import e45tm3d.pit.ThePit;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;

public enum Yaml {

    BUFF("buff"), CURSE("curse"), ENCHANCE("enchance"), ITEMS("items"), WEAPON("weapon"), CONFIG("config"),
    MONSTER("monster"), WEAPON_UPDATE("weapon_update"), ARMOR("armor"), TRASH("trash"), BLOCKS("blocks"), MATERIAL("material"),
    MESSAGES("messages");

    String file;

    private File File;
    private FileConfiguration Config;

    Yaml(String file) {
        this.file = file;
    }

    private String getFile() {
        return file;
    }

    public void initConfig() {
        File dataFolder = ThePit.getInstance().getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        File = new File(dataFolder, getFile() + ".yml");

        if (!File.exists()) {
            try {
                InputStream in = ThePit.getInstance().getResource(getFile() + ".yml");
                if (in == null) {
                    ThePit.getInstance().getLogger().warning(getFile() + ".yml has not been found!");
                    return;
                }

                Files.copy(in, File.toPath());
                in.close();
                ThePit.getInstance().getLogger().info(getFile() + ".yml has been loaded!");
            } catch (IOException e) {
                ThePit.getInstance().getLogger().severe(getFile() + ".yml has not been loaded!");
                e.printStackTrace();
            }
        }

        Config = YamlConfiguration.loadConfiguration(File);
        
        // 自动补充缺失的配置
        try {
            InputStream defaultStream = ThePit.getInstance().getResource(getFile() + ".yml");
            if (defaultStream != null) {
                YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
                
                // 检查并添加缺失的配置节点
                boolean configUpdated = false;
                for (String key : defaultConfig.getKeys(true)) {
                    if (!Config.contains(key)) {
                        Config.set(key, defaultConfig.get(key));
                        configUpdated = true;
                    }
                }
                
                // 如果有更新，保存配置
                if (configUpdated) {
                    saveConfig();
                    ThePit.getInstance().getLogger().info(getFile() + ".yml has been updated!");
                }
                
                defaultStream.close();
            }
        } catch (IOException e) {
            ThePit.getInstance().getLogger().severe(getFile() + ".yml has not been loaded!");
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        if (Config == null) {
            initConfig();
        }
        return Config;
    }

    public void saveConfig() {
        if (Config == null || File == null) {
            return;
        }
        try {
            getConfig().save(File);
        } catch (IOException e) {
            ThePit.getInstance().getLogger().severe(getFile() + ".yml has not been saved!");
        }
    }

    public void reloadConfig() {
        if (File == null) {
            File = new File(ThePit.getInstance().getDataFolder(), getFile() + ".yml");
        }
        Config = YamlConfiguration.loadConfiguration(File);

        java.io.InputStream defaultStream = ThePit.getInstance().getResource(getFile() + ".yml");
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            Config.setDefaults(defaultConfig);
        }
    }

}