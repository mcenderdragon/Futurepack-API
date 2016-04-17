package futurepack.api.main;

import java.util.concurrent.Callable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import futurepack.api.interfaces.ITileSupportAble;

public class CapabilitySupport implements ITileSupportAble
{
	public static class Factory implements Callable<ITileSupportAble>
	{
		@Override
		public ITileSupportAble call() throws Exception
		{
			return new CapabilitySupport();
		}
	}
	
	public static class Storage implements IStorage<ITileSupportAble>
	{

		@Override
		public NBTBase writeNBT(Capability<ITileSupportAble> capability, ITileSupportAble instance, EnumFacing side)
		{
			return new NBTTagInt(instance.getSupport());
		}

		@Override
		public void readNBT(Capability<ITileSupportAble> capability, ITileSupportAble instance, EnumFacing side, NBTBase nbt)
		{
			int sup = ((NBTTagInt)nbt).getInt();
			if(sup > instance.getSupport())
			{
				instance.addSupport(sup - instance.getSupport());
			}
			else if(sup < instance.getSupport())
			{
				instance.useSupport(instance.getSupport() - sup);
			}
		}
		
	}
	
	private int support = 0;
	public int maxsupport = 127;
	
	@Override
	public boolean isSupportAble(EnumFacing side)
	{
		return true;
	}

	@Override
	public EnumSupportType getSupportType()
	{
		return EnumSupportType.USER;
	}

	@Override
	public void addSupport(int i)
	{
		support += i;
		if(support > getMaxSupport())
			support = getMaxSupport();
	}

	@Override
	public void useSupport(int i)
	{
		support -= i;
		if(support < 0)
			support = 0;
	}

	@Override
	public int getSupport()
	{
		return support;
	}

	@Override
	public int getMaxSupport()
	{
		return maxsupport;
	}

	@Override
	public boolean needSupport()
	{
		return getSupport() < getMaxSupport();
	}

	@Override
	public void support() { }

}
