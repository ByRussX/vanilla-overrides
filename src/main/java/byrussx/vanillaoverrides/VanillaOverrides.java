package byrussx.vanillaoverrides;

import byrussx.vanillaoverrides.config.ModConfig;
import byrussx.vanillaoverrides.commands.ModCommands;
import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VanillaOverrides implements ModInitializer
{
	public static final String MOD_ID = "vanilla-overrides";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize()
	{
		LOGGER.info("Vanilla Overrides loaded!");

		ModConfig.load();
		ModCommands.register();
	}

	public static Identifier id(String path)
	{
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
