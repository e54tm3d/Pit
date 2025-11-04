package e45tm3d.pit.modules.listeners.player;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.User;
import e45tm3d.pit.api.enums.Messages;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.api.events.PlayerGainGoldEvent;
import e45tm3d.pit.modules.listeners.ListenerModule;
import e45tm3d.pit.utils.functions.MathFunction;
import e45tm3d.pit.utils.functions.PlayerFunction;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerPickupItemEvent;

import java.util.UUID;

public class GainGold extends ListenerModule {

    public static double gold;

    @Override
    public void listen(Event event) {
        if (event instanceof PlayerPickupItemEvent e) {

            Player p = e.getPlayer();
            UUID uuid = p.getUniqueId();

            if (User.isPlaying(p)) {

                if (PlayerFunction.isInArena(p)) {
                    if (Yaml.CONFIG.getConfig().getBoolean("settings.gain_gold.enable")) {
                        if (e.getItem().getItemStack().getType().equals(Material.GOLD_NUGGET)) {
                            gold = MathFunction.randomDouble(
                                    Yaml.CONFIG.getConfig().getDouble("settings.gain_gold.amount.max"),
                                    Yaml.CONFIG.getConfig().getDouble("settings.gain_gold.amount.min"));
                            Bukkit.getPluginManager().callEvent(new PlayerGainGoldEvent(p, gold));
                            ThePit.getEconomy().depositPlayer(p, gold);
                            p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 2, 0);
                            Messages.GAIN_GOLD.sendMessage(p);
                            e.setCancelled(true);
                            e.getItem().remove();
                        }
                    }
                }
            } else {
                e.setCancelled(true);
            }
        }
    }
}
