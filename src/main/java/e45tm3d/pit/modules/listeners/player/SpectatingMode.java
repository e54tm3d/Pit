package e45tm3d.pit.modules.listeners.player;

import e45tm3d.pit.api.User;
import e45tm3d.pit.api.enums.Messages;
import e45tm3d.pit.api.events.PlayerRightClickEvent;
import e45tm3d.pit.modules.listeners.ListenerModule;
import e45tm3d.pit.utils.functions.NMSFunction;
import e45tm3d.pit.utils.lists.PlayerLists;
import e45tm3d.pit.utils.maps.PlayerMaps;
import e45tm3d.pit.utils.menus.spectating_menus.PlayerSelector;
import e45tm3d.pit.utils.menus.spectating_menus.SpectatingSettings;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;

import java.util.*;

public class SpectatingMode extends ListenerModule {

    @Override
    public void listen(Event event) {
        if (event instanceof EntityDamageByEntityEvent e) {
            if (e.getDamager() instanceof Player p) {
                if (!User.isPlaying(p)) {
                    e.setCancelled(true);
                }
            }
            if (e.getEntity() instanceof Player p) {
                if (!User.isPlaying(p)) {
                    e.setCancelled(true);
                }
            }
        } else if (event instanceof PlayerPickupItemEvent e) {
            if (!User.isPlaying(e.getPlayer())) {
                e.setCancelled(true);
            }
        } else if (event instanceof PlayerDropItemEvent e) {
            if (!User.isPlaying(e.getPlayer())) {
                e.setCancelled(true);
            }
        } else if (event instanceof PlayerAnimationEvent e) {
            // 修复玩家选择器交互逻辑
            if (!User.isPlaying(e.getPlayer())) {
                if (!Objects.isNull(e.getPlayer().getItemInHand()) && !Objects.isNull(e.getPlayer().getItemInHand().getItemMeta())) { // 添加空检查，避免空指针异常
                    if (NMSFunction.hasNBTTag(e.getPlayer().getItemInHand(), "player_selector")) {
                        ArrayList<Player> players = PlayerLists.getPlayingPlayers();
                        if (!players.isEmpty()) {
                            UUID uuid = e.getPlayer().getUniqueId();
                            // 获取当前选择的玩家
                            Player currentSelected = PlayerMaps.spectating_selected.get(uuid);
                            int currentIndex = -1;
                            
                            // 找到当前选择的玩家在列表中的索引
                            if (currentSelected != null) {
                                for (int i = 0; i < players.size(); i++) {
                                    if (players.get(i).getUniqueId().equals(currentSelected.getUniqueId())) {
                                        currentIndex = i;
                                        break;
                                    }
                                }
                            }
                            
                            // 如果找不到当前选择的玩家或不存在，则设置为0
                            if (currentIndex == -1) {
                                currentIndex = 0;
                            }
                            
                            // 递增索引，但确保不会超出范围（使用取模运算实现循环）
                            int nextIndex = (currentIndex + 1) % players.size();
                            // 更新存储的玩家
                            Player nextPlayer = players.get(nextIndex);
                            PlayerMaps.spectating_selected.put(uuid, nextPlayer);
                            // 传送到下一个玩家
                            e.getPlayer().teleport(nextPlayer);
                        }
                    }
                }
            }
        } else if (event instanceof EntityDamageEvent e) {
            if (e.getEntity() instanceof Player p) {
                if (!User.isPlaying(p)) {
                    e.setCancelled(true);
                }
            }
        } else if (event instanceof EntityTargetEvent e) {
            if (e.getTarget() instanceof Player p) {
                if (!User.isPlaying(p)) {
                    e.setCancelled(true);
                }
            }
        } else if (event instanceof BlockPlaceEvent e) {
            if (!User.isPlaying(e.getPlayer())) {
                e.setCancelled(true);
            }
        } else if (event instanceof BlockBreakEvent e) {
            if (!User.isPlaying(e.getPlayer())) {
                e.setCancelled(true);
            }
        } else if (event instanceof PlayerInteractAtEntityEvent e) {
            if (e.getRightClicked() instanceof Player p) {
                if (User.isPlaying(p)) {
                    Player spectator = e.getPlayer();
                    if (!User.isPlaying(spectator)) {
                        e.getPlayer().setGameMode(GameMode.SPECTATOR);
                        spectator.setSpectatorTarget(p);
                        NMSFunction.sendTitle(spectator, Messages.SPECTATING.getMessage(), Messages.ONLY_STAFF.getMessage(), 0, 20, 0);
                    }
                }
            }
        } else if (event instanceof PlayerGameModeChangeEvent e) {
            Player p = e.getPlayer();
            if (!User.isPlaying(p)) {
                if (e.getNewGameMode().equals(GameMode.SURVIVAL)) {
                    NMSFunction.sendTitle(p, Messages.SPECTATING.getMessage(), Messages.ONLY_STAFF.getMessage(), 0, 20, 0);
                }
            }
        } else if (event instanceof PlayerToggleSneakEvent e) {
            Player p = e.getPlayer();
            if (!User.isPlaying(p)) {
                if (p.getGameMode().equals(GameMode.SPECTATOR)) {
                    p.setGameMode(GameMode.SURVIVAL);
                    p.setAllowFlight(true);
                    p.setFlying(true);
                }
            }
        } else if (event instanceof InventoryClickEvent e) {
            if (e.getWhoClicked() instanceof Player p) {
                if (!User.isPlaying(p)) {
                    if (e.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
                        switch (e.getSlot()) {
                            case 0 -> {
                                PlayerSelector.open(p);
                            }
                            case 4 -> {
                                SpectatingSettings.open(p);
                            }
                        }
                    }
                    e.setCancelled(true);
                }
            }
        } else if (event instanceof PlayerRightClickEvent e) {
            if (!User.isPlaying(e.getPlayer())) {
                if (!Objects.isNull(e.getItem()) && !Objects.isNull(e.getItem().getItemMeta())) { // 添加空检查，避免空指针异常
                    if (e.getClickType().equals(PlayerRightClickEvent.RightClickType.AIR) || e.getClickType().equals(PlayerRightClickEvent.RightClickType.BLOCK)) {
                        if (NMSFunction.hasNBTTag(e.getItem(), "player_selector")) {
                            PlayerSelector.open(e.getPlayer());
                        } else if (NMSFunction.hasNBTTag(e.getItem(), "spectating_setting")) {
                            SpectatingSettings.open(e.getPlayer());
                        } else if (NMSFunction.hasNBTTag(e.getItem(), "left_game")) {
                            e.getPlayer().performCommand("goto lobby");
                        }
                    }
                }
            }
        } else if (event instanceof PlayerInteractEvent e) {
            if (!User.isPlaying(e.getPlayer())) {
                e.setCancelled(true);
            }
        }
    }
}