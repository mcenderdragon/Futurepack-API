package futurepack.api;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import futurepack.common.FPMain;
import futurepack.common.research.CustomPlayerData;
import futurepack.common.research.Research;
import futurepack.common.research.ResearchManager;

/**
 * Important: this MUST be called after the init (because the dimensions ids and the Researches would be null)
 * TODO:alle Dimension registratiuon should be here
 */
public enum EnumPlanets
{
	Earth(new ResourceLocation("fp", "textures/gui/planet_minecraft.png"), null, 0),
	Menelaus(new ResourceLocation("fp", "textures/gui/planet_menelaus.png"), ResearchManager.getResearch("raumfahrt"), FPMain.dimMenelausID), //TODO: richtige Research eintragen
	Tyros(new ResourceLocation("fp", "textures/gui/planet_tyros.png"), ResearchManager.getResearch("upgradeT3"), FPMain.dimTyrosID),
	Entros(new ResourceLocation("fp", "textures/gui/planet_entros.png"), ResearchManager.getResearch("astronomie1"), FPMain.dimEntrosID),
	UNKNOWN(new ResourceLocation("fp", "textures/gui/undefinierbarer_datensatz.png"), null, 0);
	
	public static EnumPlanets[] VALUES = new EnumPlanets[]{Earth,Menelaus,Tyros};
	
	private ResourceLocation texture;
	private Research needed;
	private int dimID;
	
	private EnumPlanets(ResourceLocation res, Research need, int dim)
	{
		texture = res;
		needed = need;
		dimID = dim;
	}
	
	public boolean canReachPlanet(CustomPlayerData data)
	{
		if(needed==null)
			return true;
		return data.hasResearch(needed);
	}
	
	public int getDimenion()
	{
		return this.dimID;
	}
	
	public ResourceLocation getTexture()
	{
		return this.texture;
	}
	
	public String getUnlocalizedName()
	{
		return "planet." + this.name().toLowerCase();
	}
	
	@SideOnly(Side.CLIENT)
	public String getLocalizedName()
	{
		return I18n.format(getUnlocalizedName() + ".name");
	}
	
	public void setDim(int d)
	{
		if(this==UNKNOWN)
		{
			dimID = d;
		}
	}
}
