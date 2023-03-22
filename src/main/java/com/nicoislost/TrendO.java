package com.nicoislost;

import com.nicoislost.owo.ZoomOConfig;
import com.nicoislost.util.ModRegistries;
import net.fabricmc.api.ClientModInitializer;

public class TrendO implements ClientModInitializer {

	public static final ZoomOConfig CONFIG = ZoomOConfig.createAndLoad();

	@Override
	public void onInitializeClient() {

		ModRegistries.registerModPackages();
	}

}