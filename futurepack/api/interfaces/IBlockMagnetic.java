package futurepack.api.interfaces;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Implemenet this in your block for it to get detected by the EScanner
 */
public interface IBlockMagnetic 
{
	/**
	 * @param w the World
	 * @param pos the Block Position in the World
	 * @param state the sate of the block
	 * @return THe magnetic intensitity, (magnetite ore has 4)
	 */
	public double getMagnetIntesity(World w, BlockPos pos, IBlockState state);
}
