package byrussx.vanillaoverrides.command;

import byrussx.vanillaoverrides.config.ModConfig;
import com.mojang.brigadier.arguments.BoolArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.permissions.Permissions;

import static byrussx.vanillaoverrides.VanillaOverrides.LOGGER;

public class ModCommands
{

    public static void register()
    {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {

            dispatcher.register(Commands.literal("overrides")
                    .requires(source -> source.permissions().hasPermission(Permissions.COMMANDS_OWNER))

                            // /vo villagerLeads
                            .then(Commands.literal("villagerLeads")
                                    .executes(ctx -> {
                                        boolean current = ModConfig.INSTANCE.villagerLeads;
                                        ctx.getSource().sendSuccess(
                                                () -> Component.literal("Override villagerLeads is currently set to " + current), false);
                                        return 1;
                                    })
                                    .then(Commands.argument("value", BoolArgumentType.bool())
                                            .executes(ctx -> {
                                                boolean value = BoolArgumentType.getBool(ctx, "value");
                                                ModConfig.INSTANCE.villagerLeads = value;
                                                ModConfig.save();

                                                ctx.getSource().sendSuccess(
                                                        () -> Component.literal("Override villagerLeads has been set to " + value), true);
                                                LOGGER.info("Override villagerLeads has been set to {}", value);
                                                return 1;
                                            })
                                    )
                            )
            );
        });
    }
}