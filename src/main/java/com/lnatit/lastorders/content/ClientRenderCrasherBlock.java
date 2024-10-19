package com.lnatit.lastorders.content;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ClientRenderCrasherBlock extends BaseEntityBlock {
    public static final MapCodec<ClientRenderCrasherBlock> CODEC = simpleCodec(ClientRenderCrasherBlock::new);

    private ClientRenderCrasherBlock(Properties properties) {
        super(properties);
    }

    public ClientRenderCrasherBlock() {
        this(BlockBehaviour.Properties.of());
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}
