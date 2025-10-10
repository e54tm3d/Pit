package e45tm3d.pit.modules.enchance.enchances.weapon;

import com.google.common.collect.Lists;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.modules.enchance.EnchanceModule;
import e45tm3d.pit.modules.enchance.EnchanceType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.List;
import java.util.Random;

public class LifeSteal extends EnchanceModule {

    @Override
    public String getIdentifier() {
        return "life_steal";
    }

    @Override
    public EnchanceType getEnchanceType() {
        return EnchanceType.WEAPON;
    }

    @Override
    public List<String> getDescription() {
        return Yaml.ENCHANCE.getConfig().getStringList("enchance.life_steal.description");
    }

    @Override
    public void listen(Event event) {
        if (event instanceof EntityDamageByEntityEvent e) {
            if (e.getDamager() instanceof Player p) {
                if (hasEnchance(p)) {
                    Random r = new Random();
                    if (r.nextInt(100) < 10) {
                        p.setHealth(Math.min(p.getHealth() + 4, p.getMaxHealth()));
                    }
                }
            }
        }
    }

    @Override
    public void run(EnchanceModule task) {

    }
}
