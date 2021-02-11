package futurepack.api.interfaces;

import java.util.ArrayList;
import java.util.Random;

import futurepack.api.EnumLogisticIO;
import futurepack.common.block.modification.machines.TileEntityRecycler;
import futurepack.common.recipes.recycler.IRecyclerRecipe;
import net.minecraft.item.ItemStack;

public interface IRecyclerTool 
{
	/**
	 * called if crafting is finished and output is merged
	 * @param tile TileEntity of Recycler
	 * @param cache Recipe cache with valid cache.recipe
	 * @param tool ItemStack of Tool (correct type)
	 * @param in ItemStack of Input
	 * @return true for automatic decrease of input
	 */
	public boolean craftComplete(TileEntityRecycler tile, IRecyclerRecipe recipe, ItemStack tool, ItemStack in);
	
	/**
	 * called if crafting is complete to get output
	 * @param tile TileEntity of Recycler
	 * @param cache Recipe cache with valid cache.recipe
	 * @param tool ItemStack of Tool (correct type)
	 * @param in ItemStack of Input
	 * @param random an Random instance
	 * @return effective output list
	 */
	public ArrayList<ItemStack> getOutput(TileEntityRecycler tile, IRecyclerRecipe recipe, ItemStack tool, ItemStack in, Random random);
	
	/**
	 * called if recycler need new recipe; should set cache.recipe if possible
	 * @param tile TileEntity of Recycler
	 * @param cache Recipe cache, cache.recipe is null
	 * @param tool ItemStack of Tool (correct type) 
	 * @param in ItemStack of Input
	 */
	public IRecyclerRecipe updateRecipe(TileEntityRecycler tile, ItemStack tool, ItemStack in);
	
	/**
	 * called every machine tick while recycler make progress
	 * @param tile TileEntity of Recycler
	 * @param cache Recipe cache with valid cache.recipe
	 * @param tool ItemStack of Tool (correct type) 
	 * @param in ItemStack of Input
	 */
	public void tick(TileEntityRecycler tile, IRecyclerRecipe recipe, ItemStack tool, ItemStack in);

	/**
	 * called every machine tick while recycler should make progress
	 * @param tile TileEntity of Recycler
	 * @param cache Recipe cache with valid cache.recipe
	 * @param tool ItemStack of Tool (correct type) 
	 * @param in ItemStack of Input
	 * @return maximal progress for current recipe
	 */
	public int getMaxProgress(TileEntityRecycler tile, IRecyclerRecipe recipe, ItemStack tool, ItemStack in);

	/**
	 * called every machine tick while recycler should make progress
	 * @param tile TileEntity of Recycler
	 * @param cache Recipe cache with valid cache.recipe
	 * @param tool ItemStack of Tool (correct type) 
	 * @param in ItemStack of Input
	 * @return false pause the machine
	 */
	public boolean canWork(TileEntityRecycler tile, IRecyclerRecipe recipe, ItemStack tool, ItemStack in, int ticks);

	/**
	 * @return Support behavior for current tool
	 */
	public EnumLogisticIO getSupportMode();
	
}
