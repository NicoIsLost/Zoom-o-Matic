package com.nicoislost.inputs;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static com.mojang.brigadier.arguments.BoolArgumentType.getBool;
import static com.mojang.brigadier.arguments.BoolArgumentType.bool;
import static com.nicoislost.ZoomO.CONFIG;

public class Commands {
    public static void register(
            CommandDispatcher<ServerCommandSource> serverCommandSourceCommandDispatcher,
            CommandRegistryAccess commandRegistryAccess,
            CommandManager.RegistrationEnvironment registrationEnvironment) {

            serverCommandSourceCommandDispatcher.register(CommandManager.literal("ZoomO")
                    .then(CommandManager.literal("ZoomAmount")
                            .then(CommandManager.literal("Zoom_1")
                                    .then(CommandManager.argument("Percent_Zoom", integer())
                                            .executes(c -> {
                                                if(getInteger(c, "Percent_Zoom")<=99 && getInteger(c, "Percent_Zoom")>=1) {
                                                    CONFIG.zoom1(getInteger(c, "Percent_Zoom"));
                                                    c.getSource().sendMessage(Text.literal("Zoom 1 is now set to " + CONFIG.zoom1() + "%"));
                                                    return 1;
                                                }
                                                throw new SimpleCommandExceptionType(Text.translatable("command.exception.between")).create();
                                            })
                                    )
                            )
                            .then(CommandManager.literal("Zoom_2")
                                    .then(CommandManager.argument("Percent_Zoom", integer())
                                            .executes(c -> {
                                                if(getInteger(c, "Percent_Zoom")<=99 && getInteger(c, "Percent_Zoom")>=1) {
                                                    CONFIG.zoom2(getInteger(c, "Percent_Zoom"));
                                                    c.getSource().sendMessage(Text.literal("Zoom 2 is now set to " + CONFIG.zoom2() + "%"));
                                                    return 1;
                                                }
                                                throw new SimpleCommandExceptionType(Text.translatable("command.exception.between")).create();
                                            })
                                    )
                            )
                            .then(CommandManager.literal("Zoom_3")
                                    .then(CommandManager.argument("Percent_Zoom", integer())
                                            .executes(c -> {
                                                if(getInteger(c, "Percent_Zoom")<=99 && getInteger(c, "Percent_Zoom")>=1) {
                                                    CONFIG.zoom3(getInteger(c, "Percent_Zoom"));
                                                    c.getSource().sendMessage(Text.literal("Zoom 3 is now set to " + CONFIG.zoom3() + "%"));
                                                    return 1;
                                                }
                                                throw new SimpleCommandExceptionType(Text.translatable("command.exception.between")).create();
                                            })
                                    )
                            )
                    )
                    .then(CommandManager.literal("SmoothCamera")
                            .then(CommandManager.literal("Zoom_1")
                                    .then(CommandManager.argument("Bool", bool())
                                            .executes(c -> {
                                                CONFIG.zoom1SmoothCamera(getBool(c, "Bool"));
                                                c.getSource().sendMessage(Text.literal("Zoom 1 smooth camera is now: " + CONFIG.zoom1SmoothCamera()));
                                                return 1;
                                            })
                                    )
                            )
                    )
                    .then(CommandManager.literal("SmoothCamera")
                            .then(CommandManager.literal("Zoom_2")
                                    .then(CommandManager.argument("Bool", bool())
                                            .executes(c -> {
                                                CONFIG.zoom2SmoothCamera(getBool(c, "Bool"));
                                                c.getSource().sendMessage(Text.literal("Zoom 2 smooth camera is now: " + CONFIG.zoom2SmoothCamera()));
                                                return 1;
                                            })
                                    )
                            )
                    )
                    .then(CommandManager.literal("SmoothCamera")
                            .then(CommandManager.literal("Zoom_3")
                                    .then(CommandManager.argument("Bool", bool())
                                            .executes(c -> {
                                                CONFIG.zoom3SmoothCamera(getBool(c, "Bool"));
                                                c.getSource().sendMessage(Text.literal("Zoom 3 smooth camera is now: " + CONFIG.zoom3SmoothCamera()));
                                                return 1;
                                            })
                                    )
                            )
                    )
                    .then(CommandManager.literal("ActionBarWriting")
                            .then(CommandManager.argument("Bool", bool())
                                    .executes(c -> {
                                        CONFIG.actionBarWriting(getBool(c, "Bool"));
                                        c.getSource().sendMessage(Text.literal("Action bar writing is now: " + CONFIG.actionBarWriting()));
                                        return 1;
                                    })
                            )

                    )
            );
    }
}
