package e45tm3d.pit.modules.tasks.player;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.User;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.modules.tasks.TaskModule;
import e45tm3d.pit.utils.functions.PlayerFunction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class LevelUpdate extends TaskModule {

    @Override
    public void run(TaskModule task) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ThePit.getInstance(), () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {

                int exp = User.getExp(p);
                int level = User.getLevel(p);

                if (exp >= 5000) {

                    String title = Yaml.CONFIG.getConfig().getString("title.level_up.title");
                    String subtitle = Yaml.CONFIG.getConfig().getString("title.level_up.subtitle");

                    title = ChatColor.translateAlternateColorCodes('&', title);
                    subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);

                    title = title
                            .replace("%level%", String.valueOf(level))
                            .replace("%new_level%", String.valueOf(level + 1)
                            );

                    PlayerFunction.sendTitle(p, title, subtitle, 0, 60, 0);

                    p.playSound(p.getLocation(), Sound.LEVEL_UP, 1f, 1f);

                    User.setExp(p, exp - 5000);
                    User.setLevel(p, level + 1);
                }
                p.setExp((float) exp / 5000);
                p.setLevel(level);
            }
        }, 0, 20);
    }
}
