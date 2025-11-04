package e45tm3d.pit.utils.menus.spectating_menus;

import e45tm3d.pit.api.User;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.utils.ItemBuilder;
import e45tm3d.pit.utils.PlaceholdersItemBuilder;
import e45tm3d.pit.utils.functions.ItemFunction;
import e45tm3d.pit.utils.functions.PlayerFunction;
import e45tm3d.pit.utils.lists.PlayerLists;
import e45tm3d.pit.utils.maps.PlayerMaps;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class PlayerSelector {

    public static void open(Player p) {

        p.closeInventory();

        Inventory inv = Bukkit.createInventory(null, 27, Yaml.SPECTATING.getConfig().getString("menus.player_selector.title").replaceAll("&", "§"));

        if (!User.isPlaying(p)) {

            PlayerMaps.playing.put(p.getUniqueId(), PlayerLists.getPlayingPlayers());
            for (int i = 0; i < PlayerLists.getPlayingPlayers().size(); i++) {
                Player player = PlayerLists.getPlayingPlayers().get(i);
                if (User.isPlaying(player)) {
                    PlaceholdersItemBuilder head = new PlaceholdersItemBuilder(ItemFunction.getNameHead(player.getName()), p)
                            .setName(Yaml.SPECTATING.getConfig().getString("menus.player_selector.players.name").replaceAll("%player%", player.getName()))
                            .setLore(Yaml.SPECTATING.getConfig().getStringList("menus.player_selector.players.lore"));
                    inv.setItem(i, head.build());
                }
            }
        }
        p.openInventory(inv);
        PlayerMaps.menu.put(p.getUniqueId(), "player_selector");
    }
}
