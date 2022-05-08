package com.mz.mobaspects.aspect.goal;

import com.mz.mobaspects.util.Utils;
import net.minecraft.block.AirBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

import java.util.List;
import java.util.stream.Collectors;

public class PlantBerryGoal extends UseAbilityGoal {

    public PlantBerryGoal(MobEntity mobEntity) {
        super(mobEntity);
    }

    @Override
    public boolean shouldExecute() {
        if (this.mobEntity.getEntityWorld().getDifficulty() == Difficulty.PEACEFUL){
            return false;
        }
        return true;
    }

    @Override
    public void useAbility() {
        BlockPos posBelow = mobEntity.getPosition().down();
        BlockState bsBelow = mobEntity.getEntityWorld().getBlockState(posBelow);
        if(!bsBelow.getMaterial().isSolid()){
            return;
        }

        BlockPos posCurrent = mobEntity.getPosition();
        BlockState bsCurrent = mobEntity.getEntityWorld().getBlockState(posCurrent);

        if(bsCurrent.getBlock() instanceof AirBlock) {
            mobEntity.getEntityWorld()
                    .setBlockState(posCurrent,
                            Blocks.SWEET_BERRY_BUSH.getDefaultState().with(SweetBerryBushBlock.AGE, 2),
                            2);
        }
    }

    /*
    public void tryPlant(ItemStack stack, BlockPos blockPos, World world)
    {
        // IBlockState state = getPlant(stack, world, plantPosition);
        BlockState blockstate = world.getBlockState(blockPos);
        blockstate.on();
        boolean flag = blockstate.getMaterial().blocksMovement();
        boolean flag1 = blockstate.getFluidState().isTagged(FluidTags.WATER);

        if(canPlace(state, plantPosition, world))
        {
            world.setBlockState(plantPosition, state);
            state.getBlock().onBlockPlacedBy(world, plantPosition, state, null, stack);
        }
    }

    public boolean canPlace(BlockState state, BlockPos pos, World world)
    {
        boolean flag2 = state.getMaterial().isSolid();
        boolean flag3 = world.isAirBlock(pos);
        boolean flag4 = !isBlockBlacklisted(state.getBlock());
        return flag2 && flag3 && flag4;
    }
    */
}