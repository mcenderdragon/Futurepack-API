package futurepack.api.interfaces;

import net.minecraft.util.EnumFacing;

public interface ITileNeonEngine 
{
	
	public int getPower();
	
	public int getMaxPower(); 
	
	public boolean usePower(int e);
	
	public boolean addPower(int e);
	
	public boolean needPowerFrom(ITileNeonEngine engine, EnumFacing side);
	
	public boolean canPowerTo(ITileNeonEngine engine, EnumFacing side);
	
	public EnumPowerMode getType();
	
	/**
	 * This is only used for Wires
	 */
	void setPower(int e);
	
	public static enum EnumPowerMode
	{
		PRODUCE(1), WIRE(2), USE(3), NONE(0);
		
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
