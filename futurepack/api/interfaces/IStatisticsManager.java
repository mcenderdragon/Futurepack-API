package futurepack.api.interfaces;

import java.util.function.Predicate;

import net.minecraft.block.Block;

public interface IStatisticsManager
{
	/**
	 * @param b the Block type
	 */
	public void addBlockToStatistics(Block bl);
	
	/**
	 * @param b the Block type
	 * @return the total of this Blocks selected
	 */
	public int getBlockCount(Block bl);
	
	public int getBlockCountFromPredicate(Predicate<Block> bl);

	public void clear();
	
	
}
