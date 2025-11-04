package e45tm3d.pit;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import e45tm3d.pit.api.Preplayer;
import e45tm3d.pit.api.User;
import e45tm3d.pit.api.Variables;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.api.events.PlayerConnectEvent;
import e45tm3d.pit.modules.buff.Buffs;
import e45tm3d.pit.modules.commands.Commands;
import e45tm3d.pit.modules.curse.Curses;
import e45tm3d.pit.modules.enchance.Enchances;
import e45tm3d.pit.modules.items.materials.Materials;
import e45tm3d.pit.modules.items.weapon.Weapons;
import e45tm3d.pit.modules.listeners.Listeners;
import e45tm3d.pit.modules.listeners.player.ArrowLoader;
import e45tm3d.pit.modules.monsters.Monsters;
import e45tm3d.pit.modules.tasks.Tasks;
import e45tm3d.pit.storage.MySQL;
import e45tm3d.pit.storage.SQLite;
import e45tm3d.pit.utils.functions.DatabaseFunction;
import e45tm3d.pit.utils.functions.InventoryFunction;
import e45tm3d.pit.utils.functions.NMSFunction;
import e45tm3d.pit.utils.lists.MonsterLists;
import e45tm3d.pit.utils.maps.BlocksMaps;
import e45tm3d.pit.utils.maps.PlayerMaps;
import e45tm3d.pit.utils.nms.RightClickHandle;
import me.clip.placeholderapi.PlaceholderAPI;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public final class ThePit extends JavaPlugin implements PluginMessageListener, Listener {

    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;
    public static final String minecraft_server_version = getMinecraftVersion();
    public static RightClickHandle nmsHook;

    public static ThePit instance;

    @Override
    public void onEnable() {

        instance = this;

        Bukkit.getPluginManager().registerEvents(this, this);

        nmsHook = NMSFunction.newNMSRightClickHook(this);

        for (Player player : Bukkit.getOnlinePlayers()) {
            hookPlayer(player);
        }
        
        // 初始化配置文件
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
        Yaml.SPECTATING.initConfig();

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new Placeholders().register();
        }

        if (Yaml.CONFIG.getConfig().getString("storage.type").equals("mysql")) {
            MySQL.createTable();
        } else if (Yaml.CONFIG.getConfig().getString("storage.type").equals("sqlite")) {
            SQLite.createTable();
        }

        if (!setupEconomy()) {
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

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);

        getLogger().info("Checking server version: " + minecraft_server_version);

    }

    @Override
    public void onDisable() {

        ArrowLoader.restoreAllPlayerItemSlots();

        getServer().getMessenger().unregisterOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().unregisterIncomingPluginChannel(this, "BungeeCord", this);

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

            if (User.isPlaying(p)) {
                InventoryFunction.saveInventory(p);
            }
            InventoryFunction.loadInventory(p);

            p.kickPlayer("Server is restatring or disabling...");

            p.closeInventory();

            DatabaseFunction.saveDatabase(p);
        }

        for (Entity entities : Bukkit.getWorld(Variables.getActiveArena()).getEntities()) {
            if (entities instanceof Projectile) {
                entities.remove();
            }
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

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] data) {
        if (!channel.equals("BungeeCord")) return;

        this.getLogger().info("Received BungeeCord message of length: " + data.length + " bytes");
        ByteArrayDataInput in = ByteStreams.newDataInput(data);

        if (data.length == 0) {
            this.getLogger().warning("Empty BungeeCord message received");
            return;
        }

        // 步骤1：读取子通道
        String subChannel = in.readUTF();
        this.getLogger().info("BungeeCord message subchannel: " + subChannel);

        if (subChannel.equals("gameproxy")) {
            try {
                String message = in.readUTF(); // 读取字符串消息
                getLogger().info("Received message: " + message);
                String[] parts = message.split(":");
                String type = parts[0];
                if (type.equals("connect")) {
                    String name = parts[1];
                    UUID uuid = UUID.fromString(parts[2]);
                    Bukkit.getPluginManager().callEvent(new PlayerConnectEvent(new Preplayer(name, uuid)));
                }
            } catch (Exception e) {
                this.getLogger().severe("Failed to read message: " + e.getMessage());
            }
        }
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (player.isOnline()) {
                    hookPlayer(player);
                }
            }
        }.runTaskLater(this, 10);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        nmsHook.unhookPlayer(e.getPlayer());
    }

    private void hookPlayer(Player player) {
        nmsHook.hookPlayer(player);
        getLogger().info("Right click listener registered for player " + player.getName());
    }
}