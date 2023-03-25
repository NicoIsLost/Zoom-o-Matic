package com.nicoislost.mixin;

import com.nicoislost.ZoomO;
import com.nicoislost.inputs.KeyBinds;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.nicoislost.ZoomO.CONFIG;
import static com.nicoislost.ZoomO.zoomModifer;

@Mixin(GameRenderer.class)
public class ZoomOMixin {
	@Inject(at = @At("RETURN"), method = "getFov(Lnet/minecraft/client/render/Camera;FZ)D",cancellable = true)
	private void getZoomLevel(CallbackInfoReturnable<Double> callbackInfo) {
		if (ZoomO.isZooming()){
			double fov = callbackInfo.getReturnValue();
			callbackInfo.setReturnValue(fov * (1 - zoomModifer()/100));
		}
	}
}