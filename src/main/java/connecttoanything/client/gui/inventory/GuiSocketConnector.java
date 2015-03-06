package connecttoanything.client.gui.inventory;

import connecttoanything.inventory.ContainerSocketConnector;
import connecttoanything.ref.R;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

public class GuiSocketConnector extends GuiContainer {

	private IInventory inventory;
	private R.GUI gui;

	public GuiSocketConnector(IInventory inventory) {
		super(new ContainerSocketConnector(
				Minecraft.getMinecraft().thePlayer.inventory, inventory,
				Minecraft.getMinecraft().thePlayer));

		this.inventory = inventory;
		this.gui = R.GUI.SOCKET_CONNECTOR;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks,
			int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(gui.getResource());
		drawTexturedModalRect(gui.centerX(width), gui.centerY(height), 0, 0,
				gui.getWidth(), gui.getHeight());
	}

}
