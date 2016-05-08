package futurepack.api;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.minecraft.item.ItemStack;
import futurepack.api.interfaces.IPlanet;
import futurepack.api.interfaces.IScanPart;

public class FPApiIMain
{
	
	
	
	
	public static void registerScanPart(EnumScanPosition pos, IScanPart entry)
	{
		if(isFPpresend())
		{
			try {
				m_registerScanPart.invoke(null, pos, entry);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void registerPlanet(IPlanet planet)
	{
		if(isFPpresend())
		{
			try {
				m_registerPlanet.invoke(null, planet);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void addAssemblyRecipe(ItemStack[] in, ItemStack out)
	{
		if(isFPpresend())
		{
			try {
				m_addAssemblyRecipe.invoke(null, in,out);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void addCrushingRecipe(ItemStack in, ItemStack out)
	{
		if(isFPpresend())
		{
			try {
				m_addCrushingRecipe.invoke(null, in, out);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void addIndustrialFurnaceRecipe(ItemStack[] in, ItemStack out)
	{
		if(isFPpresend())
		{
			try {
				m_addIndustrialFurnaceRecipe.invoke(null, in, out);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void addIndustrialNeonFurnaceRecipe(ItemStack[] in, ItemStack out, int support)
	{
		if(isFPpresend())
		{
			try {
				m_addIndustrialNeonFurnaceRecipe.invoke(null, in, out, support);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private static boolean checked, present;
	
	private static Method m_registerScanPart;
	private static Method m_registerPlanet;
	private static Method m_addAssemblyRecipe;
	private static Method m_addCrushingRecipe;
	private static Method m_addIndustrialFurnaceRecipe;
	private static Method m_addIndustrialNeonFurnaceRecipe;
	
	
	private static boolean isFPpresend()
	{
		if(checked)
		{
			return present;
		}
		else
		{
			checked = true;
			try
			{
				Class c = Class.forName("futurepack.depend.api.RegistryCollection");
				present = true;
				
				m_registerScanPart = c.getMethod("registerScanPart", EnumScanPosition.class, IScanPart.class);
				m_registerPlanet = c.getMethod("registerPlanet", IPlanet.class);
				m_addAssemblyRecipe = c.getMethod("addAssemblyRecipe", ItemStack[].class, ItemStack.class);
				m_addCrushingRecipe = c.getMethod("addCrushingRecipe", ItemStack.class, ItemStack.class);
				m_addIndustrialFurnaceRecipe = c.getMethod("addIndustrialFurnaceRecipe", ItemStack[].class, ItemStack.class); 
				m_addIndustrialNeonFurnaceRecipe = c.getMethod("addIndustrialNeonFurnaceRecipe", ItemStack[].class, ItemStack.class, int.class);
				
				return true;
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
			present = false;
			return false;
		}
	}
}
