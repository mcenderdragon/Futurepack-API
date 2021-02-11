package futurepack.api.helper;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import futurepack.api.interfaces.filter.IItem;
import futurepack.depend.api.helper.HelperOreDict;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraft.tags.ITagCollection;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class HelperTags 
{
	public static final HelperTags INSTANCE = new HelperTags();
	
	public boolean hasTag(Item item, ResourceLocation res)
	{
		ITag<Item> tag = ItemTags.getCollection().get(res);
		if(tag==null)
			return false;
		else
			return tag.contains(item);
	}
	
	public boolean hasTag(IItem iitem, String res)
	{
		Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(iitem.getRegisteredName()));
		if(item==null)
			return false;
		else
			return hasTag(item, new ResourceLocation(res));
	}
	
	public boolean isOre(IItem iitem)
	{
		return hasTag(iitem, "forge:ores");
	}
	
	public boolean isIngot(IItem iitem)
	{
		return hasTag(iitem, "forge:ingots");
	}
	
	public boolean isDust(IItem iitem)
	{
		return hasTag(iitem, "forge:dusts");
	}
	
	private Map<Item, Set<ITag<Item>>> map;
	private ITagCollection<Item> mapBase;
	
	public Set<ITag<Item>> getTags(Item item)
	{
		if(mapBase != ItemTags.getCollection())
		{
			map = new HashMap<Item, Set<ITag<Item>>>();
			mapBase = ItemTags.getCollection();
		}
		
		return map.computeIfAbsent(item, this::createTagCollection);
	}
	
	private Set<ITag<Item>> createTagCollection(Item item)
	{
		Set<ITag<Item>> set = new HashSet<ITag<Item>>();
		for(Entry<ResourceLocation, ITag<Item>> e : mapBase.getIDTagMap().entrySet())
		{
			if(e.getValue().contains(item))
			{
				set.add(e.getValue());
			}
		}
		return set;
	}
	
	public Set<ResourceLocation> getTags(IItem iitem)
	{
		Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(iitem.getRegisteredName()));
		if(item==null)
			return Collections.EMPTY_SET;
		
		Collection<ITag<Item>> col = getTags(item);
		return col.stream()
				.filter(t -> t instanceof INamedTag)
				.map(t -> (INamedTag) t)
				.map(INamedTag::getName).collect(Collectors.toSet());
	}
	
	public static boolean areTagsLoaded()
	{
		return HelperOreDict.areTagsLoaded();
	}
}
