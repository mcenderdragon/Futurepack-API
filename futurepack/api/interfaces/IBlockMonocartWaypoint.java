package futurepack.api.interfaces;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IBlockMonocartWaypoint
{
	public String getName(World w, BlockPos pos, IBlockState state);
	
	public boolean isWaypoint(World w, BlockPos pos, IBlockState state);
}
