package com.nicoislost.mixin;

import com.nicoislost.ZoomOMatic;
import com.nicoislost.Zooms;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
@Environment(EnvType.CLIENT)
public abstract class MouseMixin {

    /**
     * Gives feedback to the player about the current zoom level
     * @param zoom the zoom setting
     */
    @Unique
    private void actionSend(Zooms zoom) {
        ClientPlayerEntity client = MinecraftClient.getInstance().player;
        if (ZoomOMatic.CONFIG.actionBarWriting() && client != null) {
            client.sendMessage(Text.literal(zoom.getName() + " - " + zoom.configGet() + "%"), true);
        }
    }

    /**
     * Changes the zoom level when the mouse is scrolled
     * @param window the window
     * @param horizontal the horizontal scroll amount
     * @param vertical the vertical scroll amount
     * @param ci the callback info
     */
    @Inject(
            at = @At("HEAD"),
            method = "onMouseScroll"
    )
    private void zoom$onMouseScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
        if(ZoomOMatic.isZooming() && MinecraftClient.getInstance().player != null){
            int increment = vertical > 0 ? 1 : -1;
            int newZoom = ZoomOMatic.getActiveZoom().configGet() + increment;
            if (newZoom >= 0 && newZoom <= 99) {
                ZoomOMatic.getActiveZoom().configSet(newZoom);
                actionSend(ZoomOMatic.getActiveZoom());
            }
        }
    }

    /**
     * Redirector for the smooth camera setting
     * @param instance the game options instance
     * @return the smooth camera setting
     */
    @Redirect(
            method = "updateMouse",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/option/GameOptions;smoothCameraEnabled:Z"
            )
    )
    private boolean zoom$updateMouse$smoothCameraEnabled(GameOptions instance) {
        return (ZoomOMatic.isZooming() && ZoomOMatic.getActiveZoom().configGetSmooth()) || instance.smoothCameraEnabled;
    }
}
