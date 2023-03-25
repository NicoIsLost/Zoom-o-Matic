package com.nicoislost;

import com.nicoislost.inputs.KeyBinds;
import com.nicoislost.owo.ZoomOConfig;
import com.nicoislost.util.ModRegistries;
import net.fabricmc.api.ClientModInitializer;


public class ZoomO implements ClientModInitializer {

	public static final ZoomOConfig CONFIG = ZoomOConfig.createAndLoad();

	@Override
	public void onInitializeClient() {
		ModRegistries.registerModPackages();
	}

	public static boolean isZooming() {
		if(KeyBinds.keyBinding1.isPressed() || KeyBinds.keyBinding2.isPressed() || KeyBinds.keyBinding3.isPressed()) {return true;}
		return false;
	}

	public static double zoomModifer() {
		if(KeyBinds.keyBinding1.isPressed()) {
			return (double)CONFIG.Zoom1();
		} else if (KeyBinds.keyBinding2.isPressed()) {
			return (double)CONFIG.Zoom2();
		} else if (KeyBinds.keyBinding3.isPressed()) {
			return (double)CONFIG.Zoom3();
		}
		return 0;
	}
}