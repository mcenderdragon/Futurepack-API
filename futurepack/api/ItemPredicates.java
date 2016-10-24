package futurepack.api;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import net.minecraft.item.ItemStack;

/**
 * This base class is used to determain if an ItemStack is valid.
 */
public abstract class ItemPredicates implements Predicate<ItemStack>
{
	/**
	 * This is for GUi use, do NOT edit the returned ItemStack
	 */
	@Nullable
	public abstract ItemStack getRepresentItem();
}
