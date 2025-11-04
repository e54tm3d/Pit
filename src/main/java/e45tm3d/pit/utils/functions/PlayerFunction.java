package e45tm3d.pit.utils.functions;

import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.utils.lists.PlayerLists;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;

public class PlayerFunction {

    public static void setInventory(Player p, PlayerInventory inv) {
        if (p == null || inv == null) {
            return;
        }

        PlayerInventory targetInv = p.getInventory();

        // 首先获取源背包的内容
        ItemStack[] mainContents = inv.getContents();
        ItemStack[] armorContents = inv.getArmorContents();

        // 只有在内容不为null时才进行设置操作
        if (mainContents != null) {
            targetInv.setContents(mainContents);
        } else {
            targetInv.clear();
        }
        
        if (armorContents != null) {
            targetInv.setArmorContents(armorContents);
        } else {
            targetInv.setArmorContents(null);
        }

        p.updateInventory();
    }

    public static void spawnSingleParticle(EnumParticle particle, Location location, Player player) {
        NMSFunction.spawnSingleParticle(particle, location, player);
    }

    public static void spawnSingleParticleToAll(EnumParticle particle, Location location) {
        for (Player player : location.getWorld().getPlayers()) {
            spawnSingleParticle(particle, location, player);
        }
    }

    public static void sendTitle(Player player, String title, String subTitle, int fadeIn, int stay, int fadeOut) {
        NMSFunction.sendTitle(player, title, subTitle, fadeIn, stay, fadeOut);
    }

    public static void setTablist(Player player, String header, String footer) {
        NMSFunction.setTablist(player, header, footer);
    }

    public static void sendActionBar(Player player, String message) {
        NMSFunction.sendActionBar(player, message);
    }

    public  static boolean isDevelopMode(Player p) {
        return PlayerLists.developers.contains(p.getUniqueId());
    }

    public static void addDevelopMode(Player p) {
        PlayerLists.developers.add(p.getUniqueId());
    }

    public  static void removeDevelopMode(Player p) {
        PlayerLists.developers.remove(p.getUniqueId());
    }

    public static boolean isInArena(Player p) {
        return Yaml.CONFIG.getConfig().getString("worlds.active_arena").equals(p.getLocation().getWorld().getName());
    }

    public static boolean isInSpawn(Player p) {

        String activeArena = Yaml.CONFIG.getConfig().getString("worlds.active_arena");

        String configPath = "worlds.world_manager." + activeArena + ".spawn_pos";
        Configuration config = Yaml.CONFIG.getConfig();
        Location loc = p.getLocation();

        double minX = Math.min(config.getDouble(configPath + "1.x"), config.getDouble(configPath + "2.x"));
        double maxX = Math.max(config.getDouble(configPath + "1.x"), config.getDouble(configPath + "2.x"));
        double minY = Math.min(config.getDouble(configPath + "1.y"), config.getDouble(configPath + "2.y"));
        double maxY = Math.max(config.getDouble(configPath + "1.y"), config.getDouble(configPath + "2.y"));
        double minZ = Math.min(config.getDouble(configPath + "1.z"), config.getDouble(configPath + "2.z"));
        double maxZ = Math.max(config.getDouble(configPath + "1.z"), config.getDouble(configPath + "2.z"));

        boolean inX = loc.getX() >= minX && loc.getX() <= maxX;
        boolean inY = loc.getY() >= minY && loc.getY() <= maxY;
        boolean inZ = loc.getZ() >= minZ && loc.getZ() <= maxZ;
        boolean inWorld = p.getWorld().equals(Bukkit.getWorld(activeArena));

        return inX && inY && inZ && inWorld;
    }

    public static Location getBlockFrontOfEntity(Entity entity, double blocks) {
        Location loc = entity.getLocation().clone();
        loc.setPitch(0);
        Vector direction = loc.getDirection().multiply(blocks);
        return loc.add(direction);
    }



}