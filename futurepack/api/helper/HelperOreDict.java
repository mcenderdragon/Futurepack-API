package futurepack.api.helper;

import java.util.HashMap;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class HelperOreDict
{
	/**
	 * set in {@link futurepack.common.FPMain}.preInit()
	 */
	public static HelperOreDict FuturepackConveter;
	
	
	private HashMap<Integer,ItemStack> used = new HashMap<Integer, ItemStack>(); 
	
	public HelperOreDict(ItemStack...stacks)
	{
		checkStacks(stacks);
	}
	
	private void checkStacks(ItemStack[] used)
	{
		for(int i=0;i<used.length;i++)
		{
			int[] ids = OreDictionary.getOreIDs(used[i]);
			if(ids.length==0)
			{
				continue;
			}
			
			for(int id : ids)
			{
				this.used.put(id, used[i]);
			}
		}
	}
	
	
	public ItemStack getChangedItem(ItemStack it)
	{
		int[] ids1 = OreDictionary.getOreIDs(it);
		
		for(int id : ids1)
		{
			ItemStack st =  used.get(id);
			if(st!=null)
				return st;
		}
		
		return it;
		
	}
	
	public ItemStack getChangedItemSizeSensitiv(ItemStack it)
	{
		int base = it.stackSize;
		ItemStack bs = getChangedItem(it);
		bs.stackSize = base;
		return bs;
		
	}
	
	public void addOre(String name, ItemStack ore)
	{
		OreDictionary.registerOre(name, ore);
		int id = OreDictionary.getOreID(name);
		used.put(id, ore);
	}
}
