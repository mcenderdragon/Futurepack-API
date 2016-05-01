package futurepack.api.interfaces;

/**
 * Used to render the Items/Blocks and Icons in the Research main overview and the entries.
 */
public interface IGuiRenderable
{
	/**
	 * Use GL methods to render.
	 */
	public void render(int mouseX, int mouseY, int x, int y);
}
