package e45tm3d.pit.api.enums;

import e45tm3d.pit.modules.listeners.player.GainGold;
import e45tm3d.pit.modules.listeners.player.Murder;
import e45tm3d.pit.utils.functions.ItemFunction;
import e45tm3d.pit.utils.functions.PlayerFunction;
import e45tm3d.pit.utils.lists.MonsterLists;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public enum Messages {

    CMD_ONLY_PLAYER("cmd_only_player"),
    CMD_SET_SPAWN("cmd_set_spawn"),
    CMD_SPAWN("cmd_spawn"),
    CMD_POS_USAGE("cmd_pos_usage"),
    CMD_POS1_SET("cmd_pos1_set"),
    CMD_POS2_SET("cmd_pos2_set"),
    CMD_DENY_USE("cmd_deny_use"),
    CMD_SET_HEIGHT_USAGE("cmd_set_height_usage"),
    CMD_SET_HEIGHT("cmd_set_height"),
    CMD_ARENA_USAGE("cmd_arena_usage"),
    CMD_ARENA_SET("cmd_arena_set"),
    CMD_ITEM_USAGE("cmd_item_usage"),
    CMD_ITEM_NOT_FOUND("cmd_item_not_found"),
    CMD_MONSTERSPAWN_USAGE("cmd_monsterspawn_usage"),
    CMD_MONSTERSPAWN("cmd_monsterspawn"),
    CMD_MONSTERSPAWN_INVALID_MOB("cmd_monsterspawn_invalid_mob"),
    WEAPON_LOCKED("weapon_locked"),
    CURSE_LOCKED("curse_locked"),
    CURSE_UNLOCKED("curse_unlocked"),
    CURSE_ALREADY_UNLOCKED("curse_already_unlocked"),
    CURSE_SELECT("curse_select"),
    CURSE_EQUIP("curse_equip"),
    CURSE_REMOVE("curse_remove"),
    BUFF_LOCKED("curse_locked"),
    BUFF_UNLOCKED("curse_unlocked"),
    BUFF_ALREADY_UNLOCKED("curse_already_unlocked"),
    BUFF_SELECT("curse_select"),
    BUFF_EQUIP("curse_equip"),
    BUFF_REMOVE("curse_remove"),
    ENCHANCE_SUCCESS("enchance_success"),
    ENCHANCE_FAILURE("enchance_failure"),
    UPGRADE_SWORD("upgrade_sword"),
    KILL("kill"),
    KILLSTREAK("killstreak"),
    BACKPACK_FULL("backpack_full"),
    ADD_DEV("add_dev"),
    REMOVE_DEV("remove_dev"),
    BREAK_BLOCK("break_block"),
    DENY_PLACE("deny_place"),
    UPGRADE_ARMOR("upgrade_armor"),
    PURCHASE_ITEM("purchase_item"),
    GAIN_GOLD("gain_gold"),
    CANNOT_AFFORD("cannot_afford"),
    ALREADY_HAVE_ITEMS("already_have_items"),;

    private static final Map<UUID, Map<Messages, Long>> cooldownMap = new ConcurrentHashMap<>();
    private static final Map<UUID, Map<Messages, Long>> lastSendMap = new ConcurrentHashMap<>();

    private final String msg;

    Messages(String msg) {

        this.msg = msg;
    }

    public String getMessage() {
        DecimalFormat df = new DecimalFormat("#.##");
        switch (this) {
            case GAIN_GOLD -> {
                return Yaml.MESSAGES.getConfig().getString(msg)
                        .replaceAll("%gold%", df.format(GainGold.gold))
                        .replaceAll("&", "§");
            }
            case KILL -> {
                return Yaml.MESSAGES.getConfig().getString(msg)
                        .replaceAll("%gold%", df.format(Murder.gold))
                        .replaceAll("%exp%", df.format(Murder.exp))
                        .replaceAll("%killer%", Murder.killer.getName())
                        .replaceAll("%dead%", Murder.dead.getName())
                        .replaceAll("&", "§");
            }
            case KILLSTREAK -> {
                return Yaml.MESSAGES.getConfig().getString(msg)
                        .replaceAll("%killstreak%", String.valueOf(Murder.killstreak))
                        .replaceAll("%player%", Murder.killer.getName())
                        .replaceAll("&", "§");
            }
            case CMD_MONSTERSPAWN_INVALID_MOB -> {
                return Yaml.MESSAGES.getConfig().getString(msg)
                        .replaceAll("%monsters%", String.join(", ", MonsterLists.entityTypes))
                        .replaceAll("&", "§");
            }
            case CMD_ITEM_NOT_FOUND -> {
                return Yaml.MESSAGES.getConfig().getString(msg)
                        .replaceAll("%items%", String.join(", ", Arrays.toString(ItemFunction.searchAllItems().toArray(new String[0])).toLowerCase()))
                        .replaceAll("&", "§");
            }
            default -> {
                return Yaml.MESSAGES.getConfig().getString(msg)
                        .replaceAll("&", "§");
            }
        }
    }

    public MessageSender sendMessage(Player p) {
        return new MessageSender(this, p);
    }

    public static class MessageSender {
        private final Messages msg;
        private final Player player;

        public MessageSender(Messages msg, Player player) {
            this.msg = msg;
            this.player = player;
            UUID uuid = player.getUniqueId();

            long cooldown = cooldownMap
                    .computeIfAbsent(uuid, k -> new ConcurrentHashMap<>())
                    .getOrDefault(msg, 0L);

            long now = System.currentTimeMillis();
            long lastSend = lastSendMap
                    .computeIfAbsent(uuid, k -> new ConcurrentHashMap<>())
                    .getOrDefault(msg, 0L);

            if (cooldown <= 0 || now - lastSend >= cooldown) {
                msg.sendMessageDirect(player);
                lastSendMap.get(uuid).put(msg, now);
            }
        }

        public void cooldown(long millis) {
            UUID uuid = player.getUniqueId();
            cooldownMap
                .computeIfAbsent(uuid, k -> new ConcurrentHashMap<>())
                .put(msg, millis);
        }
    }

    private void sendMessageDirect(Player p) {
        DecimalFormat df = new DecimalFormat("#.##");
        switch (this) {
            case GAIN_GOLD -> p.sendMessage(getMessage()
                    .replaceAll("%gold%", df.format(GainGold.gold)));
            case KILL -> p.sendMessage(getMessage()
                    .replaceAll("%gold%", df.format(Murder.gold))
                    .replaceAll("%exp%", df.format(Murder.exp))
                    .replaceAll("%killer%", Murder.killer.getName())
                    .replaceAll("%dead%", Murder.dead.getName())
                    .replaceAll("&", "§"));
            case KILLSTREAK -> p.sendMessage(getMessage()
                    .replaceAll("%killstreak%", String.valueOf(Murder.killstreak))
                    .replaceAll("%player%", Murder.killer.getName())
                    .replaceAll("&", "§"));
            case CMD_MONSTERSPAWN_INVALID_MOB -> p.sendMessage(getMessage()
                    .replaceAll("%monsters%", String.join(", ", MonsterLists.entityTypes))
                    .replaceAll("&", "§"));
            case CMD_ITEM_NOT_FOUND -> p.sendMessage(getMessage()
                    .replaceAll("%items%", String.join(", ", Arrays.toString(ItemFunction.searchAllItems().toArray(new String[0])).toLowerCase())).replaceAll("[\\[\\]]", "")
                    .replaceAll("&", "§"));
            default -> p.sendMessage(getMessage());
        }
    }

    public void sendActionbar(Player p) {
        DecimalFormat df = new DecimalFormat("#.##");
        switch (this) {
            case GAIN_GOLD -> PlayerFunction.sendActionBar(p, getMessage()
                    .replaceAll("%gold%", df.format(GainGold.gold)));
            case KILL -> PlayerFunction.sendActionBar(p, getMessage()
                    .replaceAll("%gold%", df.format(Murder.gold))
                    .replaceAll("%exp%", df.format(Murder.exp))
                    .replaceAll("%killer%", Murder.killer.getName())
                    .replaceAll("%dead%", Murder.dead.getName())
                    .replaceAll("&", "§"));
            case KILLSTREAK -> PlayerFunction.sendActionBar(p, getMessage()
                    .replaceAll("%killstreak%", String.valueOf(Murder.killstreak))
                    .replaceAll("%player%", Murder.killer.getName()));
            case CMD_MONSTERSPAWN_INVALID_MOB -> PlayerFunction.sendActionBar(p, getMessage()
                    .replaceAll("%monsters%", String.join(", ", MonsterLists.entityTypes))
                    .replaceAll("&", "§"));
            case CMD_ITEM_NOT_FOUND -> PlayerFunction.sendActionBar(p, getMessage()
                    .replaceAll("%items%", String.join(", ", Arrays.toString(ItemFunction.searchAllItems().toArray(new String[0])).toLowerCase()))
                    .replaceAll("&", "§"));
            default -> PlayerFunction.sendActionBar(p, getMessage());
        }
    }

}