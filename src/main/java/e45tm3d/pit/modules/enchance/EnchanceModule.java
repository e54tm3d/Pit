package e45tm3d.pit.modules.enchance;

import e45tm3d.pit.api.User;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.utils.lists.EnchanceList;
import e45tm3d.pit.utils.maps.EnchanceMaps;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class EnchanceModule {

    public abstract void listen(Event event);

    public abstract void run(EnchanceModule task);

    public abstract String getEnchance();

    public abstract EnchanceType getEnchanceType();

    public abstract List<String> getDescription();

    public void register() {

        run(this);
        Enchances.registerEnchcance(this);

        EnchanceList.enchances.add(getEnchance());
        EnchanceMaps.enchances.put(getEnchance(), getDescription());

        switch (getEnchanceType()) {
            case WEAPON -> EnchanceList.weapon_enchances.add(getEnchance());
            case HELMET -> EnchanceList.helmet_enchances.add(getEnchance());
            case CHESTPLATE -> EnchanceList.chestplate_enchances.add(getEnchance());
            case LEGGINGS -> EnchanceList.leggings_enchances.add(getEnchance());
            case BOOTS -> EnchanceList.boots_enchances.add(getEnchance());
            case NORMAL -> EnchanceList.normal_enchances.add(getEnchance());
        }
    }

    public boolean hasEnchance(Player p) {
        boolean b = false;
        if (!Objects.isNull(p.getItemInHand())) {
            if (User.getEnchance(p, "weapon").equalsIgnoreCase(getEnchance())) {
                if (p.getItemInHand().getType().toString().toUpperCase().endsWith("SWORD")) {
                    b = true;
                }
            }
        }
        if (!Objects.isNull(p.getInventory().getHelmet())) {
            if (User.getEnchance(p, "helmet").equalsIgnoreCase(getEnchance())) {
                if (p.getInventory().getHelmet().getType().toString().toUpperCase().endsWith("HELMET")) {
                    b = true;
                }
            }
        }
        if (!Objects.isNull(p.getInventory().getChestplate())) {
            if (User.getEnchance(p, "chestplate").equalsIgnoreCase(getEnchance())) {
                if (p.getInventory().getChestplate().getType().toString().toUpperCase().endsWith("CHESTPLATE")) {
                    b = true;
                }
            }
        }
        if (!Objects.isNull(p.getInventory().getLeggings())) {
            if (User.getEnchance(p, "leggings").equalsIgnoreCase(getEnchance())) {
                if (p.getInventory().getLeggings().getType().toString().toUpperCase().endsWith("LEGGINGS")) {
                    b = true;
                }
            }
        }
        if (!Objects.isNull(p.getInventory().getBoots())) {
            if (User.getEnchance(p, "boots").equalsIgnoreCase(getEnchance())) {
                if (p.getInventory().getBoots().getType().toString().toUpperCase().endsWith("BOOTS")) {
                    b = true;
                }
            }
        }
        return b;
    }
}