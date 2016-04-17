package futurepack.api.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import futurepack.api.ItemPredicates;

public class HelperJSON
{
	private static HashMap<String, Integer> variables = new HashMap<String, Integer>();
	
	public static void addVar(String name, int val)
	{
		variables.put(name, val);
	}
	
	/*
	 * Item:"OreDict:ingotIron"
	 * Item:apple
	 * Item:{"name":dirt, size:20, meta:2}
	 * 
	 * Item:["apple","dirt","OreDict:dustNeon",{"name":"stone",...}, ....]
	 */
	public static List<ItemStack> getItemFromJSON(JsonElement elm)
	{
		if(elm==null || elm.isJsonNull())
			return new ArrayList();
		
		//System.out.println("tray Parsing JSON " + elm);
		if(elm.isJsonPrimitive())
		{
			JsonPrimitive prim = elm.getAsJsonPrimitive();
			String s = prim.getAsString();
			if(s.startsWith("OreDict:"))
			{
				s = s.substring("OreDict:".length(), s.length());
				//System.err.println(s);
				return OreDictionary.getOres(s);
			}
			Item i = (Item) Item.getByNameOrId(s);
			if(i!=null)
			{
				ItemStack it = new ItemStack(i,1,Short.MAX_VALUE);
				//System.out.println(it);
				return new ArrayList(Arrays.asList(it));
			}
		}
		if(elm.isJsonObject())
		{
			JsonObject obj = elm.getAsJsonObject();
			if(obj.has("name"))
			{	
				String name = obj.get("name").getAsString();
				int size = 1;
				int meta = Short.MAX_VALUE;
				if(obj.has("size"))
				{
					size = obj.get("size").getAsInt();
				}
				if(obj.has("meta"))
				{
					meta = getVarSave(obj.get("meta"));
				}
				Item i = (Item) Item.itemRegistry.getObject(new ResourceLocation(name)); //TODO: maybe this breaks
				if(i !=null)
				{
					ItemStack it = new ItemStack(i, size, meta);
					//System.out.println(it);
					return new ArrayList(Arrays.asList(it));
				}
			}
			else if(obj.has("OreDict"))
			{
				String ore = obj.get("OreDict").getAsString();
				List<ItemStack> base = OreDictionary.getOres(ore);
				int size = 1;
				if(obj.has("size"))
				{
					size = obj.get("size").getAsInt();
				}
				List<ItemStack> copy = new ArrayList<ItemStack>(base.size());
				for(ItemStack it : base)
				{
					ItemStack newIt = it.copy();
					newIt.stackSize = size;
					copy.add(newIt);
				}
				return copy;
			}
		}
		if(elm.isJsonArray())
		{
			JsonArray arr = elm.getAsJsonArray();
			ArrayList<ItemStack> arrl = new ArrayList(arr.size());
			for(int i=0;i<arr.size();i++)
			{
				arrl.addAll(getItemFromJSON(arr.get(i)));
			}
			return arrl;
		}
		System.err.println("ItemStack parsing failed: '"+elm+"' is null");
		return new ArrayList();
		
	}
	
	public static void setupRendering(List<ItemStack> raw)
	{
		for(ItemStack ut : raw)
		{
			if(ut.getItemDamage() == 32767)
			{
				ut.setItemDamage(0);
			}
		}
	}
	
	public static String ChatToJSON(IChatComponent chat)
	{
		String json = IChatComponent.Serializer.componentToJson(chat);
		return json;
	}
	
	public static String ChatToJSON(IChatComponent[] chat)
	{
		ChatComponentText text = new ChatComponentText("");
		for(int i=0;i<chat.length;i++)
		{
			if(chat[i]!=null)
				text.appendSibling(chat[i]);
		}
		return ChatToJSON(text);
	}
	
	/**
	 * var lol => 10
	 * 
	 * s:2 -> s=2
	 * s:"2" -> s=2
	 * s:"<lol>" -> s=10 //because it uses the above defined var
	 * s:"lol" -> ERROR lol  is not a Number
	 * s:"<soo>" -> s=0 //because soo is not defined so its asumed as 0
	 * 
	 * yes you need <> to use a defined var
	 */
	private static int getVarSave(JsonElement jo)
	{
		if(jo.isJsonPrimitive())
		{
			JsonPrimitive jprim = jo.getAsJsonPrimitive();
			if(jprim.isString())
			{
				String s = jprim.getAsString();
				if(s.startsWith("<") && s.endsWith(">") && s.length()>2)
				{
					String sub = s.substring(1, s.length()-1);
					Integer val = variables.get(sub);
					if(val!=null)
					{
						return val;
					}
					else
					{
						System.out.println("[WARN] Dont found '" + sub+"'");
						return 0;
					}
				}
				else
				{
					return Integer.valueOf(s);
				}
			}
			return jprim.getAsInt();
		}
		return 0;
	}
	
	public static ItemPredicates getItemPredicateFromJSON(JsonElement elm)
	{
		if(elm==null || elm.isJsonNull())
			return new ItemPredicates.NullPredicate();
		else if(elm.isJsonObject())
		{
			JsonObject obj = elm.getAsJsonObject();
			if(obj.has("name"))
			{	
				String name = obj.get("name").getAsString();
				int size = 1;
				int meta = Short.MAX_VALUE;
				if(obj.has("size"))
				{
					size = obj.get("size").getAsInt();
				}
				if(obj.has("meta"))
				{
					meta = getVarSave(obj.get("meta"));
				}
				Item i = (Item) Item.itemRegistry.getObject(new ResourceLocation(name)); //TODO: maybe this breaks
				if(i !=null)
				{
					ItemStack it = new ItemStack(i, size, meta);
					return new ItemPredicates.ItemStackPredicate(it);
				}
			}
			else if(obj.has("OreDict"))
			{
				String ore = obj.get("OreDict").getAsString();
				int size = 1;
				if(obj.has("size"))
				{
					size = obj.get("size").getAsInt();
				}
				return new ItemPredicates.OreDictPredicate(ore, size);
			}
		}
		else if(elm.isJsonPrimitive())
		{
			JsonPrimitive prim = elm.getAsJsonPrimitive();
			String s = prim.getAsString();
			if(s.startsWith("OreDict:"))
			{
				s = s.substring("OreDict:".length(), s.length());
				return new ItemPredicates.OreDictPredicate(s);
			}
			Item i = (Item) Item.getByNameOrId(s);
			if(i!=null)
			{
				return new ItemPredicates.ItemPredicate(i);
			}
		}
		else if(elm.isJsonArray())
		{
			JsonArray arr = elm.getAsJsonArray();
			ItemPredicates[] predicates = new ItemPredicates[arr.size()];
			for(int i=0;i<arr.size();i++)
			{
				predicates[i] = getItemPredicateFromJSON(arr.get(i));
			}
			return new ItemPredicates.ListPredicate(true, predicates);
		}
		
		System.err.println("Predicate parsing failed: '"+elm+"' is null ");
		return new ItemPredicates.NullPredicate();
	}
}
