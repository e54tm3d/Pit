package e45tm3d.pit.utils.nms.nms1_8_R3;

import e45tm3d.pit.api.events.PlayerRightClickEvent;
import e45tm3d.pit.utils.nms.RightClickHandle;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

public class NMSRightClickHook1_8_R3 implements RightClickHandle {

    private static final String NMS_PACKAGE = "net.minecraft.server.v1_8_R3";
    private final Plugin plugin;
    private final Map<UUID, Object> originalListeners = new HashMap<>(); // 存储原始监听器

    public NMSRightClickHook1_8_R3(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void hookPlayer(Player player) {
        try {
            // 1. 获取 NMS Player（CraftPlayer -> getHandle()）
            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer");
            Method getHandleMethod = craftPlayerClass.getMethod("getHandle");
            Object nmsPlayer = getHandleMethod.invoke(player);

            // 2. 获取 PlayerConnection
            Field playerConnectionField = nmsPlayer.getClass().getField("playerConnection");
            Object playerConnection = playerConnectionField.get(nmsPlayer);

            // 3. 获取 NetworkManager
            Field networkManagerField = playerConnection.getClass().getField("networkManager");
            Object networkManager = networkManagerField.get(playerConnection);

            // 4. 获取原始 PacketListenerPlayIn - 进一步改进字段查找逻辑
            Field packetListenerField = null;
            try {
                // 首先尝试通过类型查找（最可靠的方法）
                for (Field field : networkManager.getClass().getDeclaredFields()) {
                    // 检查字段类型是否包含"PacketListener"且不是final类型
                    if (field.getType().getName().contains("PacketListener") && 
                        !Modifier.isFinal(field.getModifiers()) &&
                        !field.getType().isAssignableFrom(java.util.Queue.class)) {
                        packetListenerField = field;
                        packetListenerField.setAccessible(true);
                        plugin.getLogger().info("Find suitable PacketListener field: " + field.getName() + ", type: " + field.getType().getName());
                        break;
                    }
                }
                
                // 如果没找到，尝试所有可能的混淆名称（优先级低）
                if (packetListenerField == null) {
                    String[] possibleNames = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m"};
                    for (String name : possibleNames) {
                        try {
                            packetListenerField = networkManager.getClass().getDeclaredField(name);
                            packetListenerField.setAccessible(true);
                            // 验证字段是否可设置且不是队列
                            if (!Modifier.isFinal(packetListenerField.getModifiers()) &&
                                !packetListenerField.getType().isAssignableFrom(java.util.Queue.class)) {
                                plugin.getLogger().info("Found suitable field via obfuscated name: " + name);
                                break;
                            } else {
                                // 虽然找到了字段，但它是final或Queue，继续尝试
                                packetListenerField = null;
                            }
                        } catch (NoSuchFieldException ignore) {
                            // 字段不存在，继续尝试下一个名称
                        }
                    }
                }
            } catch (Exception e) {
                plugin.getLogger().severe("Error while searching for PacketListener field in NetworkManager: " + e.getMessage());
                return; // 发生错误，放弃挂钩
            }
            
            // 验证找到的字段是否可用
            if (packetListenerField == null) {
                plugin.getLogger().severe("Cannot find settable PacketListener field in NetworkManager! Please check if Minecraft version is 1.8 R3");
                return;
            }
            
            // 再次确认字段不是final类型
            if (Modifier.isFinal(packetListenerField.getModifiers())) {
                plugin.getLogger().severe("Found field is final, cannot replace: " + packetListenerField.getName() + ", Type: " + packetListenerField.getType().getName());
                return;
            }
            
            Object originalListener = packetListenerField.get(networkManager);
            originalListeners.put(player.getUniqueId(), originalListener);

            // 5. 创建自定义数据包监听器（动态代理）
            Class<?> packetListenerInterface = Class.forName(NMS_PACKAGE + ".PacketListenerPlayIn");
            Object customListener = Proxy.newProxyInstance(
                    plugin.getClass().getClassLoader(),
                    new Class[]{packetListenerInterface},
                    new PacketProxyHandler(player, originalListener)
            );

            // 6. 替换原始监听器
            packetListenerField.set(networkManager, customListener);

        } catch (Exception e) {
            plugin.getLogger().severe("Hook player " + player.getName() + " failed!");
            e.printStackTrace();
        }
    }

    @Override
    public void unhookPlayer(Player player) {
        UUID uuid = player.getUniqueId();
        Object originalListener = originalListeners.remove(uuid);
        if (originalListener == null) return;

        try {
            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer");
            Method getHandleMethod = craftPlayerClass.getMethod("getHandle");
            Object nmsPlayer = getHandleMethod.invoke(player);

            Field playerConnectionField = nmsPlayer.getClass().getField("playerConnection");
            Object playerConnection = playerConnectionField.get(nmsPlayer);

            Field networkManagerField = playerConnection.getClass().getField("networkManager");
            Object networkManager = networkManagerField.get(playerConnection);

            // 使用相同的逻辑查找字段
            Field packetListenerField = null;
            try {
                // 首先尝试通过类型查找
                for (Field field : networkManager.getClass().getDeclaredFields()) {
                    if (field.getType().getName().contains("PacketListener") && 
                        !Modifier.isFinal(field.getModifiers()) &&
                        !field.getType().isAssignableFrom(java.util.Queue.class)) {
                        packetListenerField = field;
                        packetListenerField.setAccessible(true);
                        break;
                    }
                }
                
                // 如果没找到，尝试所有可能的混淆名称
                if (packetListenerField == null) {
                    String[] possibleNames = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m"};
                    for (String name : possibleNames) {
                        try {
                            packetListenerField = networkManager.getClass().getDeclaredField(name);
                            packetListenerField.setAccessible(true);
                            // 验证字段是否可设置且不是队列
                            if (!Modifier.isFinal(packetListenerField.getModifiers()) &&
                                !packetListenerField.getType().isAssignableFrom(java.util.Queue.class)) {
                                break;
                            } else {
                                packetListenerField = null;
                            }
                        } catch (NoSuchFieldException ignore) {
                            // 字段不存在，继续尝试
                        }
                    }
                }
            } catch (Exception e) {
                plugin.getLogger().severe("Error while searching for PacketListener field in NetworkManager: " + e.getMessage());
                return; // 发生错误，放弃卸载
            }
            
            if (packetListenerField != null && !Modifier.isFinal(packetListenerField.getModifiers())) {
                packetListenerField.set(networkManager, originalListener);
                plugin.getLogger().info("Successfully restored original listener, field: " + packetListenerField.getName());
            } else if (packetListenerField != null) {
                plugin.getLogger().severe("Found field is final, cannot replace: " + packetListenerField.getName());
            } else {
                plugin.getLogger().severe("Cannot find settable PacketListener field in NetworkManager!");
            }

        } catch (Exception e) {
            plugin.getLogger().severe("Failed to unhook listener for player " + player.getName() + "!");
            e.printStackTrace();
        }
    }

    private class PacketProxyHandler implements InvocationHandler {

        private final Player player;
        private final Object originalListener;

        public PacketProxyHandler(Player player, Object originalListener) {
            this.player = player;
            this.originalListener = originalListener;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            try {
                // 仅拦截数据包处理方法 "a"（1.8.8 固定）
                if (method.getName().equals("a") && args != null && args.length == 1) {
                    Object packet = args[0];
                    String packetClass = packet.getClass().getName();

                    // 处理右键方块/空气数据包
                    if (packetClass.equals(NMS_PACKAGE + ".PacketPlayInBlockPlace")) {
                        handleBlockPlacePacket(packet);
                    }
                    // 处理右键实体数据包
                    else if (packetClass.equals(NMS_PACKAGE + ".PacketPlayInUseEntity")) {
                        handleUseEntityPacket(packet);
                    }
                }

                // 执行原始逻辑（如果事件未被取消）
                return method.invoke(originalListener, args);
            } catch (InvocationTargetException e) {
                // 解包原始异常
                Throwable targetException = e.getTargetException();
                // 如果是自定义的取消事件异常，记录日志但不传播给客户端
                if (targetException instanceof RuntimeException && 
                    targetException.getMessage() != null && 
                    targetException.getMessage().contains("Event has been cancelled!")) {
                    plugin.getLogger().info("Successfully cancel " + player.getName() + "'s right click data packet!");
                    return null; // 返回null以避免操作继续执行
                }
                // 其他异常正常传播
                throw targetException;
            } catch (Exception e) {
                // 记录所有其他异常，但不中断服务器运行
                plugin.getLogger().severe("Error occurred while processing right-click packet for player " + player.getName() + ": " + e.getMessage());
                e.printStackTrace();
                // 尝试继续执行原始逻辑
                try {
                    return method.invoke(originalListener, args);
                } catch (Exception ex) {
                    // 如果原始逻辑也失败，返回null并允许连接继续
                    return null;
                }
            }
        }

        /**
         * 解析 PacketPlayInBlockPlace（右键空气/方块）
         */
        private void handleBlockPlacePacket(Object packet) throws Exception {
            try {
                // 1. 解析点击方向（-1 = 空气，0-5 = 方块）
                // 尝试通过反射查找合适的字段（int类型的字段）
                Field faceField = null;
                for (Field field : packet.getClass().getDeclaredFields()) {
                    if (field.getType() == int.class) {
                        faceField = field;
                        faceField.setAccessible(true);
                        break;
                    }
                }
                
                if (faceField == null) {
                    throw new NoSuchFieldException("Cannot find suitable click direction field");
                }
                
                int clickFace = faceField.getInt(packet);

                // 2. 解析手持物品
                // 尝试通过反射查找物品字段
                Field itemField = null;
                for (Field field : packet.getClass().getDeclaredFields()) {
                    if (field.getType().getName().endsWith("ItemStack")) {
                        itemField = field;
                        itemField.setAccessible(true);
                        break;
                    }
                }
                
                ItemStack holdingItem = null;
                if (itemField != null) {
                    Object nmsItem = itemField.get(packet);
                    holdingItem = convertNMSItemToBukkit(nmsItem);
                } else {
                    // 如果找不到物品字段，使用玩家手持物品作为备选
                    holdingItem = player.getItemInHand();
                }

                // 3. 解析右键目标
                Object target = null;
                PlayerRightClickEvent.RightClickType clickType;
                if (clickFace == -1) {
                    clickType = PlayerRightClickEvent.RightClickType.AIR;
                } else {
                    clickType = PlayerRightClickEvent.RightClickType.BLOCK;
                    target = getTargetBlock(player); // 获取玩家指向的方块
                }

                // 4. 触发自定义事件
                PlayerRightClickEvent event = new PlayerRightClickEvent(player, clickType, holdingItem, target);
                Bukkit.getPluginManager().callEvent(event);

                // 5. 如果事件被取消，阻止原始行为
                if (event.isCancelled()) {
                    throw new RuntimeException("Event has been cancelled");
                }
            } catch (Exception e) {
                plugin.getLogger().info("Error while processing block right-click event: " + e.getMessage());
                throw e;
            }
        }

        /**
         * 解析 PacketPlayInUseEntity（右键实体）
         */
        private void handleUseEntityPacket(Object packet) throws Exception {
            try {
                // 1. 解析交互类型（0 = 右键，1 = 攻击）
                // 尝试通过反射查找action字段
                Field actionField = null;
                for (Field field : packet.getClass().getDeclaredFields()) {
                    if (field.getType().isEnum()) {
                        actionField = field;
                        actionField.setAccessible(true);
                        break;
                    }
                }
                
                if (actionField == null) {
                    throw new NoSuchFieldException("Cannot find interaction type field");
                }
                
                int actionType = ((Enum<?>) actionField.get(packet)).ordinal();
                if (actionType != 0) return; // 仅处理右键

                // 2. 解析目标实体 ID
                // 尝试通过反射查找实体ID字段
                Field entityIdField = null;
                for (Field field : packet.getClass().getDeclaredFields()) {
                    if (field.getType() == int.class) {
                        entityIdField = field;
                        entityIdField.setAccessible(true);
                        break;
                    }
                }
                
                if (entityIdField == null) {
                    throw new NoSuchFieldException("Cannot find entity ID field");
                }
                
                int targetEntityId = entityIdField.getInt(packet);

                // 3. 解析手持物品
                ItemStack holdingItem = player.getItemInHand();

                // 4. 查找目标实体 - 改进异常处理
                Entity target = null;
                for (Entity entity : player.getWorld().getEntities()) {
                    try {
                        if (getEntityId(entity) == targetEntityId) {
                            target = entity;
                            break;
                        }
                    } catch (Exception e) {
                        plugin.getLogger().warning("Error while getting entity ID: " + e.getMessage());
                        // 继续查找下一个实体
                    }
                }

                // 5. 触发自定义事件
                PlayerRightClickEvent event = new PlayerRightClickEvent(
                        player,
                        PlayerRightClickEvent.RightClickType.ENTITY,
                        holdingItem,
                        target
                );
                Bukkit.getPluginManager().callEvent(event);

                // 6. 取消事件则阻止原始行为
                if (event.isCancelled()) {
                    throw new RuntimeException("Event has been cancelled");
                }
            } catch (Exception e) {
                plugin.getLogger().info("Error while processing entity right-click event: " + e.getMessage());
                throw e;
            }
        }

        /**
         * NMS ItemStack 转换为 Bukkit ItemStack
         */
        private ItemStack convertNMSItemToBukkit(Object nmsItem) throws Exception {
            if (nmsItem == null) return new ItemStack(org.bukkit.Material.AIR);
            
            Class<?> itemStackClass = Class.forName(NMS_PACKAGE + ".ItemStack");
            
            // Minecraft 1.8 R3 不支持 isEmpty() 方法，使用替代方式检查物品是否为空
            boolean isEmpty = false;
            try {
                // 尝试通过 getItem() 方法判断物品是否为空（1.8 R3 常用方式）
                Method getItemMethod = itemStackClass.getMethod("getItem");
                Object item = getItemMethod.invoke(nmsItem);
                if (item == null) {
                    isEmpty = true;
                } else {
                    // 尝试获取物品的 ID，如果为 0 则为空
                    try {
                        Field idField = item.getClass().getField("id");
                        int id = idField.getInt(item);
                        if (id == 0) {
                            isEmpty = true;
                        }
                    } catch (Exception ignored) {
                        // 如果无法获取 ID，使用其他方式判断
                        try {
                            // 尝试获取物品的材质名称
                            Method getNameMethod = item.getClass().getMethod("getName");
                            String name = (String) getNameMethod.invoke(item);
                            if (name == null || name.isEmpty()) {
                                isEmpty = true;
                            }
                        } catch (Exception e) {
                            // 如果所有尝试都失败，记录警告但不中断转换
                            plugin.getLogger().warning("Cannot check if item is empty, will attempt direct conversion: " + e.getMessage());
                        }
                    }
                }
            } catch (Exception e) {
                plugin.getLogger().warning("Error while checking if item is empty: " + e.getMessage());
            }
            
            if (isEmpty) {
                return new ItemStack(org.bukkit.Material.AIR);
            }
            
            // 通过 CraftItemStack 转换
            try {
                Class<?> craftItemClass = Class.forName("org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack");
                Method asBukkitCopyMethod = craftItemClass.getMethod("asBukkitCopy", itemStackClass);
                return (ItemStack) asBukkitCopyMethod.invoke(null, nmsItem);
            } catch (Exception e) {
                plugin.getLogger().severe("Error while converting item: " + e.getMessage());
                // 转换失败时返回空气物品
                return new ItemStack(org.bukkit.Material.AIR);
            }
        }

        /**
         * 获取玩家指向的方块（最大距离 5 格）
         */
        private Block getTargetBlock(Player player) {
            return player.getTargetBlock((HashSet<Byte>) null, 5);
        }

        /**
         * 获取实体的 NMS ID
         */
        private int getEntityId(Entity entity) throws Exception {
            Class<?> craftEntityClass = Class.forName("org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity");
            Method getHandleMethod = craftEntityClass.getMethod("getHandle");
            Object nmsEntity = getHandleMethod.invoke(entity);
            Field idField = nmsEntity.getClass().getField("id");
            return idField.getInt(nmsEntity);
        }
    }
}