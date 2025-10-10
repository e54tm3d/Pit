package e45tm3d.pit.modules.listeners;

import java.util.ArrayList;
import java.util.List;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.events.PlayerDeadEvent;
import e45tm3d.pit.api.events.PlayerEnchanceEvent;
import e45tm3d.pit.api.events.PlayerMurderEvent;
import e45tm3d.pit.api.events.PlayerObtainWeaponEvent;
import e45tm3d.pit.modules.items.weapon.WeaponModule;
import e45tm3d.pit.modules.items.weapon.items.bows.ArtemisBow;
import e45tm3d.pit.modules.items.weapon.items.bows.WoodenBow;
import e45tm3d.pit.modules.items.weapon.items.swords.*;
import e45tm3d.pit.modules.listeners.player.*;
import e45tm3d.pit.modules.listeners.world.*;
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

import com.google.common.collect.Lists;

public class Listeners implements Listener {

	private static List<ListenerModule> listeners = new ArrayList<>();
    private static boolean registered = false;

    public Listeners() {
        ThePit.getInstance().getLogger().info("Loading listener module...");

        if (registered) {
            HandlerList.unregisterAll(this);
            registered = false;
        }

        listeners.clear();

        listeners = Lists.newArrayList(new ArmorLoader(), new ArmorSlotLock(), new ArrowLoader(), new DatabaseLoader(), new Dead(), new DevelopMode(),
                new Fighting(), new Saturation(), new GainGold(), new JumpPad(), new LevelLoader(), new MenuManager(), new Murder(), new ItemCleaner(),
                new NoArrowPickUp(), new NoFallDamage(), new ScoreboardRemove(), new SpawnProtect(), new TpLogin(), new EnchanceTable(), new WeaponLoader(),
                new ItemProtect()

                , new ArenaProtect(), new WeatherChange());

        Bukkit.getPluginManager().registerEvents(this, ThePit.getInstance());
        registered = true;

        ThePit.getInstance().getLogger().info("Listener module loaded successfully!");
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        listen(e);
    }

    @EventHandler
    public void onEnchance(PlayerEnchanceEvent e) {
        if (e.isCancelled()) {
            return;
        }
        listen(e);
    }

    @EventHandler
    public void onObtainWeapon(PlayerObtainWeaponEvent e ) {
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
    public void onCommandPreprocess(PlayerCommandPreprocessEvent e) {
        listen(e);
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent e) {
        if (e.isCancelled()) {
            return;
        }
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
		for (ListenerModule listener : this.listeners) {
			listener.listen(event);
		}
	}

    public static void registerListener(ListenerModule listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }
}