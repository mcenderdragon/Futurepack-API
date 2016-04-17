package futurepack.api.main;

import java.util.concurrent.Callable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import futurepack.api.helper.HelperHologram;
import futurepack.api.interfaces.ITileHologramAble;

public class CapabilityHologram implements ITileHologramAble
{

	public static class Factory implements Callable<ITileHologramAble>
	{
		@Override
		public CapabilityHologram call() throws Exception 
		{
			return new CapabilityHologram();
		}
		
	}
	
	public static class Storage implements IStorage<ITileHologramAble>
	{

		@Override
		public NBTBase writeNBT(Capability<ITileHologramAble> capability, ITileHologramAble instance, EnumFacing side)
		{
			if(instance.hasHologram())
			{
				return HelperHologram.toNBT(instance.getHologram());
			}
			return null;
		}

		@Override
		public void readNBT(Capability<ITileHologramAble> capability, ITileHologramAble instance, EnumFacing side, NBTBase nbt)
		{
			instance.setHologram(HelperHologram.fromNBT((NBTTagCompound) nbt));
		}
		
	}
	
	
	IBlockState render;
	
	@Override
	public IBlockState getHologram()
	{
		return render;
	}

	@Override
	public boolean hasHologram()
	{
		return render!=null;
	}

	@Override
	public void setHologram(IBlockState state)
	{
		render = state;
	}

}
