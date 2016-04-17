package futurepack.api.interfaces;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * Used for AirBrush Block Handling
 * 
 * @author MCenderdragon
 *
 */
public interface IBlockColorAble
{
	public IBlockState setColor(World w, BlockPos pos, IBlockState state, EnumDyeColor col);
	
	public IBlockState removeColor(World w, BlockPos pos, IBlockState state);

	
}
