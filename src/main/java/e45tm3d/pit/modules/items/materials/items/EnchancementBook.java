package e45tm3d.pit.modules.items.materials.items;

import e45tm3d.pit.api.User;
import e45tm3d.pit.api.events.PlayerMurderEvent;
import e45tm3d.pit.modules.items.materials.MaterialModule;
import e45tm3d.pit.utils.enums.materials.Materials;
import e45tm3d.pit.utils.functions.ItemFunction;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Random;

public class EnchancementBook extends MaterialModule {
    @Override
    public String getType() {
        return "enchancement_book";
    }

    @Override
    public ItemStack getItem() {
        return Materials.ENCHANCEMENT_BOOK.getItemStack();
    }

    @Override
    public void listen(Event event) {
        if (event instanceof PlayerMurderEvent e) {

            Player p = e.getDead();

            if (User.getLevel(p) >= 100) {
                Random r = new Random();
                if (r.nextInt(100) <= 5) {
                    e.getDead().getWorld().dropItemNaturally(e.getDead().getLocation(), ItemFunction.searchItem(getType()));
                }
            }
        }else if (event instanceof PlayerPickupItemEvent e) {

            ItemStack pickup = e.getItem().getItemStack();
            int toPickup = pickup.getAmount();
            int maxStack = 64;

            if (!isItem(pickup)) return;

            for (ItemStack item : e.getPlayer().getInventory().getContents()) {
                if (item == null || item.getType() == Material.AIR) continue;
                if (isItem(item) && item.getAmount() < maxStack && toPickup > 0) {
                    int space = maxStack - item.getAmount();
                    int move = Math.min(space, toPickup);
                    item.setAmount(item.getAmount() + move);
                    toPickup -= move;
                }
            }
            while (toPickup > 0) {
                int add = Math.min(maxStack, toPickup);
                ItemStack newStack = pickup.clone();
                newStack.setAmount(add);
                e.getPlayer().getInventory().addItem(newStack);
                toPickup -= add;
            }
            e.setCancelled(true);
            e.getItem().remove();
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ITEM_PICKUP, 1, 1);

        } else if (event instanceof InventoryClickEvent e) {

            ItemStack cursor = e.getCursor();
            ItemStack current = e.getCurrentItem();

            if (cursor == null || cursor.getType() == Material.AIR) return;
            if (current == null || current.getType() == Material.AIR) return;
            if (isItem(cursor) && isItem(current)) {
                int maxStack = 64;
                int currentAmount = current.getAmount();
                int cursorAmount = cursor.getAmount();
                e.setCancelled(true);
                if (e.getClick() == ClickType.LEFT) {
                    if (currentAmount < maxStack && cursorAmount > 0) {

                        int space = maxStack - currentAmount;
                        int toMove = Math.min(space, cursorAmount);
                        current.setAmount(currentAmount + toMove);
                        if (cursorAmount - toMove > 0) {
                            cursor.setAmount(cursorAmount - toMove);
                            e.setCursor(cursor);
                        } else {
                            e.setCursor(null);
                        }
                    } else {

                        ItemStack temp = current.clone();
                        e.setCurrentItem(cursor.clone());
                        e.setCursor(temp);
                    }
                } else if (e.getClick() == ClickType.RIGHT) {
                    if (currentAmount < maxStack && cursorAmount > 0) {
                        current.setAmount(currentAmount + 1);
                        if (cursorAmount - 1 > 0) {
                            cursor.setAmount(cursorAmount - 1);
                            e.setCursor(cursor);
                        } else {
                            e.setCursor(null);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void run(MaterialModule task) {

    }
}
