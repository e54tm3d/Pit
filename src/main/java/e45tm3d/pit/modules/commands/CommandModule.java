package e45tm3d.pit.modules.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class CommandModule implements CommandExecutor {

    public abstract String getCommand();

    public void register() {
        Commands.registerCommand(this);
        Bukkit.getPluginCommand(getCommand()).setExecutor(this);
    }

}
