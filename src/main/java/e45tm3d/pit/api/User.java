package e45tm3d.pit.api;

import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.utils.functions.DatabaseFunction;
import e45tm3d.pit.utils.functions.ItemFunction;
import e45tm3d.pit.utils.functions.PlayerFunction;
import e45tm3d.pit.utils.maps.PlayerMaps;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class User {

    public static boolean isDevelopMode(Player p) {
        return PlayerFunction.isDevelopMode(p);
    }

    public static int getKills(Player p) {
        if (!PlayerMaps.kills.containsKey(p.getUniqueId())) {
            if (p.hasPlayedBefore()) {
                PlayerMaps.kills.put(p.getUniqueId(), DatabaseFunction.getKills(p));
            } else {
                PlayerMaps.kills.put(p.getUniqueId(), 0);
            }
        }
        return PlayerMaps.kills.get(p.getUniqueId());
    }

    public static void setKills(Player p, int v) {
        PlayerMaps.kills.put(p.getUniqueId(), v);
    }

    public static void loadKills(Player p) {
        if (!PlayerMaps.kills.containsKey(p.getUniqueId())) {
            PlayerMaps.kills.put(p.getUniqueId(), DatabaseFunction.getKills(p));
        }
    }

    public static void updateKills(Player p) {
        PlayerMaps.kills.put(p.getUniqueId(), DatabaseFunction.getKills(p));
    }

    public static int getDeaths(Player p) {
        if (!PlayerMaps.deaths.containsKey(p.getUniqueId())) {
            if (p.hasPlayedBefore()) {
                PlayerMaps.deaths.put(p.getUniqueId(), DatabaseFunction.getDeaths(p));
            } else {
                PlayerMaps.deaths.put(p.getUniqueId(), 0);
            }
        }
        return PlayerMaps.deaths.get(p.getUniqueId());
    }

    public static void setDeaths(Player p, int v) {
        PlayerMaps.deaths.put(p.getUniqueId(), v);
    }

    public static void loadDeaths(Player p) {
        if (!PlayerMaps.deaths.containsKey(p.getUniqueId())) {
            PlayerMaps.deaths.put(p.getUniqueId(), DatabaseFunction.getDeaths(p));
        }
    }

    public static void updateDeaths(Player p) {
        PlayerMaps.deaths.put(p.getUniqueId(), DatabaseFunction.getDeaths(p));
    }

    public static int getKillstreak(Player p) {
        if (!PlayerMaps.killstreak.containsKey(p.getUniqueId())) {
            if (p.hasPlayedBefore()) {
                PlayerMaps.killstreak.put(p.getUniqueId(), DatabaseFunction.getKillstreak(p));
            } else {
                PlayerMaps.killstreak.put(p.getUniqueId(), 0);
            }
        }
        return PlayerMaps.killstreak.get(p.getUniqueId());
    }

    public static void setKillstreak(Player p, int v) {
        PlayerMaps.killstreak.put(p.getUniqueId(), v);
    }

    public static void loadKillstreak(Player p) {
        if (!PlayerMaps.killstreak.containsKey(p.getUniqueId())) {
            PlayerMaps.killstreak.put(p.getUniqueId(), DatabaseFunction.getKillstreak(p));
        }
    }

    public static void updateKillstreak(Player p) {
        PlayerMaps.killstreak.put(p.getUniqueId(), DatabaseFunction.getKillstreak(p));
    }

    public static int getHelmetLevel(Player p) {
        if (!PlayerMaps.helmet_level.containsKey(p.getUniqueId())) {
            if (p.hasPlayedBefore()) {
                PlayerMaps.helmet_level.put(p.getUniqueId(), DatabaseFunction.getHelmetLevel(p));
            } else {
                PlayerMaps.helmet_level.put(p.getUniqueId(), 0);
            }
        }
        return PlayerMaps.helmet_level.get(p.getUniqueId());
    }

    public static void setHelmetLevel(Player p, int v) {
        PlayerMaps.helmet_level.put(p.getUniqueId(), v);
    }

    public static void loadHelmetLevel(Player p) {
        if (!PlayerMaps.helmet_level.containsKey(p.getUniqueId())) {
            PlayerMaps.helmet_level.put(p.getUniqueId(), DatabaseFunction.getHelmetLevel(p));
        }
    }

    public static void updateHelmetLevel(Player p) {
        PlayerMaps.helmet_level.put(p.getUniqueId(), DatabaseFunction.getHelmetLevel(p));
    }

    public static int getChestplateLevel(Player p) {
        if (!PlayerMaps.chestplate_level.containsKey(p.getUniqueId())) {
            if (p.hasPlayedBefore()) {
                PlayerMaps.chestplate_level.put(p.getUniqueId(), DatabaseFunction.getChestplateLevel(p));
            } else {
                PlayerMaps.chestplate_level.put(p.getUniqueId(), 0);
            }
        }
        return PlayerMaps.chestplate_level.get(p.getUniqueId());
    }

    public static void setChestplateLevel(Player p, int v) {
        PlayerMaps.chestplate_level.put(p.getUniqueId(), v);
    }

    public static void loadChestplateLevel(Player p) {
        if (!PlayerMaps.chestplate_level.containsKey(p.getUniqueId())) {
            PlayerMaps.chestplate_level.put(p.getUniqueId(), DatabaseFunction.getChestplateLevel(p));
        }
    }

    public static void updateChestplateLevel(Player p) {
        PlayerMaps.chestplate_level.put(p.getUniqueId(), DatabaseFunction.getChestplateLevel(p));
    }

    public static int getLeggingsLevel(Player p) {
        if (!PlayerMaps.leggings_level.containsKey(p.getUniqueId())) {
            if (p.hasPlayedBefore()) {
                PlayerMaps.leggings_level.put(p.getUniqueId(), DatabaseFunction.getLeggingsLevel(p));
            } else {
                PlayerMaps.leggings_level.put(p.getUniqueId(), 0);
            }
        }
        return PlayerMaps.leggings_level.get(p.getUniqueId());
    }

    public static void setLeggingsLevel(Player p, int v) {
        PlayerMaps.leggings_level.put(p.getUniqueId(), v);
    }

    public static void loadLeggingsLevel(Player p) {
        if (!PlayerMaps.leggings_level.containsKey(p.getUniqueId())) {
            PlayerMaps.leggings_level.put(p.getUniqueId(), DatabaseFunction.getLeggingsLevel(p));
        }
    }

    public static void updateLeggingsLevel(Player p) {
        PlayerMaps.leggings_level.put(p.getUniqueId(), DatabaseFunction.getLeggingsLevel(p));
    }

    public static int getBootsLevel(Player p) {
        if (!PlayerMaps.boots_level.containsKey(p.getUniqueId())) {
            if (p.hasPlayedBefore()) {
                PlayerMaps.boots_level.put(p.getUniqueId(), DatabaseFunction.getBootsLevel(p));
            } else {
                PlayerMaps.boots_level.put(p.getUniqueId(), 0);
            }
        }
        return PlayerMaps.boots_level.get(p.getUniqueId());
    }

    public static void setBootsLevel(Player p, int v) {
        PlayerMaps.boots_level.put(p.getUniqueId(), v);
    }

    public static void loadBootsLevel(Player p) {
        if (!PlayerMaps.boots_level.containsKey(p.getUniqueId())) {
            PlayerMaps.boots_level.put(p.getUniqueId(), DatabaseFunction.getBootsLevel(p));
        }
    }

    public static void updateBootsLevel(Player p) {
        PlayerMaps.boots_level.put(p.getUniqueId(), DatabaseFunction.getBootsLevel(p));
    }

    public static int getWeaponLevel(Player p, String type) {
        UUID uuid = p.getUniqueId();

        if (!PlayerMaps.sword_level.containsKey(uuid)) {
            PlayerMaps.sword_level.put(uuid, new HashMap<>());
        }

        Map<String, Integer> swordMap = PlayerMaps.sword_level.get(uuid);

        if (!swordMap.containsKey(type)) {

            int level = 0;

            if (p.hasPlayedBefore()) {
                level = DatabaseFunction.getWeaponLevel(p, type);
            }

            swordMap.put(type, level);
            return level;
        }

        return swordMap.get(type);
    }

    public static void setWeaponLevel(Player p, String type, int v) {
        UUID uuid = p.getUniqueId();

        if (!PlayerMaps.sword_level.containsKey(uuid)) {
            PlayerMaps.sword_level.put(uuid, new HashMap<>());
        }

        PlayerMaps.sword_level.get(uuid).put(type, v);
    }

    public static void loadWeaponLevel(Player p, String type) {
        UUID uuid = p.getUniqueId();

        if (!PlayerMaps.sword_level.containsKey(uuid)) {
            PlayerMaps.sword_level.put(uuid, new HashMap<>());
        }

        Map<String, Integer> swordMap = PlayerMaps.sword_level.get(uuid);

        if (!swordMap.containsKey(type)) {
            int level = DatabaseFunction.getWeaponLevel(p, type);
            swordMap.put(type, level);
        }
    }

    public static void updateWeaponLevel(Player p, String type) {
        UUID uuid = p.getUniqueId();

        if (!PlayerMaps.sword_level.containsKey(uuid)) {
            PlayerMaps.sword_level.put(uuid, new HashMap<>());
        }

        int level = DatabaseFunction.getWeaponLevel(p, type);
        PlayerMaps.sword_level.get(uuid).put(type, level);
    }

    public static int getLevel(Player p) {
        if (!PlayerMaps.level.containsKey(p.getUniqueId())) {
            if (p.hasPlayedBefore()) {
                PlayerMaps.level.put(p.getUniqueId(), DatabaseFunction.getLevel(p));
            } else {
                PlayerMaps.level.put(p.getUniqueId(), 0);
            }
        }
        return PlayerMaps.level.get(p.getUniqueId());
    }

    public static void setLevel(Player p, int v) {
        PlayerMaps.level.put(p.getUniqueId(), v);
    }

    public static void loadLevel(Player p) {
        if (!PlayerMaps.level.containsKey(p.getUniqueId())) {
            PlayerMaps.level.put(p.getUniqueId(), DatabaseFunction.getLevel(p));
        }
    }

    public static void updateLevel(Player p) {
        PlayerMaps.level.put(p.getUniqueId(), DatabaseFunction.getLevel(p));
    }

    public static int getExp(Player p) {
        if (!PlayerMaps.exp.containsKey(p.getUniqueId())) {
            if (p.hasPlayedBefore()) {
                PlayerMaps.exp.put(p.getUniqueId(), DatabaseFunction.getExp(p));
            } else {
                PlayerMaps.exp.put(p.getUniqueId(), 0);
            }
        }
        return PlayerMaps.exp.get(p.getUniqueId());
    }

    public static void setExp(Player p, int v) {
        PlayerMaps.exp.put(p.getUniqueId(), v);
    }

    public static void loadExp(Player p) {
        if (!PlayerMaps.exp.containsKey(p.getUniqueId())) {
            PlayerMaps.exp.put(p.getUniqueId(), DatabaseFunction.getExp(p));
        }
    }

    public static void updateExp(Player p) {
        PlayerMaps.exp.put(p.getUniqueId(), DatabaseFunction.getExp(p));
    }

    public static boolean isFighting(Player p) {

        UUID uuid = p.getUniqueId();

        if (PlayerMaps.fight_time.containsKey(uuid)) {
            return System.currentTimeMillis() - PlayerMaps.fight_time.get(p.getUniqueId()) < 1000 * Yaml.CONFIG.getConfig().getLong("settings.fighting.duration");
        } else {
            PlayerMaps.fight_time.put(uuid, 0L);
        }
        return false;
    }

    public static long getFightTime(Player p) {
        UUID uuid = p.getUniqueId();
        if (PlayerMaps.fight_time.containsKey(uuid)) {
            return System.currentTimeMillis() - PlayerMaps.fight_time.get(uuid);
        }
        return 0L;
    }

    public static boolean getCurseStat(Player p, String type) {
        UUID uuid = p.getUniqueId();

        if (!PlayerMaps.curse.containsKey(uuid)) {
            PlayerMaps.curse.put(uuid, new HashMap<>());
        }

        Map<String, Boolean> curseMap = PlayerMaps.curse.get(uuid);

        if (!curseMap.containsKey(type)) {

            boolean stat = false;

            if (p.hasPlayedBefore()) {
                stat = DatabaseFunction.getCurseStat(p, type);
            }

            curseMap.put(type, stat);
            return stat;
        }

        return curseMap.get(type);
    }

    public static void setCurseStat(Player p, String type, boolean v) {
        UUID uuid = p.getUniqueId();

        if (!PlayerMaps.curse.containsKey(uuid)) {
            PlayerMaps.curse.put(uuid, new HashMap<>());
        }
        PlayerMaps.curse.get(uuid).put(type, v);
    }

    public static void loadCurseStat(Player p, String type) {
        UUID uuid = p.getUniqueId();

        if (!PlayerMaps.curse.containsKey(uuid)) {
            PlayerMaps.curse.put(uuid, new HashMap<>());
        }

        Map<String, Boolean> curseMap = PlayerMaps.curse.get(uuid);

        if (!curseMap.containsKey(type)) {
            boolean level = DatabaseFunction.getCurseStat(p, type);
            curseMap.put(type, level);
        }
    }

    public static void updateCurseStat(Player p, String type) {
        UUID uuid = p.getUniqueId();

        if (!PlayerMaps.curse.containsKey(uuid)) {
            PlayerMaps.curse.put(uuid, new HashMap<>());
        }

        boolean b = DatabaseFunction.getCurseStat(p, type);
        PlayerMaps.curse.get(uuid).put(type, b);
    }

    public static String getEquipCurse(Player p, String type) {
        UUID uuid = p.getUniqueId();

        if (!PlayerMaps.equip_curse.containsKey(uuid)) {
            PlayerMaps.equip_curse.put(uuid, new HashMap<>());
        }

        Map<String, String> curseMap = PlayerMaps.equip_curse.get(uuid);

        if (!curseMap.containsKey(type)) {

            String equip = "none";

            if (p.hasPlayedBefore()) {
                equip = DatabaseFunction.getEquipCurse(p, type);
            }

            curseMap.put(type, equip);
            return equip;
        }

        return curseMap.get(type);
    }

    public static void setEquipCurse(Player p, String type, String v) {
        UUID uuid = p.getUniqueId();

        if (!PlayerMaps.equip_curse.containsKey(uuid)) {
            PlayerMaps.equip_curse.put(uuid, new HashMap<>());
        }

        PlayerMaps.equip_curse.get(uuid).put(type, v);
    }

    public static void loadEquipCurse(Player p, String type) {
        UUID uuid = p.getUniqueId();

        if (!PlayerMaps.equip_curse.containsKey(uuid)) {
            PlayerMaps.equip_curse.put(uuid, new HashMap<>());
        }

        Map<String, String> curseMap = PlayerMaps.equip_curse.get(uuid);

        if (!curseMap.containsKey(type)) {
            String level = DatabaseFunction.getEquipCurse(p, type);
            curseMap.put(type, level);
        }
    }

    public static void updateEquipCurse(Player p, String type) {
        UUID uuid = p.getUniqueId();

        if (!PlayerMaps.equip_curse.containsKey(uuid)) {
            PlayerMaps.equip_curse.put(uuid, new HashMap<>());
        }

        String v = DatabaseFunction.getEquipCurse(p, type);
        PlayerMaps.equip_curse.get(uuid).put(type, v);
    }

    public static String getEquipBuff(Player p, String type) {
        UUID uuid = p.getUniqueId();

        if (!PlayerMaps.equip_buff.containsKey(uuid)) {
            PlayerMaps.equip_buff.put(uuid, new HashMap<>());
        }

        Map<String, String> buffMap = PlayerMaps.equip_buff.get(uuid);

        if (!buffMap.containsKey(type)) {

            String equip = "none";

            if (p.hasPlayedBefore()) {
                equip = DatabaseFunction.getEquipBuff(p, type);
            }

            buffMap.put(type, equip);
            return equip;
        }

        return buffMap.get(type);
    }

    public static void setEquipBuff(Player p, String type, String v) {
        UUID uuid = p.getUniqueId();

        if (!PlayerMaps.equip_buff.containsKey(uuid)) {
            PlayerMaps.equip_buff.put(uuid, new HashMap<>());
        }

        PlayerMaps.equip_buff.get(uuid).put(type, v);
    }

    public static void loadEquipBuff(Player p, String type) {
        UUID uuid = p.getUniqueId();

        if (!PlayerMaps.equip_buff.containsKey(uuid)) {
            PlayerMaps.equip_buff.put(uuid, new HashMap<>());
        }

        Map<String, String> curseMap = PlayerMaps.equip_buff.get(uuid);

        if (!curseMap.containsKey(type)) {
            String level = DatabaseFunction.getEquipBuff(p, type);
            curseMap.put(type, level);
        }
    }

    public static void updateEquipBuff(Player p, String type) {
        UUID uuid = p.getUniqueId();

        if (!PlayerMaps.equip_buff.containsKey(uuid)) {
            PlayerMaps.equip_buff.put(uuid, new HashMap<>());
        }

        String v = DatabaseFunction.getEquipBuff(p, type);
        PlayerMaps.equip_buff.get(uuid).put(type, v);
    }

    public static boolean getBuffStat(Player p, String type) {
        UUID uuid = p.getUniqueId();

        if (!PlayerMaps.buff.containsKey(uuid)) {
            PlayerMaps.buff.put(uuid, new HashMap<>());
        }

        Map<String, Boolean> buffMap = PlayerMaps.buff.get(uuid);

        if (!buffMap.containsKey(type)) {

            boolean equip = false;

            if (p.hasPlayedBefore()) {
                equip = DatabaseFunction.getBuffStat(p, type);
            }

            buffMap.put(type, equip);
            return equip;
        }

        return buffMap.get(type);
    }

    public static void setBuffStat(Player p, String type, boolean v) {
        UUID uuid = p.getUniqueId();

        if (!PlayerMaps.buff.containsKey(uuid)) {
            PlayerMaps.buff.put(uuid, new HashMap<>());
        }

        PlayerMaps.buff.get(uuid).put(type, v);
    }

    public static void loadBuffStat(Player p, String type) {
        UUID uuid = p.getUniqueId();

        if (!PlayerMaps.buff.containsKey(uuid)) {
            PlayerMaps.buff.put(uuid, new HashMap<>());
        }

        Map<String, Boolean> buffMap = PlayerMaps.buff.get(uuid);

        if (!buffMap.containsKey(type)) {
            boolean level = DatabaseFunction.getBuffStat(p, type);
            buffMap.put(type, level);
        }
    }

    public static void updateBuffStat(Player p, String type) {
        UUID uuid = p.getUniqueId();

        if (!PlayerMaps.buff.containsKey(uuid)) {
            PlayerMaps.buff.put(uuid, new HashMap<>());
        }

        boolean b = DatabaseFunction.getBuffStat(p, type);
        PlayerMaps.buff.get(uuid).put(type, b);
    }

    public static String getEnchance(Player p, String type) {
        UUID uuid = p.getUniqueId();

        if (!PlayerMaps.enchance.containsKey(uuid)) {
            PlayerMaps.enchance.put(uuid, new HashMap<>());
        }

        Map<String, String> enchanceMap = PlayerMaps.enchance.get(uuid);

        if (!enchanceMap.containsKey(type)) {

            String equip = "none";

            if (p.hasPlayedBefore()) {
                equip = DatabaseFunction.getEnchance(p, type);
            }

            enchanceMap.put(type, equip);
            return equip;
        }

        return enchanceMap.get(type);
    }

    public static void setEnchance(Player p, String type, String v) {
        UUID uuid = p.getUniqueId();

        if (!PlayerMaps.enchance.containsKey(uuid)) {
            PlayerMaps.enchance.put(uuid, new HashMap<>());
        }

        PlayerMaps.enchance.get(uuid).put(type, v);
    }

    public static void loadEnchance(Player p, String type) {
        UUID uuid = p.getUniqueId();

        if (!PlayerMaps.enchance.containsKey(uuid)) {
            PlayerMaps.enchance.put(uuid, new HashMap<>());
        }

        Map<String, String> enchanceMap = PlayerMaps.enchance.get(uuid);

        if (!enchanceMap.containsKey(type)) {
            String level = DatabaseFunction.getEnchance(p, type);
            enchanceMap.put(type, level);
        }
    }

    public static void updateEnchance(Player p, String type) {
        UUID uuid = p.getUniqueId();

        if (!PlayerMaps.enchance.containsKey(uuid)) {
            PlayerMaps.enchance.put(uuid, new HashMap<>());
        }

        String v = DatabaseFunction.getEnchance(p, type);
        PlayerMaps.enchance.get(uuid).put(type, v);
    }
}