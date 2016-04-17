package futurepack.api.interfaces;

/**
 * Used for spaceship Thrusters to power them.
 */
public interface ITileFuelProvider
{
	public int getFuel();
	
	public int getMaxFuel();
	
	public boolean useFuel(int amount);
	
	public boolean addFuel(int amount);
}
