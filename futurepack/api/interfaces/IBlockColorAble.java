package futurepack.api.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.BlockPos;
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
	 * @return the new IBlockState (or the original state if not possible)
	 */
	@Nonnull
	public IBlockState setColor(World w, BlockPos pos, IBlockState state, EnumDyeColor col);
	
	/**
	 * Called if gravel or sand is inside the airbrush.
	 * @param w the World
	 * @param pos the Postion
	 * @param state the current block state
	 * @return the new IBlockState (or the original state if not possible)
	 */
	@Nonnull
	public IBlockState removeColor(World w, BlockPos pos, IBlockState state);

	
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface OnlyDefaultState
	{
		
	}
}
