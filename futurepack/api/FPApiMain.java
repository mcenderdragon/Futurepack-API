package futurepack.api;

import java.io.Reader;
import java.util.function.Supplier;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;

/**
 * This is the Main registering class.
 * It uses IMC Messages so it is not a hard dependency
 */
public class FPApiMain
{
	/**
	 * This registers a json object (in form of a reader). The JSON should be an array of objects representing the researches (like the futurepack one does)
	 * Call this from the {@link InterModEnqueueEvent}
	 * 
	 * @param modId the mod id of the caller.
	 * @param jsonReader a supplier of the reader to a json file. The {@code Supplier#get()} method will be called each time researches get (re)loaded.
	 */
	public static void addResearchPath(String modId, Supplier<Reader> jsonReader)
	{
		InterModComms.sendTo(Constants.MOD_ID, Constants.ADD_RESEARCH_JSON, () -> new IMCResearchPath(modId, jsonReader));
	}
	
	public static class IMCResearchPath
	{
		final String modID;
		final Supplier<Reader> reader;
		
		public IMCResearchPath(String modID, Supplier<Reader> reader) 
		{
			super();
			this.modID = modID;
			this.reader = reader;
		}
		
		public String getModID() {
			return modID;
		}
		public Supplier<Reader> getReader() {
			return reader;
		}
	}
//	
//	
//	/**
//	 * If you want to Display bonus informations if someone scan a Block/Entity
//	 * 
//	 * @param pos is to order all Entrys.
//	 * @param entry is to produce the text displayed.
//	 */
//	public static void registerScanPart(EnumScanPosition pos, IScanPart entry)
//	{
//		if(isFPpresend())
//		{
//			try {
//				m_registerScanPart.invoke(null, pos, entry);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	
//	/**
//	 * Register your Dimension here if anyone should be able to fly/jump to it 
//	 * @param planet see {@link IPlanet} for more information
//	 */
//	public static void registerPlanet(IPlanet planet)
//	{
//		if(isFPpresend())
//		{
//			try {
//				m_registerPlanet.invoke(null, planet);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	
//	/**
//	 * Register your upgrade for a Spaceship here.
//	 * @param upgrade see {@link ISpaceshipUpgrade} for more information.
//	 */
//	public static void registerShipUpgrade(ISpaceshipUpgrade upgrade)
//	{
//		if(isFPpresend())
//		{
//			try {
//				m_registerShipUpgrade.invoke(null, upgrade);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	/**
//	 * Adds a recipe to the Assemblytable
//	 * @param in ItemStacks required for the recipe (max. 3)
//	 * @param out the resulting ItemStack
//	 */
//	public static void addAssemblyRecipe(String id, ItemStack[] in, ItemStack out)
//	{
//		if(isFPpresend())
//		{
//			try {
//				m_addAssemblyRecipe.invoke(null, id,in,out);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	
//	/**
//	 * Adds a recipe to the SuperCrusher
//	 * @param in the ItemStack that will be crushed.
//	 * @param out the resulting ItemStack ( e.g. a Dust)
//	 */
//	public static void addCrushingRecipe(ItemStack in, ItemStack out)
//	{
//		if(isFPpresend())
//		{
//			try {
//				m_addCrushingRecipe.invoke(null, in, out);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	
//	/**
//	 * Adds a recipe to the IndustrialFurnace (the one with Lava)
//	 * @param in ItemStacks required for the recipe (max. 3)
//	 * @param out the resulting ItemStack
//	 */
//	public static void addIndustrialFurnaceRecipe(String id, ItemStack[] in, ItemStack out)
//	{
//		if(isFPpresend())
//		{
//			try {
//				m_addIndustrialFurnaceRecipe.invoke(null, id, in, out);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	
//	/**
//	 * Adds a recipe to the IndustrialNeonFurnace (the one with Neon and support)
//	 * @param in ItemStacks required for the recipe (max. 3)
//	 * @param out the resulting ItemStack
//	 * @param support the amount of support points needed for that recipe
//	 */
//	public static void addIndustrialNeonFurnaceRecipe(String id, ItemStack[] in, ItemStack out, int support)
//	{
//		if(isFPpresend())
//		{
//			try {
//				m_addIndustrialNeonFurnaceRecipe.invoke(null, id, in, out, support);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	

	/**
	 * This adds a blockstate to a whitelist of always breakable blocks (in the protected dungeon). This is used in the dungeons to ensure you can still break gravestones and things like that.
	 * @param res the id of the block for the whitelist
	 */
	public static void addBreakAbleBlockToWHitelist(ResourceLocation res)
	{
		InterModComms.sendTo(Constants.MOD_ID, Constants.DUNGEON_WHITELIST, () -> res);
	}
//	
//	private static boolean checked, present;
//	
//	private static Method m_registerScanPart;
//	private static Method m_registerPlanet;
//	private static Method m_registerShipUpgrade;
//	private static Method m_addAssemblyRecipe;
//	private static Method m_addCrushingRecipe;
//	private static Method m_addIndustrialFurnaceRecipe;
//	private static Method m_addIndustrialNeonFurnaceRecipe;
//	
//	
//	private static boolean isFPpresend()
//	{
//		if(checked)
//		{
//			return present;
//		}
//		else
//		{
//			checked = true;
//			try
//			{
//				Class c = Class.forName("futurepack.depend.api.RegistryCollection");
//				present = true;
//				
//				m_registerScanPart = c.getMethod("registerScanPart", EnumScanPosition.class, IScanPart.class);
//				m_registerPlanet = c.getMethod("registerPlanet", IPlanet.class);
//				m_registerShipUpgrade = c.getMethod("registerShipUpgrade", ISpaceshipUpgrade.class);
//				m_addAssemblyRecipe = c.getMethod("addAssemblyRecipe", String.class, ItemStack[].class, ItemStack.class);
//				m_addCrushingRecipe = c.getMethod("addCrushingRecipe", ItemStack.class, ItemStack.class);
//				m_addIndustrialFurnaceRecipe = c.getMethod("addIndustrialFurnaceRecipe", String.class, ItemStack[].class, ItemStack.class); 
//				m_addIndustrialNeonFurnaceRecipe = c.getMethod("addIndustrialNeonFurnaceRecipe", String.class, ItemStack[].class, ItemStack.class, int.class);
//				
//				return true;
//			}
//			catch (ClassNotFoundException e) {
//				e.printStackTrace();
//			} catch (NoSuchMethodException e) {
//				e.printStackTrace();
//			} catch (SecurityException e) {
//				e.printStackTrace();
//			}
//			present = false;
//			return false;
//		}
//	}
}
