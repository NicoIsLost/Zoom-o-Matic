package com.nicoislost.mixin;

import com.nicoislost.ZoomOMatic;
import net.minecraft.entity.player.PlayerInventory;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {

    /**
     * Prevents the player from scrolling through the hotbar while zooming
     * @param scrollAmount the amount scrolled
     * @param ci the callback info
     */
    @Inject(at = @At("HEAD"), method = "scrollInHotbar", cancellable = true)
    private void zoom$scrollInHotbar(double scrollAmount, @NotNull CallbackInfo ci) {
        if (ZoomOMatic.getActiveZoom() != null) {
            ci.cancel();
        }
    }
}