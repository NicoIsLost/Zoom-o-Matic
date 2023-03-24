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

	public static boolean isZooming1() {
		if(KeyBinds.keyBinding1.isPressed()) {return true;}
		return false;
	}
	public static boolean isZooming2() {
		if(KeyBinds.keyBinding2.isPressed()) {return true;}
		return false;
	}
	public static boolean isZooming3() {
		if(KeyBinds.keyBinding3.isPressed()) {return true;}
		return false;
	}

}