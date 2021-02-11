package futurepack.api;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import net.minecraft.item.ItemStack;

/**
 * This base class is used to determain if an ItemStack is valid.
 */
public abstract class ItemPredicateBase implements Predicate<ItemStack>
{
	/**
	 * This is for GUi use, do NOT edit the returned ItemStack
	 */
	@Nullable
	public abstract ItemStack getRepresentItem();
	
	public boolean apply(ItemStack item, boolean ignoreStackSize)
	{
		if(ignoreStackSize)
			return apply(item);
		else
			return apply(item) & item.getCount() >= getMinimalItemCount(item);
	}
	
	public abstract int getMinimalItemCount(ItemStack item);
	
	public abstract List<ItemStack> collectAcceptedItems(@Nonnull List<ItemStack> list);
	
}
