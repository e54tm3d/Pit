package e45tm3d.pit.modules.enchance.enchances.chestplate;

import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.modules.enchance.EnchanceModule;
import e45tm3d.pit.modules.enchance.EnchanceType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bounce extends EnchanceModule {

    @Override
    public void listen(Event event) {
        if (event instanceof EntityDamageByEntityEvent e) {
            if (e.getEntity() instanceof Player p) {
                if (hasEnchance(p)) {

                    Random r = new Random();

                    if (r.nextInt(100) <= 10) {

                        Vector vector = e.getDamager().getLocation().subtract(p.getLocation()).toVector();

                        vector = vector.normalize();
                        vector = vector.multiply(1.0);
                        vector = vector.setY(0.42);

                        e.getDamager().setVelocity(vector);
                    }
                }
            }
        }
    }

    @Override
    public void run(EnchanceModule task) {

    }

    @Override
    public String getIdentifier() {
        return "bounce";
    }

    @Override
    public EnchanceType getEnchanceType() {
        return EnchanceType.CHESTPLATE;
    }

    @Override
    public List<String> getDescription() {
        List<String> list = new ArrayList<>();
        for (String s : Yaml.ENCHANCE.getConfig().getStringList("enchance.bounce.description")) {
            list.add(s.replace("&", "§"));
        }
        return list;
    }
}
