package e45tm3d.pit.modules.commands;

import com.google.common.collect.Lists;
import e45tm3d.pit.ThePit;
import e45tm3d.pit.modules.commands.commands.*;
import e45tm3d.pit.modules.commands.commands.menus.*;

import java.util.ArrayList;
import java.util.List;

public class Commands {

    private static List<CommandModule> commands = new ArrayList<>();

    public Commands() {

        ThePit.getInstance().getLogger().info("Loading command module...");

        commands.clear();

        commands = Lists.newArrayList(new CMDArmor(), new CMDBlocks(), new CMDBuff(), new CMDCurse(), new CMDEnchance(), new CMDItem(), new CMDTrash()
                , new CMDWeapon(), new CMDMonesterSpawn(), new CMDArena(), new CMDDev(), new CMDSetHeight(), new CMDSetSpawn(), new CMDSpawn()
                , new CMDSpawnPos());

        List<CommandModule> copy = Lists.newArrayList(commands);

        copy.forEach(this::register);

        ThePit.getInstance().getLogger().info("Command module loaded successfully!");
    }

    private void register(CommandModule command) {
        command.register();
    }

    protected static void registerCommand(CommandModule command) {
        commands.add(command);
    }
}
