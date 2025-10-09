package e45tm3d.pit.modules.commands.commands.menus;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import e45tm3d.pit.api.enums.Messages;
import e45tm3d.pit.modules.commands.CommandModule;
import e45tm3d.pit.utils.functions.ItemFunction;
import e45tm3d.pit.utils.functions.PlayerFunction;
import e45tm3d.pit.utils.menus.CurseMenu;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

public class CMDCurse extends CommandModule {

    @Override
    public String getCommand() {
        return "curse";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof Player p) {
            if (PlayerFunction.isInSpawn(p)) {
                CurseMenu.open(p);
                p.getInventory().addItem(ItemFunction.getBase64Head("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmMxYzE3OWFkNTE5NTVmMTUyMmM0OGVhOTkzMWYwOWMxNjI3NDFiNDVlMjJlOWQzZmViNjgyYzdlNWVkODI3NCJ9fX0="));
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