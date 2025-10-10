package e45tm3d.pit.modules.enchance;

import e45tm3d.pit.api.User;
import e45tm3d.pit.utils.lists.EnchanceList;
import e45tm3d.pit.utils.maps.EnchanceMaps;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.List;
import java.util.Objects;

public abstract class EnchanceModule {

    public abstract void listen(Event event);

    public abstract void run(EnchanceModule task);

    public abstract String getIdentifier();

    public abstract EnchanceType getEnchanceType();

    public abstract List<String> getDescription();

    protected void register() {

        run(this);
        Enchances.registerEnchcance(this);

        EnchanceList.enchances.add(getIdentifier());
        EnchanceMaps.enchances.put(getIdentifier(), getDescription());

        switch (getEnchanceType()) {
            case WEAPON -> EnchanceList.weapon_enchances.add(getIdentifier());
            case HELMET -> EnchanceList.helmet_enchances.add(getIdentifier());
            case CHESTPLATE -> EnchanceList.chestplate_enchances.add(getIdentifier());
            case LEGGINGS -> EnchanceList.leggings_enchances.add(getIdentifier());
            case BOOTS -> EnchanceList.boots_enchances.add(getIdentifier());
            case NORMAL -> EnchanceList.normal_enchances.add(getIdentifier());
        }
    }

    public boolean hasEnchance(Player p) {
        boolean b = false;
        if (!Objects.isNull(p.getItemInHand())) {
            if (User.getEnchance(p, "weapon").equalsIgnoreCase(getIdentifier())) {
                if (p.getItemInHand().getType().toString().toUpperCase().endsWith("SWORD")) {
                    b = true;
                }
            }
        }
        if (!Objects.isNull(p.getInventory().getHelmet())) {
            if (User.getEnchance(p, "helmet").equalsIgnoreCase(getIdentifier())) {
                if (p.getInventory().getHelmet().getType().toString().toUpperCase().endsWith("HELMET")) {
                    b = true;
                }
            }
        }
        if (!Objects.isNull(p.getInventory().getChestplate())) {
            if (User.getEnchance(p, "chestplate").equalsIgnoreCase(getIdentifier())) {
                if (p.getInventory().getChestplate().getType().toString().toUpperCase().endsWith("CHESTPLATE")) {
                    b = true;
                }
            }
        }
        if (!Objects.isNull(p.getInventory().getLeggings())) {
            if (User.getEnchance(p, "leggings").equalsIgnoreCase(getIdentifier())) {
                if (p.getInventory().getLeggings().getType().toString().toUpperCase().endsWith("LEGGINGS")) {
                    b = true;
                }
            }
        }
        if (!Objects.isNull(p.getInventory().getBoots())) {
            if (User.getEnchance(p, "boots").equalsIgnoreCase(getIdentifier())) {
                if (p.getInventory().getBoots().getType().toString().toUpperCase().endsWith("BOOTS")) {
                    b = true;
                }
            }
        }
        return b;
    }
}