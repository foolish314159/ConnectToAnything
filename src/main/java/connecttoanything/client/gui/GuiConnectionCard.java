package connecttoanything.client.gui;

import java.awt.Color;
import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.input.Keyboard;

import connecttoanything.init.ItemsConnectToAnything;
import connecttoanything.item.ItemConnectionCard;
import connecttoanything.network.MessageConnectionCardChange;
import connecttoanything.network.NetworkHandler;
import connecttoanything.ref.R;

@SideOnly(Side.CLIENT)
public class GuiConnectionCard extends GuiScreen {

	private InventoryPlayer playerInventory;
	private ItemStack stackCard;
	private GuiTextField textfieldHost;
	private GuiTextField textfieldPort;
	private GuiButton buttonSave;
	private R.GUI gui;

	private static final String REGEX_IP_V4 = "\\b(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b";
	private static final String REGEX_PORT = "[0-9]{1,5}";

	public GuiConnectionCard(InventoryPlayer inventory) {
		playerInventory = inventory;
		stackCard = playerInventory.getCurrentItem();
		gui = R.GUI.CONNECTION_CARD;
	}

	@Override
	public void initGui() {
		super.initGui();

		Keyboard.enableRepeatEvents(true);

		textfieldHost = GUIUtil.createCenteredTextFieldOnGUI(fontRendererObj,
				this, gui, fontRendererObj.getStringWidth("255_255_255_255"),
				30);
		textfieldHost.setTextColor(-1);
		textfieldHost.setDisabledTextColour(-1);
		textfieldHost.setEnableBackgroundDrawing(true);
		textfieldHost.setMaxStringLength(15);
		textfieldHost.setEnabled(true);
		textfieldHost.setText(stackCard.getTagCompound().getString(
				ItemConnectionCard.NBT_HOST));

		textfieldPort = GUIUtil.createCenteredTextFieldOnGUI(fontRendererObj,
				this, gui, fontRendererObj.getStringWidth("_99999_"), 60);
		textfieldPort.setTextColor(-1);
		textfieldPort.setDisabledTextColour(-1);
		textfieldPort.setEnableBackgroundDrawing(true);
		textfieldPort.setMaxStringLength(5);
		textfieldPort.setEnabled(true);
		textfieldPort.setText(String.valueOf(stackCard.getTagCompound()
				.getInteger(ItemConnectionCard.NBT_PORT)));

		buttonSave = GUIUtil.createCenteredButtonOnGUI(0, this, gui, 80,
				textfieldPort.getWidth(), "Save");
		buttonList.add(buttonSave);
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();

		Keyboard.enableRepeatEvents(false);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();

		mc.getTextureManager().bindTexture(gui.getResource());
		drawTexturedModalRect(gui.centerX(width), gui.centerY(height), 0, 0,
				gui.getWidth(), gui.getHeight());

		GUIUtil.drawCenteredStringOnGUI(fontRendererObj,
				ItemsConnectToAnything.itemConnectionCard, this, gui, 5,
				Color.DARK_GRAY.getRGB());

		GUIUtil.drawCenteredStringOnGUI(fontRendererObj, "Host", this, gui, 20,
				Color.DARK_GRAY.getRGB());

		GUIUtil.drawCenteredStringOnGUI(fontRendererObj, "Port", this, gui, 50,
				Color.DARK_GRAY.getRGB());

		textfieldHost.drawTextBox();
		textfieldPort.drawTextBox();

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
			throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		textfieldHost.mouseClicked(mouseX, mouseY, mouseButton);
		textfieldPort.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (textfieldHost.textboxKeyTyped(typedChar, keyCode)) {

		} else if (textfieldPort.textboxKeyTyped(typedChar, keyCode)) {

		} else {
			super.keyTyped(typedChar, keyCode);
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		boolean ipValid = textfieldHost.getText().matches(REGEX_IP_V4)
				|| textfieldHost.getText().equals("localhost");
		boolean portValid = textfieldPort.getText().matches(REGEX_PORT);
		if (!ipValid) {
			textfieldHost.setTextColor(Color.RED.getRGB());
		}
		if (!portValid) {
			textfieldPort.setTextColor(Color.RED.getRGB());
		}

		if (ipValid && portValid) {
			textfieldHost.setTextColor(Color.WHITE.getRGB());
			textfieldPort.setTextColor(Color.WHITE.getRGB());
			NetworkHandler.sendToServer(new MessageConnectionCardChange(
					textfieldHost.getText(), Integer.valueOf(textfieldPort
							.getText())));
		}
	}
}
