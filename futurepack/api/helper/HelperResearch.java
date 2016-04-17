package futurepack.api.helper;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import futurepack.common.research.CustomPlayerData;

public class HelperResearch
{
//	public static boolean isUseable(EntityPlayer pl, Object o)
//	{
//		return true;
//	}
//	
	public static boolean isUseable(EntityPlayer pl, Block o)
	{
		return isUseable(pl, new ItemStack(o,1,Short.MAX_VALUE));
	}
	
	public static boolean isUseable(EntityPlayer pl, Item o)
	{
		return isUseable(pl, new ItemStack(o,1,Short.MAX_VALUE));
	}
	
	public static boolean isUseable(EntityPlayer pl, TileEntity o)
	{
		return isUseable(pl, o.getWorld().getBlockState(o.getPos()));
	}
	
	public static boolean isUseable(EntityPlayer pl, IBlockState o)
	{
		Block b = o.getBlock();
		int meta = b.damageDropped(o);
		return isUseable(pl, new ItemStack(b,1,meta));
	}
	
	public static boolean isUseable(EntityPlayer pl, ItemStack o)
	{
		CustomPlayerData cd = CustomPlayerData.getDataFromPlayer(pl);	
		return cd.canProduce(o);
	}
	
	public static boolean canOpen(EntityPlayer pl, IBlockState bl)
	{
		if(pl.worldObj.isRemote)
		{
			return false;
		}
		else if(isUseable(pl, bl))
		{
			return true;
		}
		else
		{
			ChatComponentTranslation trans = new ChatComponentTranslation("research.useblock.missing", "");
			ChatStyle style = new ChatStyle();
			style.setColor(EnumChatFormatting.RED);
			trans.setChatStyle(style);
			pl.addChatComponentMessage(trans);
			return false;
		}
	}

	public static boolean canOpen(EntityPlayer pl, Entity entity)
	{
		return true;
	}
}
