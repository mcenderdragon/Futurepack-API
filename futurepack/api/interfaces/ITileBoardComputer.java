package futurepack.api.interfaces;

import futurepack.api.EnumStateSpaceship;
import futurepack.depend.api.interfaces.ISpaceshipUpgrade;

/**
 * This is to determine if the given BoardComputer is good enough for a spaceship upgrade ({@link futurepack.depend.api.interfaces.ISpaceshipUpgrade})
 */
public interface ITileBoardComputer
{
	/**
	 * Returns the status of the current spaceship.
	 */
	public EnumStateSpaceship getState();
	
	/**
	 * Returns if this is the Advanced version of the BoardComputer
	 * for example the FTL drive need the advances version to work
	 */
	public boolean isAdvancedBoardComputer();
}
