package e45tm3d.pit;

import e45tm3d.pit.api.User;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.utils.functions.VariableFunction;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public class Placeholders extends PlaceholderExpansion {

    DecimalFormat df = new DecimalFormat("#.#");

    @Override
    public @NotNull String getIdentifier() {
        return "pit";
    }

    @Override
    public @NotNull String getAuthor() {
        return "E45t_M3d";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        Player p = player.getPlayer();
        int exp = User.getExp(p);
        if (params.equalsIgnoreCase("exp")) {
            return df.format(Double.valueOf((double) User.getExp(p) / 1000)) + "k";
        } else if (params.equalsIgnoreCase("level")) {
            String str = "";
            for (String key : Yaml.CONFIG.getConfig().getConfigurationSection("settings.level.level_format").getKeys(false)) {
                if (User.getLevel(p) >= Integer.parseInt(key)) {
                    str = Yaml.CONFIG.getConfig().getString("settings.level.level_format." + key).replaceAll("%level%", String.valueOf(User.getLevel(p)));
                    str = replaceLevelDigitPlaceholders(str, User.getLevel(p));
                } else {
                    break;
                }
            }
            return str;
        } else if (params.equalsIgnoreCase("level_without_brackets")) {
            String str = "";
            for (String key : Yaml.CONFIG.getConfig().getConfigurationSection("settings.level.level_format").getKeys(false)) {
                if (User.getLevel(p) >= Integer.parseInt(key)) {
                    str = Yaml.CONFIG.getConfig().getString("settings.level.level_format." + key).replaceAll("%level%", String.valueOf(User.getLevel(p)));
                    str = replaceLevelDigitPlaceholders(str, User.getLevel(p));
                    str = VariableFunction.removeBrackets(str);
                } else {
                    break;
                }
            }
            return str;
        } else if (params.startsWith("level_") && params.length() > 6) {
            try {
                int digitPosition = Integer.parseInt(params.substring(6));
                return getLevelDigit(User.getLevel(p), digitPosition);
            } catch (NumberFormatException e) {
                return "0";
            }
        } else if (params.equalsIgnoreCase("expbar")) {
            if (exp == 0) {
                return "§8[&7■■■■■■■■■■§8]";
            } else if (exp > 0 && exp <= 500) {
                return "§8[§b■§7■■■■■■■■■§8]";
            } else if (exp > 500 && exp <= 1000) {
                return "§8[§b■■§7■■■■■■■■§8]";
            } else if (exp > 1000 && exp <= 1500) {
                return "§8[§b■■■§7■■■■■■■§8]";
            } else if (exp > 1500 && exp <= 2000) {
                return "§8[§b■■■■§7■■■■■■§8]";
            } else if (exp > 2000 && exp <= 2500) {
                return "§8[§b■■■■■§7■■■■■§8]";
            } else if (exp > 2500 && exp <= 3000) {
                return "§8[§b■■■■■■§7■■■■§8]";
            } else if (exp > 3000 && exp <= 3500) {
                return "§8[§b■■■■■■■§7■■■§8]";
            } else if (exp > 3500 && exp <= 4000) {
                return "§8[§b■■■■■■■■§7■■§8]";
            } else if (exp > 4000 && exp <= 4500) {
                return "§8[§b■■■■■■■■■§7■§8]";
            } else if (exp > 4500) {
                return "§8[§b■■■■■■■■■■§8]";
            }
        }
        return null;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        int exp = User.getExp(player);
        if (params.equalsIgnoreCase("exp")) {
            return df.format(Double.valueOf((double) User.getExp(player) / 1000)) + "k";
        } else if (params.equalsIgnoreCase("level")) {
            String str = "";
            for (String key : Yaml.CONFIG.getConfig().getConfigurationSection("settings.level.level_format").getKeys(false)) {
                if (User.getLevel(player) >= Integer.parseInt(key)) {
                    str = Yaml.CONFIG.getConfig().getString("settings.level.level_format." + key).replaceAll("%level%", String.valueOf(User.getLevel(player)));
                    str = replaceLevelDigitPlaceholders(str, User.getLevel(player));
                } else {
                    break;
                }
            }
            return str;
        } else if (params.startsWith("level_") && params.length() > 6) {
            try {
                int digitPosition = Integer.parseInt(params.substring(6));
                return getLevelDigit(User.getLevel(player), digitPosition);
            } catch (NumberFormatException e) {
                return "0";
            }
        } else if (params.equalsIgnoreCase("level_without_brackets")) {
            String str = "";
            for (String key : Yaml.CONFIG.getConfig().getConfigurationSection("settings.level.level_format").getKeys(false)) {
                if (User.getLevel(player) >= Integer.parseInt(key)) {
                    str = Yaml.CONFIG.getConfig().getString("settings.level.level_format." + key).replaceAll("%level%", String.valueOf(User.getLevel(player)));
                    str = replaceLevelDigitPlaceholders(str, User.getLevel(player));
                    str = VariableFunction.removeBrackets(str);
                } else {
                    break;
                }
            }
            return str;
        } else if (params.equalsIgnoreCase("expbar")) {
            if (exp == 0) {
                return "§8[&7■■■■■■■■■■§8]";
            } else if (exp > 0 && exp <= 500) {
                return "§8[§b■§7■■■■■■■■■§8]";
            } else if (exp > 500 && exp <= 1000) {
                return "§8[§b■■§7■■■■■■■■§8]";
            } else if (exp > 1000 && exp <= 1500) {
                return "§8[§b■■■§7■■■■■■■§8]";
            } else if (exp > 1500 && exp <= 2000) {
                return "§8[§b■■■■§7■■■■■■§8]";
            } else if (exp > 2000 && exp <= 2500) {
                return "§8[§b■■■■■§7■■■■■§8]";
            } else if (exp > 2500 && exp <= 3000) {
                return "§8[§b■■■■■■§7■■■■§8]";
            } else if (exp > 3000 && exp <= 3500) {
                return "§8[§b■■■■■■■§7■■■§8]";
            } else if (exp > 3500 && exp <= 4000) {
                return "§8[§b■■■■■■■■§7■■§8]";
            } else if (exp > 4000 && exp <= 4500) {
                return "§8[§b■■■■■■■■■§7■§8]";
            } else if (exp > 4500) {
                return "§8[§b■■■■■■■■■■§8]";
            }
        }
        return null;
    }


    private String replaceLevelDigitPlaceholders(String text, int level) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        String result = text;
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("%level_(\\d+)%");
        java.util.regex.Matcher matcher = pattern.matcher(result);
        
        while (matcher.find()) {
            String placeholder = matcher.group();
            int digitPosition = Integer.parseInt(matcher.group(1));
            String digit = getLevelDigit(level, digitPosition);
            result = result.replace(placeholder, digit);
        }
        
        return result;
    }

    private String getLevelDigit(int level, int position) {
        String levelStr = String.valueOf(level);
        if (position > 0 && position <= levelStr.length()) {
            return String.valueOf(levelStr.charAt(position - 1));
        } else {
            return "0";
        }
    }
}