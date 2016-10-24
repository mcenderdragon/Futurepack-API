package futurepack.api.interfaces;

import java.util.Collection;

import futurepack.api.ParentCoords;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public interface ISelector
{
	/**
	 * It will iterate over every found Block and if the Block is valid it will add to the Collection.
	 * @param valid checks if the Block is valid or not.
	 * @return a Collection of all found ParentCoords.
	 */
	public Collection<ParentCoords> getValidBlocks(IBlockValidator valid);
	
	/**
	 * @return the World this selector had used.
	 */
	public World getWorld();
	
	/**
	 * @param b the Block type
	 * @return the total of this Blocks selected
	 */
	public int getBlockCount(Block b);
	
	
}
