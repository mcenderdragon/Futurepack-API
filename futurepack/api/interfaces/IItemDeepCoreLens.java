package futurepack.api.interfaces;

import javax.annotation.Nullable;

import futurepack.common.block.multiblock.DeepCoreLogic;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;

public interface IItemDeepCoreLens
{

	/**
	 * @return The needed energy fpr this lense.
	 */
	int getNeededEnergie(ItemStack item, IDeepCoreLogic logic);

	/**
	 * Is everything woring and should energy be consumed.
	 * @return true if energy should get consumed
	 */
	boolean isWorking(ItemStack item, IDeepCoreLogic logic);

	boolean isSupportConsumer(ItemStack item, IDeepCoreLogic logic);

	/**
	 * Updates the progress of the lense.
	 * @param item
	 * @param logic
	 * @return true if progress is completed, so the lense will be damaged
	 */
	boolean updateProgress(ItemStack item, IDeepCoreLogic logic);

	/**
	 * The Color the laser will hav
	 * @return a color in 0xRRGGBB, (so 0xFF00FF is Pink)
	 */
	int getColor(ItemStack item, IDeepCoreLogic logic);
	
	int getMaxDurability(ItemStack item, IDeepCoreLogic logic);

	@Nullable
	default EnumParticleTypes randomParticle(ItemStack lenseStack, DeepCoreLogic logic)
	{
		return null;
	}
	
}
