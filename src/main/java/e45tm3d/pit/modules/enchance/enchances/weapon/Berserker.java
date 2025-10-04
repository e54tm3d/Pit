package e45tm3d.pit.modules.enchance.enchances.weapon;

import com.google.common.collect.Lists;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.modules.enchance.EnchanceModule;
import e45tm3d.pit.modules.enchance.EnchanceType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class Berserker extends EnchanceModule {

    @Override
    public String getEnchance() {
        return "berserker";
    }

    @Override
    public EnchanceType getEnchanceType() {
        return EnchanceType.WEAPON;
    }

    @Override
    public List<String> getDescription() {
        List<String> list = new ArrayList<>();
        for (String s : Yaml.ENCHANCE.getConfig().getStringList("enchance.berserker.description")) {
            list.add(s.replace("&", "§"));
        }
        return list;
    }

    @Override
    public void listen(Event event) {
        if (event instanceof EntityDamageByEntityEvent e) {
            if (e.getDamager() instanceof Player p) {
                if (hasEnchance(p)) {
                    p.addPotionEffect(PotionEffectType.SPEED.createEffect(100, 1));
                }
            } else {
                if (e.getEntity() instanceof Player p) {
                    if (hasEnchance(p)) {
                        p.addPotionEffect(PotionEffectType.INCREASE_DAMAGE.createEffect(100, 0));
                    }
                }
            }
        }
    }

    @Override
    public void run(EnchanceModule task) {

    }
}
