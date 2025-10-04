package e45tm3d.pit.modules.commands.commands;

import e45tm3d.pit.api.enums.Messages;
import e45tm3d.pit.modules.commands.CommandModule;
import e45tm3d.pit.utils.functions.VariableFunction;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDSetSpawn extends CommandModule {

    @Override
    public String getCommand() {
        return "setspawn";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof Player p) {
            VariableFunction.setSpawnLocation(p.getLocation());
            Messages.CMD_SET_SPAWN.sendMessage(p);
        } else {
            sender.sendMessage(Messages.CMD_ONLY_PLAYER.getMessage());
            return true;
        }
        return false;
    }
}
