package com.nicoislost;

import com.nicoislost.command.Commands;
import com.nicoislost.inputs.KeyBinds;
import com.nicoislost.owo.ZoomConfig;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * The main class for the mod that initializes and handles a majority of the
 * logic.
 */
public class ZoomOMatic implements ClientModInitializer { // Mod initializer

	public static final Logger LOGGER = LoggerFactory.getLogger(ZoomOMatic.class);

	// We use owo-config to store the mod settings
	// this is the call to the configuration
	// https://docs.wispforest.io/owo/setup/
	public static final ZoomConfig CONFIG = ZoomConfig.createAndLoad();
	private static final ArrayList<Zooms> zoomStack = new ArrayList<>();

	@Override
	public void onInitializeClient() {

		KeyBinds.registerKeyBinds();

		ClientCommandRegistrationCallback.EVENT.register(
				(dispatcher, dedicated) -> Commands.register(dispatcher)
		);

		ClientTickEvents.START_CLIENT_TICK.register(client -> updateZoomStack());

	}

	/**
	 * @return If any keys are being pressed
	 */
	public static boolean isZooming() {
		return getActiveZoom() != Zooms.NONE;
	}

	/**
	 * @return The active zoom setting
	 */
	public static Zooms getActiveZoom() {
		if (zoomStack.isEmpty()) {
			return Zooms.NONE;
		}

		return zoomStack.get(zoomStack.size() - 1);
	}

	public static void updateZoomStack() {
		KeyBinds.getKeybindings().forEach(k -> {

			Zooms zoom = Zooms.fromKeyBinding(k);

			if (zoom != Zooms.NONE) {
				if (k.isPressed() && !zoomStack.contains(zoom))
					zoomStack.add(zoom);

				if (!k.isPressed())
					zoomStack.remove(zoom);
			}
		});
	}

}