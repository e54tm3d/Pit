package e45tm3d.pit.modules.enchance;

import com.google.common.collect.Lists;
import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.events.PlayerDeadEvent;
import e45tm3d.pit.api.events.PlayerGainGoldEvent;
import e45tm3d.pit.api.events.PlayerMurderEvent;
import e45tm3d.pit.modules.buff.buffs.StabilizedProjectile;
import e45tm3d.pit.modules.enchance.enchances.boots.Speed;
import e45tm3d.pit.modules.enchance.enchances.chestplate.DamageAbsorption;
import e45tm3d.pit.modules.enchance.enchances.helmet.Shield;
import e45tm3d.pit.modules.enchance.enchances.leggings.JumpBoost;
import e45tm3d.pit.modules.enchance.enchances.normal.None;
import e45tm3d.pit.modules.enchance.enchances.normal.Regeneration;
import e45tm3d.pit.modules.enchance.enchances.weapon.Berserker;
import e45tm3d.pit.modules.enchance.enchances.weapon.LifeSteal;
import e45tm3d.pit.modules.tasks.TaskModule;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;

import java.util.ArrayList;
import java.util.List;

public class Enchances implements Listener {

	private static List<EnchanceModule> enchances = new ArrayList<>();

	public Enchances() {
        ThePit.getInstance().getLogger().info("Loading enchance module...");

        enchances = Lists.newArrayList(new None(), new Regeneration(), new DamageAbsorption(), new JumpBoost(), new Shield(), new Berserker(),
                new LifeSteal(), new Speed());

		Bukkit.getPluginManager().registerEvents(this, ThePit.getInstance());

        List<EnchanceModule> copy = Lists.newArrayList(enchances);
        copy.forEach(this::register);

        ThePit.getInstance().getLogger().info("Enchance module loaded successfully!");
	}

    @EventHandler
    public void onPlayerGainGold(final PlayerGainGoldEvent e) {
        if (e.isCancelled()) {
            return;
        }
        listen(e);
    }

    @EventHandler
    public void onDead(final PlayerDeadEvent e) {
        listen(e);
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent e) {
        listen(e);
    }

    @EventHandler
    public void onDrop(final InventoryOpenEvent e) {
        if (e.isCancelled()) {
            return;
        }
        listen(e);
    }

    @EventHandler
    public void onDrop(final PlayerDropItemEvent e) {
        if (e.isCancelled()) {
            return;
        }
        listen(e);
    }

    @EventHandler
    public void onMurder(final PlayerMurderEvent e) {
        if (e.isCancelled()) {
            return;
        }
        listen(e);
    }

    @EventHandler
    public void onFoodLevelChange(final FoodLevelChangeEvent e) {
        if (e.isCancelled()) {
            return;
        }
        listen(e);
    }

    @EventHandler
    public void onPlayerPickupItem(final PlayerPickupItemEvent e) {
        if (e.isCancelled()) {
            return;
        }
        listen(e);
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent e) {
        listen(e);
    }

    @EventHandler
    public void onInventoryClose(final InventoryCloseEvent e) {
        listen(e);
    }

	@EventHandler
	public void onInventoryClick(final InventoryClickEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onInteractAtEntity(final PlayerInteractAtEntityEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onAnimation(final PlayerAnimationEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onDamageByEntity(final EntityDamageByEntityEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onShootBow(final EntityShootBowEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onSprint(final PlayerToggleSprintEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onRegain(final EntityRegainHealthEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onMove(final PlayerMoveEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onChat(final AsyncPlayerChatEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onBreak(final BlockBreakEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onPlace(final BlockPlaceEvent e) {
		if (e.isCancelled() || !e.canBuild()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onDamage(final EntityDamageEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onInteract(final PlayerInteractEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onItemConsume(final PlayerItemConsumeEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	private void listen(Event event) {
		for (EnchanceModule item : enchances) {
            item.listen(event);
		}
	}

    private void register(EnchanceModule enchance) {
        enchance.register();
    }

    protected static void registerEnchcance(EnchanceModule enchance) {
        enchances.add(enchance);
    }
}