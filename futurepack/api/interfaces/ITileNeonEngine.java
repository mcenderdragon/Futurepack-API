package futurepack.api.interfaces;

import net.minecraft.util.EnumFacing;

/**
 * Implement this in you {@link net.minecraft.tileentity.TileEntity} to get powered by NeonEnergie.
 */
public interface ITileNeonEngine 
{
	
	public int getPower();
	
	public int getMaxPower(); 
	
	public boolean usePower(int e);
	
	public boolean addPower(int e);
	
	public boolean needPowerFrom(ITileNeonEngine engine, EnumFacing side);
	
	public boolean canPowerTo(ITileNeonEngine engine, EnumFacing side);
	
	/**
	 * this determines if you get power and from who.
	 */
	public EnumPowerMode getType();
	
	/**
	 * This is only used for Wires
	 */
	void setPower(int e);
	
	public static enum EnumPowerMode
	{
		/**
		 * dont get power only send power
		 */
		PRODUCE(1),
		/**
		 * get only power from PRODUCE
		 */
		WIRE(2),
		/**
		 * gets power from everyone
		 */
		USE(3),
		/**
		 * this is as if you have not implemented the interface
		 */
		NONE(0);
		
		private EnumPowerMode(int  p)
		{
			preority = p;
		}
		
		private int preority;
		
		public int getPreority()
		{
			return preority;
		}
	}
}
