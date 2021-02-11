package futurepack.api.capabilities;

public interface INeonEnergyStorage extends IEnergyStorageBase
{
	boolean canAcceptFrom(INeonEnergyStorage other);
	
	boolean canTransferTo(INeonEnergyStorage other);
	
	@Override
	default boolean canAcceptFrom(IEnergyStorageBase other)
	{
		return canAcceptFrom((INeonEnergyStorage)other);
	}
	
	@Override
	default boolean canTransferTo(IEnergyStorageBase other)
	{
		return canTransferTo((INeonEnergyStorage)other);
	}
	
}
