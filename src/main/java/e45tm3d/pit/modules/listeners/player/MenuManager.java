package e45tm3d.pit.modules.listeners.player;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.User;
import e45tm3d.pit.api.enums.Messages;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.api.events.PlayerEnchanceEvent;
import e45tm3d.pit.api.events.PlayerEquipBuffEvent;
import e45tm3d.pit.api.events.PlayerEquipCurseEvent;
import e45tm3d.pit.api.events.PlayerObtainWeaponEvent;
import e45tm3d.pit.modules.enchance.EnchanceType;
import e45tm3d.pit.modules.listeners.ListenerModule;
import e45tm3d.pit.utils.functions.InventoryFunction;
import e45tm3d.pit.utils.functions.ItemFunction;
import e45tm3d.pit.utils.lists.EnchanceList;
import e45tm3d.pit.utils.maps.CurseMaps;
import e45tm3d.pit.utils.maps.PlayerMaps;
import e45tm3d.pit.utils.maps.WeaponMaps;
import e45tm3d.pit.utils.menus.*;
import e45tm3d.pit.utils.menus.second_menus.WeaponUpdateMenu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class MenuManager extends ListenerModule {

    @Override
    public void listen(Event event) {
        if (event instanceof InventoryClickEvent e) {

            if (e.getWhoClicked() instanceof Player p) {

                UUID uuid = p.getUniqueId();

                int level_helmet = User.getHelmetLevel(p);
                int level_chestplate = User.getChestplateLevel(p);
                int level_leggings = User.getLeggingsLevel(p);
                int level_boots = User.getBootsLevel(p);

                if (PlayerMaps.menu.containsKey(uuid)) {
                    if (PlayerMaps.menu.get(uuid).equals("armor")) {

                        double money = ThePit.getEconomy().getBalance(p);

                        e.setCancelled(true);
                        switch (e.getSlot()) {
                            case 10 -> {
                                if (level_helmet < 5) {
                                    if (money > Yaml.ARMOR.getConfig().getDouble("menu.items.helmet.costs.tier_" + (level_helmet + 1))) {
                                        ThePit.getEconomy().withdrawPlayer(p, Yaml.ARMOR.getConfig().getDouble("menu.items.helmet.costs.tier_" + (level_helmet + 1)));
                                        User.setHelmetLevel(p, level_helmet + 1);
                                        p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
                                        Messages.UPGRADE_ARMOR.sendMessage(p);
                                        ArmorMenu.open(p);
                                    } else {
                                        p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
                                        Messages.CANNOT_AFFORD.sendMessage(p);
                                    }
                                }
                            }
                            case 19 -> {
                                if (level_chestplate < 5) {
                                    if (money > Yaml.ARMOR.getConfig().getDouble("menu.items.chestplate.costs.tier_" + (level_chestplate + 1))) {
                                        ThePit.getEconomy().withdrawPlayer(p, Yaml.ARMOR.getConfig().getDouble("menu.items.chestplate.costs.tier_" + (level_chestplate + 1)));
                                        User.setChestplateLevel(p, level_chestplate + 1);
                                        p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
                                        Messages.UPGRADE_ARMOR.sendMessage(p);
                                        ArmorMenu.open(p);
                                    } else {
                                        p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
                                        Messages.CANNOT_AFFORD.sendMessage(p);
                                    }
                                }
                            }
                            case 28 -> {
                                if (level_leggings < 5) {
                                    if (money > Yaml.ARMOR.getConfig().getDouble("menu.items.leggings.costs.tier_" + (level_leggings + 1))) {
                                        ThePit.getEconomy().withdrawPlayer(p, Yaml.ARMOR.getConfig().getDouble("menu.items.leggings.costs.tier_" + (level_leggings + 1)));
                                        User.setLeggingsLevel(p, level_leggings + 1);
                                        p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
                                        Messages.UPGRADE_ARMOR.sendMessage(p);
                                        ArmorMenu.open(p);
                                    } else {
                                        p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
                                        Messages.CANNOT_AFFORD.sendMessage(p);
                                    }
                                }
                            }
                            case 37 -> {
                                if (level_boots < 5) {
                                    if (money > Yaml.ARMOR.getConfig().getDouble("menu.items.boots.costs.tier_" + (level_boots + 1))) {
                                        ThePit.getEconomy().withdrawPlayer(p, Yaml.ARMOR.getConfig().getDouble("menu.items.boots.costs.tier_" + (level_boots + 1)));
                                        User.setBootsLevel(p, level_boots + 1);
                                        p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
                                        Messages.UPGRADE_ARMOR.sendMessage(p);
                                        ArmorMenu.open(p);
                                    } else {
                                        p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
                                        Messages.CANNOT_AFFORD.sendMessage(p);
                                    }
                                }
                            }
                            case 49 -> p.closeInventory();
                        }
                    } else if (PlayerMaps.menu.get(uuid).equals("blocks")) {
                        e.setCancelled(true);
                        switch (e.getSlot()) {
                            case 49 -> p.closeInventory();
                            default -> {
                                if (!e.getSlotType().equals(InventoryType.SlotType.OUTSIDE)
                                        && e.getClickedInventory().getType().equals(InventoryType.CHEST)) {
                                    if (e.getSlot() <= 44) {
                                        if (!Objects.isNull(e.getInventory().getItem(e.getSlot()))
                                                && !e.getInventory().getItem(e.getSlot()).getType().equals(Material.AIR)) {
                                            if (InventoryFunction.hasInventorySpace(p.getInventory())) {
                                                double money = ThePit.getEconomy().getBalance(p);
                                                double price = Yaml.BLOCKS.getConfig().getDouble("menu.custom_items." + BlocksMenu.blocks[e.getSlot()] + ".price");
                                                String material = Yaml.BLOCKS.getConfig().getString("menu.custom_items." + BlocksMenu.blocks[e.getSlot()] + ".material");
                                                if (money >= price) {
                                                    if (material.contains(":")) {
                                                        String[] parts = material.split(":");
                                                        ItemStack item = new ItemStack(Material.getMaterial(parts[0]), 16, Byte.parseByte(parts[1]));
                                                        p.getInventory().addItem(item);
                                                    } else {
                                                        ItemStack item = new ItemStack(Material.getMaterial(material), 16);
                                                        p.getInventory().addItem(item);
                                                    }
                                                    p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
                                                    Messages.PURCHASE_ITEM.sendMessage(p);
                                                    ThePit.getEconomy().withdrawPlayer(p, price);
                                                } else {
                                                    p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
                                                    Messages.CANNOT_AFFORD.sendMessage(p);
                                                }
                                            } else {
                                                p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
                                                Messages.BACKPACK_FULL.sendMessage(p);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else if (PlayerMaps.menu.get(uuid).equals("trash")) {
                        if (e.getSlot() == 13) {
                            if (e.getClickedInventory().getType().equals(InventoryType.CHEST)) {
                                p.closeInventory();
                                e.setCancelled(true);
                            }
                        }
                    } else if (PlayerMaps.menu.get(uuid).equals("weapon")) {
                        e.setCancelled(true);
                        if (e.getSlot() == 49) {
                            p.closeInventory();
                            return;
                        }
                        for (String slot : WeaponMaps.weapon_slots.keySet()) {
                            if (e.getSlot() == WeaponMaps.weapon_slots.get(slot)) {
                                WeaponUpdateMenu.open(p, slot);
                            }
                        }
                    } else if (PlayerMaps.menu.get(uuid).startsWith("weapon_update_")) {
                        String item = PlayerMaps.menu.get(uuid).replace("weapon_update_", "");
                        int level = User.getWeaponLevel(p, item);
                        e.setCancelled(true);
                        switch (e.getSlot()) {
                            case 11 -> {
                                if (level < 4) {

                                    List<String> consume_items = WeaponMaps.getTierConsumeItem(item, level + 1);
                                    double price = WeaponMaps.getTierPrice(item, level + 1);

                                    if (!Objects.isNull(consume_items) && !consume_items.isEmpty()) {
                                        for (String str : consume_items) {
                                            String[] parts = str.split(":");
                                            if (ThePit.getEconomy().getBalance(p) >= price
                                                    && ItemFunction.hasItemAtLease(p, Integer.parseInt(parts[1]), parts[0].toLowerCase())) {
                                                ThePit.getEconomy().withdrawPlayer(p, price);
                                                ItemFunction.consumeItem(p, Integer.parseInt(parts[1]), parts[0].toLowerCase());
                                                User.setWeaponLevel(p, item, level + 1);
                                                Messages.UPGRADE_SWORD.sendMessage(p);
                                                WeaponUpdateMenu.open(p, item);
                                                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
                                            } else {
                                                Messages.CANNOT_AFFORD.sendMessage(p);
                                                p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
                                            }
                                        }
                                    } else {
                                        if (ThePit.getEconomy().getBalance(p) >= price) {
                                            ThePit.getEconomy().withdrawPlayer(p, price);
                                            User.setWeaponLevel(p, item, level + 1);
                                            Messages.UPGRADE_SWORD.sendMessage(p);
                                            WeaponUpdateMenu.open(p, item);
                                            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
                                        } else {
                                            Messages.CANNOT_AFFORD.sendMessage(p);
                                            p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
                                        }
                                    }
                                }
                            }
                            case 27 -> {
                                if (User.getWeaponLevel(p, item) >= 1) {
                                    if (!Objects.isNull(WeaponMaps.weapon_items.get(item))) {
                                        if (!ItemFunction.hasItemAtLease(p, 1, item)) {
                                            Bukkit.getPluginManager().callEvent(new PlayerObtainWeaponEvent(p, item, ItemFunction.searchItem(item)));
                                            p.getInventory().addItem(ItemFunction.addNBTTag(WeaponMaps.weapon_items.get(item), item));
                                            p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1, 1);
                                        } else {
                                            Messages.ALREADY_HAVE_ITEMS.sendMessage(p);
                                            p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
                                        }
                                    }
                                } else {
                                    Messages.WEAPON_LOCKED.sendMessage(p);
                                    p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
                                }
                            }
                            case 31 -> p.closeInventory();
                        }
                    } else if (PlayerMaps.menu.get(uuid).equals("curse")) {
                        e.setCancelled(true);
                        switch (e.getSlot()) {
                            case 49 -> p.closeInventory();
                            case 39, 40, 41 -> {
                                if (PlayerMaps.selected_curse.containsKey(uuid)) {
                                    Bukkit.getPluginManager().callEvent(new PlayerEquipCurseEvent(p, PlayerMaps.selected_curse.get(uuid), e.getSlot() - 38));
                                    User.setEquipCurse(p, "slot_" + (e.getSlot() - 38), PlayerMaps.selected_curse.get(uuid));
                                    Messages.CURSE_EQUIP.sendMessage(p);
                                    p.playSound(p.getLocation(), Sound.WITHER_SPAWN, 1, 1);
                                    PlayerMaps.selected_curse.remove(uuid);
                                    CurseMenu.open(p);
                                } else {
                                    if (!User.getEquipCurse(p, "slot_" + (e.getSlot() - 38)).equals("none")) {
                                        User.setEquipCurse(p, "slot_" + (e.getSlot() - 38), "none");
                                        Messages.CURSE_REMOVE.sendMessage(p);
                                        p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
                                        CurseMenu.open(p);
                                    }
                                }
                            }
                            default -> {
                                if (!e.getSlotType().equals(InventoryType.SlotType.OUTSIDE)
                                        && e.getClickedInventory().getType().equals(InventoryType.CHEST)) {
                                    if (e.getSlot() <= 44) {
                                        if (!Objects.isNull(e.getInventory().getItem(e.getSlot()))
                                                && !e.getInventory().getItem(e.getSlot()).getType().equals(Material.AIR)) {

                                            String slot = CurseMenu.curse[e.getSlot()];

                                            List<String> consume_items = CurseMaps.consume_items.get(slot);
                                            double price = CurseMaps.price.get(slot);

                                            if (!Objects.isNull(slot)) {
                                                if (!User.getCurseStat(p, slot)) {
                                                    if (!Objects.isNull(consume_items)
                                                            && !consume_items.isEmpty()) {
                                                        for (String str : consume_items) {
                                                            String[] parts = str.split(":");
                                                            if (ThePit.getEconomy().getBalance(p) >= price
                                                                    && ItemFunction.hasItemAtLease(p, Integer.parseInt(parts[1]), parts[0].toLowerCase())) {
                                                                ThePit.getEconomy().withdrawPlayer(p, price);
                                                                ItemFunction.consumeItem(p, Integer.parseInt(parts[1]), parts[0].toLowerCase());
                                                                User.setCurseStat(p, slot, true);
                                                                Messages.CURSE_UNLOCKED.sendMessage(p);
                                                                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
                                                                CurseMenu.open(p);
                                                            } else {
                                                                Messages.CANNOT_AFFORD.sendMessage(p);
                                                                p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
                                                            }
                                                        }
                                                    } else {
                                                        if (ThePit.getEconomy().getBalance(p) >= price) {
                                                            ThePit.getEconomy().withdrawPlayer(p, price);
                                                            User.setCurseStat(p, slot, true);
                                                            Messages.CURSE_UNLOCKED.sendMessage(p);
                                                            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
                                                        } else {
                                                            Messages.CANNOT_AFFORD.sendMessage(p);
                                                            p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
                                                        }
                                                    }
                                                } else {
                                                    if (User.getCurseStat(p, slot)) {
                                                        PlayerMaps.selected_curse.put(uuid, slot);
                                                        Messages.CURSE_SELECT.sendMessage(p);
                                                        p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else if (PlayerMaps.menu.get(uuid).equals("buff")) {
                        e.setCancelled(true);
                        switch (e.getSlot()) {
                            case 49 -> p.closeInventory();
                            case 38, 39, 40, 41, 42 -> {
                                if (PlayerMaps.selected_buff.containsKey(uuid)) {
                                    Bukkit.getPluginManager().callEvent(new PlayerEquipBuffEvent(p, PlayerMaps.selected_buff.get(uuid), e.getSlot() - 37));
                                    User.setEquipBuff(p, "slot_" + (e.getSlot() - 37), PlayerMaps.selected_buff.get(uuid));
                                    Messages.BUFF_EQUIP.sendMessage(p);
                                    p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
                                    PlayerMaps.selected_buff.remove(uuid);
                                    BuffMenu.open(p);
                                } else {
                                    if (!User.getEquipBuff(p, "slot_" + (e.getSlot() - 37)).equals("none")) {
                                        User.setEquipBuff(p, "slot_" + (e.getSlot() - 37), "none");
                                        Messages.BUFF_REMOVE.sendMessage(p);
                                        p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
                                        BuffMenu.open(p);
                                    }
                                }
                            }
                            default -> {
                                if (!e.getSlotType().equals(InventoryType.SlotType.OUTSIDE)
                                        && e.getClickedInventory().getType().equals(InventoryType.CHEST)) {
                                    if (e.getSlot() <= 44) {
                                        if (!Objects.isNull(e.getInventory().getItem(e.getSlot()))
                                                && !e.getInventory().getItem(e.getSlot()).getType().equals(Material.AIR)) {

                                            String slot = BuffMenu.buffs[e.getSlot()];

                                            List<String> consume_items = Yaml.BUFF.getConfig().getStringList("menu.items." + slot + ".consume_items");
                                            double price = Yaml.BUFF.getConfig().getDouble("menu.items." + slot + ".price");

                                            if (!Objects.isNull(slot)) {
                                                if (!User.getBuffStat(p, slot)) {
                                                    if (!Objects.isNull(consume_items)
                                                            && !consume_items.isEmpty()) {
                                                        for (String str : consume_items) {
                                                            String[] parts = str.split(":");
                                                            if (ThePit.getEconomy().getBalance(p) >= price
                                                                    && ItemFunction.hasItemAtLease(p, Integer.parseInt(parts[1]), parts[0].toLowerCase())) {
                                                                ThePit.getEconomy().withdrawPlayer(p, price);
                                                                ItemFunction.consumeItem(p, Integer.parseInt(parts[1]), parts[0].toLowerCase());
                                                                User.setBuffStat(p, slot, true);
                                                                Messages.BUFF_UNLOCKED.sendMessage(p);
                                                                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
                                                                BuffMenu.open(p);
                                                            } else {
                                                                Messages.CANNOT_AFFORD.sendMessage(p);
                                                                p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
                                                            }
                                                        }
                                                    } else {
                                                        if (ThePit.getEconomy().getBalance(p) >= price) {
                                                            ThePit.getEconomy().withdrawPlayer(p, price);
                                                            User.setBuffStat(p, slot, true);
                                                            Messages.BUFF_UNLOCKED.sendMessage(p);
                                                            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
                                                        } else {
                                                            Messages.CANNOT_AFFORD.sendMessage(p);
                                                            p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
                                                        }
                                                    }
                                                } else {
                                                    PlayerMaps.selected_buff.put(uuid, slot);
                                                    Messages.BUFF_SELECT.sendMessage(p);
                                                    p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else if (PlayerMaps.menu.get(uuid).equals("enchance")) {
                        e.setCancelled(true);
                        switch (e.getSlot()) {
                            case 13 -> p.closeInventory();
                            case 2 -> {

                                List<String> consume_items = Yaml.ENCHANCE.getConfig().getStringList("menu.items.weapon.consume_items");
                                double price = Yaml.ENCHANCE.getConfig().getDouble("menu.items.weapon.price");

                                if (!Objects.isNull(consume_items)
                                        && !consume_items.isEmpty()) {
                                    for (String str : consume_items) {
                                        String[] parts = str.split(":");
                                        if (ThePit.getEconomy().getBalance(p) >= price
                                                && ItemFunction.hasItemAtLease(p, Integer.parseInt(parts[1]), parts[0].toLowerCase())) {
                                            Random none = new Random();
                                            if (none.nextInt(100) < 80) {
                                                Bukkit.getPluginManager().callEvent(new PlayerEnchanceEvent(p, null, "none", true));
                                                User.setEnchance(p, "weapon", "none");
                                                Messages.ENCHANCE_FAILURE.sendMessage(p);
                                                p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 1);
                                            } else {
                                                List<String> list = new ArrayList<>();
                                                list.addAll(EnchanceList.weapon_enchances);
                                                list.addAll(EnchanceList.normal_enchances);
                                                list.remove("none");
                                                Random r = new Random();
                                                int i = r.nextInt(list.size());
                                                String enchance = list.get(i);
                                                if (EnchanceList.weapon_enchances.contains(enchance)) {
                                                    Bukkit.getPluginManager().callEvent(new PlayerEnchanceEvent(p, EnchanceType.WEAPON, enchance, false));
                                                } else {
                                                    Bukkit.getPluginManager().callEvent(new PlayerEnchanceEvent(p, EnchanceType.NORMAL, enchance, false));
                                                }
                                                User.setEnchance(p, "weapon", enchance);
                                                Messages.ENCHANCE_SUCCESS.sendMessage(p);
                                                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
                                            }
                                            ItemFunction.consumeItem(p, Integer.parseInt(parts[1]), parts[0].toLowerCase());
                                            EnchanceMenu.open(p);
                                        } else {
                                            Messages.CANNOT_AFFORD.sendMessage(p);
                                            p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
                                        }
                                    }
                                } else {
                                    if (ThePit.getEconomy().getBalance(p) >= price) {
                                        ThePit.getEconomy().withdrawPlayer(p, price);
                                        Random none = new Random();
                                        if (none.nextInt(100) < 50) {
                                            Bukkit.getPluginManager().callEvent(new PlayerEnchanceEvent(p, null, "none", true));
                                            User.setEnchance(p, "weapon", "none");
                                            Messages.ENCHANCE_FAILURE.sendMessage(p);
                                            p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 1);
                                        } else {
                                            List<String> list = new ArrayList<>();
                                            list.addAll(EnchanceList.weapon_enchances);
                                            list.addAll(EnchanceList.normal_enchances);
                                            list.remove("none");
                                            Random r = new Random();
                                            int i = r.nextInt(list.size());
                                            String enchance = list.get(i);
                                            if (EnchanceList.weapon_enchances.contains(enchance)) {
                                                Bukkit.getPluginManager().callEvent(new PlayerEnchanceEvent(p, EnchanceType.WEAPON, enchance, false));
                                            } else {
                                                Bukkit.getPluginManager().callEvent(new PlayerEnchanceEvent(p, EnchanceType.NORMAL, enchance, false));
                                            }
                                            User.setEnchance(p, "weapon", enchance);
                                            Messages.ENCHANCE_SUCCESS.sendMessage(p);
                                            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
                                        }
                                        EnchanceMenu.open(p);
                                    } else {
                                        Messages.CANNOT_AFFORD.sendMessage(p);
                                        p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
                                    }
                                }
                            }
                            case 3 -> {

                                List<String> consume_items = Yaml.ENCHANCE.getConfig().getStringList("menu.items.helmet.consume_items");
                                double price = Yaml.ENCHANCE.getConfig().getDouble("menu.items.helmet.price");

                                if (!Objects.isNull(consume_items)
                                        && !consume_items.isEmpty()) {
                                    for (String str : consume_items) {
                                        String[] parts = str.split(":");
                                        if (ThePit.getEconomy().getBalance(p) >= price
                                                && ItemFunction.hasItemAtLease(p, Integer.parseInt(parts[1]), parts[0].toLowerCase())) {
                                            Random none = new Random();
                                            if (none.nextInt(100) < 80) {
                                                Bukkit.getPluginManager().callEvent(new PlayerEnchanceEvent(p, null, "none", true));
                                                User.setEnchance(p, "helmet", "none");
                                                Messages.ENCHANCE_FAILURE.sendMessage(p);
                                                p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 1);
                                            } else {
                                                List<String> list = new ArrayList<>();
                                                list.addAll(EnchanceList.helmet_enchances);
                                                list.addAll(EnchanceList.normal_enchances);
                                                list.remove("none");
                                                Random r = new Random();
                                                int i = r.nextInt(list.size());
                                                String enchance = list.get(i);
                                                if (EnchanceList.helmet_enchances.contains(enchance)) {
                                                    Bukkit.getPluginManager().callEvent(new PlayerEnchanceEvent(p, EnchanceType.HELMET, enchance, false));
                                                } else {
                                                    Bukkit.getPluginManager().callEvent(new PlayerEnchanceEvent(p, EnchanceType.NORMAL, enchance, false));
                                                }
                                                User.setEnchance(p, "helmet", enchance);
                                                Messages.ENCHANCE_SUCCESS.sendMessage(p);
                                                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
                                            }
                                            ItemFunction.consumeItem(p, Integer.parseInt(parts[1]), parts[0].toLowerCase());
                                            EnchanceMenu.open(p);
                                        } else {
                                            Messages.CANNOT_AFFORD.sendMessage(p);
                                            p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
                                        }
                                    }
                                } else {
                                    if (ThePit.getEconomy().getBalance(p) >= price) {
                                        ThePit.getEconomy().withdrawPlayer(p, price);
                                        Random none = new Random();
                                        if (none.nextInt(100) < 80) {
                                            User.setEnchance(p, "helmet", "none");
                                            Messages.ENCHANCE_FAILURE.sendMessage(p);
                                            p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 1);
                                        } else {
                                            List<String> list = new ArrayList<>();
                                            list.addAll(EnchanceList.helmet_enchances);
                                            list.addAll(EnchanceList.normal_enchances);
                                            list.remove("none");
                                            Random r = new Random();
                                            int i = r.nextInt(list.size());
                                            String enchance = list.get(i);
                                            if (EnchanceList.helmet_enchances.contains(enchance)) {
                                                Bukkit.getPluginManager().callEvent(new PlayerEnchanceEvent(p, EnchanceType.HELMET, enchance, false));
                                            } else {
                                                Bukkit.getPluginManager().callEvent(new PlayerEnchanceEvent(p, EnchanceType.NORMAL, enchance, false));
                                            }
                                            User.setEnchance(p, "helmet", enchance);
                                            Messages.ENCHANCE_SUCCESS.sendMessage(p);
                                            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
                                        }
                                        EnchanceMenu.open(p);
                                    } else {
                                        Messages.CANNOT_AFFORD.sendMessage(p);
                                        p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
                                    }
                                }
                            }
                            case 4 -> {

                                List<String> consume_items = Yaml.ENCHANCE.getConfig().getStringList("menu.items.chestplate.consume_items");
                                double price = Yaml.ENCHANCE.getConfig().getDouble("menu.items.chestplate.price");

                                if (!Objects.isNull(consume_items)
                                        && !consume_items.isEmpty()) {
                                    for (String str : consume_items) {
                                        String[] parts = str.split(":");
                                        if (ThePit.getEconomy().getBalance(p) >= price
                                                && ItemFunction.hasItemAtLease(p, Integer.parseInt(parts[1]), parts[0].toLowerCase())) {
                                            Random none = new Random();
                                            if (none.nextInt(100) < 80) {
                                                Bukkit.getPluginManager().callEvent(new PlayerEnchanceEvent(p, null, "none", true));
                                                User.setEnchance(p, "chestplate", "none");
                                                Messages.ENCHANCE_FAILURE.sendMessage(p);
                                                p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 1);
                                            } else {
                                                List<String> list = new ArrayList<>();
                                                list.addAll(EnchanceList.chestplate_enchances);
                                                list.addAll(EnchanceList.normal_enchances);
                                                list.remove("none");
                                                Random r = new Random();
                                                int i = r.nextInt(list.size());
                                                String enchance = list.get(i);
                                                if (EnchanceList.chestplate_enchances.contains(enchance)) {
                                                    Bukkit.getPluginManager().callEvent(new PlayerEnchanceEvent(p, EnchanceType.CHESTPLATE, enchance, false));
                                                } else {
                                                    Bukkit.getPluginManager().callEvent(new PlayerEnchanceEvent(p, EnchanceType.NORMAL, enchance, false));
                                                }
                                                User.setEnchance(p, "chestplate", enchance);
                                                Messages.ENCHANCE_SUCCESS.sendMessage(p);
                                                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
                                            }
                                            ItemFunction.consumeItem(p, Integer.parseInt(parts[1]), parts[0].toLowerCase());
                                            EnchanceMenu.open(p);
                                        } else {
                                            Messages.CANNOT_AFFORD.sendMessage(p);
                                            p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
                                        }
                                    }
                                } else {
                                    if (ThePit.getEconomy().getBalance(p) >= price) {
                                        ThePit.getEconomy().withdrawPlayer(p, price);
                                        Random none = new Random();
                                        if (none.nextInt(100) < 80) {
                                            Bukkit.getPluginManager().callEvent(new PlayerEnchanceEvent(p, null, "none", true));
                                            User.setEnchance(p, "chestplate", "none");
                                            Messages.ENCHANCE_FAILURE.sendMessage(p);
                                            p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 1);
                                        } else {
                                            List<String> list = new ArrayList<>();
                                            list.addAll(EnchanceList.chestplate_enchances);
                                            list.addAll(EnchanceList.normal_enchances);
                                            list.remove("none");
                                            Random r = new Random();
                                            int i = r.nextInt(list.size());
                                            String enchance = list.get(i);
                                            if (EnchanceList.chestplate_enchances.contains(enchance)) {
                                                Bukkit.getPluginManager().callEvent(new PlayerEnchanceEvent(p, EnchanceType.CHESTPLATE, enchance, false));
                                            } else {
                                                Bukkit.getPluginManager().callEvent(new PlayerEnchanceEvent(p, EnchanceType.NORMAL, enchance, false));
                                            }
                                            User.setEnchance(p, "chestplate", enchance);
                                            Messages.ENCHANCE_SUCCESS.sendMessage(p);
                                            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
                                        }
                                        EnchanceMenu.open(p);
                                    } else {
                                        Messages.CANNOT_AFFORD.sendMessage(p);
                                        p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
                                    }
                                }
                            }
                            case 5 -> {

                                List<String> consume_items = Yaml.ENCHANCE.getConfig().getStringList("menu.items.leggings.consume_items");
                                double price = Yaml.ENCHANCE.getConfig().getDouble("menu.items.leggings.price");

                                if (!Objects.isNull(consume_items)
                                        && !consume_items.isEmpty()) {
                                    for (String str : consume_items) {
                                        String[] parts = str.split(":");
                                        if (ThePit.getEconomy().getBalance(p) >= price
                                                && ItemFunction.hasItemAtLease(p, Integer.parseInt(parts[1]), parts[0].toLowerCase())) {
                                            Random none = new Random();
                                            if (none.nextInt(100) < 50) {
                                                Bukkit.getPluginManager().callEvent(new PlayerEnchanceEvent(p, null, "none", true));
                                                User.setEnchance(p, "leggings", "none");
                                                Messages.ENCHANCE_FAILURE.sendMessage(p);
                                                p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 1);
                                            } else {
                                                List<String> list = new ArrayList<>();
                                                list.addAll(EnchanceList.leggings_enchances);
                                                list.addAll(EnchanceList.normal_enchances);
                                                list.remove("none");
                                                Random r = new Random();
                                                int i = r.nextInt(list.size());
                                                String enchance = list.get(i);
                                                if (EnchanceList.leggings_enchances.contains(enchance)) {
                                                    Bukkit.getPluginManager().callEvent(new PlayerEnchanceEvent(p, EnchanceType.LEGGINGS, enchance, false));
                                                } else {
                                                    Bukkit.getPluginManager().callEvent(new PlayerEnchanceEvent(p, EnchanceType.NORMAL, enchance, false));
                                                }
                                                User.setEnchance(p, "leggings", enchance);
                                                Messages.ENCHANCE_SUCCESS.sendMessage(p);
                                                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
                                            }
                                            ItemFunction.consumeItem(p, Integer.parseInt(parts[1]), parts[0].toLowerCase());
                                            EnchanceMenu.open(p);
                                        } else {
                                            Messages.CANNOT_AFFORD.sendMessage(p);
                                            p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
                                        }
                                    }
                                } else {
                                    if (ThePit.getEconomy().getBalance(p) >= price) {
                                        ThePit.getEconomy().withdrawPlayer(p, price);
                                        Random none = new Random();
                                        if (none.nextInt(100) < 50) {
                                            Bukkit.getPluginManager().callEvent(new PlayerEnchanceEvent(p, null, "none", true));
                                            User.setEnchance(p, "leggings", "none");
                                            Messages.ENCHANCE_FAILURE.sendMessage(p);
                                            p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 1);
                                        } else {
                                            List<String> list = new ArrayList<>();
                                            list.addAll(EnchanceList.leggings_enchances);
                                            list.addAll(EnchanceList.normal_enchances);
                                            list.remove("none");
                                            Random r = new Random();
                                            int i = r.nextInt(list.size());
                                            String enchance = list.get(i);
                                            if (EnchanceList.leggings_enchances.contains(enchance)) {
                                                Bukkit.getPluginManager().callEvent(new PlayerEnchanceEvent(p, EnchanceType.LEGGINGS, enchance, false));
                                            } else {
                                                Bukkit.getPluginManager().callEvent(new PlayerEnchanceEvent(p, EnchanceType.NORMAL, enchance, false));
                                            }
                                            User.setEnchance(p, "leggings", enchance);
                                            Messages.ENCHANCE_SUCCESS.sendMessage(p);
                                            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
                                        }
                                        EnchanceMenu.open(p);
                                    } else {
                                        Messages.CANNOT_AFFORD.sendMessage(p);
                                        p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
                                    }
                                }
                            }
                            case 6 -> {

                                List<String> consume_items = Yaml.ENCHANCE.getConfig().getStringList("menu.items.boots.consume_items");
                                double price = Yaml.ENCHANCE.getConfig().getDouble("menu.items.boots.price");

                                if (!Objects.isNull(consume_items)
                                        && !consume_items.isEmpty()) {
                                    for (String str : consume_items) {
                                        String[] parts = str.split(":");
                                        if (ThePit.getEconomy().getBalance(p) >= price
                                                && ItemFunction.hasItemAtLease(p, Integer.parseInt(parts[1]), parts[0].toLowerCase())) {
                                            Random none = new Random();
                                            if (none.nextInt(100) < 50) {
                                                Bukkit.getPluginManager().callEvent(new PlayerEnchanceEvent(p, null, "none", true));
                                                User.setEnchance(p, "boots", "none");
                                                Messages.ENCHANCE_FAILURE.sendMessage(p);
                                                p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 1);
                                            } else {
                                                List<String> list = new ArrayList<>();
                                                list.addAll(EnchanceList.boots_enchances);
                                                list.addAll(EnchanceList.normal_enchances);
                                                list.remove("none");
                                                Random r = new Random();
                                                int i = r.nextInt(list.size());
                                                String enchance = list.get(i);
                                                if (EnchanceList.boots_enchances.contains(enchance)) {
                                                    Bukkit.getPluginManager().callEvent(new PlayerEnchanceEvent(p, EnchanceType.BOOTS, enchance, false));
                                                } else {
                                                    Bukkit.getPluginManager().callEvent(new PlayerEnchanceEvent(p, EnchanceType.NORMAL, enchance, false));
                                                }
                                                User.setEnchance(p, "boots", enchance);
                                                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
                                            }
                                            Messages.ENCHANCE_SUCCESS.sendMessage(p);
                                            EnchanceMenu.open(p);
                                        } else {
                                            Messages.CANNOT_AFFORD.sendMessage(p);
                                            p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
                                        }
                                    }
                                } else {
                                    if (ThePit.getEconomy().getBalance(p) >= price) {
                                        ThePit.getEconomy().withdrawPlayer(p, price);
                                        Random none = new Random();
                                        if (none.nextInt(100) < 80) {
                                            Bukkit.getPluginManager().callEvent(new PlayerEnchanceEvent(p, null, "none", true));
                                            User.setEnchance(p, "boots", "none");
                                            Messages.ENCHANCE_FAILURE.sendMessage(p);
                                            p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 1);
                                        } else {
                                            List<String> list = new ArrayList<>();
                                            list.addAll(EnchanceList.boots_enchances);
                                            list.addAll(EnchanceList.normal_enchances);
                                            list.remove("none");
                                            Random r = new Random();
                                            int i = r.nextInt(list.size());
                                            String enchance = list.get(i);
                                            if (EnchanceList.boots_enchances.contains(enchance)) {
                                                Bukkit.getPluginManager().callEvent(new PlayerEnchanceEvent(p, EnchanceType.BOOTS, enchance, false));
                                            } else {
                                                Bukkit.getPluginManager().callEvent(new PlayerEnchanceEvent(p, EnchanceType.NORMAL, enchance, false));
                                            }
                                            User.setEnchance(p, "boots", enchance);
                                            Messages.ENCHANCE_SUCCESS.sendMessage(p);
                                            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
                                        }
                                        EnchanceMenu.open(p);
                                    } else {
                                        Messages.CANNOT_AFFORD.sendMessage(p);
                                        p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    PlayerMaps.menu.put(uuid, "none");
                }
            }
        } else if (event instanceof InventoryCloseEvent e) {

            if (e.getPlayer() instanceof Player p) {

                UUID uuid = p.getUniqueId();

                if (PlayerMaps.menu.containsKey(uuid)) {
                    PlayerMaps.menu.put(uuid, "none");
                } else {
                    PlayerMaps.menu.put(uuid, "none");
                }
            }
        } else if (event instanceof PlayerQuitEvent e) {

            Player p = e.getPlayer();
            UUID uuid = p.getUniqueId();

            if (PlayerMaps.menu.containsKey(uuid)) {
                PlayerMaps.menu.put(uuid, "none");
            } else {
                PlayerMaps.menu.put(uuid, "none");
            }
        }
    }
}
