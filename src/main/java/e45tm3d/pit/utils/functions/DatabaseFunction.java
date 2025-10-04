package e45tm3d.pit.utils.functions;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.storage.MySQL;
import e45tm3d.pit.api.User;
import e45tm3d.pit.storage.SQLite;
import e45tm3d.pit.utils.lists.BuffLists;
import e45tm3d.pit.utils.lists.CurseLists;
import e45tm3d.pit.utils.lists.ItemLists;
import e45tm3d.pit.utils.maps.PlayerMaps;
import org.bukkit.entity.Player;

import java.util.UUID;

public class DatabaseFunction {

    public static int getKills(Player p) {
        if (Yaml.CONFIG.getConfig().getString("storage.type").equals("mysql")) {
            return MySQL.getPlayerInfo(p, "kills");
        } else if (Yaml.CONFIG.getConfig().getString("storage.type").equals("sqlite")) {
            return SQLite.getPlayerInfo(p, "kills");
        }
        return 0;
    }

    public static void setKills(Player p, int k) {
        if (Yaml.CONFIG.getConfig().getString("storage.type").equals("mysql")) {
            MySQL.setPlayerInfo(p, "kills", k);
        } else if (Yaml.CONFIG.getConfig().getString("storage.type").equals("sqlite")) {
            SQLite.setPlayerInfo(p, "kills", k);
        }
        User.updateKills(p);
    }

    public static int getDeaths(Player p) {
        if (Yaml.CONFIG.getConfig().getString("storage.type").equals("mysql")) {
            return MySQL.getPlayerInfo(p, "deaths");
        } else if (Yaml.CONFIG.getConfig().getString("storage.type").equals("sqlite")) {
            return SQLite.getPlayerInfo(p, "deaths");
        }
        return 0;
    }

    public static void setDeaths(Player p, int k) {
        if (Yaml.CONFIG.getConfig().getString("storage.type").equals("mysql")) {
            MySQL.setPlayerInfo(p, "deaths", k);
        } else if (Yaml.CONFIG.getConfig().getString("storage.type").equals("sqlite")) {
            SQLite.setPlayerInfo(p, "deaths", k);
        }
        User.updateDeaths(p);
    }

    public static int getKillstreak(Player p) {
        if (Yaml.CONFIG.getConfig().getString("storage.type").equals("mysql")) {
            return MySQL.getPlayerInfo(p, "killstreak");
        } else if (Yaml.CONFIG.getConfig().getString("storage.type").equals("sqlite")) {
            return SQLite.getPlayerInfo(p, "killstreak");
        }
        return 0;
    }

    public static void setKillstreak(Player p, int k) {
        if (Yaml.CONFIG.getConfig().getString("storage.type").equals("mysql")) {
            MySQL.setPlayerInfo(p, "killstreak", k);
        } else if (Yaml.CONFIG.getConfig().getString("storage.type").equals("sqlite")) {
            SQLite.setPlayerInfo(p, "killstreak", k);
        }
        User.updateKillstreak(p);
    }

    public static int getHelmetLevel(Player p) {
        if (Yaml.CONFIG.getConfig().getString("storage.type").equals("mysql")) {
            return MySQL.getArmorLevel(p, "helmet");
        } else if (Yaml.CONFIG.getConfig().getString("storage.type").equals("sqlite")) {
            return SQLite.getArmorLevel(p, "helmet");
        }
        return 0;
    }

    public static void setHelmetLevel(Player p, int level) {
        if (Yaml.CONFIG.getConfig().getString("storage.type").equals("mysql")) {
            MySQL.setArmorLevel(p, "helmet", level);
        } else if (Yaml.CONFIG.getConfig().getString("storage.type").equals("sqlite")) {
            SQLite.setArmorLevel(p, "helmet", level);
        }
        User.updateHelmetLevel(p);
    }

    public static int getChestplateLevel(Player p) {
        if (Yaml.CONFIG.getConfig().getString("storage.type").equals("mysql")) {
            return MySQL.getArmorLevel(p, "chestplate");
        } else if (Yaml.CONFIG.getConfig().getString("storage.type").equals("sqlite")) {
            return SQLite.getArmorLevel(p, "chestplate");
        }
        return 0;
    }

    public static void setChestplateLevel(Player p, int level) {
        if (Yaml.CONFIG.getConfig().getString("storage.type").equals("mysql")) {
            MySQL.setArmorLevel(p, "chestplate", level);
        } else if (Yaml.CONFIG.getConfig().getString("storage.type").equals("sqlite")) {
            SQLite.setArmorLevel(p, "chestplate", level);
        }
        User.updateChestplateLevel(p);
    }

    public static int getLeggingsLevel(Player p) {
        if (Yaml.CONFIG.getConfig().getString("storage.type").equals("mysql")) {
            return MySQL.getArmorLevel(p, "leggings");
        } else if (Yaml.CONFIG.getConfig().getString("storage.type").equals("sqlite")) {
            return SQLite.getArmorLevel(p, "leggings");
        }
        return 0;
    }

    public static void setLeggingsLevel(Player p, int level) {
        if (Yaml.CONFIG.getConfig().getString("storage.type").equals("mysql")) {
            MySQL.setArmorLevel(p, "leggings", level);
        } else if (Yaml.CONFIG.getConfig().getString("storage.type").equals("sqlite")) {
            SQLite.setArmorLevel(p, "leggings", level);
        }
        User.updateLeggingsLevel(p);
    }

    public static int getBootsLevel(Player p) {
        if (Yaml.CONFIG.getConfig().getString("storage.type").equals("mysql")) {
            return MySQL.getArmorLevel(p, "boots");
        } else if (Yaml.CONFIG.getConfig().getString("storage.type").equals("sqlite")) {
            return SQLite.getArmorLevel(p, "boots");
        }
        return 0;
    }

    public static void setBootsLevel(Player p, int level) {
        if (Yaml.CONFIG.getConfig().getString("storage.type").equals("mysql")) {
            MySQL.setArmorLevel(p, "boots", level);
        } else if (Yaml.CONFIG.getConfig().getString("storage.type").equals("sqlite")) {
            SQLite.setArmorLevel(p, "boots", level);
        }
        User.updateBootsLevel(p);
    }
    public static int getWeaponLevel(Player p, String type) {
            if (Yaml.CONFIG.getConfig().getString("storage.type").equals("mysql")) {
                return MySQL.getWeaponLevel(p, type);
            } else if (Yaml.CONFIG.getConfig().getString("storage.type").equals("sqlite")) {
                return SQLite.getWeaponLevel(p, type);
            }
        return 0;
    }

    public static void setWeaponLevel(Player p, String type, int level) {
        if (Yaml.CONFIG.getConfig().getString("storage.type").equals("mysql")) {
            MySQL.setWeaponLevel(p, type, level);
        } else if (Yaml.CONFIG.getConfig().getString("storage.type").equals("sqlite")) {
            SQLite.setWeaponLevel(p, type, level);
        }
        User.updateWeaponLevel(p, type);
    }

    public static int getLevel(Player p) {
        e45tm3d.api.User user = new e45tm3d.api.User(p);
        return user.getLevel("pit");
    }

    public static void setLevel(Player p, int level) {
        e45tm3d.api.User user = new e45tm3d.api.User(p);
        user.setLevel("pit", level);
    }

    public static int getExp(Player p) {
        e45tm3d.api.User user = new e45tm3d.api.User(p);
        return user.getExp("pit");
    }

    public static void setExp(Player p, int exp) {
        e45tm3d.api.User user = new e45tm3d.api.User(p);
        user.setExp("pit", exp);
    }
public static boolean getCurseStat(Player p, String type) {
        if (Yaml.CONFIG.getConfig().getString("storage.type").equals("mysql")) {
            return MySQL.getCurseStat(p, type);
        } else if (Yaml.CONFIG.getConfig().getString("storage.type").equals("sqlite")) {
            return SQLite.getCurseStat(p, type);
        }
        return false;
    }

    public static void setCurseStat(Player p, String type, boolean value) {
        if (Yaml.CONFIG.getConfig().getString("storage.type").equals("mysql")) {
            MySQL.setCurseStat(p, type, value);
        } else if (Yaml.CONFIG.getConfig().getString("storage.type").equals("sqlite")) {
            SQLite.setCurseStat(p, type, value);
        }
    }

    public static String getEquipCurse(Player p, String type) {
        if (Yaml.CONFIG.getConfig().getString("storage.type").equals("mysql")) {
            return MySQL.getEquipedCurse(p, type);
        } else if (Yaml.CONFIG.getConfig().getString("storage.type").equals("sqlite")) {
            return SQLite.getEquipedCurse(p, type);
        }
        return "";
    }

    public static void setEquipCurse(Player p, String type, String value) {
        if (Yaml.CONFIG.getConfig().getString("storage.type").equals("mysql")) {
            MySQL.setEquipedCurse(p, type, value);
        } else if (Yaml.CONFIG.getConfig().getString("storage.type").equals("sqlite")) {
            SQLite.setEquipedCurse(p, type, value);
        }
    }

    public static boolean getBuffStat(Player p, String type) {
        if (Yaml.CONFIG.getConfig().getString("storage.type").equals("mysql")) {
            return MySQL.getBuffStat(p, type);
        } else if (Yaml.CONFIG.getConfig().getString("storage.type").equals("sqlite")) {
            return SQLite.getBuffStat(p, type);
        }
        return false;
    }

    public static void setBuffStat(Player p, String type, boolean value) {
        if (Yaml.CONFIG.getConfig().getString("storage.type").equals("mysql")) {
            MySQL.setBuffStat(p, type, value);
        } else if (Yaml.CONFIG.getConfig().getString("storage.type").equals("sqlite")) {
            SQLite.setBuffStat(p, type, value);
        }
    }

    public static String getEquipBuff(Player p, String type) {
        if (Yaml.CONFIG.getConfig().getString("storage.type").equals("mysql")) {
            return MySQL.getEquipedBuff(p, type);
        } else if (Yaml.CONFIG.getConfig().getString("storage.type").equals("sqlite")) {
            return SQLite.getEquipedBuff(p, type);
        }
        return "";
    }

    public static void setEquipBuff(Player p, String type, String value) {
        if (Yaml.CONFIG.getConfig().getString("storage.type").equals("mysql")) {
            MySQL.setEquipedBuff(p, type, value);
        } else if (Yaml.CONFIG.getConfig().getString("storage.type").equals("sqlite")) {
            SQLite.setEquipedBuff(p, type, value);
        }
    }

    public static String getEnchance(Player p, String type) {
        if (Yaml.CONFIG.getConfig().getString("storage.type").equals("mysql")) {
            return MySQL.getEnchance(p, type);
        } else if (Yaml.CONFIG.getConfig().getString("storage.type").equals("sqlite")) {
            return SQLite.getEnchance(p, type);
        }
        return "";
    }

    public static void setEnchance(Player p, String type, String value) {
        if (Yaml.CONFIG.getConfig().getString("storage.type").equals("mysql")) {
            MySQL.setEnchance(p, type, value);
        } else if (Yaml.CONFIG.getConfig().getString("storage.type").equals("sqlite")) {
            SQLite.setEnchance(p, type, value);
        }
    }

    public static void saveDatabase(Player p) {

        ThePit.getInstance().getLogger().info("Saving database for " + p.getName());

        setKills(p, User.getKills(p));
        setDeaths(p, User.getDeaths(p));
        setKillstreak(p, User.getKillstreak(p));
        setHelmetLevel(p, User.getHelmetLevel(p));
        setChestplateLevel(p, User.getChestplateLevel(p));
        setLeggingsLevel(p, User.getLeggingsLevel(p));
        setBootsLevel(p, User.getBootsLevel(p));

        setEquipCurse(p, "slot_1", User.getEquipCurse(p, "slot_1"));
        setEquipCurse(p, "slot_2", User.getEquipCurse(p, "slot_2"));
        setEquipCurse(p, "slot_3", User.getEquipCurse(p, "slot_3"));

        setEquipBuff(p, "slot_1", User.getEquipBuff(p, "slot_1"));
        setEquipBuff(p, "slot_2", User.getEquipBuff(p, "slot_2"));
        setEquipBuff(p, "slot_3", User.getEquipBuff(p, "slot_3"));
        setEquipBuff(p, "slot_4", User.getEquipBuff(p, "slot_4"));
        setEquipBuff(p, "slot_5", User.getEquipBuff(p, "slot_5"));

        setEnchance(p, "helmet", User.getEnchance(p, "helmet"));
        setEnchance(p, "chestplate", User.getEnchance(p, "chestplate"));
        setEnchance(p, "leggings", User.getEnchance(p, "leggings"));
        setEnchance(p, "boots", User.getEnchance(p, "boots"));
        setEnchance(p, "weapon", User.getEnchance(p, "weapon"));

        for (String weapon : ItemLists.weapons) {
            setWeaponLevel(p, weapon, User.getWeaponLevel(p, weapon));
        }

        for (String curse : CurseLists.curse) {
            setCurseStat(p, curse, User.getCurseStat(p, curse));
        }

        for (String buff : BuffLists.buff) {
            setBuffStat(p, buff, User.getBuffStat(p, buff));
        }

        setLevel(p, User.getLevel(p));
        setExp(p, User.getExp(p));

        ThePit.getInstance().getLogger().info("Successfully saved Database for " + p.getName());

    }

    public static void setupDatabaseInRam(Player p) {

        User.loadKills(p);
        User.loadDeaths(p);
        User.loadKillstreak(p);
        User.loadHelmetLevel(p);
        User.loadChestplateLevel(p);
        User.loadLeggingsLevel(p);
        User.loadBootsLevel(p);

        User.loadEquipCurse(p, "slot_1");
        User.loadEquipCurse(p, "slot_2");
        User.loadEquipCurse(p, "slot_3");

        User.loadEquipBuff(p, "slot_1");
        User.loadEquipBuff(p, "slot_2");
        User.loadEquipBuff(p, "slot_3");
        User.loadEquipBuff(p, "slot_4");
        User.loadEquipBuff(p, "slot_5");

        User.loadEnchance(p, "helmet");
        User.loadEnchance(p, "chestplate");
        User.loadEnchance(p, "leggings");
        User.loadEnchance(p, "boots");
        User.loadEnchance(p, "weapon");

        for (String weapon : ItemLists.weapons) {
            User.loadWeaponLevel(p, weapon);
        }
        for (String curse : CurseLists.curse) {
            User.loadCurseStat(p, curse);
        }

        for (String buff : BuffLists.buff) {
            User.loadBuffStat(p, buff);
        }

        User.loadLevel(p);
        User.loadExp(p);
    }
}