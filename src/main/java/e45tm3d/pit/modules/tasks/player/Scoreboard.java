package e45tm3d.pit.modules.tasks.player;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.User;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.modules.tasks.TaskModule;
import e45tm3d.pit.utils.functions.PlayerFunction;
import e45tm3d.pit.utils.functions.VariableFunction;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Scoreboard extends TaskModule {

    private static final Map<UUID, org.bukkit.scoreboard.Scoreboard> playerScoreboards = new HashMap<>();

    @Override
    public void run(TaskModule task) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ThePit.getInstance(), () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (PlayerFunction.isInArena(player)) {
                    if (Yaml.CONFIG.getConfig().getBoolean("settings.scoreboard.enable")) {
                        updatePlayerScoreboard(player);
                    }
                } else {
                    removePlayerScoreboard(player);
                }
            }
        }, 100L, 100L);
    }

    private void updatePlayerScoreboard(Player player) {
        UUID playerUUID = player.getUniqueId();

        org.bukkit.scoreboard.Scoreboard scoreboard;
        Objective objective;
        
        if (playerScoreboards.containsKey(playerUUID)) {
            scoreboard = playerScoreboards.get(playerUUID);
            objective = scoreboard.getObjective("ThePit");
        } else {
            scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
            objective = scoreboard.registerNewObjective("ThePit", "dummy");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            playerScoreboards.put(playerUUID, scoreboard);
        }

        objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', Yaml.CONFIG.getConfig().getString("settings.scoreboard.title")));

        for (String entry : scoreboard.getEntries()) {
            scoreboard.resetScores(entry);
        }

        int kills = User.getKills(player);
        int deaths = User.getDeaths(player);
        int killstreak = User.getKillstreak(player);

        double kdRatio = deaths > 0 ? (double) kills / deaths : kills;
        String kdFormatted = String.format("%.1f", kdRatio);

        java.util.List<String> lines = Yaml.CONFIG.getConfig().getStringList("settings.scoreboard.lines");
        int lineCount = lines.size();

        for (int i = 0; i < lineCount; i++) {
            String line = lines.get(i);

            if (line == null || line.isEmpty()) {
                addScore(objective, ChatColor.RESET + " ".repeat(i), lineCount - i);
                continue;
            }
            String status = "";
            if (User.isFighting(player)) status = Yaml.CONFIG.getConfig().getString("settings.scoreboard.status.fight");
            if (!User.isFighting(player)) status = Yaml.CONFIG.getConfig().getString("settings.scoreboard.status.wait");
            if (User.isDevelopMode(player)) status = Yaml.CONFIG.getConfig().getString("settings.scoreboard.status.develop_mode");
            status = ChatColor.translateAlternateColorCodes('&', status);

            String processedLine = line
                .replaceAll("%kills%", String.valueOf(kills))
                .replaceAll("%deaths%", String.valueOf(deaths))
                .replaceAll("%killstreak%", String.valueOf(killstreak))
                .replaceAll("%kd%", kdFormatted)
                .replaceAll("%date%", VariableFunction.getFormattedDate())
                .replaceAll("%status%", status);

            processedLine = PlaceholderAPI.setPlaceholders(player, processedLine);

            processedLine = ChatColor.translateAlternateColorCodes('&', processedLine);

            addScore(objective, processedLine, lineCount - i);
        }

        player.setScoreboard(scoreboard);
    }

    private void addScore(Objective objective, String text, int score) {
        String uniqueText = text;
        int index = 0;

        while (objective.getScoreboard().getEntries().contains(uniqueText)) {
            uniqueText = text + ChatColor.RESET + ChatColor.MAGIC + index;
            index++;
        }

        objective.getScore(uniqueText).setScore(score);
    }

    public static void removePlayerScoreboard(Player player) {
        UUID playerUUID = player.getUniqueId();
        if (playerScoreboards.containsKey(playerUUID)) {
            playerScoreboards.remove(playerUUID);
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }
    }
}