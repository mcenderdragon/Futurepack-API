package futurepack.api.capabilities;

import java.util.Arrays;

import futurepack.api.EnumLogisticIO;
import futurepack.api.EnumLogisticType;
import futurepack.api.LogisticStorage;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class CapabilityLogistic implements ILogisticInterface
{
	@CapabilityInject(ILogisticInterface.class)
	public static final Capability<ILogisticInterface> cap_LOGISTIC = null;
	
	
	public static class Storage implements IStorage<ILogisticInterface>
	{

		@Override
		public INBT writeNBT(Capability<ILogisticInterface> capability, ILogisticInterface instance, Direction side)
		{
			
			return null;
		}

		@Override
		public void readNBT(Capability<ILogisticInterface> capability, ILogisticInterface instance, Direction side, INBT nbt)
		{
			
		}
		
	}
	
	private LogisticStorage storage;
	
	public CapabilityLogistic()
	{
		storage = new LogisticStorage(this::onLogisticChange);
	}

	@Override
	public EnumLogisticIO getMode(EnumLogisticType mode)
	{
		return storage.getModeForFace(Direction.UP, mode);
	}

	@Override
	public boolean setMode(EnumLogisticIO log, EnumLogisticType mode)
	{
		return storage.setModeForFace(Direction.UP, log, mode);
	}

	@Override
	public boolean isTypeSupported(EnumLogisticType type)
	{
		return Arrays.binarySearch(storage.getLogisticModes(), type) >= 0;
	}
	
	public void onLogisticChange(Direction face, EnumLogisticType log)
	{
		
	}

}
