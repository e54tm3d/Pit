package e45tm3d.pit.modules.listeners.player;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.User;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.api.events.PlayerDeadEvent;
import e45tm3d.pit.api.events.PlayerMurderEvent;
import e45tm3d.pit.modules.listeners.ListenerModule;
import e45tm3d.pit.utils.functions.PlayerFunction;
import e45tm3d.pit.utils.functions.VariableFunction;
import e45tm3d.pit.utils.maps.PlayerMaps;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Random;

public class Dead extends ListenerModule {

    @Override
    public void listen(Event event) {
        if (event instanceof PlayerDeadEvent e) {
            Player p = e.getDead();
            if (PlayerFunction.isInArena(p)) {
                int deaths = User.getDeaths(p);
                User.setKillstreak(p, 0);
                User.setDeaths(p, deaths + 1);
                PlayerMaps.fight_time.put(p.getUniqueId(), 0L);
            }
        } else if (event instanceof PlayerDeathEvent e) {
            Bukkit.getPluginManager().callEvent(new PlayerDeadEvent(e.getEntity()));
            Bukkit.getPluginManager().callEvent(new PlayerMurderEvent(e.getEntity(), e.getEntity().getKiller()));
            e.setDeathMessage(null);
            e.setKeepInventory(true);
            e.setKeepLevel(true);
            Bukkit.getScheduler().scheduleSyncDelayedTask(ThePit.getInstance(), () -> {
                e.getEntity().spigot().respawn();
            }, 1);
        } else if (event instanceof EntityDamageByEntityEvent e) {

            if (e.getEntity() instanceof Player p && User.isPlaying(p)) {

                boolean isPlayerDead = p.getHealth() <= e.getFinalDamage() || p.getMaxHealth() <= e.getFinalDamage();

                if (isPlayerDead && !e.isCancelled()) {
                    Bukkit.getPluginManager().callEvent(new PlayerMurderEvent(p, p.getKiller()));
                    Bukkit.getPluginManager().callEvent(new PlayerDeadEvent(p));
                    e.setCancelled(true);
                    p.teleport(VariableFunction.getSpawnLocation());
                    p.setHealth(p.getMaxHealth());
                    p.setFoodLevel(20);
                    p.getActivePotionEffects().clear();
                    p.setFallDistance(0);
                    p.setFireTicks(0);
                    PlayerFunction.sendTitle(p, Yaml.CONFIG.getConfig().getString("title.death.title").replaceAll("&", "§"),
                            Yaml.CONFIG.getConfig().getString("title.death.subtitle").replaceAll("&", "§"), 0, 60, 0);
                }
            }
        } else if (event instanceof EntityDamageEvent e) {
            if (e.getEntity() instanceof Player p && User.isPlaying(p)) {
                if (!e.isCancelled()) {
                    if (!e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)
                            && !e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)
                            && !e.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE)
                            && !e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                        if (p.getHealth() <= e.getFinalDamage()) {
                            Bukkit.getPluginManager().callEvent(new PlayerDeadEvent(p));
                            e.setCancelled(true);
                            p.teleport(VariableFunction.getSpawnLocation());
                            p.setHealth(p.getMaxHealth());
                            p.setFoodLevel(20);
                            p.getActivePotionEffects().clear();
                            p.setFallDistance(0);
                            p.setFireTicks(0);
                            PlayerFunction.sendTitle(p, Yaml.CONFIG.getConfig().getString("title.death.title").replaceAll("&", "§"),
                                    Yaml.CONFIG.getConfig().getString("title.death.subtitle").replaceAll("&", "§"), 0, 60, 0);
                        }
                    }
                }
            }
        }
    }
}
