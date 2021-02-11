package futurepack.api.interfaces.tilentity;

import futurepack.api.EnumStateSpaceship;

/**
 * This is to determine if the given BoardComputer is good enough for a spaceship upgrade ({@link futurepack.api.interfaces.ISpaceshipUpgrade})
 */
public interface ITileBoardComputer
{
	/**
	 * Returns the status of the current spaceship.
	 */
	public boolean getState(EnumStateSpaceship state);
	
	/**
	 * Returns if this is the Advanced version of the BoardComputer
	 * for example the FTL drive need the advances version to work
	 */
	public boolean isAdvancedBoardComputer();
}
