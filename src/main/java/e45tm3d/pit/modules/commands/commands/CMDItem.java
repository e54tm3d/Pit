package e45tm3d.pit.modules.commands.commands;

import e45tm3d.pit.api.enums.Messages;
import e45tm3d.pit.modules.commands.CommandModule;
import e45tm3d.pit.utils.functions.ItemFunction;
import e45tm3d.pit.utils.lists.ItemLists;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CMDItem extends CommandModule implements TabCompleter {

    @Override
    public String getCommand() {
        return "item";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player p) {
            if (args.length == 1) {
                if (!Objects.isNull(ItemFunction.searchItem(args[0]))) {
                    p.getInventory().addItem(ItemFunction.searchItem(args[0]));
                    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1, 1);
                } else {
                    Messages.CMD_ITEM_NOT_FOUND.sendMessage(p);
                    p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
                }
            } else if (args.length == 2) {
                if (args[1].matches("-?\\d+")) {
                    if (!Objects.isNull(ItemFunction.searchItem(args[0]))) {
                        int amount = Integer.parseInt(args[1]);
                        ItemStack item = new ItemStack(ItemFunction.searchItem(args[0]));
                        item.setAmount(amount);
                        p.getInventory().addItem(item);
                        p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1, 1);
                    } else {
                        Messages.CMD_ITEM_NOT_FOUND.sendMessage(p);
                        p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
                    }
                } else {
                    Messages.CMD_ITEM_USAGE.sendMessage(p);
                }
            } else {
                Messages.CMD_ITEM_USAGE.sendMessage(p);
                return true;
            }
        } else {
            sender.sendMessage(Messages.CMD_ONLY_PLAYER.getMessage());
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> items = new ArrayList<>();

            items.addAll(ItemLists.materials);
            items.addAll(ItemLists.weapons);

            String current = args[0].toLowerCase();
            items.removeIf(i -> !i.startsWith(current));
            return items;
        }
        return Collections.emptyList();
    }
}