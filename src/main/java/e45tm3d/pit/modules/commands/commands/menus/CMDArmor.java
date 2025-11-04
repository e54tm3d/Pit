package e45tm3d.pit.modules.commands.commands.menus;

import e45tm3d.pit.api.enums.Messages;
import e45tm3d.pit.modules.commands.CommandModule;
import e45tm3d.pit.utils.functions.PlayerFunction;
import e45tm3d.pit.utils.menus.normal_menus.ArmorMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDArmor extends CommandModule {

    @Override
    public String getCommand() {
        return "armor";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof Player p) {
            if (PlayerFunction.isInSpawn(p)) {
                ArmorMenu.open(p);
            } else {
                Messages.CMD_DENY_USE.sendMessage(p);
            }
        } else {
            sender.sendMessage(Messages.CMD_ONLY_PLAYER.getMessage());
            return true;
        }
        return false;
    }
}