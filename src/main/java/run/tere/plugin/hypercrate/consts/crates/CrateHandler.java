package run.tere.plugin.hypercrate.consts.crates;

import org.bukkit.Location;
import run.tere.plugin.hypercrate.apis.JsonAPI;

import java.util.HashSet;

public class CrateHandler {
    private HashSet<Crate> crateList;

    public CrateHandler() {
        this.crateList = new HashSet<>();
    }

    public void addCrate(Crate crate) {
        this.crateList.add(crate);
        saveCrateHandler();
    }

    public void saveCrateHandler() {
        JsonAPI.write(this, "data");
    }

    public Crate getCrate(String name) {
        for (Crate crate : this.crateList) {
            if (crate.getCrateSettings().getCrateName().equalsIgnoreCase(name)) {
                return crate;
            }
        }
        return null;
    }

    public boolean containsCrate(String name) {
        return (getCrate(name) != null);
    }

    public Crate getCrateFromLocation(Location location) {
        for (Crate crate : this.crateList) {
            if (crate.containsCrateLocationByLocation(location)) {
                return crate;
            }
        }
        return null;
    }

    public boolean containsCrateFromLocation(Location location) {
        return (getCrateFromLocation(location) != null);
    }

    public HashSet<Crate> getCratesFromStartWithName(String startWithName) {
        HashSet<Crate> crates = new HashSet<>();
        for (Crate crate : this.crateList) {
            if (crate.getCrateSettings().getCrateName().startsWith(startWithName)) {
                crates.add(crate);
            }
        }
        return crates;
    }

    public boolean containsCrateFromKey(String key) {
        return (getCrateFromKey(key) != null);
    }

    public Crate getCrateFromKey(String key) {
        for (Crate crate : this.crateList) {
            if (crate.getCrateSettings().getCrateKey().equalsIgnoreCase(key)) {
                return crate;
            }
        }
        return null;
    }

    public int getCrateSizeFromStartWithName(String startWithName) {
        return getCratesFromStartWithName(startWithName).size();
    }

    public int getCrateNameSizeFromStartWithName(String startWithName) {
        if (getCrateSizeFromStartWithName(startWithName) == 0) return 0;
        int count = 1;
        while (true) {
            String equalsName = startWithName + " (" + count + ")";
            if (!containsCrate(equalsName)) {
                return count;
            }
            count++;
        }
    }

    public void removeCrate(String name) {
        if (!containsCrate(name)) return;
        Crate crate = getCrate(name);
        this.crateList.remove(crate);
        saveCrateHandler();
    }

    public void removeCrate(Crate crate) {
        this.crateList.remove(crate);
        saveCrateHandler();
    }

    public HashSet<Crate> getCrateList() {
        return crateList;
    }
}
