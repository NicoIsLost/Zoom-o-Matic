package com.nicoislost;

//Internal dependencies
import com.nicoislost.inputs.KeyBinds;
import com.nicoislost.owo.ZoomOConfig;
import com.nicoislost.util.ModRegistries;

//Fabric / MC dependencies
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;

//Troubleshooting logger dependencies
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;


public class ZoomO implements ClientModInitializer { //Mod initializer

	// We used owo from wispforest to store the mod settings
	//this is the call to the configuration
	//https://docs.wispforest.io/owo/setup/
	public static final ZoomOConfig CONFIG = ZoomOConfig.createAndLoad();

	//public static final Logger LOGGER = LoggerFactory.getLogger("modid"); // Used for troubleshooting

	private static final int[] zoomOrder = {0,0,0};
	// Used in zoomModifierNum(), saves the order in which the zoom keys were pressed.

	@Override
	public void onInitializeClient() {
		ModRegistries.registerModPackages();
	} //Loads our mod registries (for good organization)

	public static boolean isZooming() {
		return KeyBinds.keyBinding1.isPressed() || KeyBinds.keyBinding2.isPressed() || KeyBinds.keyBinding3.isPressed();
	}
	// Are any of the three keys pressed?


	public static int zoomModifierNum() {

		// This function determines which zoom setting should be used and outputs it as a number 1,2, or 3

		if (keyTotNum() == 0) {
			return 0;
			//failure case

		} else if (keyTotNum() == 1) {

			for (int i = 1; i < 3; i++) {
				zoomOrder[i] = 0;
			}
			// resets the zoomOrder array to {0,0,0}

			if (KeyBinds.keyBinding1.isPressed()) {
				zoomOrder[0] = 1;
				return 1;
			} else if (KeyBinds.keyBinding2.isPressed()) {
				zoomOrder[0] = 2;
				return 2;
			} else if (KeyBinds.keyBinding3.isPressed()) {
				zoomOrder[0] = 3;
				return 3;
			}
			//After this if-statement the zoomOrder array first value zoomOrder[0] will be set to the first key pressed.
			// if no second key is pressed it will reset next time a key is pressed.

		} else if (keyTotNum() == 2) {
			// if two keys are pressed.

			if (zoomOrder[2] == 0) {
				// This indicates there was 1 key, then a second was pressed.

				zoomOrder[1] = keyNum() / zoomOrder[0];
				// add to the second order slot, see below for an explaination.

				return zoomOrder[0];
				// still output the first key. When a user lifts the first key it will be processed in keyTotNum() == 1, above.

			} else {
				//This is the case in which there were 3 keys pressed and someone lifts one of the two keys

				boolean found = false;

				for (int i = 0; i < 3; i++) {
					//Reorder loop

					if (!found) {
						found = zoomOrder[i] == 6 / keyNum();
					}
					//This found == true when the right key is found.
					//See key index algorithm to explain why.
					//Found is outside the for loop to make it save for the next iteration.


					if (i == 2) {
						//If the array is in the last slot

						zoomOrder[i] = 0;
						//Only two keys are pressed, empty the last slot.

						break;
						//Break to skip the "found conditions"

					}


					if (found) {
						zoomOrder[i] = zoomOrder[i + 1];
						//Reorganizes the slots after found.

					}
				}
				return zoomOrder[0];
				//return the new first position.

			}
		} else {
			//All three keys are pressed.

			if (zoomOrder[2] == 0) {
				//First time pressing the third key.

				zoomOrder[2] = 6 / (zoomOrder[0]*zoomOrder[1]);
			}

			return zoomOrder[0];
			//Return the first key pressed.
		}
		return 0;
		//failure return, meathods with values need to return something.
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
		//failure return, meathods with values need to return something.
	}

	public static int keyTotNum() {
		//Tells us how many keybings are pressed to use in zoomModifierNum()

		int num = 0;
		//cant use 1 here because we don't know which will be pressed!
		//we throw to a failure in zoomModifierNum() just in case!

		if (KeyBinds.keyBinding1.isPressed()) { ++num; }
		if (KeyBinds.keyBinding2.isPressed()) { ++num; }
		if (KeyBinds.keyBinding3.isPressed()) { ++num; }
		return num;
	}

	public static int keyNum() {
		//This function allows us to assign a number to each key press, and use math to determine which key has
		//been pressed when more than one has been pressed.

		//So for instance, when 1 and 2 have been pressed the output is 1*2=2. If I know the first key pressed
		//Which we store in zoomOrder[0], then I can find the second by doing keyNum() / zoomOrder[0]

		//If I know the first two as in zoomOrder[0] and zoomOrder[1], then I can find the third by
		// keyNum() / ( zoomOrder[0] + zoomOrder[1] )

		//I like this little algorithm, and it should cut down on complex if-statements.

		int num = 1;
		//We start with one to avoid multiplying by zero, and it is only activates if at least 1 key is pressed.

		if (KeyBinds.keyBinding2.isPressed()) { num = num * 2; }
		if (KeyBinds.keyBinding3.isPressed()) { num = num * 3; }
		return num;
		//Outputs the product of the key values.
	}

	public static void smooothDuuude(int zoomNum) {
		//Sooooooo smoooooth duuuuuuude
		//Sets and resets the smoothening filter that slows down the mouse.

		if (isZooming() && !MinecraftClient.getInstance().options.smoothCameraEnabled && smoothChecker(zoomNum)) {
			//If the zooming buttons are pressed, and it's not smooth, AND the smooth option is checked, SMOOOoooothhh it out.

			MinecraftClient.getInstance().options.smoothCameraEnabled = true;
		} else if ((!isZooming() && MinecraftClient.getInstance().options.smoothCameraEnabled) || !smoothChecker(zoomNum)) {
			//If the zooming buttons NOT , and it's smooth, OR the smooth option is unchecked, un--SMOOOoooothhh it out.

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
		//Failure state
	}
}