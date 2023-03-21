package com.nicoislost.keybinds;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyBinds {
    private static KeyBinding keyBinding1;
    private static KeyBinding keyBinding2;
    private static KeyBinding keyBinding3;
    public static void RegisterKeyBinds() {
        keyBinding1 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.nicoislost.zoom1",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_C, "category.nicoislost.zoom"
        ));

        keyBinding1 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.nicoislost.zoom2",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN, "category.nicoislost.zoom"
        ));

        keyBinding1 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.nicoislost.zoom3",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN, "category.nicoislost.zoom"
        ));
    }
}
