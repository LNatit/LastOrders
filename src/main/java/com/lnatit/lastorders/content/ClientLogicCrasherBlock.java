package com.lnatit.lastorders.content;

import com.mojang.blaze3d.Blaze3D;
import com.mojang.serialization.MapCodec;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.core.BlockPos;
import net.minecraft.util.NativeModuleLister;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class ClientLogicCrasherBlock extends Block {
    public static final String INFO = "Manually triggered crash on client logic thread";

    private ClientLogicCrasherBlock(Properties properties) {
        super(properties);
    }

    public ClientLogicCrasherBlock() {
        this(BlockBehaviour.Properties.of());
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        CrashReport crashreport = new CrashReport(INFO, new Throwable(INFO));
        CrashReportCategory crashreportcategory = crashreport.addCategory("Manual crash details");
        NativeModuleLister.addCrashSection(crashreportcategory);
        throw new ReportedException(crashreport);
    }
}
