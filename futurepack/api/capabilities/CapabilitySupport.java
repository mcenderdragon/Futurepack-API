package futurepack.api.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.INBTSerializable;


public class CapabilitySupport extends EnergyStorageBase implements ISupportStorage, INBTSerializable<CompoundNBT>
{
	@CapabilityInject(ISupportStorage.class)
	public static final Capability<ISupportStorage> cap_SUPPORT = null;
	
	public static class Storage implements IStorage<ISupportStorage>
	{

		@Override
		public INBT writeNBT(Capability<ISupportStorage> capability, ISupportStorage instance, Direction side)
		{
			return IntNBT.valueOf(instance.get());
		}

		@Override
		public void readNBT(Capability<ISupportStorage> capability, ISupportStorage instance, Direction side, INBT nbt)
		{
			int sup = ((IntNBT)nbt).getInt();
			if(sup > instance.get())
			{
				instance.add(sup - instance.get());
			}
			else if(sup < instance.get())
			{
				instance.add(instance.get() - sup);
			}
		}
		
	}

	public CapabilitySupport()
	{
		super(128, EnumEnergyMode.USE);
	}
	
	public CapabilitySupport(int max, EnumEnergyMode mode)
	{
		super(max, mode);
	}

	@Override
	public void support() { }

	
	@Override
	public boolean canTransferTo(ISupportStorage other)
	{
		return getType().getPriority() < other.getType().getPriority();
	}

	@Override
	public boolean canAcceptFrom(ISupportStorage other)
	{
		return get() < getMax();
	}
	
	@Override
	public CompoundNBT serializeNBT()
	{
		CompoundNBT nbt = new CompoundNBT();
		nbt.putInt("s", get());
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt)
	{
		energy = nbt.getInt("s");
	}
}
