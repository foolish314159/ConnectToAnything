package connecttoanything.handler;

import connecttoanything.client.gui.GuiConnectionCard;
import connecttoanything.ref.R;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if (ID == R.GUI.CONNECTION_CARD.ordinal()) {
			return new GuiConnectionCard(player.inventory);
		}
		
		return null;
	}

}
