package futurepack.api.interfaces;

import net.minecraft.item.ItemStack;

/**
 * This is for Items to store NeonEnergie
 */
public interface IItemNeon 
{
	/**
	 * to determine if the Item can accept Neon 
	 * @param it the ItemStack
	 */
	public boolean isNeonable(ItemStack it);
	
	/**
	 * This to add/remove energie from the item
	 * @param it the ItemStack
	 * @param i the delta amount of energie (can be negative)
	 */
	public void addNeon(ItemStack it, int i);
	
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
	public int getNeon(ItemStack it);
}
