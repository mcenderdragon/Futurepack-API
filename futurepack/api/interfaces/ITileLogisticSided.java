package futurepack.api.interfaces;

import net.minecraft.util.EnumFacing;
import futurepack.api.EnumLogistic;
import futurepack.api.EnumLogisticMode;

public interface ITileLogisticSided
{
	public EnumLogistic getModeForFace(EnumFacing face, EnumLogisticMode mode);
	
	/**
	 * @param face the Side of the Block to change
	 * @param log the Mode for this side (see {@link EnumLogistic})
	 * @param mode the part that is changed (see {@link EnumLogisticMode})
	 * @return true on Success 
	 */
	public boolean setModeForFace(EnumFacing face, EnumLogistic log, EnumLogisticMode mode);
	
	/**
	 * @return an sorted Arrays (so it can be used with Arrays.binarySearch!)
	 */
	public EnumLogisticMode[] getLogisticModes();
	
}
