//package futurepack.api;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import javax.annotation.Nonnull;
//
//import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
//import net.minecraft.block.Block;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraftforge.oredict.OreDictionary;
//
///**
// * Register you addon Block here if you want toadd sub variants to the default one,
// * for example if you have a green colored T1 modules, register them with "modul_1" and "color.green"
// * 
// * since 26.3.49
// */
//public class FPTagDictionary
//{
//	private static List<String> IDtoName = new ArrayList<String>();
//	private static Map<String, Integer> NametoID = new HashMap<String, Integer>();
//	private static Map<Integer, ArrayList<ItemStack>> IDtoStack = new Int2ObjectOpenHashMap<ArrayList<ItemStack>>();
//	private static Map<Integer, ArrayList<Integer>> StacktoID = new Int2ObjectOpenHashMap<ArrayList<Integer>>();
//	
//	private static boolean init = false;
//	
//	static
//	{
//		init();
//	}
//	
//	public static void init()
//	{		
//		if(!init)
//		{
//			register(new ItemStack(Block.getBlockFromName("futurepack:wire"), 1,0), "wire", "type.neon");
//			register(new ItemStack(Block.getBlockFromName("futurepack:wire"), 1,1), "wire", "type.redstone", "type.neon");
//			register(new ItemStack(Block.getBlockFromName("futurepack:wire"), 1,2), "wire", "type.super", "type.neon");
//			register(new ItemStack(Block.getBlockFromName("futurepack:wire"), 1,3), "wire", "type.support", "type.neon");
//			register(new ItemStack(Block.getBlockFromName("futurepack:wire"), 1,4), "wire", "type.network", "type.neon");
//			
//			addWithColor(Block.getBlockFromName("futurepack:modul_1"));
//			addWithColor(Block.getBlockFromName("futurepack:modul_2"));
//			addWithColor(Block.getBlockFromName("futurepack:modul_3"));
//			addWithColor(Block.getBlockFromName("futurepack:modul1calc"));
//			
//			addWithColor(Block.getBlockFromName("futurepack:opti_bench"));
//			addWithColor(Block.getBlockFromName("futurepack:plasma_generator_on"));
//			addWithColor(Block.getBlockFromName("futurepack:e_furnace"));
//			addWithColor(Block.getBlockFromName("futurepack:crusher"));
//			addWithColor(Block.getBlockFromName("futurepack:ind_neon_furn"));
//			addWithColor(Block.getBlockFromName("futurepack:recycler"));
//			addWithColor(Block.getBlockFromName("futurepack:zentrifuge"));
//				
//			addWithColor(Block.getBlockFromName("futurepack:assembly_table"));
//			addWithColor(Block.getBlockFromName("futurepack:forscher"));
//			addWithColor(Block.getBlockFromName("futurepack:scanner_block"));
//			addWithColor(Block.getBlockFromName("futurepack:board_computer"));
//			addWithColor(Block.getBlockFromName("futurepack:advanced_boardcomputer"));
//			addWithColor(Block.getBlockFromName("futurepack:flash_server"));
//			
//			//wandrope
//			//solar panel
//			//ion collecotr
//			//water turbin
//			
//			register(new ItemStack(Block.getBlockFromName("futurepack:industrie_ofen"),1,0), "industrie_ofen");
//			
//			register(new ItemStack(Block.getBlockFromName("futurepack:pipe"), 1,0), "pipe", "type.item");
//			register(new ItemStack(Block.getBlockFromName("futurepack:pipe"), 1,1), "pipe", "type.redstone", "type.item");
//			register(new ItemStack(Block.getBlockFromName("futurepack:pipe"), 1,2), "pipe", "type.neon", "type.item");
//			register(new ItemStack(Block.getBlockFromName("futurepack:pipe"), 1,3), "pipe", "type.support", "type.item" );
//			
//			//FPBlocks.cristal;
//			//metal_gitter_pane ? there are not colored so...... ?!
//			//pusher
//			//metal fence
//			//monorail ?
//			//extern cooler ?
//			//erze blocke subt ypes
//			//futurepack:core ?
//			//other metal blocks ?
//			init = true;
//		}
//	}
//	
//	private static void addWithColor(Block bl)
//	{
//		register(new ItemStack(bl, 1, 2), bl.getRegistryName().getPath(), "color.white");
//		register(new ItemStack(bl, 1, 6), bl.getRegistryName().getPath(), "color.silver");
//		register(new ItemStack(bl, 1, 10), bl.getRegistryName().getPath(), "color.black");
//	}
//	
//	/**
//	 * @param name The Tag name
//	 * @return return an unique Id for each Tag name if the tag was registered else a NullPointerException will be thorwn
//	 */
//	public static int getTagId(String name)
//	{
//		return NametoID.get(name);
//	}
//	
//	/**
//	 * @param it The ItemStack
//	 * @param tagId The the tag id
//	 * @return true if this ItemStack has this tag
//	 */
//	public static boolean hasTag(ItemStack it, int tagId)
//	{
//		if(it==null || it.isEmpty())
//			return false;
//		
//		ArrayList<Integer> tags = StacktoID.get(hash(it));	
//		if(tags != null && tags.contains(tagId))
//			return true;
//		
//		tags = StacktoID.get(hash(new ItemStack(it.getItem(), 1, OreDictionary.WILDCARD_VALUE)));
//		if(tags!=null && tags.contains(tagId))
//			return true;
//		
//		return false;
//	}
//	
//	/**
//	 * @param it The ItemStack
//	 * @param tagIds an array of Tag ids to check
//	 * @return true if the ItemStack has all the tags
//	 */
//	public static boolean hasTags(ItemStack it, int[] tagIds)
//	{
//		if(it==null || it.isEmpty())
//			return false;
//		
//		ArrayList<Integer> tags1 = StacktoID.get(hash(it));	
//		ArrayList<Integer> tags2 = StacktoID.get(hash(new ItemStack(it.getItem(), 1, OreDictionary.WILDCARD_VALUE)));
//		int size = 0;
//		if(tags1!=null)
//			size  += tags1.size();
//		if(tags2!=null)
//			size += tags2.size();
//		
//		if(size > 0)
//		{
//			ArrayList<Integer> list = new ArrayList<Integer>();
//			if(tags1!=null)
//				list.addAll(tags1);
//			if(tags2!=null)
//				list.addAll(tags2);
//			
//			Collections.sort(list);
//			for(int key : tagIds)
//			{
//				if(Collections.binarySearch(list, key) < 0)
//					return false;
//			}
//			return true;
//			
//		}
//		return false;
//	}
//	
//	/**
//	 * @param it The ItemStack
//	 * @param name The Tag name
//	 * @return true if the ItemStack has this tag
//	 */
//	public static boolean hasTag(ItemStack it, String name)
//	{
//		return hasTag(it, getTagId(name));
//	}
//	
//	/**
//	 * @param it The ItemStack
//	 * @return a List of all the tag ids assigned to this Stack
//	 */
//	public static ArrayList<Integer> getTags(ItemStack it)
//	{
//		return getTagsFromStackId(hash(it));
//	}
//	
//	/**
//	 * @param id A hashed itemStack
//	 * @return a List of all the tag ids assigned to this Stack
//	 */
//	public static ArrayList<Integer> getTagsFromStackId(int id)
//	{
//		return StacktoID.get(id);
//	}
//		
//	public static Collection<Integer> getRegisteredItems()
//	{
//		return StacktoID.keySet();
//	}
//	
//	public static ArrayList<ItemStack> getItemsMatching(int...tags)
//	{
//		ArrayList<ItemStack>[] lists = new ArrayList[tags.length];
//		int length = Integer.MAX_VALUE;
//		int pos = 0;
//		for(int i=0;i<tags.length;i++)
//		{
//			lists[i] = IDtoStack.get(tags[i]);
//			if(lists[i].size() < length)
//			{
//				pos = i;
//				length = lists[i].size();
//			}
//		}
//		
//		ArrayList<ItemStack> matching = new ArrayList(length);
//		if(length>0)
//		{
//			Iterator<ItemStack> iter = lists[pos].iterator();
//			lists = null;
//			while(iter.hasNext())
//			{
//				ItemStack it = iter.next();
//				if(hasTags(it, tags))
//				{
//					matching.add(it);
//				}
//			}
//		}
//		return matching;
//	}
//	/**
//	 * 
//	 * @param item The ItemStack to register
//	 * @param tags the tags the ItemStack will get
//	 */
//	public static void register(ItemStack item, String...tags)
//	{
//		for(String s : tags)
//			registerImpl(item, s);
//	}
//	
//	public static void register(Item item, String...tags)
//	{
//		register(new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE));
//	}
//	
//	public static void register(Block block, String...tags)
//	{
//		register(new ItemStack(block, 1, OreDictionary.WILDCARD_VALUE));
//	}
//	
//	private static void registerImpl(ItemStack item, String tag)
//	{
//		if(item.isEmpty() || tag==null || tag.isEmpty())
//			return;
//		
//		Integer tagID = NametoID.get(tag);
//		if(tagID == null)
//		{
//			tagID = IDtoName.size();
//			IDtoName.add(tag);
//			NametoID.put(tag, tagID);
//		}
//		
//		int stackHash = hash(item);
//		ArrayList<ItemStack> stacks = IDtoStack.get(tagID);
//		if(stacks == null)
//		{
//			stacks = new ArrayList<ItemStack>();
//			IDtoStack.put(tagID, stacks);
//		}
//		ArrayList<Integer> ids = StacktoID.get(stackHash);		
//		if(ids == null)
//		{
//			ids = new ArrayList<Integer>();
//			StacktoID.put(stackHash, ids);
//		}		
//		
//		if(stacks.contains(item))
//			return;	
//		if(ids.contains(tagID))
//			return;
//		
//		stacks.add(item);
//		ids.add(tagID);
//	}
//	
//	public static int hash(@Nonnull ItemStack it)
//	{
//		if(it==null)
//			return 0;
//		
//		int id = Item.getIdFromItem(it.getItem()); //Same as in OreDict to have same ids even after id remapping
//		int meta = it.getItemDamage();
//		return id << 16 | meta;
//	}
//	
//	public static String[] getNames(int[] ids)
//	{
//		String[] ss = new String[ids.length];
//		for(int i=0;i<ss.length;i++)
//		{
//			ss[i] = IDtoName.get(ids[i]);
//		}
//		return ss;
//	}
//	
//	public static void rebakeMap()
//	{
//		StacktoID.clear();
//		for(Entry<Integer, ArrayList<ItemStack>> e : IDtoStack.entrySet())
//		{
//			ArrayList<ItemStack> items = e.getValue();
//			int tagID = e.getKey();
//			for(ItemStack it : items)
//			{
//				int stackHash = hash(it);
//				ArrayList<Integer> ids = StacktoID.get(stackHash);
//				if(ids == null)
//				{
//					ids = new ArrayList<Integer>();
//					StacktoID.put(stackHash, ids);
//				}
//				if(ids.contains(tagID))
//					return;
//				
//				ids.add(tagID);
//			}
//		}
//	}
//}
