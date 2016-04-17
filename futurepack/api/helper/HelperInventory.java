package futurepack.api.helper;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class HelperInventory 
{

	public static class SlotContent
	{
		public IInventory inv;
		public int slot;
		public ItemStack item;
		public EntityItem entity;
		
		public SlotContent(IInventory p1, int p2, ItemStack p3, EntityItem p4) 
		{
			inv = p1;
			slot = p2;
			item = p3;
			entity = p4;
		}
	}
	
	public static List<SlotContent> putItems(World w, BlockPos jkl, EnumFacing face, List<SlotContent> items)
	{	
		BlockPos xyz = jkl.offset(face.getOpposite());

		ArrayList<SlotContent> removed = new ArrayList<SlotContent>();
		TileEntity tile = w.getTileEntity(xyz);
		if(tile instanceof IInventory)
		{			
			if(tile instanceof ISidedInventory)
			{
				ISidedInventory inv = (ISidedInventory) tile;
				int[] slots = inv.getSlotsForFace(face);
				for(SlotContent slot : items)
				{
					ItemStack it = slot.item;
					for(int i : slots)
					{
						if(inv.getStackInSlot(i)==null || (inv.getStackInSlot(i).isItemEqual(it) && ItemStack.areItemStackTagsEqual(it, inv.getStackInSlot(i)) && inv.getStackInSlot(i).stackSize+it.stackSize<=inv.getInventoryStackLimit()))
						{
							if(inv.isItemValidForSlot(i, it) && inv.canInsertItem(i, it, face))
							{
								if(inv.getStackInSlot(i)==null)
									inv.setInventorySlotContents(i, it);
								else
									inv.getStackInSlot(i).stackSize+=it.stackSize;
								removed.add(slot);
								break;
							}
						}
					}
				}
			}
			else
			{
				IInventory inv = (IInventory) tile;
				for(SlotContent slot : items)
				{
					ItemStack it = slot.item;
					for(int i=0;i<inv.getSizeInventory();i++)
					{
						if(inv.getStackInSlot(i)==null || (inv.getStackInSlot(i).isItemEqual(it) && ItemStack.areItemStackTagsEqual(it, inv.getStackInSlot(i)) && inv.getStackInSlot(i).stackSize+it.stackSize<=inv.getInventoryStackLimit()))
						{
							if(inv.isItemValidForSlot(i, it))
							{
								if(inv.getStackInSlot(i)==null)
									inv.setInventorySlotContents(i, it);
								else
									inv.getStackInSlot(i).stackSize+=it.stackSize;
								removed.add(slot);
								break;
							}
						}
					}
				}
			}
			items.removeAll(removed);
		}
		else
		{
			for(SlotContent slot : items)
			{
				ItemStack it = slot.item;
				EntityItem item = new EntityItem(w, xyz.getX()+0.5,  xyz.getY()+0.5,  xyz.getZ()+0.5, it);
				w.spawnEntityInWorld(item);
				removed.add(slot);
			}
			items.removeAll(removed);
		}
		return removed;
	}
	
	private static void tryInsert(IInventory inv, ItemStack it)
	{
		
	}

	public static boolean areItemsEqualNoSize(ItemStack it1, ItemStack it2) 
	{
		return it1.isItemEqual(it2) && ItemStack.areItemStackTagsEqual(it1, it2);
	}
	
	/**This method fills the items given by <b>items</b> into the <b>inventory</b> starting at the <b>start</b> index and only use the slots given through <b>length</b>
	 * @return am ItemStack[] with unplaceable itmes
	 */
	public static ItemStack[] placeSomewhereOverfillingItems(ItemStack[] inventory, int start, int length, ItemStack[] items, int maxinv)
	{
		ArrayList<ItemStack> list = new ArrayList<ItemStack>(items.length);
		for(ItemStack it : items)
		{
			if(it==null)
				continue;
			ItemStack result = placeInInventory(inventory, start, length, it, maxinv);
			if(result!=null)
			{
				list.add(result);
			}
		}
		
		if(list.isEmpty())
			return null;
		else
			return list.toArray(new ItemStack[list.size()]);
	}
	
	public static ItemStack placeInInventory(ItemStack[] inventory, int start, int length, ItemStack it, int maxinv)
	{
		it = it.copy();
		boolean innull = false;
		for(int i=start;i<length;i++)
		{
			if(inventory[i]==null && innull)
			{
				int max = it.getMaxStackSize();
				int size = it.stackSize;				
				int change = Math.min(size, Math.min(max, maxinv));
				
				if(size > change)
				{
					inventory[i] = it.copy();
					inventory[i].stackSize = change;
					it.stackSize -= change;
				}
				else
				{
					inventory[i] = it;
					return null;
				}
			}
			else if(inventory[i]!=null)
			{
				if(areItemsEqualNoSize(inventory[i], it))
				{
					int max = it.getMaxStackSize();
					int size = inventory[i].stackSize + it.stackSize;				
					int change = Math.min(size, Math.min(max, maxinv));
					
					if(size > change)
					{
						int dif = change - inventory[i].stackSize;
						inventory[i].stackSize = change;
						it.stackSize -= dif;
					}
					else
					{
						inventory[i].stackSize = change;
						return null;
					}
				}
			}
			
			if(it.stackSize<=0)
				return null;
			if(!innull && i+1==length)
			{
				i=start;
				innull = true;
			}
		}
		
		return null;
	}
}
