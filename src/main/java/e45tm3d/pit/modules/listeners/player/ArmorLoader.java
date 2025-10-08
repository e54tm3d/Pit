package e45tm3d.pit.modules.listeners.player;

import e45tm3d.pit.api.User;
import e45tm3d.pit.modules.listeners.ListenerModule;
import e45tm3d.pit.utils.lists.EnchanceList;
import e45tm3d.pit.utils.maps.EnchanceMaps;
import e45tm3d.pit.utils.maps.PlayerMaps;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ArmorLoader extends ListenerModule {

    @Override
    public void listen(Event event) {
        if (event instanceof InventoryCloseEvent e) {
            handleInventoryClose(e);
        } else if (event instanceof PlayerJoinEvent e) {
            handlePlayerJoin(e);
        } else if (event instanceof PlayerPickupItemEvent e) {
            handlePlayerPickupItem(e);
        }
    }

    private void handleInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        UUID playerId = player.getUniqueId();

        String menuType = PlayerMaps.menu.getOrDefault(playerId, "none");
        PlayerMaps.menu.putIfAbsent(playerId, "none");

        if ("armor".equals(menuType)) {
            applyPlayerArmor(player);
        } else if ("enchance".equals(menuType)) {
            applyArmorEnchants(player);
        }
    }

    private void handlePlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        Player player = event.getPlayer();

        clearAllArmorSlots(player);
        applyPlayerArmor(player);
        applyArmorEnchants(player);
    }

    private void clearAllArmorSlots(Player player) {
        player.getInventory().setHelmet(new ItemStack(Material.AIR));
        player.getInventory().setChestplate(new ItemStack(Material.AIR));
        player.getInventory().setLeggings(new ItemStack(Material.AIR));
        player.getInventory().setBoots(new ItemStack(Material.AIR));
    }

    private void applyPlayerArmor(Player player) {

        int helmetLevel = User.getHelmetLevel(player);
        if (helmetLevel >= 1) {
            player.getInventory().setHelmet(createArmorPiece(ArmorType.HELMET, helmetLevel));
        }

        int chestplateLevel = User.getChestplateLevel(player);
        if (chestplateLevel >= 1) {
            player.getInventory().setChestplate(createArmorPiece(ArmorType.CHESTPLATE, chestplateLevel));
        }

        int leggingsLevel = User.getLeggingsLevel(player);
        if (leggingsLevel >= 1) {
            player.getInventory().setLeggings(createArmorPiece(ArmorType.LEGGINGS, leggingsLevel));
        }

        int bootsLevel = User.getBootsLevel(player);
        if (bootsLevel >= 1) {
            player.getInventory().setBoots(createArmorPiece(ArmorType.BOOTS, bootsLevel));
        }
    }

    private void applyArmorEnchants(Player player) {

        if (player.getInventory().getHelmet() != null) {
            boolean helmetHasEnchant = !User.getEnchance(player, "helmet").equals("none");
            applyArmorEnchant(player.getInventory().getHelmet(), helmetHasEnchant);

            if (helmetHasEnchant) {

                String enchantType = User.getEnchance(player, "helmet");
                ItemMeta meta = player.getInventory().getHelmet().getItemMeta();
                boolean empty = !EnchanceList.enchances.contains(enchantType);
                if (empty) User.setEnchance(player, "helmet", "none");
                if (enchantType != null && EnchanceMaps.enchances.containsKey(enchantType)) {
                    if (!Objects.isNull(player.getInventory().getHelmet().getItemMeta().getLore())) {
                        for (String enchance : EnchanceMaps.enchances.get(enchantType)) {
                            player.getInventory().getHelmet().getItemMeta().getLore().add(enchance
                                    .replaceAll("&", "§")
                            );
                        }
                    } else {
                        List<String> lore = new ArrayList<>();
                        for (String enchance : EnchanceMaps.enchances.get(enchantType)) {
                            lore.add(enchance
                                    .replaceAll("&", "§")
                            );
                        }
                        meta.setLore(lore);
                    }
                }
                player.getInventory().getHelmet().setItemMeta(meta);
            }

        }

        if (player.getInventory().getChestplate() != null) {
            boolean chestplateHasEnchant = !User.getEnchance(player, "chestplate").equals("none");
            applyArmorEnchant(player.getInventory().getChestplate(), chestplateHasEnchant);

            if (chestplateHasEnchant) {

                String enchantType = User.getEnchance(player, "chestplate");
                ItemMeta meta = player.getInventory().getChestplate().getItemMeta();
                boolean empty = !EnchanceList.enchances.contains(enchantType);
                if (empty) User.setEnchance(player, "chestplate", "none");
                if (enchantType != null && EnchanceMaps.enchances.containsKey(enchantType)) {
                    if (!Objects.isNull(player.getInventory().getChestplate().getItemMeta().getLore())) {

                        for (String enchance : EnchanceMaps.enchances.get(enchantType)) {
                            player.getInventory().getChestplate().getItemMeta().getLore().add(enchance
                                    .replaceAll("&", "§")
                            );
                        }
                    } else {
                        List<String> lore = new ArrayList<>();
                        for (String enchance : EnchanceMaps.enchances.get(enchantType)) {
                            lore.add(enchance
                                    .replaceAll("&", "§")
                            );
                        }
                        meta.setLore(lore);
                    }
                }
                player.getInventory().getChestplate().setItemMeta(meta);
            }

        }

        if (player.getInventory().getLeggings() != null) {
            boolean leggingsHasEnchant = !User.getEnchance(player, "leggings").equals("none");
            applyArmorEnchant(player.getInventory().getLeggings(), leggingsHasEnchant);

            if (leggingsHasEnchant) {

                String enchantType = User.getEnchance(player, "leggings");
                ItemMeta meta = player.getInventory().getLeggings().getItemMeta();
                boolean empty = !EnchanceList.enchances.contains(enchantType);
                if (empty) User.setEnchance(player, "leggings", "none");
                if (enchantType != null && EnchanceMaps.enchances.containsKey(enchantType)) {
                    if (!Objects.isNull(player.getInventory().getLeggings().getItemMeta().getLore())) {

                        for (String enchance : EnchanceMaps.enchances.get(enchantType)) {
                            player.getInventory().getLeggings().getItemMeta().getLore().add(enchance
                                    .replaceAll("&", "§")
                            );
                        }
                    } else {
                        List<String> lore = new ArrayList<>();
                        for (String enchance : EnchanceMaps.enchances.get(enchantType)) {
                            lore.add(enchance
                                    .replaceAll("&", "§")
                            );
                        }
                        meta.setLore(lore);
                    }
                }
                player.getInventory().getLeggings().setItemMeta(meta);
            }

        }

        if (player.getInventory().getBoots() != null) {
            boolean bootsHasEnchant = !User.getEnchance(player, "boots").equals("none");
            applyArmorEnchant(player.getInventory().getBoots(), bootsHasEnchant);

            if (bootsHasEnchant) {

                String enchantType = User.getEnchance(player, "boots");
                ItemMeta meta = player.getInventory().getBoots().getItemMeta();
                boolean empty = !EnchanceList.enchances.contains(enchantType);
                if (empty) User.setEnchance(player, "boots", "none");
                if (enchantType != null && EnchanceMaps.enchances.containsKey(enchantType)) {
                    if (!Objects.isNull(player.getInventory().getBoots().getItemMeta().getLore())) {

                        for (String enchance : EnchanceMaps.enchances.get(enchantType)) {
                            player.getInventory().getBoots().getItemMeta().getLore().add(enchance
                                    .replaceAll("&", "§")
                            );
                        }
                    } else {
                        List<String> lore = new ArrayList<>();
                        for (String enchance : EnchanceMaps.enchances.get(enchantType)) {
                            lore.add(enchance
                                    .replaceAll("&", "§")
                            );
                        }
                        meta.setLore(lore);
                    }
                }
                player.getInventory().getBoots().setItemMeta(meta);
            }

        }
    }

    private ItemStack createArmorPiece(ArmorType type, int level) {
        ItemStack armorPiece = new ItemStack(type.getMaterialByLevel(level));
        ItemMeta meta = armorPiece.getItemMeta();
        
        if (meta != null) {
            meta.spigot().setUnbreakable(true);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            armorPiece.setItemMeta(meta);
        }
        
        return armorPiece;
    }

    private void applyArmorEnchant(ItemStack armorPiece, boolean shouldAddEnchant) {
        ItemMeta meta = armorPiece.getItemMeta();
        
        if (meta != null) {
            meta.removeEnchant(Enchantment.DURABILITY);

            if (shouldAddEnchant) {
                meta.addEnchant(Enchantment.DURABILITY, 1, true);
            }

            meta.spigot().setUnbreakable(true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            
            armorPiece.setItemMeta(meta);
        }
    }

    // 新增方法：处理玩家捡起物品事件，只更新捡起的盔甲
    private void handlePlayerPickupItem(PlayerPickupItemEvent event) {
        ItemStack item = event.getItem().getItemStack();
        Player player = event.getPlayer();

        // 判断是否为盔甲
        if (isArmor(item.getType())) {
            updateArmorLoreAndEnchant(player, item);
        }
    }

    // 判断物品类型是否为盔甲
    private boolean isArmor(Material material) {
        return material.name().endsWith("_HELMET")
                || material.name().endsWith("_CHESTPLATE")
                || material.name().endsWith("_LEGGINGS")
                || material.name().endsWith("_BOOTS");
    }

    // 只更新捡起的盔甲的lore和附魔
    private void updateArmorLoreAndEnchant(Player player, ItemStack armorPiece) {
        if (armorPiece == null) return;
        String slot = getArmorSlot(armorPiece.getType());
        if (slot == null) return;

        boolean hasEnchant = !User.getEnchance(player, slot).equals("none");
        applyArmorEnchant(armorPiece, hasEnchant);

        if (hasEnchant) {
            String enchantType = User.getEnchance(player, slot);
            ItemMeta meta = armorPiece.getItemMeta();
            boolean empty = !EnchanceList.enchances.contains(enchantType);
            if (empty) User.setEnchance(player, slot, "none");
            if (enchantType != null && EnchanceMaps.enchances.containsKey(enchantType)) {
                List<String> lore = meta.getLore() != null ? meta.getLore() : new ArrayList<>();
                for (String enchance : EnchanceMaps.enchances.get(enchantType)) {
                    lore.add(enchance.replaceAll("&", "§"));
                }
                meta.setLore(lore);
            }
            armorPiece.setItemMeta(meta);
        }
    }

    // 根据物品类型获取盔甲槽位字符串
    private String getArmorSlot(Material material) {
        if (material.name().endsWith("_HELMET")) return "helmet";
        if (material.name().endsWith("_CHESTPLATE")) return "chestplate";
        if (material.name().endsWith("_LEGGINGS")) return "leggings";
        if (material.name().endsWith("_BOOTS")) return "boots";
        return null;
    }

    private enum ArmorType {
        HELMET {
            @Override
            public Material getMaterialByLevel(int level) {
                return switch (level) {
                    case 2 -> Material.GOLD_HELMET;
                    case 3 -> Material.CHAINMAIL_HELMET;
                    case 4 -> Material.IRON_HELMET;
                    case 5 -> Material.DIAMOND_HELMET;
                    default -> Material.LEATHER_HELMET;
                };
            }
        },
        CHESTPLATE {
            @Override
            public Material getMaterialByLevel(int level) {
                return switch (level) {
                    case 2 -> Material.GOLD_CHESTPLATE;
                    case 3 -> Material.CHAINMAIL_CHESTPLATE;
                    case 4 -> Material.IRON_CHESTPLATE;
                    case 5 -> Material.DIAMOND_CHESTPLATE;
                    default -> Material.LEATHER_CHESTPLATE;
                };
            }
        },
        LEGGINGS {
            @Override
            public Material getMaterialByLevel(int level) {
                return switch (level) {
                    case 2 -> Material.GOLD_LEGGINGS;
                    case 3 -> Material.CHAINMAIL_LEGGINGS;
                    case 4 -> Material.IRON_LEGGINGS;
                    case 5 -> Material.DIAMOND_LEGGINGS;
                    default -> Material.LEATHER_LEGGINGS;
                };
            }
        },
        BOOTS {
            @Override
            public Material getMaterialByLevel(int level) {
                return switch (level) {
                    case 2 -> Material.GOLD_BOOTS;
                    case 3 -> Material.CHAINMAIL_BOOTS;
                    case 4 -> Material.IRON_BOOTS;
                    case 5 -> Material.DIAMOND_BOOTS;
                    default -> Material.LEATHER_BOOTS;
                };
            }
        };

        public abstract Material getMaterialByLevel(int level);
    }
}


