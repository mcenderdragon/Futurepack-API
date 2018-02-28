package futurepack.api.interfaces;

import javax.annotation.Nullable;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public interface IDeepCoreLogic
{	
	public EnumFacing getFacing();
		
	public TileEntity getTileEntity();
		
	public default boolean isRiftReady()
	{
		return getRift() != null;
	}
		
	@Nullable
	public TileEntity getRift();	

	public int getTotalChestSize();
	
	public float getProgress();
	
	public void setProgress(float f);
}
