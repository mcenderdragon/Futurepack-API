package futurepack.api.interfaces.tilentity;

import net.minecraft.block.BlockState;

/**
 * Implement this in your TileEntity to  support holograms
 */
public interface ITileHologramAble
{
	/**
	 * @return the current state of the hologram to display
	 */
	public BlockState getHologram();
	
	/**
	 * @return true if the state is not null
	 */
	public boolean hasHologram();
	
	 /**
	  * @param state the BlockState to render
	  */
	public void setHologram(BlockState state);
	
}
