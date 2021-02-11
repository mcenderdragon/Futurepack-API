package futurepack.api.interfaces;

import net.minecraft.util.ResourceLocation;

/**
 * This is used in the BoardcomputerGui
 * Registered in {@link futurepack.api.FPApiMain#registerPlanet(IPlanet)}
 */
public interface IPlanet
{
	/**
	 * The id of the Dimension the spaceship will travel to.
	 */
	public ResourceLocation getDimenionId();
	
//	default DimensionType getDimensionType()
//	{
//		return null;//TODO DimensionType.field_241497_i_.byName(getDimenionId());
//	}
	
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
	
	/**
	 * Only for planets with non breathable-atmosphere for the oxygen simulation.
	 * spread: air will "leak" to other blocks
	 * @return the speed in m/s, 0.0 - 20.0
	 */
	public default float getSpreadVelocity()
	{
		return 1F;
	}
	
	/**
	 * The gravity based speed value at which the oxygen will fly/sink  into the rest of the atmosphere
	 * Negative: air will sink to the ground; positive: it will fly to the sky
	 * @return the speed in m/s, -20.0 - 20.0
	 */
	public default float getGravityVelocity()
	{
		return -0.1F;
	}
}
