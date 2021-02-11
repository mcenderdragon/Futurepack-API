package futurepack.api.interfaces.filter;

import java.util.function.Predicate;

import net.minecraft.item.ItemStack;

public interface IItemFilter extends Predicate<ItemStack>
{
	/**
	 * This is a callback, when items pass the filter
	 * Always check if the item is for you as some machines can have multiple filters which get merged together so all filters are notified
	 * 
	 * @param transfered the itemstack that was not only accepted but acctualy tranfered
	 */
	default void amountTransfered(ItemStack transfered) {}
}
