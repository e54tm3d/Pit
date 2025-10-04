package e45tm3d.pit.storage;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.enums.Yaml;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.logging.Logger;
import java.io.File;
import java.util.logging.Level;

public class SQLite {

    private static String getConfigValue(String path) {
        String value = Yaml.CONFIG.getConfig().getString(path);
        if (value == null) {
            Logger.getLogger(SQLite.class.getName()).log(Level.WARNING, "Missing configuration value for: {0}", path);
        }
        return value;
    }

    private static String getUrl() {
        File dataFolder = ThePit.getInstance().getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        File databaseFile = new File(dataFolder, "database.db");
        return "jdbc:sqlite:" + databaseFile.getAbsolutePath();
    }

    private static final String JDBC_DRIVER = "org.sqlite.JDBC";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (Exception e) {
            Logger.getLogger(SQLite.class.getName()).log(Level.SEVERE, "Failed to register SQLite driver", e);
            throw new RuntimeException(e);
        }
    }

    private static void ensureTablesExist() {
        try (Connection connection = DriverManager.getConnection(getUrl());
             Statement statement = connection.createStatement()) {

            String createPlayerInfoTable = "CREATE TABLE IF NOT EXISTS player_info ("
                    + "uuid VARCHAR(50) UNIQUE PRIMARY KEY "
                    + ")";

            String createArmorTable = "CREATE TABLE IF NOT EXISTS player_armor ("
                    + "uuid VARCHAR(50) UNIQUE PRIMARY KEY "
                    + ")";

            String createWeaponTable = "CREATE TABLE IF NOT EXISTS player_weapon ("
                    + "uuid VARCHAR(50) UNIQUE PRIMARY KEY "
                    + ")";

            String createCurseTable = "CREATE TABLE IF NOT EXISTS player_curses ("
                    + "uuid VARCHAR(50) UNIQUE PRIMARY KEY "
                    + ")";

            String createEquipCurseTable = "CREATE TABLE IF NOT EXISTS player_equip_curses ("
                    + "uuid VARCHAR(50) UNIQUE PRIMARY KEY "
                    + ")";

            String createBuffTable = "CREATE TABLE IF NOT EXISTS player_buffs ("
                    + "uuid VARCHAR(50) UNIQUE PRIMARY KEY "
                    + ")";

            String createEquipBuffTable = "CREATE TABLE IF NOT EXISTS player_equip_buffs ("
                    + "uuid VARCHAR(50) UNIQUE PRIMARY KEY "
                    + ")";

            String createEnchanceTable = "CREATE TABLE IF NOT EXISTS player_enchance ("
                    + "uuid VARCHAR(50) UNIQUE PRIMARY KEY "
                    + ")";

            statement.execute(createPlayerInfoTable);
            statement.execute(createArmorTable);
            statement.execute(createWeaponTable);
            statement.execute(createCurseTable);
            statement.execute(createEquipCurseTable);
            statement.execute(createBuffTable);
            statement.execute(createEquipBuffTable);
            statement.execute(createEnchanceTable);

        } catch (Exception e) {
            Logger.getLogger(SQLite.class.getName()).log(Level.SEVERE, "Error ensuring tables exist", e);
            throw new RuntimeException(e);
        }
    }

    public static void createTable() {
        try {
            if (checkDatabaseExists()) {
                Logger.getLogger(SQLite.class.getName()).log(Level.INFO, "Attempting connection with URL: {0}", getUrl());
                ensureTablesExist();
            } else {
                Bukkit.getPluginManager().disablePlugin(ThePit.getInstance());
                Bukkit.getLogger().severe("Failed to create SQLite connection. Disabling plugin.");
            }
        } catch (Exception e) {
            Logger.getLogger(SQLite.class.getName()).log(Level.SEVERE, "Error creating tables", e);
            throw new RuntimeException(e);
        }
    }

    private static String formatField(String field) {
        return '"' + field + '"';
    }

    // 获取数据库连接
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(getUrl());
    }

    // 合并字段存在性检查
    private static void ensureFieldExists(String table, String field, String fieldType, Object defaultValue) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            boolean fieldExists;
            try (ResultSet resultSet = connection.getMetaData().getColumns(null, null, table, field)) {
                fieldExists = resultSet.next();
            }
            if (!fieldExists) {
                String defaultVal = (defaultValue instanceof String)
                        ? String.format("DEFAULT '%s'", defaultValue)
                        : String.format("DEFAULT %s", defaultValue);
                statement.executeUpdate(
                    String.format("ALTER TABLE %s ADD COLUMN %s %s %s",
                        table, formatField(field), fieldType, defaultVal)
                );
            }
        } catch (Exception e) {
            Logger.getLogger(SQLite.class.getName()).log(Level.SEVERE, "Failed to ensure field exists: " + field + " in table: " + table, e);
            throw new RuntimeException(e);
        }
    }

    // 合并玩家存在性检查
    private static void ensurePlayerExists(String table, Player p) {
        try (Connection connection = getConnection()) {
            try (PreparedStatement checkStatement = connection.prepareStatement(
                    String.format("SELECT 1 FROM %s WHERE uuid = ?", table))) {
                checkStatement.setString(1, p.getUniqueId().toString());
                try (ResultSet resultSet = checkStatement.executeQuery()) {
                    if (!resultSet.next()) {
                        try (PreparedStatement insertStatement = connection.prepareStatement(
                                String.format("INSERT INTO %s (uuid) VALUES (?)", table))) {
                            insertStatement.setString(1, p.getUniqueId().toString());
                            insertStatement.executeUpdate();
                        }
                    }
                }
            }
        } catch (Exception e) {
            Logger.getLogger(SQLite.class.getName()).log(Level.SEVERE, "Failed to ensure player exists in table: " + table, e);
            throw new RuntimeException(e);
        }
    }

    // 统一字段和玩家检查
    private static void ensureFieldAndPlayer(String table, String field, String fieldType, Object defaultValue, Player p) {
        ensureFieldExists(table, field, fieldType, defaultValue);
        ensurePlayerExists(table, p);
    }

    public static int getWeaponLevel(Player p, String type) {
        ensureFieldAndPlayer("player_weapon", type, "INTEGER", type.equals("wooden_sword") ? 1 : 0, p);
        int l = 0;
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(
                 String.format("SELECT %s FROM player_weapon WHERE uuid = ?", formatField(type)))) {
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
        ensureFieldAndPlayer("player_weapon", type, "INTEGER", type.equals("wooden_sword") ? 1 : 0, p);
        try (Connection connection = getConnection()) {
            PreparedStatement update = connection.prepareStatement(
                "UPDATE player_weapon SET " + formatField(type) + " = ? WHERE uuid = ?"
            );
            update.setInt(1, level);
            update.setString(2, p.getUniqueId().toString());
            int affected = update.executeUpdate();
            update.close();
            if (affected == 0) {
                PreparedStatement insert = connection.prepareStatement(
                    "INSERT INTO player_weapon (uuid, " + formatField(type) + ") VALUES (?, ?)"
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

    public static int getArmorLevel(Player p, String type) {
        int l = 0;
        try {
            ensureFieldExists("player_armor", type, "INTEGER", 0);
            ensurePlayerExists("player_armor", p);

            try (Connection connection = DriverManager.getConnection(getUrl());
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "SELECT " + formatField(type) + " FROM player_armor WHERE uuid = ?")) {

                preparedStatement.setString(1, p.getUniqueId().toString());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        l = resultSet.getInt(1);
                    } else {
                        l = 0;
                        setArmorLevel(p, type, l);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return l;
    }

    public static void setArmorLevel(Player p, String type, int level) {
        try {
            ensureFieldExists("player_armor", type, "INTEGER", 0);
            ensurePlayerExists("player_armor", p);

            try (Connection connection = DriverManager.getConnection(getUrl())) {
                PreparedStatement updateStatement = connection.prepareStatement(
                    "UPDATE player_armor SET " + formatField(type) + " = ? WHERE uuid = ?"
                );
                updateStatement.setInt(1, level);
                updateStatement.setString(2, p.getUniqueId().toString());
                int affectedRows = updateStatement.executeUpdate();
                updateStatement.close();
                if (affectedRows == 0) {
                    PreparedStatement insertStatement = connection.prepareStatement(
                        "INSERT INTO player_armor (uuid, " + formatField(type) + ") VALUES (?, ?)"
                    );
                    insertStatement.setString(1, p.getUniqueId().toString());
                    insertStatement.setInt(2, level);
                    insertStatement.executeUpdate();
                    insertStatement.close();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int getPlayerInfo(Player p, String type) {
        int v = 0;
        try {
            ensureFieldExists("player_info", type, "INTEGER", 0);
            ensurePlayerExists("player_info", p);

            try (Connection connection = DriverManager.getConnection(getUrl());
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "SELECT " + formatField(type) + " FROM player_info WHERE uuid = ?")) {

                preparedStatement.setString(1, p.getUniqueId().toString());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        v = resultSet.getInt(1);
                    } else {
                        v = 0;
                        setPlayerInfo(p, type, v);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return v;
    }

    public static void setPlayerInfo(Player p, String type, int value) {
        try {
            ensureFieldExists("player_info", type, "INTEGER", 0);
            ensurePlayerExists("player_info", p);

            try (Connection connection = DriverManager.getConnection(getUrl())) {
                PreparedStatement updateStatement = connection.prepareStatement(
                    "UPDATE player_info SET " + formatField(type) + " = ? WHERE uuid = ?"
                );
                updateStatement.setInt(1, value);
                updateStatement.setString(2, p.getUniqueId().toString());
                int affectedRows = updateStatement.executeUpdate();
                updateStatement.close();
                if (affectedRows == 0) {
                    PreparedStatement insertStatement = connection.prepareStatement(
                        "INSERT INTO player_info (uuid, " + formatField(type) + ") VALUES (?, ?)"
                    );
                    insertStatement.setString(1, p.getUniqueId().toString());
                    insertStatement.setInt(2, value);
                    insertStatement.executeUpdate();
                    insertStatement.close();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getEnchance(Player p, String type) {
        String l = "";
        try {
            ensureFieldExists("player_enchance", type, "VARCHAR(50)", "none");
            ensurePlayerExists("player_enchance", p);

            try (Connection connection = DriverManager.getConnection(getUrl());
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "SELECT " + formatField(type) + " FROM player_enchance WHERE uuid = ?")) {

                preparedStatement.setString(1, p.getUniqueId().toString());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        l = resultSet.getString(1);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return l;
    }

    public static void setEnchance(Player p, String type, String value) {
        try {
            ensureFieldExists("player_enchance", type, "VARCHAR(50)", "none");
            ensurePlayerExists("player_enchance", p);

            try (Connection connection = DriverManager.getConnection(getUrl())) {
                PreparedStatement updateStatement = connection.prepareStatement(
                    "UPDATE player_enchance SET " + formatField(type) + " = ? WHERE uuid = ?"
                );
                updateStatement.setString(1, value);
                updateStatement.setString(2, p.getUniqueId().toString());
                int affectedRows = updateStatement.executeUpdate();
                updateStatement.close();
                if (affectedRows == 0) {
                    PreparedStatement insertStatement = connection.prepareStatement(
                        "INSERT INTO player_enchance (uuid, " + formatField(type) + ") VALUES (?, ?)"
                    );
                    insertStatement.setString(1, p.getUniqueId().toString());
                    insertStatement.setString(2, value);
                    insertStatement.executeUpdate();
                    insertStatement.close();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getEquipedBuff(Player p, String type) {
        String l = "";
        try {
            ensureFieldExists("player_equip_buffs", type, "VARCHAR(50)", "none");
            ensurePlayerExists("player_equip_buffs", p);

            try (Connection connection = DriverManager.getConnection(getUrl());
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "SELECT " + formatField(type) + " FROM player_equip_buffs WHERE uuid = ?")) {

                preparedStatement.setString(1, p.getUniqueId().toString());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        l = resultSet.getString(1);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return l;
    }

    public static void setEquipedBuff(Player p, String type, String value) {
        try {
            ensureFieldExists("player_equip_buffs", type, "VARCHAR(50)", "none");
            ensurePlayerExists("player_equip_buffs", p);

            try (Connection connection = DriverManager.getConnection(getUrl())) {
                PreparedStatement updateStatement = connection.prepareStatement(
                    "UPDATE player_equip_buffs SET " + formatField(type) + " = ? WHERE uuid = ?"
                );
                updateStatement.setString(1, value);
                updateStatement.setString(2, p.getUniqueId().toString());
                int affectedRows = updateStatement.executeUpdate();
                updateStatement.close();
                if (affectedRows == 0) {
                    PreparedStatement insertStatement = connection.prepareStatement(
                        "INSERT INTO player_equip_buffs (uuid, " + formatField(type) + ") VALUES (?, ?)"
                    );
                    insertStatement.setString(1, p.getUniqueId().toString());
                    insertStatement.setString(2, value);
                    insertStatement.executeUpdate();
                    insertStatement.close();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getEquipedCurse(Player p, String type) {
        String l = "";
        try {
            ensureFieldExists("player_equip_curses", type, "VARCHAR(50)", "none");
            ensurePlayerExists("player_equip_curses", p);

            try (Connection connection = DriverManager.getConnection(getUrl());
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "SELECT " + formatField(type) + " FROM player_equip_curses WHERE uuid = ?")) {

                preparedStatement.setString(1, p.getUniqueId().toString());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        l = resultSet.getString(1);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return l;
    }

    public static void setEquipedCurse(Player p, String type, String value) {
        try {
            ensureFieldExists("player_equip_curses", type, "VARCHAR(50)", "none");
            ensurePlayerExists("player_equip_curses", p);

            try (Connection connection = DriverManager.getConnection(getUrl())) {
                PreparedStatement updateStatement = connection.prepareStatement(
                    "UPDATE player_equip_curses SET " + formatField(type) + " = ? WHERE uuid = ?"
                );
                updateStatement.setString(1, value);
                updateStatement.setString(2, p.getUniqueId().toString());
                int affectedRows = updateStatement.executeUpdate();
                updateStatement.close();
                if (affectedRows == 0) {
                    PreparedStatement insertStatement = connection.prepareStatement(
                        "INSERT INTO player_equip_curses (uuid, " + formatField(type) + ") VALUES (?, ?)"
                    );
                    insertStatement.setString(1, p.getUniqueId().toString());
                    insertStatement.setString(2, value);
                    insertStatement.executeUpdate();
                    insertStatement.close();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean getBuffStat(Player p, String type) {
        boolean l = false;
        try {
            ensureFieldExists("player_buffs", type, "BOOLEAN", 0);
            ensurePlayerExists("player_buffs", p);

            try (Connection connection = DriverManager.getConnection(getUrl());
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "SELECT " + formatField(type) + " FROM player_buffs WHERE uuid = ?")) {

                preparedStatement.setString(1, p.getUniqueId().toString());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        l = resultSet.getBoolean(1);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return l;
    }

    public static void setBuffStat(Player p, String type, boolean value) {
        try {
            ensureFieldExists("player_buffs", type, "BOOLEAN", 0);
            ensurePlayerExists("player_buffs", p);

            try (Connection connection = DriverManager.getConnection(getUrl())) {
                PreparedStatement updateStatement = connection.prepareStatement(
                    "UPDATE player_buffs SET " + formatField(type) + " = ? WHERE uuid = ?"
                );
                updateStatement.setBoolean(1, value);
                updateStatement.setString(2, p.getUniqueId().toString());
                int affectedRows = updateStatement.executeUpdate();
                updateStatement.close();
                if (affectedRows == 0) {
                    PreparedStatement insertStatement = connection.prepareStatement(
                        "INSERT INTO player_buffs (uuid, " + formatField(type) + ") VALUES (?, ?)"
                    );
                    insertStatement.setString(1, p.getUniqueId().toString());
                    insertStatement.setBoolean(2, value);
                    insertStatement.executeUpdate();
                    insertStatement.close();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean getCurseStat(Player p, String type) {
        boolean l = false;
        try {
            ensureFieldExists("player_curses", type, "BOOLEAN", 0);
            ensurePlayerExists("player_curses", p);

            try (Connection connection = DriverManager.getConnection(getUrl());
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "SELECT " + formatField(type) + " FROM player_curses WHERE uuid = ?")) {

                preparedStatement.setString(1, p.getUniqueId().toString());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        l = resultSet.getBoolean(1);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return l;
    }

    public static void setCurseStat(Player p, String type, boolean value) {
        try {
            ensureFieldExists("player_curses", type, "BOOLEAN", 0);
            ensurePlayerExists("player_curses", p);

            try (Connection connection = DriverManager.getConnection(getUrl())) {
                PreparedStatement updateStatement = connection.prepareStatement(
                    "UPDATE player_curses SET " + formatField(type) + " = ? WHERE uuid = ?"
                );
                updateStatement.setBoolean(1, value);
                updateStatement.setString(2, p.getUniqueId().toString());
                int affectedRows = updateStatement.executeUpdate();
                updateStatement.close();
                if (affectedRows == 0) {
                    PreparedStatement insertStatement = connection.prepareStatement(
                        "INSERT INTO player_curses (uuid, " + formatField(type) + ") VALUES (?, ?)"
                    );
                    insertStatement.setString(1, p.getUniqueId().toString());
                    insertStatement.setBoolean(2, value);
                    insertStatement.executeUpdate();
                    insertStatement.close();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkDatabaseExists() {
        try (Connection conn = DriverManager.getConnection(getUrl());
             Statement stmt = conn.createStatement()) {
            return true;
        } catch (Exception e) {
            Logger.getLogger(SQLite.class.getName()).log(Level.SEVERE, "Database connection failed", e);
            throw new RuntimeException(e);
        }
    }
}