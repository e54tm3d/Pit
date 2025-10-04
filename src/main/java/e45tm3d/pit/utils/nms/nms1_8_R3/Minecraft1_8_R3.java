package e45tm3d.pit.utils.nms.nms1_8_R3;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.*;
import org.bukkit.craftbukkit.v1_8_R3.inventory.*;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.*;

public class Minecraft1_8_R3 {

    private static Map<String, EntityEnderDragon> dragons = new HashMap<>();

    public static void spawnSingleParticle(EnumParticle particle, Location location, Player player) {
        // 检查参数是否有效
        if (particle == null || location == null || player == null || !player.isOnline()) {
            return;
        }

        // 粒子位置坐标
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        // 粒子参数：x偏移, y偏移, z偏移, 速度, 数量, 是否强制显示, 显示范围
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                particle,       // 粒子类型
                true,           // 是否强制显示（即使粒子设置不可见）
                (float) x,      // x坐标
                (float) y,      // y坐标
                (float) z,      // z坐标
                0, 0, 0,        // 偏移量（0表示不偏移）
                0,              // 粒子速度
                1,              // 粒子数量（1表示单个粒子）
                new int[0]      // 额外数据（大多数粒子不需要）
        );

        // 发送数据包给玩家
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    public static void setBlockBreakProgress(Player player, Location loc, int progress) {
        if (progress < -1 || progress > 9) {
            throw new IllegalArgumentException("Progress must be between -1 and 9");
        }

        BlockPosition pos = new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());

        int entityId = loc.getBlockX() * 1000000 + loc.getBlockY() * 1000 + loc.getBlockZ();
        PacketPlayOutBlockBreakAnimation packet = new PacketPlayOutBlockBreakAnimation(entityId, pos, progress);

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    public static void sendTitle(Player player, String title, String subTitle, int fadeIn, int stay, int fadeOut) {

        CraftPlayer craftPlayer = (CraftPlayer) player;
        PlayerConnection connection = craftPlayer.getHandle().playerConnection;

        IChatBaseComponent titleComponent = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + title + "\"}");
        IChatBaseComponent subTitleComponent = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + subTitle + "\"}");

        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleComponent);
        PacketPlayOutTitle subTitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subTitleComponent);
        PacketPlayOutTitle timesPacket = new PacketPlayOutTitle(fadeIn, stay, fadeOut);

        connection.sendPacket(timesPacket);
        connection.sendPacket(titlePacket);
        connection.sendPacket(subTitlePacket);
    }

    public static void sendActionBar(Player player, String message) {
        if (player == null || message == null) return;
        try {

            Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);

            Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);

            Class<?> chatComponentTextClass = Class.forName("net.minecraft.server.v1_8_R3.ChatComponentText");
            Object chatComponent = chatComponentTextClass.getConstructor(String.class).newInstance(message);

            Class<?> packetPlayOutChatClass = Class.forName("net.minecraft.server.v1_8_R3.PacketPlayOutChat");

            Class<?> iChatBaseComponentClass = Class.forName("net.minecraft.server.v1_8_R3.IChatBaseComponent");
            Object packet = packetPlayOutChatClass.getConstructor(iChatBaseComponentClass, byte.class)
                    .newInstance(chatComponent, (byte) 2);


            playerConnection.getClass().getMethod("sendPacket", Class.forName("net.minecraft.server.v1_8_R3.Packet"))
                    .invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static org.bukkit.inventory.ItemStack addNBTTag(org.bukkit.inventory.ItemStack item, String tagName) {
        if (item == null || tagName == null) return item;

        ItemStack nmsItem = CraftItemStack.asNMSCopy(item);

        if (!nmsItem.hasTag()) {
            nmsItem.setTag(new NBTTagCompound());
        }

        NBTTagCompound tag = nmsItem.getTag();
        tag.setBoolean(tagName, true);

        return CraftItemStack.asBukkitCopy(nmsItem);
    }

    public static org.bukkit.inventory.ItemStack removeNBTTag(org.bukkit.inventory.ItemStack item, String tagName) {
        if (item == null || tagName == null) return item;

        ItemStack nmsItem = CraftItemStack.asNMSCopy(item);

        if (!nmsItem.hasTag()) {
            nmsItem.setTag(new NBTTagCompound());
        }

        NBTTagCompound tag = nmsItem.getTag();
        tag.remove(tagName);

        return CraftItemStack.asBukkitCopy(nmsItem);
    }

    public static boolean hasNBTTag(org.bukkit.inventory.ItemStack item, String tagName) {
        if (item == null || tagName == null) return false;

        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);

        if (!nmsItem.hasTag()) return false;

        NBTTagCompound tag = nmsItem.getTag();
        return tag.hasKey(tagName);
    }

    public static org.bukkit.inventory.ItemStack addAttribute(org.bukkit.inventory.ItemStack item, String attributeName, double amount, int operation) {
        if (item == null || attributeName == null || attributeName.isEmpty()) {
            return item;
        }

        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound();

        NBTTagList attributes;
        if (tag.hasKey("AttributeModifiers")) {
            attributes = tag.getList("AttributeModifiers", 10);
        } else {
            attributes = new NBTTagList();
        }

        NBTTagCompound attribute = new NBTTagCompound();
        UUID uuid = UUID.randomUUID();
        attribute.setString("AttributeName", attributeName);
        attribute.setString("Name", attributeName);
        attribute.setDouble("Amount", amount);
        attribute.setInt("Operation", operation);
        attribute.setLong("UUIDMost", uuid.getMostSignificantBits());
        attribute.setLong("UUIDLeast", uuid.getLeastSignificantBits());

        attributes.add(attribute);
        tag.set("AttributeModifiers", attributes);
        nmsItem.setTag(tag);

        return CraftItemStack.asBukkitCopy(nmsItem);
    }

    public static void setPlayerListHeaderFooter(Player player, String header, String footer) {
        if (player == null || (header == null && footer == null)) {
            return;
        }
        
        try {
            // 创建头部和底部的IChatBaseComponent，确保中文字符正确转义
            IChatBaseComponent headerComponent = (header != null) ? 
                IChatBaseComponent.ChatSerializer.a(escapeJson(header)) : null;
            IChatBaseComponent footerComponent = (footer != null) ? 
                IChatBaseComponent.ChatSerializer.a(escapeJson(footer)) : null;
            
            // 获取NMS玩家实例
            EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
            
            // 创建PacketPlayOutPlayerListHeaderFooter数据包
            PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
            
            // 设置数据包的头部和底部
            if (headerComponent != null) {
                setField(packet, "a", headerComponent);
            }
            if (footerComponent != null) {
                setField(packet, "b", footerComponent);
            }
            
            // 发送数据包
            entityPlayer.playerConnection.sendPacket(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String escapeJson(String input) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"text\":\"");
        
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            switch (c) {
                case '\\':
                    sb.append("\\\\");
                    break;
                case '\"':
                    sb.append("\\\"");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                // 特殊处理Minecraft颜色代码字符§
                case '§':
                    if (i + 1 < input.length()) {
                        char nextChar = input.charAt(i + 1);
                        // 检查是否是有效的颜色代码（0-9, a-f, k-o, r）
                        if (isValidColorCodeChar(nextChar)) {
                            // 保持颜色代码格式不变
                            sb.append("\\u00A7"); // 使用Unicode转义表示§字符
                            sb.append(nextChar);
                            i++; // 跳过下一个字符，因为已经处理过了
                            continue;
                        }
                    }
                    // 如果不是有效的颜色代码，就直接添加§字符
                    sb.append("\\u00A7");
                    break;
                default:
                    // 对于非ASCII字符（如中文），直接添加，不进行额外转义
                    sb.append(c);
                    break;
            }
        }
        
        sb.append("\"}");
        return sb.toString();
    }

    private static boolean isValidColorCodeChar(char c) {
        return (c >= '0' && c <= '9') || 
               (c >= 'a' && c <= 'f') || 
               (c >= 'k' && c <= 'o') || 
               c == 'r';
    }

    private static void setField(Object object, String fieldName, Object value) throws Exception {
        java.lang.reflect.Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }

    public static void spawnItemExplosion(Location location, org.bukkit.inventory.ItemStack item, int amount, double speed, double radius) {
        if (location == null || item == null || amount <= 0) {
            return;
        }

        World world = location.getWorld();
        if (world == null) {
            return;
        }

        WorldServer worldServer = ((CraftWorld) world).getHandle();
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);

        // 计算要生成的物品堆叠数量
        int maxStackSize = item.getMaxStackSize();
        int remainingAmount = amount;

        while (remainingAmount > 0) {
            int stackSize = Math.min(remainingAmount, maxStackSize);
            remainingAmount -= stackSize;

            // 创建物品实体
            EntityItem entityItem = new EntityItem(
                    worldServer,
                    location.getX(),
                    location.getY(),
                    location.getZ(),
                    nmsItem.cloneItemStack() // 修复这里，使用cloneItemStack方法
            );

            // 随机生成飞散方向
            double randomX = (Math.random() - 0.5) * 2 * radius;
            double randomY = 0.5 + Math.random() * radius;
            double randomZ = (Math.random() - 0.5) * 2 * radius;

            // 设置物品飞散的速度和方向
            entityItem.motX = randomX * speed;
            entityItem.motY = randomY * speed;
            entityItem.motZ = randomZ * speed;

            // 设置物品的拾取延迟
            entityItem.pickupDelay = 10;

            // 生成物品
            worldServer.addEntity(entityItem, CreatureSpawnEvent.SpawnReason.CUSTOM);
        }
    }

}
