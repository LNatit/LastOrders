package com.lnatit.lastorders.content;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.lnatit.lastorders.LastOrders.MOD_ID;

public class ContentRegistry {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BE_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MOD_ID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static final DeferredBlock<ClientRenderCrasherBlock> CRC_BLOCK = BLOCKS.register("render_crasher", ClientRenderCrasherBlock::new);
    public static final DeferredBlock<ClientLogicCrasherBlock> CLC_BLOCK = BLOCKS.register("logic_crasher", ClientLogicCrasherBlock::new);

    public static final DeferredItem<BlockItem> CRC_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(CRC_BLOCK);
    public static final DeferredItem<BlockItem> CLC_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(CLC_BLOCK);

    public static final Supplier<BlockEntityType<ClientRenderCrasherBlockEntity>> CRC_BE_TYPE = BE_TYPES.register(
            "render_crasher",
            () -> BlockEntityType.Builder.of(
                    ClientRenderCrasherBlockEntity::new,
                    CRC_BLOCK.get()
            ).build(null)
    );

//    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MOD_TAB = TABS.

    public static void registerBlockEntityRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(CRC_BE_TYPE.get(), context -> new ClientRenderCrasherBlockEntityRenderer());
    }

    public static void registerAll(IEventBus modBus) {
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        BE_TYPES.register(modBus);
        TABS.register(modBus);
        modBus.addListener(ContentRegistry::registerBlockEntityRenderer);
    }
}
