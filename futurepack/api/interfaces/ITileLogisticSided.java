package futurepack.api.interfaces;

import futurepack.api.EnumLogisticIO;
import futurepack.api.EnumLogisticType;
import net.minecraft.util.EnumFacing;

public interface ITileLogisticSided
{
	public EnumLogisticIO getModeForFace(EnumFacing face, EnumLogisticType mode);
	
	/**
	 * @param face the Side of the Block to change
	 * @param log the Mode for this side (see {@link EnumLogisticIO})
	 * @param mode the part that is changed (see {@link EnumLogisticType})
	 * @return true on Success 
	 */
	public boolean setModeForFace(EnumFacing face, EnumLogisticIO log, EnumLogisticType mode);
	
	/**
	 * @return an sorted Arrays (so it can be used with Arrays.binarySearch!)
	 */
	public EnumLogisticType[] getLogisticModes();
	
}
