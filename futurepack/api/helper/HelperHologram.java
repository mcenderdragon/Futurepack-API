package futurepack.api.helper;

import java.util.Collection;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import futurepack.api.interfaces.ITileHologramAble;


public class HelperHologram
{	
	//TODO: TileEntity einbauen, sodass wirklich jeder Block gerendert werden kann.
	
	/**
	 * @param A TileEntity implementing IHologramAble
	 */
	@SideOnly(Side.CLIENT)
	public static void renderHologram(TileEntity t)
	{
		boolean debug = isHologramDebug();
		ITileHologramAble holo = (ITileHologramAble) t;
		
		if(debug)
		{
			GlStateManager.enableBlend();
			GL11.glBlendFunc(GL11.GL_ONE_MINUS_DST_COLOR, GL11.GL_ONE);
		}
		
		HelperRenderBlocks.renderBlock(holo.getHologram(), t.getPos(), t.getWorld());
		
		if(debug)
		{
			GlStateManager.disableBlend();
		}
	}
	
	//GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_DST_ALPHA); sirgt für blend nur für hintergrund
	//GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_DST_COLOR); invertierung
	//GL11.glBlendFunc(GL11.GL_ONE_MINUS_DST_ALPHA, GL11.GL_ONE_MINUS_SRC_COLOR); invertierung + hintergrund ist drüber
	//GL11.glBlendFunc(GL11.GL_ONE_MINUS_DST_COLOR, GL11.GL_ONE_MINUS_SRC_COLOR); invertriung nur im fordergrund + bei schwarz normal
	//GL11.glBlendFunc(GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_COLOR); invertriung nur im fordergrund
	//GL11.glBlendFunc(GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE_MINUS_DST_ALPHA); hintergrund + texture ist schwarz
	//GL11.glBlendFunc(GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE_MINUS_DST_COLOR); invertriert, aber dunkler
	@SideOnly(Side.CLIENT)
	public static boolean isHologramDebug()
	{
		EntityPlayerSP sp = Minecraft.getMinecraft().thePlayer;
		if(sp==null)
		{
			return false;
		}
		
		if(sp.getHeldItem() != null)
		{
			if(sp.getHeldItem().getItem() == Item.getByNameOrId("hologramcontroler"))
			return true;
		}
		return false;
	}
	
	public static void saveInItem(ItemStack it, IBlockState state)
	{
		if(!it.hasTagCompound())
		{
			it.setTagCompound(new NBTTagCompound());
		}
		
		it.getTagCompound().setTag("blockstate", toNBT(state));
	}
	
	public static IBlockState loadFormItem(ItemStack it)
	{
		if(!it.hasTagCompound())
		{
			return null;
		}
		
		NBTTagCompound tag = it.getTagCompound().getCompoundTag("blockstate");
		
		if(tag==null)
		{
			return null;
		}
		
		return fromNBT(tag);
	}
	
	public static NBTTagCompound toNBT(IBlockState state)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("id", Block.getIdFromBlock(state.getBlock()));
		
		ImmutableMap<IProperty, Comparable> map = state.getProperties();
		ImmutableSet<Entry<IProperty, Comparable>> set = map.entrySet();
		for(Entry<IProperty, Comparable> e : set)
		{
			IProperty p = e.getKey();
			nbt.setString(p.getName(), p.getName(e.getValue()));
		}
		
		return nbt;
	}
	
	public static IBlockState fromNBT(NBTTagCompound nbt)
	{
		Block b = Block.getBlockById(nbt.getInteger("id"));
		IBlockState state = b.getDefaultState();
		
		ImmutableMap<IProperty, Comparable> map = state.getProperties();
		ImmutableSet<Entry<IProperty, Comparable>> set = map.entrySet();
		for(Entry<IProperty, Comparable> e : set)
		{
			IProperty p = e.getKey();
			
			if(nbt.hasKey(p.getName()))
			{
				String val = nbt.getString(p.getName());
				if(val!=null)
				{
					Collection<Comparable> cc = p.getAllowedValues();
					for(Comparable ca : cc)
					{
						if(val.equals(p.getName(ca)))
						{
							state = state.withProperty(p, ca);
							continue;
						}
					}
				}			
			}
		}
		
		return state;
	}
}
