package com.nicoislost.util;

import com.nicoislost.inputs.Commands;
import com.nicoislost.inputs.KeyBind;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class ModRegistries {
    public static void registerModPackages() {
        KeyBind.registerBindings();
        registerCommands();
    }
    private static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(Commands::register);}
}
