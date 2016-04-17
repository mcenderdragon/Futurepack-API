package futurepack.api.interfaces;

import net.minecraft.item.ItemStack;

public interface IItemWithRandom 
{
	public void setRandomNBT(ItemStack it, int random);
}
