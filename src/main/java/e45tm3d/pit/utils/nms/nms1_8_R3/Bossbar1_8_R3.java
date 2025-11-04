package e45tm3d.pit.utils.nms.nms1_8_R3;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.utils.nms.Bossbar;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map.Entry;

public class Bossbar1_8_R3 extends BukkitRunnable implements Bossbar {

    private String title;
    private HashMap<Player, EntityWither> withers = new HashMap<>();

    public Bossbar1_8_R3(String title) {
        this.title = title;
        runTaskTimer(ThePit.getInstance(), 0, 10);
    }

    @Override
    public void addPlayer(Player p) {
        // 检查玩家是否已经有对应的bossbar实例，防止创建多个
        if (!withers.containsKey(p)) {
            EntityWither wither = new EntityWither(((CraftWorld) p.getWorld()).getHandle());
            Location l = getWitherLocation(p.getLocation());
            wither.setCustomName(title);
            wither.setInvisible(true);
            wither.setLocation(l.getX(), l.getY(), l.getZ(), 0, 0);
            PacketPlayOutSpawnEntityLiving newPacket = new PacketPlayOutSpawnEntityLiving(wither);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(newPacket);
            withers.put(p, wither);
        }
    }

    @Override
    public void removePlayer(Player p) {
        EntityWither wither = withers.remove(p);
        if (wither != null) {
            PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[]{wither.getId()});
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        }
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
        for (Entry<Player, EntityWither> entry : withers.entrySet()) {
            EntityWither wither = entry.getValue();
            wither.setCustomName(title);
            PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata(wither.getId(), wither.getDataWatcher(), true);
            ((CraftPlayer) entry.getKey()).getHandle().playerConnection.sendPacket(packet);
        }
    }

    @Override
    public void setProgress(double progress) {
        for (Entry<Player, EntityWither> entry : withers.entrySet()) {
            EntityWither wither = entry.getValue();
            wither.setHealth((float) (progress * wither.getMaxHealth()));
            PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata(wither.getId(), wither.getDataWatcher(), true);
            ((CraftPlayer) entry.getKey()).getHandle().playerConnection.sendPacket(packet);
        }
    }

    @Override
    public Location getWitherLocation(Location l) {
        return l.add(l.getDirection().multiply(60));
    }

    @Override
    public void run() {
        for (Entry<Player, EntityWither> en : withers.entrySet()) {
            EntityWither wither = en.getValue();
            Location l = getWitherLocation(en.getKey().getLocation());
            wither.setLocation(l.getX(), l.getY(), l.getZ(), 0, 0);
            PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport(wither);
            ((CraftPlayer) en.getKey()).getHandle().playerConnection.sendPacket(packet);
        }
    }
}