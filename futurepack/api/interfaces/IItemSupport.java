package futurepack.api.interfaces;

import net.minecraft.item.ItemStack;

/**
 * Implement this in your {@link net.minecraft.item.Item} to receive/provide Support points.
 */
public interface IItemSupport 
{
	/**
	 * @param it
	 * @return if the ItemStack can receive/provide support points
	 */
	public boolean isSupportable(ItemStack it);
	
	/**
	 * This is to add/remove support from the item
	 * @param it the ItemStack
	 * @param i may be negative
	 */
	public void addSupport(ItemStack it, int i);
	
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
	public int getSupport(ItemStack it);
}
