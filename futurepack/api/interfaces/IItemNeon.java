package futurepack.api.interfaces;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

/**
 * This is for Items to store NeonEnergie
 */
public interface IItemNeon 
{
	/**
	 * to determine if the Item can accept Neon 
	 * @param it the ItemStack
	 */
	public default boolean isNeonable(ItemStack it)
	{
		return getNeon(it) < getMaxNeon(it);
	}
	
	/**
	 * This to add/remove energie from the item
	 * @param it the ItemStack
	 * @param i the delta amount of energie (can be negative)
	 */
	public default void addNeon(ItemStack it, int i)
	{
		int ne = getNeon(it) + i;
		if(ne<0)
			ne=0;
		
		CompoundNBT nbt = it.getChildTag("neon");
		if(nbt==null)
		{
			nbt = new CompoundNBT();
		}
		nbt.putInt("ne", ne);
		it.setTagInfo("neon", nbt);
	}
	
	/**
	 * @param it the ItemStack
	 * @return the maximum of energie this item can hold.
	 */
	public int getMaxNeon(ItemStack it);
	
	/**
	 * 
	 * @param it the ItemStack
	 * @return the amount of enerie currently stored
	 */
	public default int getNeon(ItemStack it)
	{
		CompoundNBT nbt = it.getChildTag("neon");
		if(nbt==null)
		{
			return 0;
		}
		return nbt.getInt("ne");
	}
}
