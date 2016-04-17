package futurepack.api.interfaces;

import net.minecraft.item.ItemStack;

public interface IItemSupport 
{
	public boolean isSupportable(ItemStack it);
	
	public void addSupport(ItemStack it, int i);
	
	public int getMaxSupport(ItemStack it);
	
	public int getSupport(ItemStack it);
}
