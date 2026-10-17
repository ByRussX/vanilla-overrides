package byrussx.vanillaoverrides.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModConfig
{
    // Rules
    public boolean villagerLeads = true;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance()
            .getConfigDir()
            .resolve("vanillaoverrides.json");

    public static ModConfig INSTANCE = new ModConfig();

    public static void load()
    {
        if (Files.exists(CONFIG_PATH))
        {
            try
            {
                String json = Files.readString(CONFIG_PATH);
                INSTANCE = GSON.fromJson(json, ModConfig.class);
                if (INSTANCE == null) INSTANCE = new ModConfig();
            }
            catch (IOException e)
            {
                e.printStackTrace();
                INSTANCE = new ModConfig();
            }
        }

        save();
    }

    public static void save()
    {
        try
        {
            Files.createDirectories(CONFIG_PATH.getParent());
            Files.writeString(CONFIG_PATH, GSON.toJson(INSTANCE));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}