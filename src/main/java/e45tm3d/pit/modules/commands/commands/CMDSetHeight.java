package e45tm3d.pit.modules.commands.commands;

import e45tm3d.pit.api.enums.Messages;
import e45tm3d.pit.modules.commands.CommandModule;
import e45tm3d.pit.utils.functions.VariableFunction;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDSetHeight extends CommandModule {

    @Override
    public String getCommand() {
        return "setheight";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player p) {
            if (args.length != 0) {
                if (Integer.parseInt(args[0]) > 0 && Integer.parseInt(args[0]) < 255) {
                    VariableFunction.setMaxBuildHeight(Integer.parseInt(args[0]));
                    Messages.CMD_SET_HEIGHT.sendMessage(p);
                }
            } else {
                Messages.CMD_SET_HEIGHT_USAGE.sendMessage(p);
                return true;
            }
        } else {
            sender.sendMessage(Messages.CMD_ONLY_PLAYER.getMessage());
            return true;
        }
        return false;
    }
}