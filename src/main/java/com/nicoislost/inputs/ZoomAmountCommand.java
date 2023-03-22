package com.nicoislost.inputs;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;

public class ZoomAmountCommand {
    public static void register(
            CommandDispatcher<ServerCommandSource> serverCommandSourceCommandDispatcher,
            CommandRegistryAccess commandRegistryAccess,
            CommandManager.RegistrationEnvironment registrationEnvironment) {

            serverCommandSourceCommandDispatcher.register(CommandManager.literal("ZoomO")
                    .then(CommandManager.literal("ZoomAmount")
                            .then(CommandManager.literal("Zoom_1")
                                    .then(CommandManager.argument("Percent_Zoom", integer())
                                            .executes(c -> {
                                                c.getSource().sendMessage(Text.literal("Zoom 1 is " + getInteger(c, "Percent_Zoom")));
                                                return 1;
                                            })
                                    )
                            )
                            .then(CommandManager.literal("Zoom_2")
                                    .then(CommandManager.argument("Percent_Zoom", integer())
                                            .executes(c -> {
                                                c.getSource().sendMessage(Text.literal("Zoom 2 is " + getInteger(c, "Percent_Zoom")));
                                                return 1;
                                            })
                                    )
                            )
                            .then(CommandManager.literal("Zoom_3")
                                    .then(CommandManager.argument("Percent_Zoom", integer())
                                            .executes(c -> {
                                                c.getSource().sendMessage(Text.literal("Zoom 3 is " + getInteger(c, "Percent_Zoom")));
                                                return 1;
                                            })
                                    )
                            )
                    )
            );

    }
}
