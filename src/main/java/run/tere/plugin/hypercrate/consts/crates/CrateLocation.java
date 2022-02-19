package run.tere.plugin.hypercrate.consts.crates;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.UUID;

public class CrateLocation {
    private String world;
    private int x;
    private int y;
    private int z;

    public CrateLocation(Location location) {
        this.world = location.getWorld().getUID().toString();
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
    }

    public CrateLocation(String world, int x, int y, int z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getWorld() {
        return world;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public Location getLocation() {
        return new Location(Bukkit.getWorld(UUID.fromString(this.world)), this.x, this.y, this.z);
    }

    public void setLocation(Location location) {
        this.world = location.getWorld().getUID().toString();
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
    }

    public static CrateLocation getCrateLocationFromLocation(Location location) {
        return new CrateLocation(location.getWorld().getUID().toString(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public static Location getLocationFromCrateLocation(CrateLocation crateLocation) {
        return new Location(Bukkit.getWorld(UUID.fromString(crateLocation.world)), crateLocation.getX(), crateLocation.getY(), crateLocation.getZ());
    }
}
