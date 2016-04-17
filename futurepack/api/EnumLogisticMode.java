package futurepack.api;

import java.util.ArrayList;

import net.minecraft.inventory.ISidedInventory;
import net.minecraftforge.fluids.IFluidHandler;
import futurepack.api.interfaces.ITileNeonEngine;
import futurepack.api.interfaces.ITileNetwork;
import futurepack.api.interfaces.ITileSupportAble;

public enum EnumLogisticMode
{
	ENERGIE(ITileNeonEngine.class, 0x00ffff),
	SUPPORT(ITileSupportAble.class, 0xffff00),
	//EXPIRENCE(ITileXpStorage.class),TODO maybe in the future
	NETWORK(ITileNetwork.class, 0x8800df),
	ITEMS(ISidedInventory.class, 0x347800),
	FLUIDS(IFluidHandler.class, 0x0066ff),
	;
	
	
	private Class neededClass;
	public final int color;
	
	private EnumLogisticMode(Class t, int color)
	{
		neededClass = t;
		this.color = color;
	}
	
	public boolean isValid(Object o)
	{
		Class c = o.getClass();
		return neededClass.isAssignableFrom(c);
	}
	
	public Class getRequred()
	{
		return neededClass;
	}
	
	
	public static EnumLogisticMode[] getAllSupported(Object o)
	{
		EnumLogisticMode[] all = EnumLogisticMode.values();
		ArrayList<EnumLogisticMode> supported = new ArrayList<EnumLogisticMode>(all.length);
		for(int i=0;i<all.length;i++)
		{
			if(all[i].isValid(o))
			{
				supported.add(all[i]);
			}
		}
		return supported.toArray(new EnumLogisticMode[supported.size()]);
	}
}
