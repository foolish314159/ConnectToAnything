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

	private IInventory inventory;
	private R.GUI gui;
	private boolean connected = false;
	private GuiButton buttonConnect;

	public GuiSocketConnector(IInventory inventory) {
		super(new ContainerSocketConnector(
				Minecraft.getMinecraft().thePlayer.inventory, inventory,
				Minecraft.getMinecraft().thePlayer));

		this.inventory = inventory;
		this.gui = R.GUI.SOCKET_CONNECTOR;
	}

	@Override
	public void initGui() {
		super.initGui();

		buttonConnect = new GuiButton(0, gui.centerX(width) + 7,
				gui.centerY(height) + 57, 50, 20, "Connect");
		buttonList.add(buttonConnect);
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

		ItemStack card = inventory.getStackInSlot(36);
		if (card != null) {
			String host = card.getTagCompound().getString(
					ItemConnectionCard.NBT_HOST);
			int port = card.getTagCompound().getInteger(
					ItemConnectionCard.NBT_PORT);
			TileEntitySocketConnector connector = (TileEntitySocketConnector) inventory;
			connected = connector.isConnected();
			String textConnected = connected ? "Yes" : "No";
			Color colorConnected = connected ? Color.GREEN : Color.RED;
			fontRendererObj.drawString(host, gui.getWidth() - 85, 27,
					Color.DARK_GRAY.getRGB());
			fontRendererObj.drawString(String.valueOf(port),
					gui.getWidth() - 85, 42, Color.DARK_GRAY.getRGB());
			fontRendererObj.drawString(textConnected, gui.getWidth() - 55, 63,
					colorConnected.getRGB());
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		ItemStack card = inventory.getStackInSlot(36);
		if (card != null) {
			TileEntity te = (TileEntity) inventory;
			NetworkHandler.sendToServer(new MessageConnect(connected, te
					.getPos()));
		}
	}

}
