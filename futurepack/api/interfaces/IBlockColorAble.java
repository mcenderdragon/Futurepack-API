package futurepack.api.interfaces;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * Used for AirBrush Block Handling. Just implement this in you Block and it will get colored.
 * 
 * @author MCenderdragon
 *
 */
public interface IBlockColorAble
{
	/**
	 * Called if you right click the Block with AirBrush (a color tank must be inside)
	 * @param w the World
	 * @param pos the Postion of the Block
	 * @param state the current block state
	 * @param col the color inside the airbrush
	 * @return the new blockstate (not null just pass the state)
	 */
	public IBlockState setColor(World w, BlockPos pos, IBlockState state, EnumDyeColor col);
	
	/**
	 * Called if gravel or sand is inside the airbrush.
	 * @param w the World
	 * @param pos the Postion
	 * @param state the current block state
	 * @return the new blockstate (not null, just pass the state)
	 */
	public IBlockState removeColor(World w, BlockPos pos, IBlockState state);

	
}
