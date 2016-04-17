package futurepack.api.helper;

import java.util.Arrays;
import java.util.List;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import futurepack.api.interfaces.ITilePropertyStorage;

public class HelperContainerSync
{
	private ITilePropertyStorage store;
	private Container holder;
	
	private int[] buffer;
	
	public HelperContainerSync(Container c, ITilePropertyStorage sto)
	{
		this.store = sto;
		this.holder = c;
		buffer = new int[sto.getPropertyCount()];
		Arrays.fill(buffer, Integer.MIN_VALUE);
	}
	
	public void detectAndSendChanges(List<ICrafting> crafts)
	{
		for(int i=0;i<store.getPropertyCount();i++)
		{
			if(buffer[i] != store.getProperty(i))
			{
				buffer[i] = store.getProperty(i);
				for(ICrafting c : crafts)
					c.sendProgressBarUpdate(holder, i, buffer[i]);			
			}
		}
	}
	
	public void onUpdate(int id, int value)
	{
		buffer[id] = value;
		store.setProperty(id, value);
	}
}
