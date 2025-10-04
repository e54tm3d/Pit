package e45tm3d.pit.modules.commands.commands;

import e45tm3d.pit.api.enums.Messages;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.modules.commands.CommandModule;
import e45tm3d.pit.utils.functions.VariableFunction;
import e45tm3d.pit.utils.lists.MonsterLists;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CMDAddMonesterSpawn extends CommandModule implements TabCompleter {

    @Override
    public String getCommand() {
        return "addmonsterspawn";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player p) {
            if (args.length == 1) {
                    String type = args[0];
                    if (MonsterLists.entityTypes.contains(type)) {
                        Location loc = p.getLocation();
                        String location = type + "," + loc.getX() + "," + loc.getY() + "," + loc.getZ();

                        if (!Objects.isNull(Yaml.CONFIG.getConfig().getStringList("worlds.world_manager." + VariableFunction.getActiveArena() + ".monster_spawns"))) {
                            List<String> list = Yaml.CONFIG.getConfig().getStringList("worlds.world_manager." + VariableFunction.getActiveArena() + ".monster_spawns");
                            list.add(location);

                            Yaml.CONFIG.getConfig().set("worlds.world_manager." + VariableFunction.getActiveArena() + ".monster_spawns", list);
                            Yaml.CONFIG.saveConfig();

                        } else {
                            Yaml.CONFIG.getConfig().addDefault("worlds.world_manager." + VariableFunction.getActiveArena() + ".monster_spawns", new ArrayList<>());

                            List<String> list = Yaml.CONFIG.getConfig().getStringList("worlds.world_manager." + VariableFunction.getActiveArena() + ".monster_spawns");
                            list.add(location);

                            Yaml.CONFIG.getConfig().set("worlds.world_manager." + VariableFunction.getActiveArena() + ".monster_spawns", list);
                            Yaml.CONFIG.saveConfig();

                        }
                        Messages.CMD_ADDMONSTERSPAWN.sendMessage(p);
                    } else {
                        Messages.CMD_ADDMONSTERSPAWN_INVALID_MOB.sendMessage(p);
                    }
            } else {
                Messages.CMD_ADDMONSTERSPAWN_USAGE.sendMessage(p);
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
            List<String> monsters = new ArrayList<>(MonsterLists.entityTypes);
            String current = args[0].toLowerCase();
            monsters.removeIf(i -> !i.startsWith(current));
            return monsters;
        }
        return Collections.emptyList();
    }
}