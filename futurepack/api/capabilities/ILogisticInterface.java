package futurepack.api.capabilities;

import futurepack.api.EnumLogisticIO;
import futurepack.api.EnumLogisticType;

public interface ILogisticInterface
{
	/**
	 * @param mode the Logistic type
	 * @return the IO mode for this type
	 */
	public EnumLogisticIO getMode(EnumLogisticType mode);
	
	/**
	 * @param log the Mode for this side (see {@link EnumLogisticIO})
	 * @param mode the part that is changed (see {@link EnumLogisticType})
	 * @return true on Success 
	 */
	public boolean setMode(EnumLogisticIO log, EnumLogisticType mode);
	
	/**
	 * @return true if this type is supported, false otherwise. Only if a mode is supported getMode & setMode are garantied to not crash.
	 */
	public boolean isTypeSupported(EnumLogisticType type);
	
}
