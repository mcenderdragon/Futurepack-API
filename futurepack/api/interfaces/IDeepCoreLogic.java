package futurepack.api.interfaces;

import javax.annotation.Nullable;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;

/**
 * This interface is to interact with a working DeepCoreMultilock
 */
public interface IDeepCoreLogic
{	
	/**
	 *  The DeepCore is rotatable. 
	 * @return the current facing of the multiblock
	 */
	public Direction getFacing();
		
	/**
	 * @return The main {@link TileEntity}
	 */
	public TileEntity getTileEntity();
		
	/**
	 * @return true if the rift is ready/available, otherwise false.
	 */
	public default boolean isRiftReady()
	{
		return getRift() != null;
	}
		
	/**
	 * @return The Rift tileEntity, can be null
	 */
	@Nullable
	public TileEntity getRift();	

	/**
	 * Return the amount of slots the intern storage has
	 * It is planned for the future to support every chest so you can use (up to 3) Diamond Chest to expand the intern storage
	 * @return the total slot size
	 */
	public int getTotalChestSize();
	
	/**
	 * The progress of the curret work circle
	 * @return a float between 0 and 1.0
	 */
	public float getProgress();
	
	/**
	 * Sets the progress  (used for syncronisation)
	 * @param f a float between 0 and 1
	 */
	public void setProgress(float f);
}
