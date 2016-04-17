package futurepack.api.main;

import java.util.concurrent.Callable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import futurepack.api.EnumLogistic;
import futurepack.api.EnumLogisticMode;
import futurepack.api.LogisticStorage;
import futurepack.api.interfaces.ITileLogisticSided;

public class CapabilityLogistic implements ITileLogisticSided
{
	public static class Factory implements Callable<CapabilityLogistic>
	{
		@Override
		public CapabilityLogistic call() throws Exception
		{
			return new CapabilityLogistic();
		}
	}
	
	public static class Storage implements IStorage<ITileLogisticSided>
	{

		@Override
		public NBTBase writeNBT(Capability<ITileLogisticSided> capability, ITileLogisticSided instance, EnumFacing side)
		{
			
			return null;
		}

		@Override
		public void readNBT(Capability<ITileLogisticSided> capability, ITileLogisticSided instance, EnumFacing side, NBTBase nbt)
		{
			
		}
		
	}
	
	private LogisticStorage storage;
	
	public CapabilityLogistic()
	{
		storage = new LogisticStorage(this);
	}
	
	@Override
	public EnumLogistic getModeForFace(EnumFacing face, EnumLogisticMode mode)
	{
		return storage.getModeForFace(face, mode);
	}

	@Override
	public boolean setModeForFace(EnumFacing face, EnumLogistic log, EnumLogisticMode mode)
	{
		return storage.setModeForFace(face, log, mode);
	}

	@Override
	public EnumLogisticMode[] getLogisticModes()
	{
		return storage.getLogisticModes();
	}

}
