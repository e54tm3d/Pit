package e45tm3d.pit.modules.items.materials;

import com.google.common.collect.Lists;
import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.events.PlayerDeadEvent;
import e45tm3d.pit.api.events.PlayerGainGoldEvent;
import e45tm3d.pit.api.events.PlayerMurderEvent;
import e45tm3d.pit.modules.items.materials.items.*;
import e45tm3d.pit.utils.functions.ItemFunction;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Materials implements Listener {

    List<MaterialModule> materials;

    public Materials() {
        ThePit.getInstance().getLogger().info("Loading material module...");

        materials = Lists.newArrayList(new GoldIngot(), new IceBlock(), new PrometheanFire(), new ResentfulHead(), new Superconductor());

        materials.forEach(this::register);

        Bukkit.getPluginManager().registerEvents(this, ThePit.getInstance());

        ThePit.getInstance().getLogger().info("Material module loaded successfully!");

    }

    private void register(MaterialModule material) {
        material.register();
    }

    @EventHandler
    public void onCraftItem(CraftItemEvent e) {
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
        for (MaterialModule item : materials) {
            item.listen(event);
        }
    }
}
