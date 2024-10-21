package com.lnatit.lastorders.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.lnatit.lastorders.client.Orderer;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    @WrapOperation(
            method = "runTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/GameRenderer;render(Lnet/minecraft/client/DeltaTracker;Z)V"
            )
    )
    private void catchRenderExceptions(GameRenderer instance, DeltaTracker deltaTracker, boolean renderLevel, Operation<Void> original) {
        try {
            original.call(instance, deltaTracker, renderLevel);
        } catch (Throwable throwable) {
            Orderer.acceptRender(throwable);
        }

    }

    @WrapOperation(
            method = "runTick",
            at = @At(
                    value = "INVOKE",
                    target = "")
    )
}
