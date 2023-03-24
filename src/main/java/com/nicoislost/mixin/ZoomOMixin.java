package com.nicoislost.mixin;

import com.nicoislost.ZoomO;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.nicoislost.ZoomO.CONFIG;

@Mixin(GameRenderer.class)
public class ZoomOMixin {
	@Inject(at = @At("RETURN"), method = "getFov(Lnet/minecraft/client/render/Camera;FZ)D",cancellable = true)
	private void getZoomLevel(CallbackInfoReturnable<Double> callbackInfo) {
		if (ZoomO.isZooming1()){
			double fov = callbackInfo.getReturnValue();
			callbackInfo.setReturnValue(fov * CONFIG.Zoom1()/100);
		}
		if (ZoomO.isZooming2()) {
			double fov = callbackInfo.getReturnValue();
			callbackInfo.setReturnValue(fov * CONFIG.Zoom2() / 100);
		}
		if (ZoomO.isZooming3()) {
			double fov = callbackInfo.getReturnValue();
			callbackInfo.setReturnValue(fov * CONFIG.Zoom3() / 100);
		}
	}
}