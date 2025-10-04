package e45tm3d.pit.modules.buff.buffs;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.modules.buff.BuffModule;
import e45tm3d.pit.utils.enums.buff.BuffItems;
import e45tm3d.pit.utils.enums.buff.BuffMenuItems;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class ForgeGoldApple extends BuffModule {

    private Map<UUID, Long> delay = new HashMap<>();

    @Override
    public ItemStack getMenuItem() {
        return BuffMenuItems.FORGE_GOLD_APPLE.getItemStack();
    }

    @Override
    public ItemStack getEquipedItem() {
        return BuffItems.FORGE_GOLD_APPLE.getItemStack();
    }

    @Override
    public String getType() {
        return "forge_gold_apple";
    }

    @Override
    public int getPrice() {
        return Yaml.BUFF.getConfig().getInt("menu.items.forge_gold_apple.price", 5000);
    }

    @Override
    public List<String> getConsumeItems() {
        return Yaml.BUFF.getConfig().getStringList("menu.items.forge_gold_apple.consume_items");
    }

    @Override
    public List<String> getUnlockedCostFormat() {
        List<String> list = Lists.newArrayList();
        for (String str : Yaml.BUFF.getConfig().getStringList("menu.items.forge_gold_apple.cost_format.unlock")) {
            list.add(str.replaceAll("&", "§"));
        }
        return list;
    }

    @Override
    public List<String> getLockedCostFormat() {
        List<String> list = Lists.newArrayList();
        for (String str : Yaml.BUFF.getConfig().getStringList("menu.items.forge_gold_apple.cost_format.lock")) {
            list.add(str.replaceAll("&", "§"));
        }
        return list;
    }

    @Override
    public void listen(Event event) {
        if (event instanceof PlayerInteractEvent e) {

            if (isEquiped(e.getPlayer())) {

                Player p = e.getPlayer();
                UUID uuid = p.getUniqueId();

                if (!Objects.isNull(e.getItem())) {
                    if (delay.containsKey(uuid)) {
                        if (System.currentTimeMillis() - delay.get(uuid) >= 500) {
                            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                                if (e.getItem().getType().equals(Material.GOLDEN_APPLE)) {
                                    int amount = e.getItem().getAmount();
                                    if (amount > 1) {
                                        e.getItem().setAmount(e.getItem().getAmount() - 1);
                                        consumeGoldApple(p);
                                    } else {
                                        p.getInventory().remove(e.getItem());
                                        consumeGoldApple(p);
                                    }
                                }
                            }
                        }
                    } else {
                        delay.put(uuid, 0L);
                    }
                }
            }
        } else if (event instanceof PlayerAnimationEvent e) {

            if (isEquiped(e.getPlayer())) {

                Player p = e.getPlayer();
                UUID uuid = p.getUniqueId();

                ItemStack item = e.getPlayer().getItemInHand();

                if (!Objects.isNull(item)) {
                    if (delay.containsKey(uuid)) {
                        if (System.currentTimeMillis() - delay.get(uuid) >= 500) {
                            if (item.getType().equals(Material.GOLDEN_APPLE)) {
                                int amount = item.getAmount();
                                if (amount > 1) {
                                    item.setAmount(item.getAmount() - 1);
                                    consumeGoldApple(p);
                                } else {
                                    p.getInventory().remove(item);
                                    consumeGoldApple(p);
                                }
                            }
                        }
                    } else {
                        delay.put(uuid, 0L);
                    }
                }
            }
        }
    }

    @Override
    public void run(BuffModule task) {

    }

    private void consumeGoldApple(Player p) {

        p.addPotionEffect(PotionEffectType.REGENERATION.createEffect(500, 1), true);
        p.addPotionEffect(PotionEffectType.SPEED.createEffect(100, 0), true);
        p.addPotionEffect(PotionEffectType.ABSORPTION.createEffect(2400, 0), true);
        p.addPotionEffect(PotionEffectType.FIRE_RESISTANCE.createEffect(2400, 0), true);
        p.playSound(p.getLocation(), Sound.EAT, 1, 1);
        delay.put(p.getUniqueId(), System.currentTimeMillis());
    }
}
