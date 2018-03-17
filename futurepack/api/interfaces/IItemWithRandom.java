package futurepack.api.interfaces;

import net.minecraft.item.ItemStack;

/**
 * This class is used to apply random values to items produced with the Assembly table
 */
public interface IItemWithRandom 
{
	/**
	 * Use this to alter the NBT of the Item to save some data, for example save the core value.
	 * @param it the ItemStack to modify
	 * @param random the maximal random value possible
	 */
	public void setRandomNBT(ItemStack it, int random);
}
