package com.nicoislost;

import com.nicoislost.inputs.KeyBinds;
import com.nicoislost.owo.ZoomOConfig;
import com.nicoislost.util.ModRegistries;
import net.fabricmc.api.ClientModInitializer;

import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ZoomO implements ClientModInitializer {
	public static final ZoomOConfig CONFIG = ZoomOConfig.createAndLoad();

	public static final Logger LOGGER = LoggerFactory.getLogger("modid");

	private static final int[] zoomOrder = {0,0,0};

	@Override
	public void onInitializeClient() {
		ModRegistries.registerModPackages();
	}

	public static boolean isZooming() {
		return KeyBinds.keyBinding1.isPressed() || KeyBinds.keyBinding2.isPressed() || KeyBinds.keyBinding3.isPressed();
	}

	public static int zoomModiferNum() {

		if (keyTotNum() == 0) {
			return 0;
		} else if (keyTotNum() == 1) {

			resetOrder();

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
		} else if (keyTotNum() == 2) {
			if (zoomOrder[2] == 0) {
				zoomOrder[1] = keyNum() / zoomOrder[0];
				return zoomOrder[0];
			} else {
				for (int i = 0; i < 3; i++) {

					boolean found = zoomOrder[i] == 6 / keyNum();

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
		} else {
			if (zoomOrder[2] == 0) {
				zoomOrder[2] = 6 / (zoomOrder[0]*zoomOrder[1]);
			}
			return zoomOrder[0];
		}
		return 0;
	}

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

	public static int keyTotNum() {
		int num = 0;
		if (KeyBinds.keyBinding1.isPressed()) { ++num; }
		if (KeyBinds.keyBinding2.isPressed()) { ++num; }
		if (KeyBinds.keyBinding3.isPressed()) { ++num; }
		return num;
	}

	public static int keyNum() {
		int num = 1;
		if (KeyBinds.keyBinding2.isPressed()) { num = num * 2; }
		if (KeyBinds.keyBinding3.isPressed()) { num = num * 3; }
		return num;
	}

	public static void resetOrder() {
		zoomOrder[0] = 0;
		zoomOrder[1] = 0;
		zoomOrder[2] = 0;
	}

	public static void smooothDuuude(int zoomNum) {
		if (isZooming() && !MinecraftClient.getInstance().options.smoothCameraEnabled && smoothChecker(zoomModiferNum())) {
			MinecraftClient.getInstance().options.smoothCameraEnabled = true;
		} else if ((!isZooming() && MinecraftClient.getInstance().options.smoothCameraEnabled) || !smoothChecker(zoomModiferNum())) {
			MinecraftClient.getInstance().options.smoothCameraEnabled = false;
		}
	}

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
}