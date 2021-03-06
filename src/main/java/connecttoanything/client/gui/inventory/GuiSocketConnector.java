package connecttoanything.client.gui.inventory;

import java.awt.Color;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import connecttoanything.client.gui.GUIUtil;
import connecttoanything.init.BlocksConnectToAnything;
import connecttoanything.inventory.ContainerSocketConnector;
import connecttoanything.item.ItemConnectionCard;
import connecttoanything.network.MessageConnect;
import connecttoanything.network.NetworkHandler;
import connecttoanything.ref.R;
import connecttoanything.tileentity.TileEntitySocketConnector;
import connecttoanything.util.Log;

public class GuiSocketConnector extends GuiContainer {

	public static GuiSocketConnector instance = null;

	private TileEntitySocketConnector connector;
	private R.GUI gui;
	private boolean connected = false;
	private GuiButton buttonConnect;

	public GuiSocketConnector(IInventory inventory) {
		super(new ContainerSocketConnector(
				Minecraft.getMinecraft().thePlayer.inventory, inventory,
				Minecraft.getMinecraft().thePlayer));

		this.gui = R.GUI.SOCKET_CONNECTOR;
		connector = (TileEntitySocketConnector) inventory;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
		buttonConnect.displayString = connected ? "Stop" : "Start";
	}

	@Override
	public void initGui() {
		super.initGui();

		buttonConnect = new GuiButton(0, gui.centerX(width) + 7,
				gui.centerY(height) + 57, 50, 20, "Connect");
		buttonList.add(buttonConnect);

		instance = this;
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();

		instance = null;
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
		fontRendererObj.drawString("Host: ", 65, 27, Color.DARK_GRAY.getRGB());
		fontRendererObj.drawString("Port: ", 65, 42, Color.DARK_GRAY.getRGB());
		fontRendererObj.drawString("Connected: ", 65, 63,
				Color.DARK_GRAY.getRGB());

		ItemStack card = connector.getStackInSlot(36);
		if (connected || card != null) {
			String host = "";
			int port = -1;
			if (card != null) {
				host = card.getTagCompound().getString(
						ItemConnectionCard.NBT_HOST);
				port = card.getTagCompound().getInteger(
						ItemConnectionCard.NBT_PORT);
			}

			String textConnected = connected ? "Yes" : "No";
			Color colorConnected = connected ? Color.GREEN : Color.RED;

			fontRendererObj.drawString(host, gui.getWidth() - 85, 27,
					Color.DARK_GRAY.getRGB());
			fontRendererObj.drawString(port != -1 ? String.valueOf(port) : "",
					gui.getWidth() - 85, 42, Color.DARK_GRAY.getRGB());
			fontRendererObj.drawString(textConnected, gui.getWidth() - 55, 63,
					colorConnected.getRGB());
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		ItemStack card = connector.getStackInSlot(36);
		if (card != null) {
			NetworkHandler.sendToServer(new MessageConnect(connected, connector
					.getPos()));
		}
	}

}
