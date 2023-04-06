package com.nicoislost.mixin;

import com.nicoislost.ZoomO;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)

public class ZoomOMixin {
	@Inject(at = @At("RETURN"), method = "getFov(Lnet/minecraft/client/render/Camera;FZ)D",cancellable = true)
	public void getZoomLevel(CallbackInfoReturnable<Double> callbackInfo) {
		if (ZoomO.isZooming()){
			double fov = callbackInfo.getReturnValue();
			callbackInfo.setReturnValue(fov * (1 - ZoomO.ZoomModifier(ZoomO.zoomModifierNum())/100));
		}

		ZoomO.smooothDuuude(ZoomO.zoomModifierNum());
		//Sets smoothness based on the settings in owo
	}
}