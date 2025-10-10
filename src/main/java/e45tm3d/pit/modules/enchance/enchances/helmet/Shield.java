package e45tm3d.pit.modules.enchance.enchances.helmet;

import com.google.common.collect.Lists;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.modules.enchance.EnchanceModule;
import e45tm3d.pit.modules.enchance.EnchanceType;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Shield extends EnchanceModule {

    @Override
    public String getIdentifier() {
        return "shield";
    }

    @Override
    public EnchanceType getEnchanceType() {
        return EnchanceType.HELMET;
    }

    @Override
    public List<String> getDescription() {
        List<String> list = new ArrayList<>();
        for (String s : Yaml.ENCHANCE.getConfig().getStringList("enchance.shield.description")) {
            list.add(s.replace("&", "§"));
        }
        return list;
    }

    @Override
    public void listen(Event event) {
        if (event instanceof EntityDamageByEntityEvent e) {
            if (e.getDamager() instanceof Player p) {
                if (hasEnchance(p)) {
                    Random r = new Random();
                    if (r.nextInt(100) < 10) {
                        p.removePotionEffect(PotionEffectType.ABSORPTION);
                        p.addPotionEffect(PotionEffectType.ABSORPTION.createEffect(1200, 2));
                        p.playSound(p.getLocation(), Sound.ANVIL_USE, 1, 1);
                    }
                }
            }
        }
    }

    @Override
    public void run(EnchanceModule task) {

    }
}
