package com.nicoislost.inputs;

import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class KeyBinds {

    public static final KeyBinding KEY_BINDING_1 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.zoom-o-matic.zoom1",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_C, "category.zoom-o-matic.zoom"
    ));
    public static final KeyBinding KEY_BINDING_2 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.zoom-o-matic.zoom2",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN, "category.zoom-o-matic.zoom"
    ));
    public static final KeyBinding KEY_BINDING_3 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.zoom-o-matic.zoom3",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN, "category.zoom-o-matic.zoom"
    ));

    private static final ImmutableList<KeyBinding> keyBindings = ImmutableList.of(
            KEY_BINDING_1,
            KEY_BINDING_2,
            KEY_BINDING_3
    );

    public static void registerKeyBinds() {}

    public static List<KeyBinding> getKeybindings() {
        return keyBindings;
    }
}
