package futurepack.api;

import futurepack.api.capabilities.INeonEnergyStorage;
import futurepack.api.capabilities.ISupportStorage;
import futurepack.api.interfaces.tilentity.ITileNetwork;
import net.minecraft.inventory.ISidedInventory;
import net.minecraftforge.fluids.capability.IFluidHandler;

public enum EnumLogisticType
{
	ENERGIE(INeonEnergyStorage.class, 0x00ffff),
	SUPPORT(ISupportStorage.class, 0xffff00),
	//EXPIRENCE(ITileXpStorage.class), maybe in the future
	NETWORK(ITileNetwork.class, 0x8800df),//unused
	ITEMS(ISidedInventory.class, 0x347800),
	FLUIDS(IFluidHandler.class, 0x0066ff),
	;
	
	
//	private Class neededClass;
	public final int color;
	public int ID;
	
	private EnumLogisticType(Class t, int color)
	{
//		neededClass = t;
		int r = color & 0xFF;
		int g = (color >> 8) & 0xFF;
		int b = (color >> 16) & 0xFF;
		this.color = 0xFF000000 | r << 16 | g<<8 | b;
		this.ID = ordinal();
	}

	public static EnumLogisticType getType(int id) 
	{
		return EnumLogisticType.values()[id];
	}
	
//	public boolean isValid(Object o)
//	{
//		Class c = o.getClass();
//		return neededClass.isAssignableFrom(c);
//	}
//	
//	public Class getRequred()
//	{
//		return neededClass;
//	}
	
	
//	public static EnumLogisticType[] getAllSupported(Object o)
//	{
//		EnumLogisticType[] all = EnumLogisticType.values();
//		ArrayList<EnumLogisticType> supported = new ArrayList<EnumLogisticType>(all.length);
//		for(int i=0;i<all.length;i++)
//		{
//			if(all[i].isValid(o))
//			{
//				supported.add(all[i]);
//			}
//		}
//		return supported.toArray(new EnumLogisticType[supported.size()]);
//	}
}
