package e45tm3d.pit.modules.tasks.player;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.User;
import e45tm3d.pit.api.enums.Messages;
import e45tm3d.pit.modules.tasks.TaskModule;
import e45tm3d.pit.utils.functions.NMSFunction;
import e45tm3d.pit.utils.lists.PlayerLists;
import e45tm3d.pit.utils.maps.PlayerMaps;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.UUID;

public class SpectatingMode extends TaskModule {

    public static String tagrte = "";
    public static String dist = "";

    DecimalFormat df = new DecimalFormat("0.00");

    @Override
    public void run(TaskModule task) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ThePit.getInstance(), () -> {
            for (Player spectator : Bukkit.getOnlinePlayers()) {
                if (!User.isPlaying(spectator)) {
                    UUID uuid = spectator.getUniqueId();

                    if (PlayerMaps.spectating_selected.containsKey(uuid)) {
                        ArrayList<Player> playing = PlayerLists.getPlayingPlayers();
                        if (!playing.isEmpty()) {
                            // 获取玩家选择的目标玩家
                            Player selectedPlayer = PlayerMaps.spectating_selected.get(uuid);
                            Player target = null;
                            
                            // 如果有选择的玩家，则使用该玩家作为目标
                            if (selectedPlayer != null) {
                                target = selectedPlayer;
                            } else {
                                // 如果没有选择的玩家或选择的玩家无效，则默认选择第一个玩家
                                target = playing.get(0);
                                PlayerMaps.spectating_selected.put(uuid, target);
                            }
                            
                            double distance = spectator.getLocation().distance(target.getLocation());

                            dist = df.format(distance);
                            // 设置目标玩家的名字，避免空指针异常
                            tagrte = target.getName();

                            Messages.SPECTATING_TARGET.sendActionbar(spectator);

                            if (distance > 10) {
                                spectator.teleport(target);
                            }
                        }
                    }

                    if (PlayerMaps.night_vision.getOrDefault(uuid, false)) {
                        spectator.addPotionEffect(PotionEffectType.NIGHT_VISION.createEffect(600, 0), true);
                    } else {
                        spectator.removePotionEffect(PotionEffectType.NIGHT_VISION);
                    }

                    if (PlayerMaps.speed.getOrDefault(uuid, 0) > 0) {
                        spectator.addPotionEffect(PotionEffectType.SPEED.createEffect(600, PlayerMaps.speed.getOrDefault(uuid, 0) - 1), true);
                    }
                    if (PlayerMaps.always_flight.getOrDefault(uuid, false) && PlayerMaps.flight.getOrDefault(uuid, false)) {
                        spectator.setAllowFlight(true);
                        spectator.setFlying(true);
                    }
                    if (PlayerMaps.jump_boost.getOrDefault(uuid, 0) > 0) {
                        spectator.addPotionEffect(PotionEffectType.JUMP.createEffect(600, PlayerMaps.jump_boost.getOrDefault(uuid, 0) - 1), true);
                    }
                }
            }
        }, 1, 1);
    }
}