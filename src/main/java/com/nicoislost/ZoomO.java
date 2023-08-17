package com.nicoislost;

import com.nicoislost.inputs.KeyBind;
import com.nicoislost.owo.ZoomOConfig;
import com.nicoislost.util.ModRegistries;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

/**
 * The main class for the mod that initializes and handles a majority of the
 * logic.
 */
public class ZoomO implements ClientModInitializer { // Mod initializer

	private static final Logger LOGGER = LoggerFactory.getLogger(ZoomO.class);

	// We used owo from wispforest to store the mod settings
	// this is the call to the configuration
	// https://docs.wispforest.io/owo/setup/
	public static final ZoomOConfig CONFIG = ZoomOConfig.createAndLoad();
	// I've spent WAY to long steering at this trying to figure out what it's trying to do.  That's not say it's not
	// a good idea, only that I'm dumb :P.  From what I understand, this seems to be keeping track of the order the
	// keys are pressed ... my immediate thought is, why not use a "+"/"-" workflow (ie step in/step out), and simply
	// keep track of the current zoom level, but I'm guessing it has to do with having the ability to choose
	// the zoom level, for more "dramatic" effects ;)
	//
	//private static final int[] zoomOrder = { 0, 0, 0 };

	// Maintains a FILO style list of the keys been pressed, where the first element is the most recent key pressed
	// and the last is the first key pressed
	private static final List<KeyBind> ACTIVE_KEYS = new ArrayList<>(3);

	@Override
	public void onInitializeClient() {
		ModRegistries.registerModPackages();
	}

	/**
	 * @return If any keys are being pressed
	 */
	public static boolean isZooming() {
//		You could also do something like...
//		for (KeyBind binding : KeyBind.values()) {
//			if (binding.isPressed()) {
//				return true;
//			}
//		}
//		return false;
//		And if you were to add more key bindings, you get automatic support
		return isLevelOnePressed() || isLevelTwoPressed() || isLevelThreePressed();
	}

	/**
	 * Synchronises the key states
	 */
	private static void syncKeys() {
		// So, in theory, as a new is pressed, it will be added to the start of the `List` and as a key is released
		// it will be removed, independent of where it resides in the `List`.
		// This should then maintain the order in which the keys have been pressed and the first element is the latest
		// key and the last element will be the earliest key pressed, so kind of a FILO list (except we can
		// technically pop keys from any location)...but should otherwise maintain the "press" order
		// I would prefer to use a `Set`, but that doesn't guarantee insertion order
		for (KeyBind bind : KeyBind.values()) {
			if (bind.isPressed() && !ACTIVE_KEYS.contains(bind)) {
				// Add the new key to the start of the list
				ACTIVE_KEYS.add(0, bind);
			} else if (!bind.isPressed()) {
				ACTIVE_KEYS.remove(bind);
			}
		}
	}

	/**
	 * @return Returns the currently active key binding or null if none are active
	 */
	private static KeyBind getCurrentKeyBind() {
		if (!isZooming() || ACTIVE_KEYS.isEmpty()) {
			return null;
		}
		return ACTIVE_KEYS.get(0);
	}

	/**
	 * Returns the configured zoom modifier for the currently active key binding
	 * @return The configure zoom modifier value for the active key binding or 0 if no key binding is active
	 */
	private static double getCurrentZoomModifier() {
		KeyBind keyBind = getCurrentKeyBind();
		if (keyBind == null) {
			return 0;
		}
		LOGGER.info("Current key = " + keyBind);
		return keyBind.getConfigValue();
	}

	/**
	 * Delegates the functionality of the MixIn callback
	 * @param callbackInfo
	 */
	// One thing to remember is, this is going to be called ALOT while one of the key bindings is being pressed,
	// so we should be conscious of doing as little work as fast as possible - so, caching values if possible and/or
	// reducing the amount of computational work we're doing.
	// One thing we could do is check the previous state with the current state and if nothing's changed, returned
	// the previously calculated value, but, arguably, comparing two `List` isn't the fastest thing in the world either,
	// so maybe a `BitSet` instead?
	public static void getZoomLevel(CallbackInfoReturnable<Double> callbackInfo) {
		syncKeys();
		if (isZooming()) {
			double fov = callbackInfo.getReturnValue();
			callbackInfo.setReturnValue(fov * ZoomO.getZoomFactor());
		}
		ZoomO.smooothDuuude();
	}

	/**
	 * Calculates the zoom factor to be applied based on the current active key binding and it's configured zoom
	 * state
	 * @return The zoom factor for the key binding or `1` if no zooming is occurring
	 */
	public static double getZoomFactor() {
		// Insert some paranoia
		if (!isZooming()) {
			LOGGER.info("You're not zooming, what are you doing here?!");
			return 1;
		}
		double modifier = getCurrentZoomModifier();
		LOGGER.info("modifier = " + modifier);
		return 1 - (modifier / 100);
	}

	private static boolean isSmoothCameraForCurrentKeyEnabled() {
		KeyBind keyBind = getCurrentKeyBind();
		if (keyBind == null) {
			return false;
		}
		return keyBind.isSmoothCameraEnabled();
//		boolean enabled = false;
//		switch (keyBind) {
//			case LEVEL_1 -> enabled = CONFIG.zoom1SmoothCamera();
//			case LEVEL_2 -> enabled = CONFIG.zoom2SmoothCamera();
//			case LEVEL_3 -> enabled = CONFIG.zoom3SmoothCamera();
//		}
//		return enabled;
	}

	/**
	 * Sooooooo smoooooth duuuuuuude Sets and resets the smoothening filter that
	 * slows down the mouse.
	 */
	public static void smooothDuuude() {
		//int zoomNum = zoomModifierNum();
		if (isZooming() && !MinecraftClient.getInstance().options.smoothCameraEnabled && isSmoothCameraForCurrentKeyEnabled()) {
			MinecraftClient.getInstance().options.smoothCameraEnabled = true;
		} else if ((!isZooming() && MinecraftClient.getInstance().options.smoothCameraEnabled)
				|| !isSmoothCameraForCurrentKeyEnabled()) {
			MinecraftClient.getInstance().options.smoothCameraEnabled = false;
		}
	}

	/*
	I spent some time steering at this trying to figure out what it was trying to say.  As a "general" recommendation,
	a little more verbose description is going to make it easier for other people to read the code and won't
	require them to jump down to the method definition to actually workout what's going.

	Java Coding Conventions would also recommend using the prefix of `is` for boolean properties/results, but I'd
	also argue that `are` is also acceptable if the grammar matches
	 */

	/**
	 * @return If key 1 is being pressed
	 */
	public static boolean isLevelOnePressed() {
		// Example of convince method
		return KeyBind.LEVEL_1.isPressed();
	}

	/**
	 * @return If key 2 is being pressed
	 */
	public static boolean isLevelTwoPressed() {
		// Example showing how to reach "more" details of the binding if you need it
		return KeyBind.LEVEL_2.getBinding().isPressed();
	}

	/**
	 * @return If key 3 is being pressed
	 */
	public static boolean isLevelThreePressed() {
		return KeyBind.LEVEL_3.getBinding().isPressed();
	}

}