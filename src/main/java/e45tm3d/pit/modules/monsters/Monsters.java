package e45tm3d.pit.modules.monsters;

import com.google.common.collect.Lists;
import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.events.PlayerDeadEvent;
import e45tm3d.pit.api.events.PlayerGainGoldEvent;
import e45tm3d.pit.api.events.PlayerMurderEvent;
import e45tm3d.pit.modules.enchance.EnchanceModule;
import e45tm3d.pit.modules.listeners.player.*;
import e45tm3d.pit.modules.listeners.world.ArenaProtect;
import e45tm3d.pit.modules.listeners.world.WeatherChange;
import e45tm3d.pit.modules.monsters.bosses.SlimeKing;
import e45tm3d.pit.modules.monsters.monsters.*;
import e45tm3d.pit.utils.lists.MonsterLists;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Monsters implements Listener {

	private static List<MonsterModule> monsters = new ArrayList<>();
    private static boolean registered = false;

    public Monsters() {
        ThePit.getInstance().getLogger().info("Loading monster module...");

        if (registered) {
            HandlerList.unregisterAll(this);
            registered = false;
        }

        monsters.clear();

        monsters = Lists.newArrayList(new LavaSlime(), new Slime(), new LightningCreeper(), new Skeleton(), new Zombie(),


                new SlimeKing());

        Bukkit.getPluginManager().registerEvents(this, ThePit.getInstance());
        registered = true;

        List<MonsterModule> copy = Lists.newArrayList(monsters);

        copy.forEach(this::register);

        ThePit.getInstance().getLogger().info("Monster module loaded successfully!");
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        if (e.isCancelled()) {
            return;
        }
        listen(e);
    }

    @EventHandler
    public void onSlimeSplit(SlimeSplitEvent e) {
        if (e.isCancelled()) {
            return;
        }
        listen(e);
    }

    @EventHandler
    public void onDead(EntityDeathEvent e) {
        e.setDroppedExp(0);
        e.getDrops().clear();
        MonsterLists.entities.remove(e.getEntity());
        MonsterLists.boss.remove(e.getEntity());
        listen(e);
    }

    @EventHandler
    public void onPlayerGainGold(PlayerGainGoldEvent e) {
        if (e.isCancelled()) {
            return;
        }
        listen(e);
    }

    @EventHandler
    public void onDead(PlayerDeadEvent e) {
        listen(e);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        listen(e);
    }

    @EventHandler
    public void onDrop(InventoryOpenEvent e) {
        if (e.isCancelled()) {
            return;
        }
        listen(e);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if (e.isCancelled()) {
            return;
        }
        listen(e);
    }

    @EventHandler
    public void onMurder(PlayerMurderEvent e) {
        if (e.isCancelled()) {
            return;
        }
        listen(e);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        if (e.isCancelled()) {
            return;
        }
        listen(e);
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent e) {
        if (e.isCancelled()) {
            return;
        }
        listen(e);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        listen(e);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        listen(e);
    }

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onInteractAtEntity(PlayerInteractAtEntityEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onAnimation(PlayerAnimationEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onDamageByEntity(EntityDamageByEntityEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onShootBow(EntityShootBowEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onSprint(PlayerToggleSprintEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onRegain(EntityRegainHealthEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if (e.isCancelled() || !e.canBuild()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onItemConsume(PlayerItemConsumeEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	private void listen(Event event) {
		for (MonsterModule monster : monsters) {
            monster.listen(event);
		}
	}

    private void register(MonsterModule monster) {
        monster.register();
    }

    public static void registerMonster(MonsterModule monster) {
        if (!monsters.contains(monster)) {
            monsters.add(monster);
        }
    }
}