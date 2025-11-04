package e45tm3d.pit.utils.functions;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class InventoryFunction {

    // 缓存玩家背包数据：Key=玩家UUID，Value=保存的背包物品数组
    private static final HashMap<UUID, ItemStack[]> BACKPACK_CACHE = new HashMap<>();
    // 缓存玩家盔甲栏数据：Key=玩家UUID，Value=保存的盔甲物品数组（顺序：头盔→胸甲→护腿→靴子）
    private static final HashMap<UUID, ItemStack[]> ARMOR_CACHE = new HashMap<>();

    public static boolean hasInventorySpace(Inventory inventory) {
        for (ItemStack item : inventory.getContents()) {
            if (item == null || item.getType() == Material.AIR) {
                return true;
            }
        }
        return false;
    }

    public static void saveInventory(Player p) {
        if (p == null || !p.isOnline()) {
            return; //过滤空对象/离线玩家，避免空指针
        }
        UUID playerId = p.getUniqueId();
        //深拷贝保存背包（含快捷栏+主背包，共36格）
        ItemStack[] originBackpack = p.getInventory().getContents();
        ItemStack[] copyBackpack = new ItemStack[originBackpack.length];
        for (int i = 0; i < originBackpack.length; i++) {
            copyBackpack[i] = originBackpack[i] != null ? new ItemStack(originBackpack[i]) : null;
        }
        BACKPACK_CACHE.put(playerId, copyBackpack);

        //深拷贝保存盔甲栏（Bukkit 1.8 getArmorContents()返回顺序固定为：头盔、胸甲、护腿、靴子）
        ItemStack[] originArmor = p.getInventory().getArmorContents();
        ItemStack[] copyArmor = new ItemStack[originArmor.length];
        for (int i = 0; i < originArmor.length; i++) {
            copyArmor[i] = originArmor[i] != null ? new ItemStack(originArmor[i]) : null;
        }
        ARMOR_CACHE.put(playerId, copyArmor);
    }

    public static void loadInventory(Player p) {
        if (p == null || !p.isOnline()) {
            return; //过滤空对象/离线玩家
        }
        UUID playerId = p.getUniqueId();
        // 校验背包和盔甲缓存是否同时存在，避免数据不完整
        if (!BACKPACK_CACHE.containsKey(playerId) || !ARMOR_CACHE.containsKey(playerId)) {
            return;
        }

        //加载背包：先清空当前背包，再深拷贝加载缓存（防止缓存数据被后续操作污染）
        ItemStack[] savedBackpack = BACKPACK_CACHE.get(playerId);
        ItemStack[] loadBackpack = new ItemStack[savedBackpack.length];
        for (int i = 0; i < savedBackpack.length; i++) {
            loadBackpack[i] = savedBackpack[i] != null ? new ItemStack(savedBackpack[i]) : null;
        }
        p.getInventory().setContents(loadBackpack);

        //加载盔甲栏：Bukkit 1.8 setArmorContents()会自动适配盔甲槽位，无需手动指定
        ItemStack[] savedArmor = ARMOR_CACHE.get(playerId);
        ItemStack[] loadArmor = new ItemStack[savedArmor.length];
        for (int i = 0; i < savedArmor.length; i++) {
            loadArmor[i] = savedArmor[i] != null ? new ItemStack(savedArmor[i]) : null;
        }
        p.getInventory().setArmorContents(loadArmor);

        //强制刷新客户端界面：解决1.8版本数据同步延迟bug
        p.updateInventory();
    }

    public static void clearCache(Player p) {
        if (p != null) {
            UUID playerId = p.getUniqueId();
            BACKPACK_CACHE.remove(playerId);
            ARMOR_CACHE.remove(playerId);
        }
    }

    public static boolean hasSavedData(Player p) {
        if (p == null) {
            return false;
        }
        UUID playerId = p.getUniqueId();
        return BACKPACK_CACHE.containsKey(playerId) && ARMOR_CACHE.containsKey(playerId);
    }
}
