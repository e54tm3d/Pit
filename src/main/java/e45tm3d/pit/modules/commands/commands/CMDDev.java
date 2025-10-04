package e45tm3d.pit.modules.commands.commands;

import e45tm3d.pit.api.enums.Messages;
import e45tm3d.pit.modules.commands.CommandModule;
import e45tm3d.pit.utils.functions.PlayerFunction;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDDev extends CommandModule {

    @Override
    public String getCommand() {
        return "dev";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof Player p) {
            if (!PlayerFunction.isDevelopMode(p)) {
                PlayerFunction.addDevelopMode(p);
                Messages.ADD_DEV.sendMessage(p);
            } else {
                PlayerFunction.removeDevelopMode(p);
                Messages.REMOVE_DEV.sendMessage(p);
                for (Player other : Bukkit.getOnlinePlayers()) {
                    other.showPlayer(p);
                }
            }
        } else {
            sender.sendMessage(Messages.CMD_ONLY_PLAYER.getMessage());
            return true;
        }
        return false;
    }
}
