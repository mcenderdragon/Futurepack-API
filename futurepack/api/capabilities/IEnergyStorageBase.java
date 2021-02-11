package futurepack.api.capabilities;

public interface IEnergyStorageBase
{
	/**
	 * @return the current amount of energy stored
	 */
	public int get();
	
	/**
	 * @return the max amount of energy stored
	 */
	public int getMax();
	
	/**
	 * @param used the amount of energy to use
	 * @return the actual used amount
	 */
	public int use(int used);
	
	/**
	 * @param added the amount of energy to add
	 * @return the actual added amount
	 */
	public int add(int added);
	
	/**
	 * @param other the target to transfer energy to.
	 * @return true if this can give energy to <b>other</b>
	 */
	public boolean canTransferTo(IEnergyStorageBase other);
	
	/**
	 * @param other the target to get energy from 
	 * @return true if this can accept energy from <b>other</b>
	 */
	public boolean canAcceptFrom(IEnergyStorageBase other);
	
	/**
	 * this determines if you get power and from who.
	 */
	public EnumEnergyMode getType();
	
	
	public static enum EnumEnergyMode
	{
		/**
		 * dont get power only send power
		 */
		PRODUCE(1),
		/**
		 * get only power from PRODUCE
		 */
		WIRE(2),
		/**
		 * gets power from everyone
		 */
		USE(3),
		/**
		 * this is as if you have not implemented the interface
		 */
		NONE(0);
		
		private EnumEnergyMode(int  p)
		{
			priority = p;
		}
		
		private int priority;
		
		public int getPriority()
		{
			return priority;
		}
	}
}
