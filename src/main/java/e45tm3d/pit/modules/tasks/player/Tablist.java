package e45tm3d.pit.modules.tasks.player;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.User;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.modules.tasks.TaskModule;
import e45tm3d.pit.utils.functions.PlayerFunction;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Tablist extends TaskModule {

    private List<String> headerFormat;
    private List<String> footerFormat;
    private boolean tablistEnabled;

    private final ConcurrentHashMap<UUID, PlayerData> playerDataCache = new ConcurrentHashMap<>();

    private long lastConfigUpdateTime = 0;
    private final long CONFIG_UPDATE_INTERVAL = 20000;

    @Override
    public void run(TaskModule task) {
        updateConfigCache();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(ThePit.getInstance(), this::processAllPlayers, 200L, 200L);
    }
    
    private void processAllPlayers() {
        if (System.currentTimeMillis() - lastConfigUpdateTime > CONFIG_UPDATE_INTERVAL) {
            updateConfigCache();
        }

        if (!tablistEnabled) {
            return;
        }

        for (Player viewer : Bukkit.getOnlinePlayers()) {
            updateTablistHeaderFooter(viewer);
        }
    }
    
    private void updateConfigCache() {
        Configuration config = Yaml.CONFIG.getConfig();
        headerFormat = config.getStringList("settings.tablist.header");
        footerFormat = config.getStringList("settings.tablist.footer");
        tablistEnabled = config.getBoolean("settings.tablist.enable");
        lastConfigUpdateTime = System.currentTimeMillis();
    }

    private void updateTablistHeaderFooter(Player viewer) {

        PlayerData data = getPlayerData(viewer);

        String header = buildTablistContent(headerFormat, data);
        String footer = buildTablistContent(footerFormat, data);

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            header = PlaceholderAPI.setPlaceholders(viewer, header);
            footer = PlaceholderAPI.setPlaceholders(viewer, footer);
        }

        PlayerFunction.setTablist(viewer, header, footer);
    }
    
    private PlayerData getPlayerData(Player player) {

        return new PlayerData(
            User.getKills(player),
            User.getDeaths(player),
            User.getKillstreak(player)
        );

    }
    
    private String buildTablistContent(List<String> formatList, PlayerData data) {
        if (formatList.isEmpty()) {
            return "";
        }
        
        StringBuilder content = new StringBuilder();
        int size = formatList.size();
        
        for (int i = 0; i < size; i++) {
            String line = formatList.get(i);

            line = line.replace("%kills%", String.valueOf(data.kills))
                      .replace("%deaths%", String.valueOf(data.deaths))
                      .replace("%killstreak%", String.valueOf(data.killstreak));
            
            // 转换颜色代码
            content.append(ChatColor.translateAlternateColorCodes('&', line));

            if (i < size - 1) {
                content.append('\n');
            }
        }
        
        return content.toString();
    }

    private static class PlayerData {
        final int kills;
        final int deaths;
        final int killstreak;
        
        PlayerData(int kills, int deaths, int killstreak) {
            this.kills = kills;
            this.deaths = deaths;
            this.killstreak = killstreak;
        }
    }
}