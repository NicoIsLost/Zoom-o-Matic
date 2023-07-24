package com.nicoislost.inputs;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyBinds {
    public static KeyBinding keyBinding1;
    public static KeyBinding keyBinding2;
    public static KeyBinding keyBinding3;
    public static void RegisterKeyBinds() {
        keyBinding1 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.nicoislost.zoom1",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_C, "category.nicoislost.zoom"
        ));

        keyBinding2 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.nicoislost.zoom2",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN, "category.nicoislost.zoom"
        ));

        keyBinding3 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.nicoislost.zoom3",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN, "category.nicoislost.zoom"
        ));
    }
}
