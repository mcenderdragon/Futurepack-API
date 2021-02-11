package futurepack.api.interfaces;

import futurepack.api.ItemPredicateBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public interface IResearch
{

	/**
	 * @return an Array of Researches needed for this to be researchable
	 */
	public IResearch[] getParents();
	
	/**
	 * @return an Array of Advancement names needed for this to be researchable
	 */
	public ResourceLocation[] getGrandparents();	
	
	public ITextComponent getLocalizedName();
	
	public String getTranslationKey();
	
	public String getName();
	
	public int getX();
	
	public int getY();
	
	/**
	 * @return The Icon displayed in the research-tree.
	 */
	public IGuiRenderable getIcon();
	
	/**
	 * @return an array of ItemStack enabled by this research (so they are producible)
	 */
	public ItemStack[] getEnables();
	
	/**
	 * @return all needed ingredients 
	 */
	public ItemPredicateBase[] getNeeded();
	
	public int getExpLvl();

	public int getSupport();

	/**
	 * @return Energie in NE
	 */
	public int getNeonenergie();

	/**
	 * @return needed research time in ticks
	 */
	public int getTime();

	/**
	 * @return an int representing the level of the research 0=only time, 1=time+neon, 2=time+neon+support, 3=time+neon+support+exp
	 */
	public int getTecLevel();

	/**
	 * @return the location of the background slot for rendering in the research-tree.
	 */
	public ResourceLocation getBackground();

	/**
	 * 0 ever vivisble
	 * 1 visible if researchable
	 * 2 visible if researched
	 * @param hasRes is this researched
	 * @param canRes can the player research this
	 * @return if this should be displayed
	 */
	public boolean isVisible(boolean hasRes, boolean canRes);
}
