package e45tm3d.pit.utils.functions;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.utils.nms.Bossbar;
import e45tm3d.pit.utils.nms.nms1_8_R3.Bossbar1_8_R3;
import e45tm3d.pit.utils.nms.nms1_8_R3.Minecraft1_8_R3;
import e45tm3d.pit.utils.nms.RightClickHandle;
import e45tm3d.pit.utils.nms.nms1_8_R3.NMSRightClickHook1_8_R3;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class NMSFunction {

    public static void setBlockBreakProgress(Player player, Location loc, int progress) {
        if (ThePit.minecraft_server_version.contains("1.8")) Minecraft1_8_R3.setBlockBreakProgress(player, loc, progress);
    }

    public static ItemStack addAttribute(ItemStack item, String attributeName, double amount, int operation) {
        if (ThePit.minecraft_server_version.contains("1.8")) return Minecraft1_8_R3.addAttribute(item, attributeName, amount, operation);
        return item;
    }

    public static ItemStack addNBTTag(ItemStack item, String nbtTagName) {
        if (ThePit.minecraft_server_version.contains("1.8")) return Minecraft1_8_R3.addNBTTag(item, nbtTagName);
        return item;
    }

    public static ItemStack removeNBTTag(ItemStack item, String nbtTagName) {
        if (ThePit.minecraft_server_version.contains("1.8")) return Minecraft1_8_R3.removeNBTTag(item, nbtTagName);
        return item;
    }

    public static boolean hasNBTTag(ItemStack item, String nbtTagName) {
        if (ThePit.minecraft_server_version.contains("1.8")) return Minecraft1_8_R3.hasNBTTag(item, nbtTagName);
        return false;
    }

    public static void spawnItemExplosion(Location location, org.bukkit.inventory.ItemStack item, int amount, int radius, int speed) {
        if (ThePit.minecraft_server_version.contains("1.8")) Minecraft1_8_R3.spawnItemExplosion(location, item, amount, radius, speed);
    }

    public static void spawnSingleParticle(EnumParticle particle, Location location, Player player) {
        if (ThePit.minecraft_server_version.contains("1.8")) Minecraft1_8_R3.spawnSingleParticle(particle, location, player);
    }

    public static void sendTitle(Player player, String title, String subTitle, int fadeIn, int stay, int fadeOut) {
        if (ThePit.minecraft_server_version.contains("1.8")) Minecraft1_8_R3.sendTitle(player, title, subTitle, fadeIn, stay, fadeOut);
    }

    public static void setTablist(Player player, String header, String footer) {
        if (ThePit.minecraft_server_version.contains("1.8")) Minecraft1_8_R3.setPlayerListHeaderFooter(player, header, footer);
    }

    public static void sendActionBar(Player player, String message) {
        if (ThePit.minecraft_server_version.contains("1.8")) Minecraft1_8_R3.sendActionBar(player, message);
    }

    public static RightClickHandle newNMSRightClickHook(Plugin plugin) {
        if (ThePit.minecraft_server_version.contains("1.8")) return new NMSRightClickHook1_8_R3(plugin);
        return null;
    }

    public static Bossbar newBossbar(String title) {
        if (ThePit.minecraft_server_version.contains("1.8")) return new Bossbar1_8_R3(title);
        return null;
    }

}