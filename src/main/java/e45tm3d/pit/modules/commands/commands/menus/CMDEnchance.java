package e45tm3d.pit.modules.commands.commands.menus;

import e45tm3d.pit.api.enums.Messages;
import e45tm3d.pit.modules.commands.CommandModule;
import e45tm3d.pit.utils.functions.PlayerFunction;
import e45tm3d.pit.utils.menus.EnchanceMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDEnchance extends CommandModule {

    @Override
    public String getCommand() {
        return "enchance";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof Player p) {
            if (PlayerFunction.isInSpawn(p)) {
                EnchanceMenu.open(p);
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