package futurepack.api.interfaces;

public interface IItemMetaSubtypes
{
	/**
	 * 
	 * @return the Maximal Metadata 1 means only meta 0 would be register
	 */
	public int getMaxMetas();
	
	/**
	 * 
	 * @param meta the Metadata
	 * @return the pure JSON string to search
	 */
	public String getMetaName(int meta);
}
