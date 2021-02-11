package futurepack.api.interfaces;

import javax.annotation.Nullable;

public interface IChunkAtmosphere
{
	public int getMaxAir();
	
	/**
	 * @param x the X position 0-15
	 * @param y the Y position 0-255
	 * @param z the Z position 0-15
	 * @return the amount of breathable air
	 */
	public int getAirAt(int x, int y, int z);
	
	/**
	 * @param x the X position 0-15
	 * @param y the Y position 0-255
	 * @param z the Z position 0-15
	 * @param amount the amount to be added
	 * @return the actually added amount
	 */
	public int addAir(int x, int y, int z, int amount);
	
	/**
	 * @param x the X position 0-15
	 * @param y the Y position 0-255
	 * @param z the Z position 0-15
	 * @param amount the amount to be removed
	 * @return the actually removed amount
	 */
	public int removeAir(int x, int y, int z, int amount);
	
	public default boolean hasAir(int x, int y, int z)
	{
		return getAirAt(x,y,z) > 0;
	}
	
	/**
	 * Used for sync/reloding
	 */
	public int setAir(int x, int y, int z, int amount);
	
	/**
	 * 
	 * @param area the area or null for the complete chunk
	 * @return true if a update is needed
	 */
	public default boolean needsUpdate(@Nullable EnumChunkArea area)
	{
		return true;
	}
	
	public static enum EnumChunkArea
	{
		A0,A1,A2,A3,A4,A5,A6,A7,A8,A9,A10,A11,A12,A13,A14,A15;
		
		public static final EnumChunkArea[] VALUES = EnumChunkArea.values();
		
		public boolean isInArea(int y)
		{
			return this.ordinal() == (y >> 4);
		}
		
		public int getMinY()
		{
			return this.ordinal() * 16;
		}
	}
}
