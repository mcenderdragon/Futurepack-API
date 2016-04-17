package futurepack.api.interfaces;

import net.minecraft.util.EnumFacing;

public interface ITileSupportAble 
{
	public boolean isSupportAble(EnumFacing side);
	
	public EnumSupportType getSupportType();
	
	public void addSupport(int i);
	
	public void useSupport(int i);
	
	public int getSupport();
	
	public int getMaxSupport();
	
	public boolean needSupport();
	
	public void support();
	
	public enum EnumSupportType
	{
		WIRE, USER;
	}
}
