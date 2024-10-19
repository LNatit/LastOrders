package com.lnatit.lastorders.content;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import static com.lnatit.lastorders.content.ContentRegistry.CRC_BE_TYPE;

public class ClientRenderCrasherBlockEntity extends BlockEntity {
    public ClientRenderCrasherBlockEntity(BlockPos pos, BlockState blockState) {
        super(CRC_BE_TYPE.get(), pos, blockState);
    }

    
}
