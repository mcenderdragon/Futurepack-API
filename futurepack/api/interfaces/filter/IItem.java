package futurepack.api.interfaces.filter;

import javax.script.Bindings;

public interface IItem 
{
	/**
	 * @return integer, how many item are there
	 */
	public int getStackSize();
	
	/**
	 * @return a String representing the name e.g. "minecraft:stick"
	 */
	public String getRegisteredName();
	
	/**
	 * The extra data of this item, can be null<br>
	 * Bindings are directly accessible from JavaScript<br>
	 * <br>
	 * <b>Example:</b><br>
	 * the NBTTag is: <i>{"display": { "text": "Sword" } }</i><br>
	 * from JavaScript you can access it like this:<br>
	 * {@code print(item.getNBT().display.text)}<br>
	 * The output would be:<br>
	 * {@code Sword}<br>
	 */
	public Bindings getNBT();
	
	/**
	 * @return true if this Item has extra data
	 */
	public boolean hasNBT();
	
	/**
	 * @return a flaot representing the amount this item can smelt, if 0 is returned no items can be melted
	 */
	public float getSmeltedItems();
	
	/**
	 * 
	 * @return true is this item is "empty" as in 0 stack size or an "air" item.
	 */
	public boolean isEmpty();
	
	/**
	 * @return the max amoutn of item you can stack, often 64.
	 */
	public int getMaxStacksize();
	
	/**
	 * @return the amount of damage this item already took.
	 */
	public int getDurability();
	
	/**
	 * @return The max amount of damage this tool can take, if 0 then this item is not damageable
	 */
	public int getMaxDurability();
}
