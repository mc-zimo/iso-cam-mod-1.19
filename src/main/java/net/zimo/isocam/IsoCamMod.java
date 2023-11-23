package net.zimo.isocam;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IsoCamMod implements ModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("isocam");

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
	}
}
