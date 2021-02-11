package futurepack.api.interfaces;

import futurepack.api.interfaces.tilentity.ITileBoardComputer;
import net.minecraft.tileentity.TileEntity;

/**
 * Register in {@link futurepack.api.FPApiMain#registerShipUpgrade(ISpaceshipUpgrade)}
 */
public interface ISpaceshipUpgrade
{
	/**
	 * @return the name of this upgrade. THis name will be used too link it with other Planets.
	 */
	public String getTranslationKey();
	
	/**
	 * Normally ship-upgrades are Blocks.
	 * @param sel this give you the power to check the Blocks of the Spaceship.
	 * @return if the upgrade is installed or not
	 */
	public boolean isUpgradeInstalled(ISpaceshipSelector sel);
	
	/**
	 * 
	 * @param tile this is the BoardComputer. It is also a {@link TileEntity}
	 * @return if the BoardComputer is powerfull enough to handle the upgrade
	 */
	public boolean isBoardComputerValid(ITileBoardComputer tile);
	
	
}
