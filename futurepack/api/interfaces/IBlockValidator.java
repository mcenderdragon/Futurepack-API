package futurepack.api.interfaces;

import futurepack.api.ParentCoords;
import net.minecraft.world.World;

/**
 * Used in the {@link futurepack.api.interfaces.ISelector}
 */
public interface IBlockValidator
{
	
	/**
	 * @param w the current Wolrd
	 * @param pos the Blocks Position
	 * @return id this Block is that kind of Block you are looking for.
	 */
	public boolean isValidBlock(World w, ParentCoords pos);
}
