package com.nicoislost.mixin;

import com.nicoislost.ZoomOMatic;
import com.nicoislost.Zooms;
import net.minecraft.client.render.GameRenderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

	/**
	 * Changes the FOV when zooming
	 * @param callbackInfo the callback info
	 */
	@Inject(at = @At("RETURN"), method = "getFov(Lnet/minecraft/client/render/Camera;FZ)D",cancellable = true)
	public void zoom$getFov(CallbackInfoReturnable<Double> callbackInfo) {
		if (ZoomOMatic.getActiveZoom() != null){

			double fov = callbackInfo.getReturnValue() * (1 - (double) ZoomOMatic.getActiveZoom().getZoom() / 100);

			callbackInfo.setReturnValue(fov);
		}
	}
}