package com.nicoislost;

import com.nicoislost.keybinds.KeyBinds;
import net.fabricmc.api.ClientModInitializer;

public class TrendO implements ClientModInitializer {


	@Override
	public void onInitializeClient() {

		KeyBinds.RegisterKeyBinds();
	}

}