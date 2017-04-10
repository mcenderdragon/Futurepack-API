package futurepack.api.interfaces;

import net.minecraft.util.ResourceLocation;

/**
 * This is used in the BoardcomputerGui
 */
public interface IPlanet
{
	/**
	 * The id of the Dimension the spaceship will travel to.
	 */
	public int getDimenion();
	
	/**
	 * The Picture rendered if this Planet is displayed
	 */
	public ResourceLocation getTexture();
	
	/**
	 * The name of the Planet. Currently only in use for the registry.
	 */
	public String getName();
	
//	public String[] getRequiredResearches();
	
	/**
	 * @return the Names of the Ship-Upgrades needed to travel to this Planet. e.g. 'drive.FTL'
	 */
	public String[] getRequiredShipUpgrades();
	
	/**
	 * @return if the Player can breath freely on this planet. If not it is like you are under water.(So the air bar is visible and the bubbles decrease)
	 */
	public boolean hasBreathableAtmosphere();
}
