package com.nicoislost;

import com.nicoislost.inputs.KeyBinds;
import net.minecraft.client.option.KeyBinding;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * An enum for the different zoom settings
 */
public enum Zooms {

    ZOOM_1(
            "Zoom 1",
            ZoomOMatic.CONFIG::zoom1,
            ZoomOMatic.CONFIG::zoom1,
            ZoomOMatic.CONFIG::zoom1SmoothCamera,
            ZoomOMatic.CONFIG::zoom1SmoothCamera,
            KeyBinds.KEY_BINDING_1
    ),
    ZOOM_2(
            "Zoom 2",
            ZoomOMatic.CONFIG::zoom2,
            ZoomOMatic.CONFIG::zoom2,
            ZoomOMatic.CONFIG::zoom2SmoothCamera,
            ZoomOMatic.CONFIG::zoom2SmoothCamera,
            KeyBinds.KEY_BINDING_2
    ),
    ZOOM_3(
            "Zoom 3",
            ZoomOMatic.CONFIG::zoom3,
            ZoomOMatic.CONFIG::zoom3,
            ZoomOMatic.CONFIG::zoom3SmoothCamera,
            ZoomOMatic.CONFIG::zoom3SmoothCamera,
            KeyBinds.KEY_BINDING_3
    ),

    NONE(
            "None",
            v -> {},
            () -> 0,
            v -> {},
            () -> false,
            null
    );

    private final String name;
    private final Consumer<Integer> setter;
    private final Supplier<Integer> getter;
    private final Consumer<Boolean> smoothSetter;
    private final Supplier<Boolean> smoothGetter;
    private final KeyBinding keyBinding;

    Zooms(String name, Consumer<Integer> setter, Supplier<Integer> getter, Consumer<Boolean> smoothSetter, Supplier<Boolean> smoothGetter, KeyBinding keyBinding) {
        this.name = name;
        this.setter = setter;
        this.getter = getter;
        this.smoothSetter = smoothSetter;
        this.smoothGetter = smoothGetter;
        this.keyBinding = keyBinding;
    }

    public String getName() {
        return name;
    }

    public void configSet(int value) {
        setter.accept(value);
    }

    public int configGet() {
        return getter.get();
    }

    public void configSetSmooth(boolean bool) {
        smoothSetter.accept(bool);
    }

    public boolean configGetSmooth() {
        return smoothGetter.get();
    }

    public static Zooms fromKeyBinding(KeyBinding keyBinding) {
        for (Zooms zoom : Zooms.values()) {
            if (zoom.keyBinding == keyBinding) {
                return zoom;
            }
        }
        return NONE;
    }
}
