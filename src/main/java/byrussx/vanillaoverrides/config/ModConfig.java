package byrussx.vanillaoverrides.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static byrussx.vanillaoverrides.VanillaOverrides.LOGGER;

public class ModConfig
{

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("vanillaoverrides.json");

    public static final Map<String, ModRule<?>> RULES = new LinkedHashMap<>();

    // Rules
    public static final ModRule<Boolean> VILLAGER_LEADS = register(new ModRule<>(
            "villagerLeads",
            "Allows villagers to be leashed with a lead.",
            false,
            List.of(true, false)
    ));

    private static <T> ModRule<T> register(ModRule<T> rule)
    {
        RULES.put(rule.getName(), rule);
        return rule;
    }

    public static void load()
    {
        // If the config file doesn't exist, create one with the default values
        if (!Files.exists(CONFIG_PATH))
        {
            save();
            return;
        }

        try
        {
            String json = Files.readString(CONFIG_PATH);
            JsonObject root = JsonParser.parseString(json).getAsJsonObject();

            for (ModRule<?> rule : RULES.values())
            {
                if (!root.has(rule.getName())) continue;

                if (rule.getDefault() instanceof Boolean)
                {
                    @SuppressWarnings("unchecked")
                    ModRule<Boolean> boolRule = (ModRule<Boolean>) rule;
                    boolRule.set(root.get(rule.getName()).getAsBoolean());
                }
            }
        }
        catch (Exception e)
        {
            LOGGER.error("Failed to load config: {}", e.getMessage());
        }
    }

    public static void save()
    {
        try
        {
            Files.createDirectories(CONFIG_PATH.getParent());

            JsonObject root = new JsonObject();
            for (ModRule<?> rule : RULES.values())
            {
                Object value = rule.get();
                if (value instanceof Boolean b)
                {
                    root.addProperty(rule.getName(), b);
                }
                else if (value instanceof Number n)
                {
                    root.addProperty(rule.getName(), n);
                }
                else if (value instanceof String s)
                {
                    root.addProperty(rule.getName(), s);
                }
            }

            Files.writeString(CONFIG_PATH, GSON.toJson(root));
        }
        catch (IOException e)
        {
            LOGGER.error("Failed to save config: {}", e.getMessage());
        }
    }
}