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

	public boolean zooming() {
		if(KeyBinds.keyBinding1.isPressed() || KeyBinds.keyBinding2.isPressed() || KeyBinds.keyBinding2.isPressed() ) {
			return true;
		}
		return false;
	}

}