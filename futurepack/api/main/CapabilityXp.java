package futurepack.api.main;

import java.util.concurrent.Callable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import futurepack.api.interfaces.ITileXpStorage;

public class CapabilityXp implements ITileXpStorage
{
	
	public static class Factory implements Callable<ITileXpStorage>
	{
		@Override
		public ITileXpStorage call() throws Exception 
		{
			return new CapabilityXp();
		}
		
	}
	
	public static class Storage implements IStorage<ITileXpStorage>
	{

		@Override
		public NBTBase writeNBT(Capability<ITileXpStorage> capability, ITileXpStorage instance, EnumFacing side)
		{
			return new NBTTagInt(instance.getXp());
		}

		@Override
		public void readNBT(Capability<ITileXpStorage> capability, ITileXpStorage instance, EnumFacing side, NBTBase nbt)
		{
			instance.setXp( ((NBTTagInt)nbt).getInt() );
		}
		
	}
	
	private int xp = 0;
	
	@Override
	public int getXp()
	{
		return xp;
	}

	@Override
	public int getMaxXp()
	{
		return 100;
	}

	@Override
	public void setXp(int lvl)
	{
		xp = lvl;
	}

}
