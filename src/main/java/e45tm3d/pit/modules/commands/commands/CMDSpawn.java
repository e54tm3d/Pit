package e45tm3d.pit.modules.commands.commands;

import e45tm3d.pit.api.enums.Messages;
import e45tm3d.pit.api.User;
import e45tm3d.pit.modules.commands.CommandModule;
import e45tm3d.pit.utils.functions.VariableFunction;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDSpawn extends CommandModule {

    @Override
    public String getCommand() {
        return "spawn";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof Player p) {
            if (!User.isFighting(p)) {
                p.teleport(VariableFunction.getSpawnLocation());
                Messages.CMD_SPAWN.sendMessage(p);
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