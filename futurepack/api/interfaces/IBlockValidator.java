package futurepack.api.interfaces;

import net.minecraft.world.World;
import futurepack.api.ParentCoords;

/**
 * Used in the {@link futurepack.api.interfaces.ISelector}
 */
public interface IBlockValidator
{
	
	/**
	 * @param w the current Wolrd
	 * @param pos the BLock Position
	 * @return id this Block is that kind of Block you are looking for.
	 */
	public boolean isValidBlock(World w, ParentCoords pos);
}
