package connecttoanything.client.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.item.Item;
import net.minecraft.util.StatCollector;
import connecttoanything.ref.R;

public class GUIUtil {

	public static int drawCenteredStringOnGUI(FontRenderer fontRendererObj,
			Item item, GuiScreen screen, R.GUI gui, int offsetY, int color) {
		String text = StatCollector.translateToLocal(item.getUnlocalizedName()
				+ ".name");
		return drawCenteredStringOnGUI(fontRendererObj, text, screen, gui,
				offsetY, color);
	}

	public static int drawCenteredStringOnGUI(FontRenderer fontRendererObj,
			String text, GuiScreen screen, R.GUI gui, int offsetY, int color) {
		return fontRendererObj.drawString(text, screen.width / 2
				- fontRendererObj.getStringWidth(text) / 2,
				gui.centerY(screen.height) + offsetY, color);
	}

	public static GuiTextField createCenteredTextFieldOnGUI(
			FontRenderer fontRendererObj, GuiScreen screen, R.GUI gui,
			int width, int offsetY) {
		return new GuiTextField(0, fontRendererObj, gui.centerX(screen.width)
				+ gui.getWidth() / 2 - width / 2, gui.centerY(screen.height)
				+ offsetY, width, 12);
	}

	public static GuiButton createCenteredButtonOnGUI(int id, GuiScreen screen,
			R.GUI gui, int offsetY, int width, String text) {
		return new GuiButton(id, gui.centerX(screen.width) + gui.getWidth() / 2
				- width / 2, gui.centerY(screen.height) + offsetY, width, 20,
				text);
	}
}
