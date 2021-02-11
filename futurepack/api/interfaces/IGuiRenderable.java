package futurepack.api.interfaces;

import com.mojang.blaze3d.matrix.MatrixStack;

/**
 * Used to render the Items/Blocks and Icons in the Research main overview and the entries.
 */
public interface IGuiRenderable
{
	/**
	 * Use GL methods to render.
	 * @param blitOffset TODO
	 */
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, int x, int y, int blitOffset);
}
