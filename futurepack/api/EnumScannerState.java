package futurepack.api;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public enum EnumScannerState
{
	WrongItem("wrong.item"),
	ResearchedEverything("research.everything"),
	MissingBasses("research.basses.missing"),
	WrongAspects("wrong.aspetcs"),
	Succses("succses"),
	Partwise("pastwise"),
	Error("error");
	
	private String unlocalized;
	
	private EnumScannerState(String text)
	{
		unlocalized = text;
	}
	
	@SideOnly(Side.CLIENT)
	public String getLocalizedText()
	{
		return I18n.format(getUnlocalizedText()+ ".name");
	}
	
	public String getUnlocalizedText()
	{
		return "scanner." + unlocalized;
	}
}
