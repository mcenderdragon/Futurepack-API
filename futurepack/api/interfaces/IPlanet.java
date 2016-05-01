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
	public String[] getReqiredShipUpgrades();
}
