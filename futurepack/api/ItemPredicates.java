package futurepack.api;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.base.Predicate;

public abstract class ItemPredicates implements Predicate<ItemStack>
{
	@Nullable
	public abstract ItemStack getRepresentItem();
	
	
	
	public static class ListPredicate extends ItemPredicates
	{
		/**
		 * true:  OR;
		 * false: AND;
		 */
		private final boolean mode;
		private Predicate<ItemStack>[] preds;
		
		public ListPredicate(boolean mode, Predicate<ItemStack>...predicates)
		{
			this.mode = mode;
			preds = predicates;
		}
		
		@Override
		public boolean apply(ItemStack input) 
		{
			if(mode)
			{
				for(Predicate<ItemStack> it : preds)
				{
					if(it.apply(input))
					{
						return true;
					}
				}
				return false;
			}
			else
			{
				for(Predicate<ItemStack> it : preds)
				{
					if(!it.apply(input))
					{
						return false;
					}
				}
				return true;
			}
		}

		@Override
		public ItemStack getRepresentItem()
		{
			for(Predicate<ItemStack> it : preds)
			{
				if(it instanceof ItemPredicates)
				{
					return ((ItemPredicates) it).getRepresentItem();
				}
			}
			return null;
		}
		
	}

	public static class ItemPredicate extends ItemPredicates
	{
		private final Item item;
		
		public ItemPredicate(Item item)
		{
			this.item = item;
		}
		
		public ItemPredicate(ItemStack item)
		{
			this.item = item.getItem();
		}

		@Override
		public boolean apply(ItemStack input)
		{
			return input!=null && input.getItem() == item && input.stackSize >= 1;
		}

		@Override
		public ItemStack getRepresentItem()
		{
			return new ItemStack(item);
		}
	}
	
	public static class ItemStackPredicate extends ItemPredicates
	{
		private final ItemStack pred;

		public ItemStackPredicate(ItemStack it)
		{
			pred = it;
		}
		
		@Override
		public boolean apply(ItemStack input)
		{
			return input!=null && input.getItem()==pred.getItem() && (pred.getItemDamage()==Short.MAX_VALUE || input.getItemDamage()==pred.getItemDamage()) && input.stackSize >= pred.stackSize;
		}

		@Override
		public ItemStack getRepresentItem()
		{
			ItemStack it = pred.copy();
			if(it.getItemDamage() == Short.MAX_VALUE)
			{
				it.setItemDamage(0);
			}
			return it;
		}
	}
	
	public static class OreDictPredicate extends ItemPredicates
	{
		private final int oreID;
		private final int stackSize;
		
		public OreDictPredicate(String oreName)
		{
			this(OreDictionary.getOreID(oreName), 1);
		}
		
		public OreDictPredicate(String oreName, int size)
		{
			this(OreDictionary.getOreID(oreName), size);
		}
		
		public OreDictPredicate(int oreID)
		{
			this(oreID, 1);
		}
		
		public OreDictPredicate(int oreID, int stacksize)
		{
			this.oreID = oreID;
			this.stackSize = stacksize;
		}
		
		@Override
		public boolean apply(ItemStack input)
		{
			if(input!=null && input.stackSize >= stackSize)
			{
				int[] ids = OreDictionary.getOreIDs(input);
				for(int id : ids)
				{
					if(id == oreID)
					{
						return true;
					}
				}
			}
			return false;
		}

		@Override
		public ItemStack getRepresentItem()
		{
			List<ItemStack> stacks = OreDictionary.getOres(OreDictionary.getOreName(oreID));
			if(!stacks.isEmpty())
			{
				ItemStack it = stacks.get(0);
				if(it!=null)
				{
					it = it.copy();
					it.stackSize = stackSize;
					return it;
				}			
			}
			return null;
		}	
	}
	
	public static class NullPredicate extends ItemPredicates
	{

		@Override
		public boolean apply(ItemStack input)
		{
			return false;
		}

		@Override
		public ItemStack getRepresentItem()
		{
			return null;
		}
		
	}
}
