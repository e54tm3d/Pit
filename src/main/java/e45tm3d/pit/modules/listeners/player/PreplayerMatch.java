package e45tm3d.pit.modules.listeners.player;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.Preplayer;
import e45tm3d.pit.api.User;
import e45tm3d.pit.api.enums.Messages;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.api.events.PlayerConnectEvent;
import e45tm3d.pit.modules.listeners.ListenerModule;
import e45tm3d.pit.utils.ItemBuilder;
import e45tm3d.pit.utils.functions.InventoryFunction;
import e45tm3d.pit.utils.functions.NMSFunction;
import e45tm3d.pit.utils.lists.PlayerLists;
import e45tm3d.pit.utils.maps.PlayerMaps;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PreplayerMatch extends ListenerModule {

    private Map<UUID, Long> connect_time = new HashMap<>();
    private Map<UUID, Location> connect_location = new HashMap<>();

    @Override
    public void listen(Event event) {
        if (event instanceof PlayerConnectEvent e) {

            PlayerLists.preplayers.add(e.getPreplayer());

            Bukkit.getScheduler().runTaskLaterAsynchronously(ThePit.getInstance(), () -> {
                PlayerLists.preplayers.remove(e.getPreplayer());
            }, 200);

        } else if (event instanceof PlayerJoinEvent e) {

            for (PotionEffect effect : e.getPlayer().getActivePotionEffects()) {
                e.getPlayer().removePotionEffect(effect.getType());
            }

            connect_time.put(e.getPlayer().getUniqueId(), System.currentTimeMillis());
            connect_location.put(e.getPlayer().getUniqueId(), e.getPlayer().getLocation());

            // 隐藏玩家，避免看到其他玩家
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.hidePlayer(e.getPlayer());
            }
            
            // 无论preplayers是否为空，都尝试进行匹配
            Bukkit.getScheduler().runTaskLater(ThePit.getInstance(), () -> {
                boolean matched = false;
                Preplayer matchedPreplayer = null;
                
                // 检查是否有匹配的预玩家
                for (Preplayer preplayers : PlayerLists.preplayers) {
                    if (preplayers.getName().equals(e.getPlayer().getName())
                            && preplayers.getUniqueId().equals(e.getPlayer().getUniqueId())) {
                        matched = true;
                        matchedPreplayer = preplayers;
                        break;
                    }
                }
                
                // 如果找到匹配的预玩家，则添加到playing列表
                if (matched && matchedPreplayer != null) {
                    PlayerLists.playing.add(e.getPlayer().getUniqueId());
                    PlayerLists.preplayers.remove(matchedPreplayer);
                }
            }, 3);
            
            // 处理玩家状态和显示
            Bukkit.getScheduler().runTaskLater(ThePit.getInstance(), () -> {
                if (PlayerLists.playing.contains(e.getPlayer().getUniqueId())) {
                    // 让所有在线玩家可见这个玩家
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        players.showPlayer(e.getPlayer());
                    }
                    // 特别确保玩家能看到自己在TAB列表中
                    e.getPlayer().showPlayer(e.getPlayer());
                } else {
                    PlayerMaps.flight.put(e.getPlayer().getUniqueId(), true);
                    e.getPlayer().updateInventory();
                    e.getPlayer().setAllowFlight(true);
                    e.getPlayer().setFlying(true);
                    if (e.getPlayer().isOnline()) InventoryFunction.saveInventory(e.getPlayer());
                    e.getPlayer().getInventory().clear();
                    e.getPlayer().getInventory().setArmorContents(null);

                    ItemBuilder player_selector = new ItemBuilder(new ItemStack(Material.COMPASS))
                            .setName(Yaml.SPECTATING.getConfig().getString("items.player_selector.name"))
                            .setLore(Yaml.SPECTATING.getConfig().getStringList("items.player_selector.lore"))
                            .setIdentifier("player_selector");

                    ItemBuilder spectating_setting = new ItemBuilder(new ItemStack(Material.REDSTONE_COMPARATOR))
                            .setName(Yaml.SPECTATING.getConfig().getString("items.spectating_setting.name"))
                            .setLore(Yaml.SPECTATING.getConfig().getStringList("items.spectating_setting.lore"))
                            .setIdentifier("spectating_setting");

                    ItemBuilder left_game = new ItemBuilder(new ItemStack(Material.BED))
                            .setName(Yaml.SPECTATING.getConfig().getString("items.left_game.name"))
                            .setLore(Yaml.SPECTATING.getConfig().getStringList("items.left_game.lore"))
                            .setIdentifier("left_game");

                    e.getPlayer().getInventory().setItem(0, player_selector.build());

                    e.getPlayer().getInventory().setItem(4, spectating_setting.build());

                    e.getPlayer().getInventory().setItem(8, left_game.build());

                    NMSFunction.sendTitle(e.getPlayer(), Messages.SPECTATING.getMessage(), Messages.ONLY_STAFF.getMessage(), 0, 200, 0);
                    Bukkit.getScheduler().scheduleSyncRepeatingTask(ThePit.getInstance(), () -> {
                        for (Player playing : Bukkit.getOnlinePlayers()) {
                            if (User.isPlaying(playing)) {
                                for (Player players : Bukkit.getOnlinePlayers()) {
                                    players.showPlayer(playing);
                                }
                                // 确保玩家始终能看到自己在TAB列表中
                                if (!playing.getUniqueId().equals(e.getPlayer().getUniqueId()) && playing.canSee(e.getPlayer())) {
                                    playing.hidePlayer(e.getPlayer());
                                }
                            }
                        }
                        
                        // 定期确保玩家能看到自己在TAB列表中
                        if (PlayerLists.playing.contains(e.getPlayer().getUniqueId())) {
                            e.getPlayer().showPlayer(e.getPlayer());
                        }
                    }, 1, 1);
                }
            }, 5);
        } else if (event instanceof PlayerMoveEvent e) {
            if (System.currentTimeMillis() - connect_time.getOrDefault(e.getPlayer().getUniqueId(), System.currentTimeMillis()) < 500) {
                e.getPlayer().teleport(connect_location.getOrDefault(e.getPlayer().getUniqueId(), e.getPlayer().getLocation()));
            }
        } else if (event instanceof PlayerInteractEvent e) {
            if (System.currentTimeMillis() - connect_time.getOrDefault(e.getPlayer().getUniqueId(), System.currentTimeMillis()) < 500) {
                e.setCancelled(true);
            }
        }
    }
}