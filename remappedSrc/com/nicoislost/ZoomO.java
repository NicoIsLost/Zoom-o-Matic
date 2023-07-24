package com.nicoislost;

import com.nicoislost.inputs.KeyBinds;
import com.nicoislost.owo.ZoomOConfig;
import com.nicoislost.util.ModRegistries;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;

/**
 * The main class for the mod that initializes and handles a majority of the
 * logic.
 */
public class ZoomO implements ClientModInitializer { // Mod initializer

	// We used owo from wispforest to store the mod settings
	// this is the call to the configuration
	// https://docs.wispforest.io/owo/setup/
	public static final ZoomOConfig CONFIG = ZoomOConfig.createAndLoad();
	private static final int[] zoomOrder = { 0, 0, 0 };

	@Override
	public void onInitializeClient() {
		ModRegistries.registerModPackages();
	}

	/**
	 * @return If any keys are being pressed
	 */
	public static boolean isZooming() {
		return key1() || key2() || key3();
	}

	/**
	 * Determine which zoom setting should be used. This is the function that is
	 * called by the
	 * {@link com.nicoislost.mixin.ZoomOMixin#getZoomLevel(org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable)
	 * getZoomLevel} method from the Mixin.
	 * 
	 * @return 1, 2, 3, or 0 if no valid keys are pressed.
	 */
	public static int zoomModifierNum() {

		if (keysPressed() == 0) {
			return 0;
		} else if (keysPressed() == 1) {
			// reset the zoomOrder array to {0,0,0}
			for (int i = 1; i < 3; i++) {
				zoomOrder[i] = 0;
			}

			if (key1()) {
				zoomOrder[0] = 1;
				return 1;
			} else if (key2()) {
				zoomOrder[0] = 2;
				return 2;
			} else if (key3()) {
				zoomOrder[0] = 3;
				return 3;
			}
			// After this if-statement the zoomOrder array first value zoomOrder[0] will be
			// set to the first key pressed.
			// if no second key gets pressed while still pressed it will reset next time a
			// key is pressed.

		} else if (keysPressed() == 2) {
			// if two keys are pressed.

			if (zoomOrder[2] == 0) {
				// This indicates there was 1 key, then a second was pressed.

				try {
					zoomOrder[1] = keyNum() / zoomOrder[0]; //TODO find a better way to avoid dividing by zero.
					// add to the second order slot, this gives us the second key pressed. See
					// keyNum()
				} catch (Exception e) {
					zoomOrder[1] = 0;
				}

				return zoomOrder[0];
			} else {
				// This is the case in which there were 3 keys pressed and someone lifts one of
				// the two keys

				boolean found = false;

				for (int i = 0; i < 3; i++) {
					// Reorder loop

					if (!found) {
						found = zoomOrder[i] == 6 / (keyNum());
						// When all three keys are pressed keyNum is always 6, this gives us the lifted
						// key.
					}
					if (i == 2) {
						zoomOrder[i] = 0;
						break;
					}
					if (found) {
						zoomOrder[i] = zoomOrder[i + 1];
					}
				}
				return zoomOrder[0];
			}
		} else { // All three keys are pressed.
			if (zoomOrder[2] == 0) {
				// First time pressing the third key.
				try {
					zoomOrder[2] = 6 / (zoomOrder[0] * zoomOrder[1]); //TODO find a better way to avoid dividing by zero.
				} catch (Exception e) {
					zoomOrder[2] = 0;
				}
			}
			return zoomOrder[0];
		}
		return 0;
	}

	/**
	 * Translates the number output from {@link #zoomModifierNum()} to a double with
	 * the zoom amount defined in the config.
	 * 
	 * @param keyNum - 1, 2, or 3
	 * @return A double that can be interpreted as a FOV value in the source based
	 *         on corresponding values in the config. Returns 0 if keyNum is
	 *         invalid.
	 */
	public static double ZoomModifier(int keyNum) {
		if (keyNum == 1) {
			return CONFIG.Zoom1();
		} else if (keyNum == 2) {
			return CONFIG.Zoom2();
		} else if (keyNum == 3) {
			return CONFIG.Zoom3();
		}
		return 0;
	}

	/**
	 * Tells us how many keybindings are pressed to use in
	 * {@link #zoomModifierNum()}
	 * 
	 * @return 0-3 depending on how many keys are pressed.
	 */
	public static int keysPressed() {
		//This converts our booleans into ints and sums them up.
		return (key1() ? 1 : 0) + (key2() ? 1 : 0) + (key3() ? 1 : 0);
	}

	/**
	 * @return 1 if 0 or 1 keys are pressed, 2 if 2 keys are pressed, 6 if 3 keys
	 *         are pressed.
	 */
	public static int keyNum() {
		int num = 1;
		// We start with one to avoid multiplying by zero, and it is only activates if
		// at least 1 key is pressed.

		if (key2()) {
			num = num * 2;
		}
		if (key3()) {
			num = num * 3;
		}
		return num;
	}

	/**
	 * Sooooooo smoooooth duuuuuuude Sets and resets the smoothening filter that
	 * slows down the mouse.
	 * 
	 * @param zoomNum - Zoom setting 1, 2, or 3
	 */
	public static void smooothDuuude(int zoomNum) {
		if (isZooming() && !MinecraftClient.getInstance().options.smoothCameraEnabled && smoothChecker(zoomNum)) {
			MinecraftClient.getInstance().options.smoothCameraEnabled = true;
		} else if ((!isZooming() && MinecraftClient.getInstance().options.smoothCameraEnabled)
				|| !smoothChecker(zoomNum)) {
			MinecraftClient.getInstance().options.smoothCameraEnabled = false;
		}
	}

	/**
	 * Fetch the boolean from the registry, to use with smooothDuuude()
	 * 
	 * @param zoomNum
	 * @return
	 */
	public static boolean smoothChecker(int zoomNum) {

		if (zoomNum == 1) {
			return CONFIG.Zoom1SmoothCamera();
		} else if (zoomNum == 2) {
			return CONFIG.Zoom2SmoothCamera();
		} else if (zoomNum == 3) {
			return CONFIG.Zoom3SmoothCamera();
		}

		return false;
	}

	/**
	 * @return If key 1 is being pressed
	 */
	public static boolean key1() {
		return KeyBinds.keyBinding1.isPressed();
	}

	/**
	 * @return If key 2 is being pressed
	 */
	public static boolean key2() {
		return KeyBinds.keyBinding2.isPressed();
	}

	/**
	 * @return If key 3 is being pressed
	 */
	public static boolean key3() {
		return KeyBinds.keyBinding3.isPressed();
	}

}