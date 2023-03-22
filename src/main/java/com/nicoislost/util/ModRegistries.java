package com.nicoislost.util;

import com.nicoislost.inputs.KeyBinds;
import com.nicoislost.inputs.ZoomAmountCommand;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class ModRegistries {
    public static void registerModPackages() {
        KeyBinds.RegisterKeyBinds();
        registerCommands();
    }

    private static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(ZoomAmountCommand::register);
    }
}
