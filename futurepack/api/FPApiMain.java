package futurepack.api;

import java.lang.reflect.Method;

import futurepack.api.interfaces.IPlanet;
import futurepack.api.interfaces.IScanPart;
import futurepack.api.interfaces.ISpaceshipUpgrade;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInterModComms;

/**
 * This is the Main registering class.
 * It uses reflect so its save even if the Futurepack is not present
 */
public class FPApiMain
{
	
	
	
	/**
	 * If you want to Display bonus informations if someone scan a Block/Entity
	 * 
	 * @param pos is to order all Entrys.
	 * @param entry is to produce the text displayed.
	 */
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
	
	/**
	 * Register your Dimension here if anyone should be able to fly/jump to it 
	 * @param planet see {@link IPlanet} for more information
	 */
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
	
	/**
	 * Register your upgrade for a Spaceship here.
	 * @param upgrade see {@link ISpaceshipUpgrade} for more information.
	 */
	public static void registerShipUpgrade(ISpaceshipUpgrade upgrade)
	{
		if(isFPpresend())
		{
			try {
				m_registerShipUpgrade.invoke(null, upgrade);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Adds a recipe to the Assemblytable
	 * @param in ItemStacks required for the recipe (max. 3)
	 * @param out the resulting ItemStack
	 */
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
	
	/**
	 * Adds a recipe to the SuperCrusher
	 * @param in the ItemStack that will be crushed.
	 * @param out the resulting ItemStack ( e.g. a Dust)
	 */
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
	
	/**
	 * Adds a recipe to the IndustrialFurnace (the one with Lava)
	 * @param in ItemStacks required for the recipe (max. 3)
	 * @param out the resulting ItemStack
	 */
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
	
	/**
	 * Adds a recipe to the IndustrialNeonFurnace (the one with Neon and support)
	 * @param in ItemStacks required for the recipe (max. 3)
	 * @param out the resulting ItemStack
	 * @param support the amount of support points needed for that recipe
	 */
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
	
	/**
	 * This adds a blockstate to a whitelsit of always breakable blocks. THis is used int the dungeons to ensure you can still break gravestones and things like that.
	 * @param res the id of the block to whitelist
	 */
	public static void addBreakAbleBlockToWHitelist(ResourceLocation res)
	{
		FMLInterModComms.sendMessage("fp", "dungeon.whitelist", res);
	}
	
	private static boolean checked, present;
	
	private static Method m_registerScanPart;
	private static Method m_registerPlanet;
	private static Method m_registerShipUpgrade;
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
				m_registerShipUpgrade = c.getMethod("registerShipUpgrade", ISpaceshipUpgrade.class);
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
