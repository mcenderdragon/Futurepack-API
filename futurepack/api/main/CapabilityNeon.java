package futurepack.api.main;

import java.util.concurrent.Callable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import futurepack.api.interfaces.ITileNeonEngine;

public class CapabilityNeon implements ITileNeonEngine
{
	public static class Factory implements Callable<ITileNeonEngine>
	{
		@Override
		public ITileNeonEngine call() throws Exception
		{
			return new CapabilityNeon();
		}
	}
	
	public static class Storage implements IStorage<ITileNeonEngine>
	{

		@Override
		public NBTBase writeNBT(Capability<ITileNeonEngine> capability, ITileNeonEngine instance, EnumFacing side)
		{
			return new NBTTagInt(instance.getPower());
		}

		@Override
		public void readNBT(Capability<ITileNeonEngine> capability, ITileNeonEngine instance, EnumFacing side, NBTBase nbt)
		{
			if(nbt instanceof NBTTagInt)
			{
				int power = ((NBTTagInt)nbt).getInt();
				
				if(power > instance.getPower())
				{
					instance.addPower(power - instance.getPower());
				}
				else if(power < instance.getPower())
				{
					instance.usePower(instance.getPower() - power);
				}
			}
		}

	}

	private int power = 0;
	public int maxpower = 100;
	
	@Override
	public int getPower()
	{
		return power;
	}

	@Override
	public int getMaxPower()
	{
		return maxpower;
	}

	@Override
	public boolean usePower(int e)
	{
		if(power -e >= 0)
		{
			power -= e;
			return true;
		}
		return false;
	}

	@Override
	public boolean addPower(int e)
	{
		if(power +e <= getMaxPower())
		{
			power += e;
			return true;
		}
		return false;
	}

	@Override
	public boolean needPowerFrom(ITileNeonEngine engine, EnumFacing side)
	{
		return getPower() < getMaxPower();
	}

	@Override
	public boolean canPowerTo(ITileNeonEngine engine, EnumFacing side)
	{
		return getType().getPreority() < engine.getType().getPreority();
	}

	@Override
	public EnumPowerMode getType()
	{
		return EnumPowerMode.USE;
	}

	@Deprecated
	@Override
	public void setPower(int e)
	{
		
	}

}
