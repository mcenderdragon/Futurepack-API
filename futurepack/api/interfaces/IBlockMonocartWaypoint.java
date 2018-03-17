package futurepack.api.interfaces;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Implement this in a Block to make it a monorail waypoint
 */
public interface IBlockMonocartWaypoint
{
	/**
	 * @param w the World
	 * @param pos the position of the b lock
	 * @param state the current blockstate
	 * @return THe name of this waypoint
	 */
	public String getName(World w, BlockPos pos, IBlockState state);
	
	/**
	 * @param w the World
	 * @param pos the position of the b lock
	 * @param state the current blockstate
	 * @return if this is realy a waypoint
	 */
	public boolean isWaypoint(World w, BlockPos pos, IBlockState state);
}
