package e45tm3d.pit.modules.tasks.player;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.modules.tasks.TaskModule;
import e45tm3d.pit.utils.functions.PlayerFunction;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class DevelopMode extends TaskModule {

    @Override
    public void run(TaskModule task) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ThePit.getInstance(), () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (PlayerFunction.isDevelopMode(p)) {
                    PlayerFunction.sendActionBar(p, Yaml.CONFIG.getConfig().getString("title.develop_mode.action_bar")
                            .replaceAll("&", "§"));
                    p.setAllowFlight(true);
                    p.setCanPickupItems(false);
                    for (Player others : Bukkit.getOnlinePlayers()) {
                        if (others != p) {
                            others.hidePlayer(p);
                        }
                    }
                } else {
                    if (!p.getGameMode().equals(GameMode.CREATIVE) && !p.getGameMode().equals(GameMode.SPECTATOR)) {
                        p.setAllowFlight(false);
                        p.setCanPickupItems(true);
                    }
                }
            }
        }, 20, 20);

    }
}
