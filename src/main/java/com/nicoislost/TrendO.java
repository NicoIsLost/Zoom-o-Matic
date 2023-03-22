package com.nicoislost;

import com.nicoislost.util.ModRegistries;
import net.fabricmc.api.ClientModInitializer;

public class TrendO implements ClientModInitializer {


	@Override
	public void onInitializeClient() {

		ModRegistries.registerModPackages();
	}

}