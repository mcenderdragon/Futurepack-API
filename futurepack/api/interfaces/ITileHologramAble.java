package futurepack.api.interfaces;

import net.minecraft.block.state.IBlockState;

/**
 * Implement this in your TileEntity to  support holograms
 */
public interface ITileHologramAble
{
	/**
	 * @return the current state of the hologram to display
	 */
	public IBlockState getHologram();
	
	/**
	 * @return true if the state is not null
	 */
	public boolean hasHologram();
	
	 /**
	  * @param state the BlockState to render
	  */
	public void setHologram(IBlockState state);
	
}
