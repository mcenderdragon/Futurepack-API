package futurepack.api.interfaces;

import net.minecraft.client.gui.GuiScreen;
import futurepack.common.gui.GuiScannerBase;

public interface IGuiComponent
{
	
	public void init(int maxWidth, GuiScreen gui);
	
	
	public void setAdditionHeight(int additionHight);
	/**
	 * @return the second param overgiven by the setAdditionHieght, just pass it back.
	 */
	public int getAdditionHeight();
	
	public int getWidth();
	
	public int getHeight();
	
	/**
	 * Render the Maion things
	 */
	public void render(int x, int y, int mouseX, int mouseY, GuiScannerBase gui);
	
	/**
	 * render Hover texts
	 */
	public void postRendering(int x, int y, int mouseX, int mouseY, boolean hover, GuiScannerBase gui);
	
	public void onClicked(int x, int y, int mouseButton, int mouseX, int mouseY, GuiScannerBase gui);
}
