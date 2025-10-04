package e45tm3d.pit.modules.commands.commands;

import e45tm3d.pit.api.enums.Messages;
import e45tm3d.pit.modules.commands.CommandModule;
import e45tm3d.pit.utils.functions.VariableFunction;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDSpawnPos extends CommandModule {

    @Override
    public String getCommand() {
        return "spawnpos";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player p) {
            if (args.length != 0) {
                switch (args[0]) {
                    case "1" -> {
                        VariableFunction.setSpawnPos1(p.getLocation());
                        Messages.CMD_POS1_SET.sendMessage(p);
                        return true;
                    }
                    case "2" -> {
                        VariableFunction.setSpawnPos2(p.getLocation());
                        Messages.CMD_POS2_SET.sendMessage(p);
                        return true;
                    }
                    default -> {
                        Messages.CMD_POS_USAGE.sendMessage(p);
                        return true;
                    }
                }
            } else {
                Messages.CMD_POS_USAGE.sendMessage(p);
                return true;
            }
        } else {
            sender.sendMessage(Messages.CMD_ONLY_PLAYER.getMessage());
            return true;
        }
    }
}