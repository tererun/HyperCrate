package run.tere.plugin.hypercrate.apis;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import run.tere.plugin.hypercrate.HyperCrate;
import run.tere.plugin.hypercrate.consts.crates.CrateHandler;

import java.io.*;

public class JsonAPI {
    public static void write(CrateHandler crateHandler, String fileName) {
        File file = new File(HyperCrate.getPlugin().getDataFolder(), fileName + ".json");
        try (Writer writer = new OutputStreamWriter(
                new FileOutputStream(file),"UTF-8")) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(crateHandler, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static CrateHandler read(String fileName) {
        File file = new File(HyperCrate.getPlugin().getDataFolder(), fileName + ".json");
        try (Reader reader = new InputStreamReader(
                new FileInputStream(file), "UTF-8")) {
            Gson gson = new Gson();
            return gson.fromJson(reader, CrateHandler.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
