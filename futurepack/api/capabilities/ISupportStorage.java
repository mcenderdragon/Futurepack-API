package futurepack.api.capabilities;

public interface ISupportStorage extends IEnergyStorageBase, Runnable
{
	
	/**
	 * Only called for wires if the get powered
	 */
	public void support();

	boolean canAcceptFrom(ISupportStorage other);
	
	boolean canTransferTo(ISupportStorage other);
	
	@Override
	default boolean canAcceptFrom(IEnergyStorageBase other)
	{
		return canAcceptFrom((ISupportStorage)other);
	}
	
	@Override
	default boolean canTransferTo(IEnergyStorageBase other)
	{
		return canTransferTo((ISupportStorage)other);
	}
	
	@Override
	default void run()
	{
		support();
	}
}
