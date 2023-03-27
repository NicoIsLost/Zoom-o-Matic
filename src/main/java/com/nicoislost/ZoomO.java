package com.nicoislost;

import com.nicoislost.inputs.KeyBinds;
import com.nicoislost.owo.ZoomOConfig;
import com.nicoislost.util.ModRegistries;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;

public class ZoomO implements ClientModInitializer { //Mod initializer

	// We used owo from wispforest to store the mod settings
	//this is the call to the configuration
	//https://docs.wispforest.io/owo/setup/
	public static final ZoomOConfig CONFIG = ZoomOConfig.createAndLoad();
	private static final int[] zoomOrder = {0,0,0};

	@Override
	public void onInitializeClient() {
		ModRegistries.registerModPackages();
	}

	public static boolean isZooming() {
		return key1() || key2() || key3();
	}

	public static int zoomModifierNum() {
		// This function determines which zoom setting should be used and outputs it as a number 1,2, or 3

		if (keyTotNum() == 0) {
			return 0;
		} else if (keyTotNum() == 1) {
			for (int i = 1; i < 3; i++) { zoomOrder[i] = 0;} // resets the zoomOrder array to {0,0,0}

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
			//After this if-statement the zoomOrder array first value zoomOrder[0] will be set to the first key pressed.
			// if no second key gets pressed while still pressed it will reset next time a key is pressed.

		} else if (keyTotNum() == 2) {
			// if two keys are pressed.

			if (zoomOrder[2] == 0) {
				// This indicates there was 1 key, then a second was pressed.

				zoomOrder[1] = keyNum() / zoomOrder[0];
				// add to the second order slot, this gives us the second key pressed. See keyNum()

				return zoomOrder[0];
			} else {
				//This is the case in which there were 3 keys pressed and someone lifts one of the two keys

				boolean found = false;

				for (int i = 0; i < 3; i++) {
					//Reorder loop

					if (!found) {
						found = zoomOrder[i] == 6 / (keyNum());
						// When all three keys are pressed keyNum is always 6, this gives us the lifted key.
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
		} else { //All three keys are pressed.
			if (zoomOrder[2] == 0) {
				//First time pressing the third key.

				zoomOrder[2] = 6 / (zoomOrder[0]*zoomOrder[1]);
			}
			return zoomOrder[0];
		}
		return 0;
	}

	public static double ZoomModifier(int keyNum) {
		//translates the number output from zoomModifierNum() to a double with the zoom amount
		//Needs to be a double even though the amount is an integer, because the FOV is a double in the source.

		if (keyNum == 1) {
			return CONFIG.Zoom1();
		} else if (keyNum == 2) {
			return CONFIG.Zoom2();
		} else if (keyNum == 3) {
			return CONFIG.Zoom3();
		}
		return 0;
	}

	public static int keyTotNum() {
		//Tells us how many keybindings are pressed to use in zoomModifierNum()

		int num = 0;
		//cant use 1 here because we don't know which will be pressed!
		//we throw to a failure in zoomModifierNum() just in case.

		if (key1()) { ++num; }
		if (key2()) { ++num; }
		if (key3()) { ++num; }
		return num;
	}

	public static int keyNum() {
		int num = 1;
		//We start with one to avoid multiplying by zero, and it is only activates if at least 1 key is pressed.

		if (key2()) { num = num * 2; }
		if (key3()) { num = num * 3; }
		return num;
	}

	public static void smooothDuuude(int zoomNum) {
		//Sooooooo smoooooth duuuuuuude
		//Sets and resets the smoothening filter that slows down the mouse.

		if (isZooming() && !MinecraftClient.getInstance().options.smoothCameraEnabled && smoothChecker(zoomNum)) {
			MinecraftClient.getInstance().options.smoothCameraEnabled = true;
		} else if ((!isZooming() && MinecraftClient.getInstance().options.smoothCameraEnabled) || !smoothChecker(zoomNum)) {
			MinecraftClient.getInstance().options.smoothCameraEnabled = false;
		}
	}

	public static boolean smoothChecker(int zoomNum) {
		//Fetch the boolean from the registry, to use with smooothDuuude()

		if (zoomNum == 1) {
			return CONFIG.Zoom1SmoothCamera();
		} else if (zoomNum == 2) {
			return CONFIG.Zoom2SmoothCamera();
		} else if (zoomNum == 3) {
			return CONFIG.Zoom3SmoothCamera();
		}

		return false;
	}

	public static boolean key1() {
		return KeyBinds.keyBinding1.isPressed();
	}

	public static boolean key2() {
		return KeyBinds.keyBinding2.isPressed();
	}

	public static boolean key3() {
		return KeyBinds.keyBinding3.isPressed();
	}

}