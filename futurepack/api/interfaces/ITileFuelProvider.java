package futurepack.api.interfaces;

/**
 * Used for spaceship Thrusters to power them.
 */
public interface ITileFuelProvider
{
	/**
	 * @return the currently stored fuel amount
	 */
	public int getFuel();
	
	/** 
	 * @return the maximal storabale fuel amount
	 */
	public int getMaxFuel();
	
	/**
	 * @param amount the amount to use from this tank
	 * @return true on success
	 */
	public boolean useFuel(int amount);
	
	/**
	 * @param amount the amount to add to this tank
	 * @return true on success
	 */
	public boolean addFuel(int amount);
}
