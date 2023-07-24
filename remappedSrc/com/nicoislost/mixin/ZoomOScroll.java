package com.nicoislost.mixin;

import com.nicoislost.ZoomO;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
@Environment(EnvType.CLIENT)
public class ZoomOScroll {
    private void actionSend(int zoomNum) {
        ClientPlayerEntity client = MinecraftClient.getInstance().player;
        assert client != null;
        if (ZoomO.CONFIG.actionBarWriting()) {
            switch (zoomNum) {
                case (1) -> client.sendMessage(Text.literal("Zoom1 - " + ZoomO.CONFIG.Zoom1()+"%"), true);
                case (2) -> client.sendMessage(Text.literal("Zoom2 - " + ZoomO.CONFIG.Zoom2()+"%"), true);
                case (3) -> client.sendMessage(Text.literal("Zoom3 - " + ZoomO.CONFIG.Zoom3()+"%"), true);
                default -> throw new IllegalStateException("Unexpected value: " + zoomNum);
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "onMouseScroll")
    private void ZoomScrollWheel (long window, double horizontal, double vertical, CallbackInfo ci) {

        if(ZoomO.isZooming() && MinecraftClient.getInstance().player != null){
            double beforehorizontal = horizontal;
            if (beforehorizontal<vertical && ZoomO.key1() && MinecraftClient.getInstance().player != null){
                ZoomO.CONFIG.Zoom1(ZoomO.CONFIG.Zoom1()+1);
                beforehorizontal = horizontal;
               actionSend(1);
            }
            else if (beforehorizontal<vertical && ZoomO.key2() && MinecraftClient.getInstance().player != null){
                ZoomO.CONFIG.Zoom2(ZoomO.CONFIG.Zoom2()+1);
                beforehorizontal = horizontal;
                actionSend(2);
            }
            else if (beforehorizontal<vertical && ZoomO.key3() && MinecraftClient.getInstance().player != null){
                ZoomO.CONFIG.Zoom3(ZoomO.CONFIG.Zoom3()+1);
                beforehorizontal = horizontal;
                actionSend(3);
            }
            else if (beforehorizontal>vertical && ZoomO.key1() && MinecraftClient.getInstance().player != null){
                ZoomO.CONFIG.Zoom1(ZoomO.CONFIG.Zoom1()-1);
                beforehorizontal = horizontal;
                actionSend(1);
            }
            else if (beforehorizontal>vertical && ZoomO.key2() && MinecraftClient.getInstance().player != null){
                ZoomO.CONFIG.Zoom2(ZoomO.CONFIG.Zoom2()-1);
                beforehorizontal = horizontal;
                actionSend(2);
            }
            else if (beforehorizontal>vertical && ZoomO.key3() && MinecraftClient.getInstance().player != null){
                ZoomO.CONFIG.Zoom3(ZoomO.CONFIG.Zoom3()-1);
                beforehorizontal = horizontal;
                actionSend(3);
            }
        }
    }
}
