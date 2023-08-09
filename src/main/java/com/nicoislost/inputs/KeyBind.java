package com.nicoislost.inputs;

import com.nicoislost.owo.ZoomOConfig;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

// Arguably, prefer singular over plural
// Personally, I find enums in this state more useful, as it possible to isolate functionality based on the current
// enum value and clearly defines the expected acceptable values.
// Also, `values` <- automatic listing of the enum values!
public enum KeyBind {
    LEVEL_1(
            new KeyBinding(
                    "key.nicoislost.zoom1",
                    InputUtil.Type.KEYSYM,
                    GLFW.GLFW_KEY_C, "category.nicoislost.zoom"
            )
    ),
    LEVEL_2(
            new KeyBinding(
                    "key.nicoislost.zoom2",
                    InputUtil.Type.KEYSYM,
                    GLFW.GLFW_KEY_C, "category.nicoislost.zoom"
            )
    ),
    LEVEL_3(
            // Since most of the parameters are basically repeated, we could simplify this down to
            // a re-usable method, for example...
            makeBinding("key.nicoislost.zoom3")
    );

    private static KeyBinding makeBinding(String name) {
        return new KeyBinding(
                name,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_C, "category.nicoislost.zoom"
        );
    }

    private final KeyBinding binding;

    private KeyBind(KeyBinding binding) {
        this.binding = binding;
    }

    public KeyBinding getBinding() {
        return binding;
    }

    // Simple convenience
    public boolean isPressed() {
        return getBinding().isPressed();
    }

    public void register() {
        KeyBindingHelper.registerKeyBinding(getBinding());
    }

    // Simple helper method
    public static void registerBindings() {
        for (KeyBind bind : values()) {
            bind.register();
        }
    }
}
