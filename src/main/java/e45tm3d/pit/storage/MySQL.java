package e45tm3d.pit.storage;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.enums.Yaml;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.logging.Logger;
import java.util.logging.Level;

public class MySQL {

    private static String getConfigValue(String path) {
        String value = Yaml.CONFIG.getConfig().getString(path);
        if (value == null) {
            Logger.getLogger(MySQL.class.getName()).log(Level.WARNING, "Missing configuration value for: {0}", path);
        }
        return value;
    }

    private static String getHost() {
        return getConfigValue("storage.mysql.host");
    }

    private static int getPort() {
        return Yaml.CONFIG.getConfig().getInt("storage.mysql.port", 3306);
    }

    private static String getDatabase() {
        return getConfigValue("storage.mysql.database");
    }

    private static String getUsername() {
        return getConfigValue("storage.mysql.username");
    }

    private static String getPassword() {
        return getConfigValue("storage.mysql.password");
    }

    private static String getProperty() {
        return getConfigValue("storage.mysql.property");
    }

    private static String getUrl() {
        return "jdbc:mysql://" + getHost() + ":" + getPort() + "/" + getDatabase() + "?" + getProperty();
    }

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (Exception e) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, "Failed to register MySQL driver", e);
            throw new RuntimeException(e);
        }
    }

    public static void createTable() {
        try {
            if (checkDatabaseExists()) {
                Connection connection = DriverManager.getConnection(getUrl(), getUsername(), getPassword());
                Statement statement = connection.createStatement();

                String createPlayerInfoTable = "CREATE TABLE IF NOT EXISTS " + getDatabase() + ".player_info ("
                        + "uuid VARCHAR(50) UNIQUE PRIMARY KEY"
                        + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";

                String createArmorTable = "CREATE TABLE IF NOT EXISTS " + getDatabase() + ".player_armor ("
                        + "uuid VARCHAR(50) UNIQUE PRIMARY KEY"
                        + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";

                String createSwordTable = "CREATE TABLE IF NOT EXISTS " + getDatabase() + ".player_weapon ("
                        + "uuid VARCHAR(50) UNIQUE PRIMARY KEY"
                        + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";

                String createCurseTable = "CREATE TABLE IF NOT EXISTS " + getDatabase() + ".player_curses ("
                        + "uuid VARCHAR(50) UNIQUE PRIMARY KEY"
                        + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";

                String createEquipCurseTable = "CREATE TABLE IF NOT EXISTS " + getDatabase() + ".player_equip_curses ("
                        + "uuid VARCHAR(50) UNIQUE PRIMARY KEY"
                        + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";

                String createBuffTable = "CREATE TABLE IF NOT EXISTS " + getDatabase() + ".player_buffs ("
                        + "uuid VARCHAR(50) UNIQUE PRIMARY KEY"
                        + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";

                String createEquipBuffTable = "CREATE TABLE IF NOT EXISTS " + getDatabase() + ".player_equip_buffs ("
                        + "uuid VARCHAR(50) UNIQUE PRIMARY KEY"
                        + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";

                String createEnchanceTable = "CREATE TABLE IF NOT EXISTS " + getDatabase() + ".player_enchance ("
                        + "uuid VARCHAR(50) UNIQUE PRIMARY KEY"
                        + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";

                statement.execute(createPlayerInfoTable);
                statement.execute(createArmorTable);
                statement.execute(createSwordTable);
                statement.execute(createCurseTable);
                statement.execute(createEquipCurseTable);
                statement.execute(createBuffTable);
                statement.execute(createEquipBuffTable);
                statement.execute(createEnchanceTable);
            } else {
                Bukkit.getPluginManager().disablePlugin(ThePit.getInstance());
                Bukkit.getLogger().severe("Failed to create MySQL connection. Disabling plugin.");
            }
        } catch (Exception e) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, "Error creating tables", e);
            throw new RuntimeException(e);
        }
    }

    private static String formatField(String field) {
        return "`" + field + "`";
    }

    // 获取数据库连接（简化调用）
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(getUrl(), getUsername(), getPassword());
    }

    // 合并字段存在性检查，避免重复连接
    private static void ensureFieldExists(String table, String field, String fieldType, Object defaultValue) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(
                    String.format(
                            "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = '%s' AND TABLE_NAME = '%s' AND COLUMN_NAME = '%s'",
                            getDatabase(), table, field
                    )
            );
            if (!resultSet.next()) {
                String defaultVal = (defaultValue instanceof String)
                        ? String.format("DEFAULT '%s'", defaultValue)
                        : String.format("DEFAULT %s", defaultValue);
                statement.executeUpdate(
                        String.format("ALTER TABLE %s.%s ADD COLUMN %s %s %s",
                                getDatabase(), table, formatField(field), fieldType, defaultVal)
                );
            }
        } catch (Exception e) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, "Failed to ensure field exists: " + field + " in table: " + table, e);
            throw new RuntimeException(e);
        }
    }

    // 合并玩家存在性检查
    private static void ensurePlayerExists(String table, Player p) {
        try (Connection connection = getConnection()) {
            try (PreparedStatement checkStatement = connection.prepareStatement(
                    String.format("SELECT 1 FROM %s.%s WHERE uuid = ?", getDatabase(), table))) {
                checkStatement.setString(1, p.getUniqueId().toString());
                try (ResultSet resultSet = checkStatement.executeQuery()) {
                    if (!resultSet.next()) {
                        try (PreparedStatement insertStatement = connection.prepareStatement(
                                String.format("INSERT INTO %s.%s (uuid) VALUES (?)", getDatabase(), table))) {
                            insertStatement.setString(1, p.getUniqueId().toString());
                            insertStatement.executeUpdate();
                        }
                    }
                }
            }
        } catch (Exception e) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, "Failed to ensure player exists in table: " + table, e);
            throw new RuntimeException(e);
        }
    }

    // 统一字段检查调用
    private static void ensureFieldAndPlayer(String table, String field, String fieldType, Object defaultValue, Player p) {
        ensureFieldExists(table, field, fieldType, defaultValue);
        ensurePlayerExists(table, p);
    }

    public static int getWeaponLevel(Player p, String type) {

        ensureFieldAndPlayer("player_weapon", type, "INT", type.equals("wooden_sword") ? 1 : 0, p);
        int l = 0;
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     String.format("SELECT %s FROM %s.player_weapon WHERE uuid = ?", formatField(type), getDatabase()))) {
            ps.setString(1, p.getUniqueId().toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) l = rs.getInt(1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return l;
    }

    public static void setWeaponLevel(Player p, String type, int level) {
        ensureFieldAndPlayer("player_weapon", type, "INT", type.equals("wooden_sword") ? 1 : 0, p);
        try (Connection connection = getConnection()) {
            // 先尝试更新
            PreparedStatement update = connection.prepareStatement(
                "UPDATE " + getDatabase() + ".player_weapon SET " + formatField(type) + " = ? WHERE uuid = ?"
            );
            update.setInt(1, level);
            update.setString(2, p.getUniqueId().toString());
            int affected = update.executeUpdate();
            update.close();
            // 如果没有行被更新，则插入
            if (affected == 0) {
                PreparedStatement insert = connection.prepareStatement(
                    "INSERT INTO " + getDatabase() + ".player_weapon (uuid, " + formatField(type) + ") VALUES (?, ?)"
                );
                insert.setString(1, p.getUniqueId().toString());
                insert.setInt(2, level);
                insert.executeUpdate();
                insert.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkDatabaseExists() {
        boolean b;
        try (Connection conn = DriverManager.getConnection(getUrl(), getUsername(), getPassword());
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SHOW DATABASES LIKE '" + getDatabase() + "'")) {
            b = rs.next();
            return b;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getEquipedBuff(Player p, String type) {

        ensureFieldExists("player_equip_buffs", type, "VARCHAR(50)", "none");

        ensurePlayerExists("player_equip_buffs", p);

        String l = "";
        try (Connection connection = DriverManager.getConnection(getUrl(), getUsername(), getPassword());
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT " + formatField(type) + " FROM " + getDatabase() + ".player_equip_buffs WHERE uuid = '" + p.getUniqueId().toString() + "'")) {

            if (resultSet.next()) {
                l = resultSet.getString(1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return l;
    }

    public static void setEquipedBuff(Player p, String type, String value) {
        ensureFieldExists("player_equip_buffs", type, "VARCHAR(50)", "none");
        ensurePlayerExists("player_equip_buffs", p);
        try (Connection connection = getConnection()) {
            PreparedStatement update = connection.prepareStatement(
                "UPDATE " + getDatabase() + ".player_equip_buffs SET " + formatField(type) + " = ? WHERE uuid = ?"
            );
            update.setString(1, value);
            update.setString(2, p.getUniqueId().toString());
            int affected = update.executeUpdate();
            update.close();
            if (affected == 0) {
                PreparedStatement insert = connection.prepareStatement(
                    "INSERT INTO " + getDatabase() + ".player_equip_buffs (uuid, " + formatField(type) + ") VALUES (?, ?)"
                );
                insert.setString(1, p.getUniqueId().toString());
                insert.setString(2, value);
                insert.executeUpdate();
                insert.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getEnchance(Player p, String type) {

        ensureFieldExists("player_enchance", type, "VARCHAR(50)", "none");

        ensurePlayerExists("player_enchance", p);

        String l = "";
        try (Connection connection = DriverManager.getConnection(getUrl(), getUsername(), getPassword());
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT " + formatField(type) + " FROM " + getDatabase() + ".player_enchance WHERE uuid = '" + p.getUniqueId().toString() + "'")) {

            if (resultSet.next()) {
                l = resultSet.getString(1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return l;
    }

    public static void setEnchance(Player p, String type, String value) {
        ensureFieldExists("player_enchance", type, "VARCHAR(50)", "none");
        ensurePlayerExists("player_enchance", p);
        try (Connection connection = getConnection()) {
            PreparedStatement update = connection.prepareStatement(
                "UPDATE " + getDatabase() + ".player_enchance SET " + formatField(type) + " = ? WHERE uuid = ?"
            );
            update.setString(1, value);
            update.setString(2, p.getUniqueId().toString());
            int affected = update.executeUpdate();
            update.close();
            if (affected == 0) {
                PreparedStatement insert = connection.prepareStatement(
                    "INSERT INTO " + getDatabase() + ".player_enchance (uuid, " + formatField(type) + ") VALUES (?, ?)"
                );
                insert.setString(1, p.getUniqueId().toString());
                insert.setString(2, value);
                insert.executeUpdate();
                insert.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean getBuffStat(Player p, String type) {

        ensureFieldExists("player_buffs", type, "BOOLEAN", 0);

        ensurePlayerExists("player_buffs", p);

        boolean l = false;
        try (Connection connection = DriverManager.getConnection(getUrl(), getUsername(), getPassword());
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM " + getDatabase() + ".player_buffs WHERE uuid = '" + p.getUniqueId().toString() + "'")) {

            if (resultSet.next()) {
                l = resultSet.getBoolean(type);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return l;
    }

    public static void setBuffStat(Player p, String type, boolean value) {
        ensureFieldExists("player_buffs", type, "BOOLEAN", 0);
        ensurePlayerExists("player_buffs", p);
        try (Connection connection = getConnection()) {
            PreparedStatement update = connection.prepareStatement(
                "UPDATE " + getDatabase() + ".player_buffs SET " + formatField(type) + " = ? WHERE uuid = ?"
            );
            update.setBoolean(1, value);
            update.setString(2, p.getUniqueId().toString());
            int affected = update.executeUpdate();
            update.close();
            if (affected == 0) {
                PreparedStatement insert = connection.prepareStatement(
                    "INSERT INTO " + getDatabase() + ".player_buffs (uuid, " + formatField(type) + ") VALUES (?, ?)"
                );
                insert.setString(1, p.getUniqueId().toString());
                insert.setBoolean(2, value);
                insert.executeUpdate();
                insert.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getEquipedCurse(Player p, String type) {

        ensureFieldExists("player_equip_curses", type, "VARCHAR(50)", "none");

        ensurePlayerExists("player_equip_curses", p);

        String l = "";
        try (Connection connection = DriverManager.getConnection(getUrl(), getUsername(), getPassword());
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT " + formatField(type) + " FROM " + getDatabase() + ".player_equip_curses WHERE uuid = '" + p.getUniqueId().toString() + "'")) {

            if (resultSet.next()) {
                l = resultSet.getString(1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return l;
    }

    public static void setEquipedCurse(Player p, String type, String value) {
        ensureFieldExists("player_equip_curses", type, "VARCHAR(50)", "none");
        ensurePlayerExists("player_equip_curses", p);
        try (Connection connection = getConnection()) {
            PreparedStatement update = connection.prepareStatement(
                "UPDATE " + getDatabase() + ".player_equip_curses SET " + formatField(type) + " = ? WHERE uuid = ?"
            );
            update.setString(1, value);
            update.setString(2, p.getUniqueId().toString());
            int affected = update.executeUpdate();
            update.close();
            if (affected == 0) {
                PreparedStatement insert = connection.prepareStatement(
                    "INSERT INTO " + getDatabase() + ".player_equip_curses (uuid, " + formatField(type) + ") VALUES (?, ?)"
                );
                insert.setString(1, p.getUniqueId().toString());
                insert.setString(2, value);
                insert.executeUpdate();
                insert.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean getCurseStat(Player p, String type) {

        ensureFieldExists("player_curses", type, "BOOLEAN", 0);

        ensurePlayerExists("player_curses", p);

        boolean l = false;
        try (Connection connection = DriverManager.getConnection(getUrl(), getUsername(), getPassword());
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT * FROM " + getDatabase() + ".player_curses WHERE uuid = '" + p.getUniqueId().toString() + "'")) {

            if (resultSet.next()) {
                l = resultSet.getBoolean(type);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return l;
    }

    public static void setCurseStat(Player p, String type, boolean value) {
        ensureFieldExists("player_curses", type, "BOOLEAN", 0);
        ensurePlayerExists("player_curses", p);
        try (Connection connection = getConnection()) {
            PreparedStatement update = connection.prepareStatement(
                "UPDATE " + getDatabase() + ".player_curses SET " + formatField(type) + " = ? WHERE uuid = ?"
            );
            update.setBoolean(1, value);
            update.setString(2, p.getUniqueId().toString());
            int affected = update.executeUpdate();
            update.close();
            if (affected == 0) {
                PreparedStatement insert = connection.prepareStatement(
                    "INSERT INTO " + getDatabase() + ".player_curses (uuid, " + formatField(type) + ") VALUES (?, ?)"
                );
                insert.setString(1, p.getUniqueId().toString());
                insert.setBoolean(2, value);
                insert.executeUpdate();
                insert.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int getArmorLevel(Player p, String type) {

        ensureFieldExists("player_armor", type, "INT", 0);

        ensurePlayerExists("player_armor", p);

        int l = 0;
        try (Connection connection = DriverManager.getConnection(getUrl(), getUsername(), getPassword());
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT * FROM " + getDatabase() + ".player_armor WHERE uuid = '" + p.getUniqueId().toString() + "'")) {

            if (resultSet.next()) {
                l = resultSet.getInt(type);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return l;
    }

    public static int getPlayerInfo(Player p, String type) {

        ensureFieldExists("player_info", type, "INT", 0);

        ensurePlayerExists("player_info", p);

        int v = 0;
        try (Connection connection = DriverManager.getConnection(getUrl(), getUsername(), getPassword());
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT * FROM " + getDatabase() + ".player_info WHERE uuid = '" + p.getUniqueId().toString() + "'")) {

            if (resultSet.next()) {
                v = resultSet.getInt(type);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return v;
    }

    public static void setArmorLevel(Player p, String type, int level) {
        ensureFieldExists("player_armor", type, "INT", 0);
        ensurePlayerExists("player_armor", p);
        try (Connection connection = getConnection()) {
            PreparedStatement update = connection.prepareStatement(
                "UPDATE " + getDatabase() + ".player_armor SET " + formatField(type) + " = ? WHERE uuid = ?"
            );
            update.setInt(1, level);
            update.setString(2, p.getUniqueId().toString());
            int affected = update.executeUpdate();
            update.close();
            if (affected == 0) {
                PreparedStatement insert = connection.prepareStatement(
                    "INSERT INTO " + getDatabase() + ".player_armor (uuid, " + formatField(type) + ") VALUES (?, ?)"
                );
                insert.setString(1, p.getUniqueId().toString());
                insert.setInt(2, level);
                insert.executeUpdate();
                insert.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void setPlayerInfo(Player p, String type, int value) {
        ensureFieldExists("player_info", type, "INT", 0);
        ensurePlayerExists("player_info", p);
        try (Connection connection = getConnection()) {
            PreparedStatement update = connection.prepareStatement(
                "UPDATE " + getDatabase() + ".player_info SET " + formatField(type) + " = ? WHERE uuid = ?"
            );
            update.setInt(1, value);
            update.setString(2, p.getUniqueId().toString());
            int affected = update.executeUpdate();
            update.close();
            if (affected == 0) {
                PreparedStatement insert = connection.prepareStatement(
                    "INSERT INTO " + getDatabase() + ".player_info (uuid, " + formatField(type) + ") VALUES (?, ?)"
                );
                insert.setString(1, p.getUniqueId().toString());
                insert.setInt(2, value);
                insert.executeUpdate();
                insert.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

