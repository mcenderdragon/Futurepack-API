package futurepack.api.interfaces;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

/**
 * Implement this in your {@link net.minecraft.item.Item} to receive/provide Support points.
 */
public interface IItemSupport 
{
	/**
	 * @param it
	 * @return if the ItemStack can receive/provide support points
	 */
	public default boolean isSupportable(ItemStack it)
	{
		return true;
	}
	
	/**
	 * This is to add/remove support from the item
	 * @param it the ItemStack
	 * @param i may be negative
	 */
	public default void addSupport(ItemStack it, int i)
	{
		int ne = getSupport(it) + i;
		
		CompoundNBT nbt = it.getChildTag("support");
		if(nbt==null)
		{
			nbt = new CompoundNBT();
		}
		nbt.putInt("sp", ne);
		it.setTagInfo("support", nbt);
	}
	
	/**
	 * @param it the ItemStack
	 * @return the maximum the Item can store
	 */
	public int getMaxSupport(ItemStack it);
	
	/**
	 * 
	 * @param it the ItemStack
	 * @return the current amount of support points stored
	 */
	public default int getSupport(ItemStack it)
	{
		CompoundNBT nbt = it.getChildTag("support");
		if(nbt==null)
		{
			return 0;
		}
		return nbt.getInt("sp");
	}
}
