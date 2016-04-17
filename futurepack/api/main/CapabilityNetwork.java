package futurepack.api.main;

import java.util.concurrent.Callable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import futurepack.api.FunkPacket;
import futurepack.api.interfaces.ITileNetwork;

public class CapabilityNetwork implements ITileNetwork
{
	public static class Factory implements Callable<ITileNetwork>
	{
		@Override
		public ITileNetwork call() throws Exception 
		{
			return new CapabilityNetwork();
		}	
	}
	
	public static class Storage implements IStorage<ITileNetwork>
	{
		@Override
		public NBTBase writeNBT(Capability<ITileNetwork> capability, ITileNetwork instance, EnumFacing side)
		{
			return null;
		}

		@Override
		public void readNBT(Capability<ITileNetwork> capability, ITileNetwork instance, EnumFacing side, NBTBase nbt)
		{
			
		}
	}
	
	@Override
	public boolean isNetworkAble()
	{
		return true;
	}

	@Override
	public boolean isWire()
	{
		return false;
	}

	@Override
	public void onFunkPacket(FunkPacket pkt) { }

}
