package futurepack.api.helper;

import java.lang.reflect.Method;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import futurepack.api.interfaces.IGuiComponent;
import futurepack.client.research.LocalPlayerResearchHelper;
import futurepack.common.gui.GuiResearchMainOverview;
import futurepack.common.research.CustomPlayerData;
import futurepack.common.research.Research;
import futurepack.common.research.ResearchLoader;

public class HelperComponent
{
	private static ResourceLocation res = new ResourceLocation("fp", "textures/gui/Symbole.png");
	
	/**
	 * renders a 18 x 18 item slot
	 */
	public static void renderSlot(int x, int y)
	{
		renderSymbol(x, y, 0);
	}
	
	static Method m_toolTip;
	
	static
	{
		for(Method m: GuiScreen.class.getDeclaredMethods())
		{
			Class<?>[] cs = m.getParameterTypes();
			if(cs.length==3)
			{
				if(cs[0] == ItemStack.class && cs[1]==int.class && cs[2]==int.class)
				{
					m_toolTip = m;
					m_toolTip.setAccessible(true);
					break;
				}
			}
		}
	}
	
	public static void renderItemStackWithSlot(ItemStack it, int x, int y)
	{
		GlStateManager.disableLighting();
		GL11.glColor4f(1, 1, 1, 1);
		renderSlot(x, y);
		
		if(it!=null)
		{
			CustomPlayerData data = LocalPlayerResearchHelper.getLocalPlayerData();
			if(!data.canProduce(it))
			{
				renderQuestionmark(x, y);
			}
			else
			{	
				Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(it, x+1, y+1);
				GlStateManager.enableAlpha();
					
				if(it.stackSize!=1)
				{		
					int xPosition = x+1;
					int yPosition = y+1;
					FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
					String s1 = Integer.toString(it.stackSize);
					GL11.glTranslatef(0f, 0f, 160f);
					fr.drawStringWithShadow(s1, (float)(xPosition + 19 - 2 - fr.getStringWidth(s1)), (float)(yPosition + 6 + 3), 16777215);
					GL11.glTranslatef(0f, 0f, -160f);
				}
			}
		}
//		GL11.glColor4f(1, 1, 1, 1);
	}
	
	public static void renderItemText(ItemStack it, int x, int y, int mouseX, int mouseY, GuiScreen gui)
	{
		if(it != null)
		{		
			GL11.glColor4f(1, 1, 1, 1);
			if(isInBox(mouseX, mouseY, x+1, y+1, x+17, y+17))
			{
				try {
					m_toolTip.invoke(gui, it,mouseX,mouseY);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}	
//		GL11.glColor4f(1, 1, 1, 1);
	}
	
	public static boolean isInBox(int mx, int my, int x1, int y1, int x2, int y2)
	{
		return mx>=x1 && my>=y1 && mx<x2 && my<y2;
	}
	
	public static void renderFire(int x, int y)
	{
		renderSymbol(x, y, 1);	
	}
	
	public static void renderArrow(int x, int y)
	{
		renderSymbol(x, y, 2);	
	}
	
	public static void renderIndFurn(int x, int y)
	{
		renderSymbol(x, y, 3);	
	}
	
	public static void renderPartPress(int x, int y)
	{
		renderSymbol(x, y, 4);
	}
	
	public static void renderSupport(int x, int y)
	{
		renderSymbol(x, y, 6);
	}
	
	public static void renderQuestionmark(int x, int y)
	{
		renderSymbol(x, y, 16);
	}
	
	public static void renderIndNeonFurn(int x, int y)
	{
		renderSymbol(x, y, 17);
	}
	
	public static void renderSymbol(int x, int y, int id)
	{
		int texW = 144;
		int texH = 144;
		int symbolW = 18;
		int symbolH = 18;
		
		int line = id % (texW/symbolW);
		int collum = id / (texW/symbolW);
		GL11.glColor4f(1F, 1f, 1f, 1f);
		Minecraft.getMinecraft().getTextureManager().bindTexture(res);
		
		Gui.drawScaledCustomSizeModalRect(x, y, symbolW*line, symbolH*collum, symbolW, symbolH, symbolW, symbolH, texW, texH);
	}
	
	public static void drawBackground(int x, int y, IGuiComponent com)
	{
		GL11.glColor4f(1F, 1f, 1f, 1f);
		Gui.drawRect(x, y, x+com.getWidth(), y+com.getHeight(), 0xffc6fffa);
		Gui.drawRect(x+1, y+1, x+com.getWidth(), y+com.getHeight(), 0xff667994);	
		Gui.drawRect(x+1,   y+1,   x+com.getWidth()-1,   y+com.getHeight()-1,   0xff99d9ea);
	}
	
	public static <T> T getItemTimeBased(int millis, List<T> col)
	{
		if(col==null)
			return null;
			
		if(col.isEmpty())
			return null;
			
		int c = (int) (System.currentTimeMillis() / millis);
		return col.get( c % col.size() );
	}
	
	public static ItemStack getStack(List<ItemStack> col)
	{
		return getItemTimeBased(800, col);
	}
	
	public static ItemStack[] getStack(List<ItemStack>[] col)
	{
		ItemStack[] its = new ItemStack[col.length];
		for(int i=0;i<col.length;i++)
		{
			its[i] = getItemTimeBased(800, col[i]);
		}
		return its;
	}

	public static void researchItem(ItemStack it, GuiResearchMainOverview gui)
	{
		if(it!=null)
		{	
			CustomPlayerData data = LocalPlayerResearchHelper.getLocalPlayerData();
			Research r = ResearchLoader.instance.getReqiredResearch(it);
			if(r!=null && data.hasResearch(r))
			{
				gui.openResearchText(r);
			}
		}
	}
	
	
}
