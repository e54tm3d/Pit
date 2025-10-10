package e45tm3d.pit.modules.enchance.enchances.leggings;

import com.google.common.collect.Lists;
import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.modules.enchance.EnchanceModule;
import e45tm3d.pit.modules.enchance.EnchanceType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class JumpBoost extends EnchanceModule {

    @Override
    public String getIdentifier() {
        return "jump_boost";
    }

    @Override
    public EnchanceType getEnchanceType() {
        return EnchanceType.LEGGINGS;
    }

    @Override
    public List<String> getDescription() {
        return Yaml.ENCHANCE.getConfig().getStringList("enchance.jump_boost.description");
    }

    @Override
    public void listen(Event event) {
    }

    @Override
    public void run(EnchanceModule task) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ThePit.getInstance(), () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (hasEnchance(p)) {
                    p.removePotionEffect(PotionEffectType.JUMP);
                    p.addPotionEffect(PotionEffectType.JUMP.createEffect(100, 1));
                }
            }
        }, 0, 20);
    }
}
