package e45tm3d.pit.modules.listeners.player;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.User;
import e45tm3d.pit.api.enums.Messages;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.api.events.PlayerMurderEvent;
import e45tm3d.pit.modules.listeners.ListenerModule;
import e45tm3d.pit.utils.functions.MathFunction;
import e45tm3d.pit.utils.functions.PlayerFunction;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.Objects;
import java.util.Random;

public class Murder extends ListenerModule {

    public static Player killer;
    public static Player dead;

    public static double gold;
    public static int exp;
    public static int killstreak;

    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    public void listen(Event event) {
        if (event instanceof PlayerMurderEvent e) {

            if (e.getKiller() instanceof Player p) {

                dead = e.getDead();
                killer = p;

                if (PlayerFunction.isInArena(p) && Yaml.CONFIG.getConfig().getBoolean("settings.murder.gain_exp.enable")) {
                    exp = (int) MathFunction.randomDouble(
                            Yaml.CONFIG.getConfig().getDouble("settings.murder.gain_exp.amount.max"),
                            Yaml.CONFIG.getConfig().getDouble("settings.murder.gain_exp.amount.min"));
                } else {
                    exp = 0;
                }

                if (PlayerFunction.isInArena(p) && Yaml.CONFIG.getConfig().getBoolean("settings.murder.gain_gold.enable")) {
                    gold = MathFunction.randomDouble(
                            Yaml.CONFIG.getConfig().getDouble("settings.murder.gain_gold.amount.max"),
                            Yaml.CONFIG.getConfig().getDouble("settings.murder.gain_gold.amount.min"));
                } else {
                    gold = 0;
                }

                ThePit.getEconomy().depositPlayer(p, gold);

                User.setExp(p, User.getExp(p) + exp);

                int kills = User.getKills(p);
                int killstreak = User.getKillstreak(p);

                kills += 1;
                killstreak += 1;

                Murder.killstreak = killstreak;

                Messages.KILL.sendMessage(p);
                PlayerFunction.sendActionBar(p, Yaml.CONFIG.getConfig().getString("title.murder.action_bar")
                        .replaceAll("%gold%", df.format(Murder.gold))
                        .replaceAll("%exp%", df.format(Murder.exp))
                        .replaceAll("%killer%", Murder.killer.getName())
                        .replaceAll("%dead%", Murder.dead.getName())
                        .replaceAll("&", "§")
                );
                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);

                User.setKills(p, kills);
                User.setKillstreak(p, killstreak);

                if (!p.getInventory().contains(Material.GOLDEN_APPLE)) {
                    if (Objects.isNull(p.getInventory().getItem(8))) {
                        p.getInventory().setItem(8, new ItemStack(Material.GOLDEN_APPLE));
                    } else {
                        p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
                    }
                } else if (!p.getInventory().containsAtLeast(new ItemStack(Material.GOLDEN_APPLE), 2)) {
                    p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
                }

                if ((killstreak) % 10 == 0 && killstreak != 0) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        Messages.KILLSTREAK.sendMessage(all);
                    }
                }
            }
        }
    }
}
