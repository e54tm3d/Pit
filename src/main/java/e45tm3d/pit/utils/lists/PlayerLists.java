package e45tm3d.pit.utils.lists;

import e45tm3d.pit.api.Preplayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerLists {

    public static ArrayList<UUID> developers = new ArrayList<>();
    public static ArrayList<Preplayer> preplayers = new ArrayList<>();
    public static ArrayList<UUID> playing = new ArrayList<>();

    public static ArrayList<Player> getPlayingPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        for (UUID uuid : playing) {
            players.add(Bukkit.getPlayer(uuid));
        }
        return players;
    }
}