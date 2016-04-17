package futurepack.api.interfaces;

import net.minecraft.item.ItemStack;

public interface IItemNeon 
{
	public boolean isNeonable(ItemStack it);
	
	public void addNeon(ItemStack it, int i);
	
	public int getMaxNeon(ItemStack it);
	
	public int getNeon(ItemStack it);
}
