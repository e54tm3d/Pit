package e45tm3d.pit.modules.enchance.enchances.boots;

import com.google.common.collect.Lists;
import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.modules.enchance.EnchanceModule;
import e45tm3d.pit.modules.enchance.EnchanceType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class Speed extends EnchanceModule {

    @Override
    public String getIdentifier() {
        return "speed";
    }

    @Override
    public EnchanceType getEnchanceType() {
        return EnchanceType.BOOTS;
    }

    @Override
    public List<String> getDescription() {
        List<String> list = new ArrayList<>();
        for (String s : Yaml.ENCHANCE.getConfig().getStringList("enchance.speed.description")) {
            list.add(s.replace("&", "§"));
        }
        return list;
    }

    @Override
    public void listen(Event event) {

    }

    @Override
    public void run(EnchanceModule task) {
        Bukkit.getScheduler().runTaskTimer(ThePit.getInstance(), () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (hasEnchance(p)) {
                    p.addPotionEffect(PotionEffectType.SPEED.createEffect(100, 0));
                }
            }
        }, 0, 20);
    }
}
