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
		// The original code wasn't sitting well for me for a number of reasons.
		// 1. You were asking `ZoomO` for the `zoomModifier`, but you were also having to feed information
		// into `ZoomO` from `ZoomO` and that kind of felt weird to me.  `zoomModifier` should be capable of been a self-
		// contained unit of work, as it reduces the risk of a dumb developer (like me) from feeding in the
		// wrong information.
		// 2. I, personally, think it should have just been returning the zoom factor (so it should have calculated
		// the factor based on the modifier and returned that), see point 1.
		// 3. I'd also argue that a lot of the "core" functionality could have been maintained here ... but
		// as a centrallised and re-usable piece of code, we could, instead, use a delegate pattern.
		//
		// So, on that note, I've delegated the functionality directly to the `ZoomO` class instead, because, really
		// this class doesn't really care
		ZoomO.getZoomLevel(callbackInfo);
	}
}