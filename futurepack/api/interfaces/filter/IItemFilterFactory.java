package futurepack.api.interfaces.filter;

import net.minecraft.item.ItemStack;

@FunctionalInterface
public interface IItemFilterFactory 
{
	/**
	 * Creates a {@link IItemFilter}. This is used for advanced filtering where the the Item cotains extra data for the filter. 
	 * For example a filter item with an NBTTAG to create a filter which checks if the item is enchanted
	 * 
	 * @param stack
	 * @return {@link IItemFilter} or null if the stack is not supported;
	 */
	public IItemFilter createFilter(ItemStack stack);
}
