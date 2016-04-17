package futurepack.api.interfaces;

import net.minecraft.block.state.IBlockState;

public interface ITileHologramAble
{
	public IBlockState getHologram();
	
	public boolean hasHologram();
	
	public void setHologram(IBlockState state);
	
}
