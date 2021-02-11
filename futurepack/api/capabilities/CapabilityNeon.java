package futurepack.api.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.INBTSerializable;


public class CapabilityNeon extends EnergyStorageBase implements INeonEnergyStorage, INBTSerializable<CompoundNBT>
{	
	@CapabilityInject(INeonEnergyStorage.class)
	public static final Capability<INeonEnergyStorage> cap_NEON = null;
	
	public static class Storage implements IStorage<INeonEnergyStorage>
	{

		@Override
		public INBT writeNBT(Capability<INeonEnergyStorage> capability, INeonEnergyStorage instance, Direction side)
		{
			return IntNBT.valueOf(instance.get());
		}

		@Override
		public void readNBT(Capability<INeonEnergyStorage> capability, INeonEnergyStorage instance, Direction side, INBT nbt)
		{
			if(nbt instanceof IntNBT)
			{
				int power = ((IntNBT)nbt).getInt();
				
				if(power > instance.get())
				{
					instance.add(power - instance.get());
				}
				else if(power < instance.get())
				{
					instance.use(instance.get() - power);
				}
			}
		}

	}

	public CapabilityNeon()
	{
		this(100, EnumEnergyMode.USE);
	}
	
	public CapabilityNeon(int maxpower, EnumEnergyMode mode)
	{
		super(maxpower, mode);
	}


	@Override
	public boolean canTransferTo(INeonEnergyStorage other)
	{
		return getType().getPriority() < other.getType().getPriority();
	}

	@Override
	public boolean canAcceptFrom(INeonEnergyStorage other)
	{
		return get() <= getMax();
	}

	@Override
	public CompoundNBT serializeNBT()
	{
		CompoundNBT nbt = new CompoundNBT();
		nbt.putInt("p", get());
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt)
	{
		this.energy = nbt.getInt("p");
	}
}
