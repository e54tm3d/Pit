package e45tm3d.pit.modules.enchance.enchances.chestplate;

import com.google.common.collect.Lists;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.modules.enchance.EnchanceModule;
import e45tm3d.pit.modules.enchance.EnchanceType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;
import java.util.List;

public class DamageAbsorption extends EnchanceModule {

    @Override
    public String getIdentifier() {
        return "damage_absorption";
    }

    @Override
    public EnchanceType getEnchanceType() {
        return EnchanceType.CHESTPLATE;
    }

    @Override
    public List<String> getDescription() {
        List<String> list = new ArrayList<>();
        for (String s : Yaml.ENCHANCE.getConfig().getStringList("enchance.damage_absorption.description")) {
            list.add(s.replace("&", "§"));
        }
        return list;
    }

    @Override
    public void listen(Event event) {
        if (event instanceof EntityDamageByEntityEvent e) {
            if (e.getEntity() instanceof Player p) {
                if (hasEnchance(p)) {
                    e.setDamage(Math.min(e.getDamage(), p.getMaxHealth() * 0.75));
                }
            }
        }
    }

    @Override
    public void run(EnchanceModule task) {

    }
}
