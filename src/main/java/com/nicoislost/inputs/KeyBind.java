package com.nicoislost.inputs;

import com.nicoislost.ZoomO;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import java.util.function.Consumer;
import java.util.function.Supplier;

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
            ),
            ZoomO.CONFIG::zoom1,
            ZoomO.CONFIG::zoom1SmoothCamera,
            ZoomO.CONFIG::zoom1SmoothCamera
    ),
    LEVEL_2(
            new KeyBinding(
                    "key.nicoislost.zoom2",
                    InputUtil.Type.KEYSYM,
                    GLFW.GLFW_KEY_C, "category.nicoislost.zoom"
            ),
            ZoomO.CONFIG::zoom2,
            ZoomO.CONFIG::zoom2SmoothCamera,
            ZoomO.CONFIG::zoom2SmoothCamera
    ),
    LEVEL_3(
            // Since most of the parameters are basically repeated, we could simplify this down to
            // a re-usable method, for example...
            makeBinding("key.nicoislost.zoom3"),
            ZoomO.CONFIG::zoom3,
            ZoomO.CONFIG::zoom3SmoothCamera,
            ZoomO.CONFIG::zoom3SmoothCamera
    );

    private static KeyBinding makeBinding(String name) {
        return new KeyBinding(
                name,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_C, "category.nicoislost.zoom"
        );
    }

    private final KeyBinding binding;
    private final Supplier<Integer> configGetter;
    private final Supplier<Boolean> smoothGetter;
    private final Consumer<Boolean> smoothSetter;

    private KeyBind(KeyBinding binding, Supplier<Integer> configGetter, Supplier<Boolean> smoothGetter, Consumer<Boolean> smoothSetter) {
        this.binding = binding;
        this.smoothGetter = smoothGetter;
        this.smoothSetter = smoothSetter;
        this.configGetter = configGetter;
    }

    public int getConfigValue() {
        return configGetter.get();
    }

    public boolean isSmoothCameraEnabled() {
        return smoothGetter.get();
    }

    public void setSmoothCamera(boolean value) {
        smoothSetter.accept(value);
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
