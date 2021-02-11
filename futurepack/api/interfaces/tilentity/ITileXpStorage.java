package futurepack.api.interfaces.tilentity;

/**
 * This is used by the T3 modul for storing XP
 */
public interface ITileXpStorage
{
	/**
	 * @return The currently stored xp.
	 */
	public int getXp();
	
	/**
	 * @return The maximal amount of storable xp.
	 */
	public int getMaxXp();
	
	/**
	 * Sets the xp stored
	 * @param lvl the xp value to store
	 */
	public void setXp(int lvl);
}
