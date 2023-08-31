package com.nicoislost;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.jetbrains.annotations.Contract;
import org.lwjgl.glfw.GLFW;

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
            GLFW.GLFW_KEY_C
    ),
    ZOOM_2(
            "Zoom 2",
            ZoomOMatic.CONFIG::zoom2,
            ZoomOMatic.CONFIG::zoom2,
            ZoomOMatic.CONFIG::zoom2SmoothCamera,
            ZoomOMatic.CONFIG::zoom2SmoothCamera
    ),
    ZOOM_3(
            "Zoom 3",
            ZoomOMatic.CONFIG::zoom3,
            ZoomOMatic.CONFIG::zoom3,
            ZoomOMatic.CONFIG::zoom3SmoothCamera,
            ZoomOMatic.CONFIG::zoom3SmoothCamera
    );

    private final String name;
    private final Consumer<Integer> setter;
    private final Supplier<Integer> getter;
    private final Consumer<Boolean> smoothSetter;
    private final Supplier<Boolean> smoothGetter;
    private final KeyBinding keyBinding;

    Zooms(String name, Consumer<Integer> setter, Supplier<Integer> getter, Consumer<Boolean> smoothSetter, Supplier<Boolean> smoothGetter, int key) {
        this.name = name;
        this.setter = setter;
        this.getter = getter;
        this.smoothSetter = smoothSetter;
        this.smoothGetter = smoothGetter;
        this.keyBinding = makeKeybind(name.toLowerCase().replaceAll("\\W", "_" ), key);
    }

    Zooms(String name, Consumer<Integer> setter, Supplier<Integer> getter, Consumer<Boolean> smoothSetter, Supplier<Boolean> smoothGetter) {
        this(name, setter, getter, smoothSetter, smoothGetter, GLFW.GLFW_KEY_UNKNOWN);
    }

    
    private static KeyBinding makeKeybind(String name, int key) {
        return new KeyBinding("key.zoom-o-matic." + name, InputUtil.Type.KEYSYM, key, "category.zoom-o-matic.zoom");
    }

    /**
     * Registers the keybinding for the zoom
     */
    public void registerKeyBinding() {
        KeyBindingHelper.registerKeyBinding(keyBinding);
    }

    public boolean isActive() {
        return keyBinding.isPressed();
    }

    public String getName() {
        return name;
    }

    public void setZoom(int zoom) {
        setter.accept(zoom);
    }

    public int getZoom() {
        return getter.get();
    }

    public void setSmooth(boolean smooth) {
        smoothSetter.accept(smooth);
    }

    public boolean getSmooth() {
        return smoothGetter.get();
    }


}
