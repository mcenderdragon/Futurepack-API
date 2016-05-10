package futurepack.api.interfaces;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import futurepack.depend.api.interfaces.ISpaceshipUpgrade;

public interface ISpaceshipSelector
{
	public int getBlockTotal();
	
	public boolean hasUpgrade(ISpaceshipUpgrade up);
	
	/**
	 * This will be passed on the the method with 3 ints
	 */
	public boolean isInArea(BlockPos pos);
	
	/**
	 *  Checks if the Blocks are inside of the sx,sz and ex,ez and checks the HighMap
	 */
	public boolean isInArea(int x, int y, int z);
	
	/**
	 * Checks if the Blocks are inside of the Above Created High Map 
	 * @param x is the relative pos to the start of this spaceship
	 * @param y is the world y high
	 * @param z is the relative pos to the start of this spaceship
	*/
	public boolean isInHeight(int x, int y, int z);
	
	/**
	 * @return the maximal width of the spaceship
	 */
	public int getWidth();
	
	/**
	 * @return the maximal height of the spaceship
	 */
	public int getHeight();
	
	/**
	 * @return the maximal depth of the spaceship
	 */
	public int getDepth();
	
	/**
	 * @return the World the spaceship is currently in
	 */
	public World getWorld();
	
	/**
	 * @return the minimum start position of the spaceship (is if there were a box around it)
	 */
	public BlockPos getStart();
	
	/**
	 * @return the maximum end position of the spaceship (is if there were a box around it)
	 */
	public BlockPos getEnd();
	
	/**
	 * @return the selector holding the block coordinates
	 * 
	 */
	public IBlockSelector getSelector();
}
