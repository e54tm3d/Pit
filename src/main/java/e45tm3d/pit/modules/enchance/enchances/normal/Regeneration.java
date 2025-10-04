package e45tm3d.pit.modules.enchance.enchances.normal;

import com.google.common.collect.Lists;
import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.modules.enchance.EnchanceModule;
import e45tm3d.pit.modules.enchance.EnchanceType;
import e45tm3d.pit.utils.functions.MathFunction;
import e45tm3d.pit.utils.functions.PlayerFunction;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class Regeneration extends EnchanceModule {

    @Override
    public String getEnchance() {
        return "regeneration";
    }

    @Override
    public EnchanceType getEnchanceType() {
        return EnchanceType.NORMAL;
    }

    @Override
    public List<String> getDescription() {
        List<String> list = new ArrayList<>();
        for (String s : Yaml.ENCHANCE.getConfig().getStringList("enchance.regeneration.description")) {
            list.add(s.replace("&", "§"));
        }
        return list;
    }

    @Override
    public void listen(Event event) {

    }

    @Override
    public void run(EnchanceModule task) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ThePit.getInstance(), new Runnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (hasEnchance(p)) {
                        p.addPotionEffect(PotionEffectType.REGENERATION.createEffect(1000, 0));
                        for (int i = 0; i < 5; i++) {
                            double offsetX = MathFunction.randomDouble(1, -1);
                            double offsetY = MathFunction.randomDouble(1, -1);
                            double offsetZ = MathFunction.randomDouble(1, -1);

                            PlayerFunction.spawnSingleParticleToAll(EnumParticle.VILLAGER_HAPPY, p.getLocation().add(offsetX, offsetY + 1, offsetZ));
                        }
                    }
                }
            }
        }, 0, 20);
    }
}
