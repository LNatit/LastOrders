package com.lnatit.lastorders.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import static com.lnatit.lastorders.LastOrders.MOD_ID;

public class OrderPayload implements CustomPacketPayload {
    public static final Type<OrderPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(MOD_ID, "order"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
