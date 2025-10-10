package e45tm3d.pit.modules.items.weapon;

import com.google.common.collect.Lists;
import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.User;
import e45tm3d.pit.api.enums.Messages;
import e45tm3d.pit.api.events.PlayerDeadEvent;
import e45tm3d.pit.api.events.PlayerGainGoldEvent;
import e45tm3d.pit.api.events.PlayerMurderEvent;
import e45tm3d.pit.modules.enchance.EnchanceModule;
import e45tm3d.pit.modules.enchance.enchances.boots.Speed;
import e45tm3d.pit.modules.enchance.enchances.chestplate.DamageAbsorption;
import e45tm3d.pit.modules.enchance.enchances.helmet.Shield;
import e45tm3d.pit.modules.enchance.enchances.leggings.JumpBoost;
import e45tm3d.pit.modules.enchance.enchances.normal.None;
import e45tm3d.pit.modules.enchance.enchances.normal.Regeneration;
import e45tm3d.pit.modules.enchance.enchances.weapon.Berserker;
import e45tm3d.pit.modules.enchance.enchances.weapon.LifeSteal;
import e45tm3d.pit.modules.items.weapon.items.amulet.ExperienceAmulet;
import e45tm3d.pit.modules.items.weapon.items.amulet.GoldAmulet;
import e45tm3d.pit.modules.items.weapon.items.amulet.MonsterRune;
import e45tm3d.pit.modules.items.weapon.items.bows.ArtemisBow;
import e45tm3d.pit.modules.items.weapon.items.bows.WoodenBow;
import e45tm3d.pit.modules.items.weapon.items.swords.*;
import e45tm3d.pit.modules.monsters.MonsterModule;
import e45tm3d.pit.utils.functions.ItemFunction;
import e45tm3d.pit.utils.lists.ItemLists;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Weapons implements Listener {

	private static List<WeaponModule> weapons = new ArrayList<>();
    private static boolean registered = false;

    public Weapons() {
        ThePit.getInstance().getLogger().info("Loading weapon module...");

        if (registered) {
            HandlerList.unregisterAll(this);
            registered = false;
        }

        weapons.clear();

        weapons = Lists.newArrayList(new WoodenSword(), new GoldenSword(), new DiamondSword(), new IceSword(), new LightningSword(), new BoneSword(),

                new GoldAmulet(), new ExperienceAmulet(), new MonsterRune(),

                new WoodenBow(), new ArtemisBow());

        Bukkit.getPluginManager().registerEvents(this, ThePit.getInstance());
        registered = true;

        List<WeaponModule> copy = Lists.newArrayList(weapons);
        copy.forEach(this::register);

        ThePit.getInstance().getLogger().info("Weapon module loaded successfully!");
    }

    @EventHandler
    public void onEntityTargetLivingEntity(EntityTargetLivingEntityEvent e) {
        if (e.isCancelled()) {
            return;
        }
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
        if (e.isCancelled() || handleLocked(e)) {
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
		if (e.isCancelled() || handleLocked(e)) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onShootBow(EntityShootBowEvent e) {
		if (e.isCancelled() || handleLocked(e)) {
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
        if (event instanceof EntityDamageByEntityEvent e) {
            if (e.getDamager() instanceof Player p) {
                if (!Objects.isNull(p.getItemInHand())
                        && !Objects.isNull(p.getItemInHand().getItemMeta())) {
                    for (String weapons : ItemLists.weapons) {
                        if (ItemFunction.isItem(p.getItemInHand(), weapons)) {
                            for (WeaponModule items : Weapons.weapons) {
                                items.listen(event);
                                break;
                            }
                        }
                    }
                }
            }
        } else {
            for (WeaponModule item : weapons) {
                item.listen(event);
            }
        }
    }

    private void register(WeaponModule objects) {
        objects.register();
    }

    public static void registerWeapon(WeaponModule item) {
        if (!weapons.contains(item)) {
            weapons.add(item);
        }
    }

    private boolean handleLocked(PlayerPickupItemEvent e) {

        boolean lock = false;
        Player p = e.getPlayer();

        List<String> list = new ArrayList<>();
        list.addAll(ItemFunction.searchWeapons());
        list.addAll(ItemFunction.searchAmulets());

        for (String weapon : list) {
            if (!Objects.isNull(e.getItem().getItemStack())
                    && !Objects.isNull(e.getItem().getItemStack().getItemMeta())) {
                if (ItemFunction.isItem(e.getItem().getItemStack(), weapon)) {
                    if (User.getWeaponLevel(p, weapon) < 1) {
                        Messages.WEAPON_LOCKED.sendMessage(p).cooldown(5000);
                        e.setCancelled(true);
                        lock = true;
                        return lock;
                    }
                    break;
                }
            }
        }
        return lock;
    }

    private boolean handleLocked(EntityDamageByEntityEvent e) {

        boolean lock = false;

        if (e.getDamager() instanceof Player p) {
            for (String weapon : ItemLists.weapons) {
                if (!Objects.isNull(p.getItemInHand())
                        && !Objects.isNull(p.getItemInHand().getItemMeta())) {
                    if (ItemFunction.isItem(p.getItemInHand(), weapon)) {
                        if (User.getWeaponLevel(p, weapon) < 1) {
                            Messages.WEAPON_LOCKED.sendMessage(p).cooldown(5000);
                            e.setDamage(1);
                            lock = true;
                            return lock;
                        }
                        break;
                    }
                }
            }
        }
        return lock;
    }

    private boolean handleLocked(EntityShootBowEvent e) {

        boolean lock = false;

        if (e.getEntity() instanceof Player p) {
            for (String weapon : ItemLists.weapons) {
                if (ItemFunction.isItem(p.getItemInHand(), weapon)) {
                    if (User.getWeaponLevel(p, weapon) < 1) {
                        Messages.WEAPON_LOCKED.sendMessage(p).cooldown(5000);
                        e.setCancelled(true);
                        lock = true;
                        return lock;
                    }
                    break;
                }
            }
        }
        return lock;
    }
}