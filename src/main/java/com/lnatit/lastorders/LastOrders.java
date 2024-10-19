package com.lnatit.lastorders;

import com.lnatit.lastorders.content.ContentRegistry;
import com.mojang.logging.LogUtils;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.renderer.GameRenderer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(LastOrders.MOD_ID)
public class LastOrders {
    public static final String MOD_ID = "lastorders";

    public static final Logger LOGGER = LogUtils.getLogger();

    public LastOrders(IEventBus modBus) {
        ContentRegistry.registerAll(modBus);
    }
}
