package byrussx.vanillaoverrides.commands;

import byrussx.vanillaoverrides.config.ModConfig;
import byrussx.vanillaoverrides.config.ModRule;
import com.mojang.brigadier.arguments.BoolArgumentType;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.permissions.Permissions;

import static byrussx.vanillaoverrides.VanillaOverrides.LOGGER;

public class ModCommands {

    public static void register()
    {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
        {
            dispatcher.register(
                    Commands.literal("overrides")
                            .requires(source -> source.permissions().hasPermission(Permissions.COMMANDS_OWNER))

                            // /overrides villagerLeads
                            .then(Commands.literal("villagerLeads")
                                    .executes(ctx -> showRule(ctx.getSource(), ModConfig.VILLAGER_LEADS))
                                    .then(Commands.argument("value", BoolArgumentType.bool())
                                            .executes(ctx -> setBoolRule(
                                                    ctx.getSource(),
                                                    ModConfig.VILLAGER_LEADS,
                                                    BoolArgumentType.getBool(ctx, "value")
                                            ))
                                    )
                            )
            );
        });
    }

    private static int showRule(CommandSourceStack source, ModRule<?> rule)
    {

        // Name
        source.sendSuccess(() ->
                Component.literal(rule.getName()).withStyle(ChatFormatting.BOLD), false);

        source.sendSuccess(() -> Component.literal(""), false);

        // Description
        source.sendSuccess(() ->
                Component.literal(rule.getDescription()), false);

        // Current value
        Object value = rule.get();
        Object def = rule.getDefault();

        ChatFormatting color;
        // Chat formatting color
        if (value instanceof Boolean b)
        {
            if (b)
            {
                color = ChatFormatting.GREEN;
            }
            else
            {
                color = ChatFormatting.RED;
            }
        }

        else
        {
            color = ChatFormatting.DARK_AQUA;
        }

        source.sendSuccess(() ->
                Component.literal("Current value: ")
                        .append(Component.literal(String.valueOf(value)).withStyle(color))
                        .append(Component.literal(" (default: " + def + ")")
                                .withStyle(ChatFormatting.GRAY)), false);
        return 1;
    }

    private static int setBoolRule(CommandSourceStack source, ModRule<Boolean> rule, boolean value)
    {
        rule.set(value);
        ModConfig.save();

        source.sendSuccess(() ->
                Component.literal(rule.getName() + " set to: ")
                        .append(Component.literal(String.valueOf(value))
                                .withStyle(value ? ChatFormatting.GREEN : ChatFormatting.RED)), true);

        LOGGER.info("Override {} has been set to {}", rule.getName(), value);
        return 1;
    }
}