package connecttoanything.client.gui.inventory;

import java.awt.Color;

import connecttoanything.client.gui.GUIUtil;
import connecttoanything.init.BlocksConnectToAnything;
import connecttoanything.init.ItemsConnectToAnything;
import connecttoanything.inventory.ContainerSocketConnector;
import connecttoanything.ref.R;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;

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

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		GUIUtil.drawCenteredStringOnGuiForeground(
				fontRendererObj,
				Item.getItemFromBlock(BlocksConnectToAnything.blockSocketConnector),
				gui, 5, Color.DARK_GRAY.getRGB());
	}

}
