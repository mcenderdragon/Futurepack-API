package futurepack.api;

import java.util.ArrayList;

import futurepack.api.interfaces.ITileNeonEngine;
import futurepack.api.interfaces.ITileNetwork;
import futurepack.api.interfaces.ITileSupportAble;
import net.minecraft.inventory.ISidedInventory;
import net.minecraftforge.fluids.capability.IFluidHandler;

public enum EnumLogisticType
{
	ENERGIE(ITileNeonEngine.class, 0x00ffff),
	SUPPORT(ITileSupportAble.class, 0xffff00),
	//EXPIRENCE(ITileXpStorage.class), maybe in the future
	NETWORK(ITileNetwork.class, 0x8800df),
	ITEMS(ISidedInventory.class, 0x347800),
	FLUIDS(IFluidHandler.class, 0x0066ff),
	;
	
	
	private Class neededClass;
	public final int color;
	
	private EnumLogisticType(Class t, int color)
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
	
	
	public static EnumLogisticType[] getAllSupported(Object o)
	{
		EnumLogisticType[] all = EnumLogisticType.values();
		ArrayList<EnumLogisticType> supported = new ArrayList<EnumLogisticType>(all.length);
		for(int i=0;i<all.length;i++)
		{
			if(all[i].isValid(o))
			{
				supported.add(all[i]);
			}
		}
		return supported.toArray(new EnumLogisticType[supported.size()]);
	}
}
