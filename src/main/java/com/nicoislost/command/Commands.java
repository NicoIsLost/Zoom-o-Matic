package com.nicoislost.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.nicoislost.Zooms;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static com.nicoislost.ZoomOMatic.CONFIG;
import static com.nicoislost.util.ClientCommandUtil.argument;
import static com.nicoislost.util.ClientCommandUtil.literal;

/**
 * A class for registering commands
 */
public class Commands {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("zoom")
                    .then(literal("amount")
                            .then(argument("zoom", ZoomArgumentType.zoom())
                                    .then(argument("zoom_percentage", IntegerArgumentType.integer(1, 99))
                                            .executes(c -> {

                                                Zooms zoom = ZoomArgumentType.getZoom(c, "zoom");

                                                int zoomPercentage = IntegerArgumentType.getInteger(c, "zoom_percentage");

                                                zoom.configSet(zoomPercentage);
                                                c.getSource().getPlayer().sendMessage(Text.literal(String.format("%s is now: %d%%", zoom.name(), zoomPercentage)).formatted(Formatting.GRAY, Formatting.ITALIC));

                                                return 1;
                                            })
                                    )
                            )
                    )
                    .then(literal("smooth")
                            .then(argument("zoom", ZoomArgumentType.zoom())
                                    .then(argument("bool", BoolArgumentType.bool())
                                            .executes(c -> {

                                                Zooms zoom = ZoomArgumentType.getZoom(c, "zoom");

                                                boolean bool = BoolArgumentType.getBool(c, "bool");

                                                zoom.configSetSmooth(bool);
                                                c.getSource().getPlayer().sendMessage(Text.literal(String.format("Smooth %s is now: %s", zoom.name(), bool)).formatted(Formatting.GRAY, Formatting.ITALIC));

                                                return 1;
                                            })
                                    )
                            )
                    )
                    .then(literal("actionbar_writing")
                            .then(argument("bool", BoolArgumentType.bool())
                                    .executes(c -> {

                                        boolean bool = BoolArgumentType.getBool(c, "bool");

                                        CONFIG.actionBarWriting(bool);
                                        c.getSource().getPlayer().sendMessage(Text.literal(String.format("Actionbar writing is now: %s", bool)).formatted(Formatting.GRAY, Formatting.ITALIC));

                                        return 1;
                                    })
                            )

                    )
            );
    }
}
