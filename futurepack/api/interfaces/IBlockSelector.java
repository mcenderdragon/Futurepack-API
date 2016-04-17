package futurepack.api.interfaces;

import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import futurepack.api.ParentCoords;

/**
 * This Interface is Used to Select Big Formations of Blocks (Curently used for the {@link futurepack.common.FPSpaceShipSelector})
*/
public interface IBlockSelector
{
	/**
	 * This checks if a Block is walid
	 * @param w is the World
	 * @param j is the x Coord
	 * @param k is the y Coord
	 * @param l is the z Coord
	 * @param m is the Material 
	 * @param dia is the boolean if the Block is Square 
	 * @return if the Block is Valid
	*/
	boolean isValidBlock(World w,BlockPos pos, Material m, boolean diagonal, ParentCoords parent);
	
	/**
	 * @return if the Block is the End or Not 
	*/
	boolean canContinue(World w,BlockPos pos, Material m, boolean diagonal, ParentCoords parent);
}