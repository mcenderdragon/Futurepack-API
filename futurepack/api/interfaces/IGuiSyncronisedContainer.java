package futurepack.api.interfaces;

import net.minecraft.nbt.NBTTagCompound;

public interface IGuiSyncronisedContainer 
{
	public void writeToNBT(NBTTagCompound nbt);
	
	public void readFromNBT(NBTTagCompound nbt);
}
