package futurepack.api.interfaces;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public interface IBlockMagnetic 
{
	public double getMagnetIntesity(World w, BlockPos pos, IBlockState state);
}
