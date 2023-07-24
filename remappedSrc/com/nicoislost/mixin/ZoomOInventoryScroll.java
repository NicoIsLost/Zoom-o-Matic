package com.nicoislost.mixin;

import com.nicoislost.ZoomO;
import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public class ZoomOInventoryScroll {
    @Shadow
    public int selectedSlot;

    private int lastSlot = -1;

    @Inject(at = @At("HEAD"), method = "scrollInHotbar")
    private void beforeScrollInHotbar(double scrollAmount, CallbackInfo ci) {
        if (ZoomO.isZooming()) {
            lastSlot = selectedSlot;
        }
    }

    @Inject(at = @At("TAIL"), method = "scrollInHotbar")
    private void afterScrollInHotbar(double scrollAmount, CallbackInfo ci) {
        if (ZoomO.isZooming()) {
            if (lastSlot > -1) {
                selectedSlot = lastSlot;
                lastSlot = -1;
            }
        }
    }
}
//code by Zihad#5252