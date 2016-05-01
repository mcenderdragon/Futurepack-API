package futurepack.api.interfaces;

import net.minecraft.util.EnumFacing;

/**
 * Implement this in your {@link net.minecraft.tileentity.TileEntity} to let receive support points
 */
public interface ITileSupportAble 
{
	/**
	 * determines if the Tile can receive points
	 * @param side the side from with its injected
	 * @return weather or not it can receive
	 */
	public boolean isSupportAble(EnumFacing side);
	
	/**
	 * @return the type of the tile
	 */
	public EnumSupportType getSupportType();
	
	/**
	 * adds some support
	 * @param i amount
	 */
	public void addSupport(int i);
	
	/**
	 * removes support
	 * @param i amount
	 */
	public void useSupport(int i);
	
	/**
	 * @return the stored amount of support points
	 */
	public int getSupport();
	
	/**
	 * @return the maximal stored amount
	 */
	public int getMaxSupport();
	
	/**
	 * @return if it needs support or not (usual it needs support if not full)
	 */
	public boolean needSupport();
	
	/**
	 * Only called for wires if the get powered
	 */
	public void support();
	
	public enum EnumSupportType
	{
		WIRE, USER;
	}
}
