package e45tm3d.pit;

import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.modules.buff.buffs.WolfTrainer;
import e45tm3d.pit.modules.commands.commands.*;
import e45tm3d.pit.modules.buff.Buffs;
import e45tm3d.pit.modules.commands.Commands;
import e45tm3d.pit.modules.commands.commands.menus.*;
import e45tm3d.pit.modules.curse.Curses;
import e45tm3d.pit.modules.curse.curses.BloodyCurse;
import e45tm3d.pit.modules.enchance.Enchances;
import e45tm3d.pit.modules.items.materials.Materials;
import e45tm3d.pit.modules.items.materials.items.Superconductor;
import e45tm3d.pit.modules.items.weapon.Weapons;
import e45tm3d.pit.modules.listeners.Listeners;
import e45tm3d.pit.modules.listeners.player.ArrowLoader;
import e45tm3d.pit.modules.monsters.Monsters;
import e45tm3d.pit.modules.tasks.Tasks;
import e45tm3d.pit.storage.MySQL;
import e45tm3d.pit.storage.SQLite;
import e45tm3d.pit.utils.functions.DatabaseFunction;
import e45tm3d.pit.utils.lists.MonsterLists;
import e45tm3d.pit.utils.maps.BlocksMaps;
import e45tm3d.pit.utils.maps.PlayerMaps;
import me.clip.placeholderapi.PlaceholderAPI;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class ThePit extends JavaPlugin {

    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;
    public static final String minecraft_server_version = getMinecraftVersion();

    public static ThePit instance;

    @Override
    public void onEnable() {



        instance = this;

        Yaml.CONFIG.initConfig();
        Yaml.BUFF.initConfig();
        Yaml.CURSE.initConfig();
        Yaml.ENCHANCE.initConfig();
        Yaml.WEAPON.initConfig();
        Yaml.WEAPON_UPDATE.initConfig();
        Yaml.ARMOR.initConfig();
        Yaml.TRASH.initConfig();
        Yaml.BLOCKS.initConfig();
        Yaml.MESSAGES.initConfig();
        Yaml.MATERIAL.initConfig();

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new Placeholders().register();
        }

        if (Yaml.CONFIG.getConfig().getString("storage.type").equals("mysql")) {
            MySQL.createTable();
        } else if (Yaml.CONFIG.getConfig().getString("storage.type").equals("sqlite")) {
            SQLite.createTable();
        }

        if (!setupEconomy() ) {
            getLogger().severe(String.format("Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupPermissions();
        setupChat();

        new Tasks();
        new Listeners();
        new Weapons();
        new Buffs();
        new Curses();
        new Enchances();
        new Monsters();
        new Materials();
        new Commands();

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.closeInventory();
            DatabaseFunction.setupDatabaseInRam(p);
        }

        getLogger().info("Checking server version: " + minecraft_server_version);

    }

    @Override
    public void onDisable() {

        ArrowLoader.restoreAllPlayerItemSlots();

        for (Location b : BlocksMaps.placed.keySet().stream().toList()) {
            if (BlocksMaps.placed.containsKey(b)) {
                if (!b.getBlock().isEmpty()) {
                    b.getBlock().setType(BlocksMaps.original_block.get(b), true);
                    b.getBlock().setData(BlocksMaps.original_block_data.get(b).getData());
                }
                BlocksMaps.placed.remove(b);
                BlocksMaps.original_block.remove(b);
            }
        }
        getLogger().info("ThePit disabled successfully");

        for (Player p : Bukkit.getOnlinePlayers()) {

            p.kickPlayer("Server is restatring or disabling...");

            p.closeInventory();

            DatabaseFunction.saveDatabase(p);
        }

        for (Entity entities : MonsterLists.entities) {
            entities.remove();
        }

        PlayerMaps.clearAllMaps();

    }

    public static ThePit getInstance() {
        return instance;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    public static Economy getEconomy() {
        return econ;
    }

    public static Permission getPermissions() {
        return perms;
    }

    public static Chat getChat() {
        return chat;
    }

    public static String setPlaceholderAPI(OfflinePlayer p, String str) {
        return PlaceholderAPI.setPlaceholders(p, str);
    }

    private static String getMinecraftVersion() {
        String bukkitVersion = Bukkit.getVersion();

        if (bukkitVersion.contains("MC: ")) {
            return bukkitVersion.split("MC: ")[1].replace(")", "");
        }

        return bukkitVersion;
    }
}