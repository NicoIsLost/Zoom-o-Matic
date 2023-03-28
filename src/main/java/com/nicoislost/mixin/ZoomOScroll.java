package com.nicoislost.mixin;

import com.nicoislost.ZoomO;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class ZoomOScroll {
    @Inject(at = @At("HEAD"), method = "onMouseScroll")
    private void ZoomScrollWheel (long window, double horizontal, double vertical, CallbackInfo ci) {

        if(ZoomO.isZooming()){
            double beforehorizontal = horizontal;
            if (beforehorizontal<vertical && ZoomO.key1()){
                ZoomO.CONFIG.Zoom1(ZoomO.CONFIG.Zoom1()+2);
                beforehorizontal = horizontal;
            }
            else if (beforehorizontal<vertical && ZoomO.key2()){
                ZoomO.CONFIG.Zoom2(ZoomO.CONFIG.Zoom2()+2);
                beforehorizontal = horizontal;
            }
            else if (beforehorizontal<vertical && ZoomO.key3()){
                ZoomO.CONFIG.Zoom3(ZoomO.CONFIG.Zoom3()+2);
                beforehorizontal = horizontal;
            }
            else if (beforehorizontal>vertical && ZoomO.key1()){
                ZoomO.CONFIG.Zoom1(ZoomO.CONFIG.Zoom1()-2);
                beforehorizontal = horizontal;
            }
            else if (beforehorizontal>vertical && ZoomO.key2()){
                ZoomO.CONFIG.Zoom2(ZoomO.CONFIG.Zoom2()-2);
                beforehorizontal = horizontal;
            }
            else if (beforehorizontal>vertical && ZoomO.key3()){
                ZoomO.CONFIG.Zoom3(ZoomO.CONFIG.Zoom3()-2);
                beforehorizontal = horizontal;
            }
        }
    }
}
