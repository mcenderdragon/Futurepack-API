package futurepack.api;

/**
 * This is used if you scan something with the EScanner and get information
 * Register here {@link futurepack.api.FPApiMain#registerScanPart(EnumScanPosition, futurepack.api.interfaces.IScanPart)}
 */
public enum EnumScanPosition
{
	/**
	 * This is displayed first. Normaly THis is just the Name of the Object
	 */
	HEADLINE,
	/**
	 * Between HEADLINE and FOOTER. Use this to display some information e.g the Entity is burning
	 */
	MAIN,
	/**
	 * After MAIN. Used this for not so important informations. e.g. NBT Tags
	 */
	FOOTER;
}
