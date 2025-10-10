package e45tm3d.pit.modules.items.materials;

import e45tm3d.pit.utils.functions.ItemFunction;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class MaterialModule {

    public abstract String getIdentifier();

    public abstract ItemStack getItem();

    public abstract void listen(Event event);

    public abstract void run(MaterialModule task);

    public boolean isItem(ItemStack item) {
        return ItemFunction.isItem(item, getIdentifier().toLowerCase());
    }

    public void register() {

        ItemStack item = getItem();
        item = ItemFunction.addNBTTag(item, getIdentifier());
        ItemMeta meta = item.getItemMeta();
        item.setItemMeta(meta);

        ItemFunction.registerMaterial(getIdentifier().toLowerCase(), item);
        Materials.registerEnchcance(this);
        run(this);
    }
}
