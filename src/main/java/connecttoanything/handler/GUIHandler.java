package connecttoanything.handler;

import connecttoanything.client.gui.GuiConnectionCard;
import connecttoanything.client.gui.inventory.GuiSocketConnector;
import connecttoanything.inventory.ContainerSocketConnector;
import connecttoanything.ref.R;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if (ID == R.GUI.SOCKET_CONNECTOR.ordinal()) {
			return new ContainerSocketConnector(player.inventory,
					(IInventory) world.getTileEntity(new BlockPos(x, y, z)),
					player);
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if (ID == R.GUI.CONNECTION_CARD.ordinal()) {
			return new GuiConnectionCard(player.inventory);
		} else if (ID == R.GUI.SOCKET_CONNECTOR.ordinal()) {
			return new GuiSocketConnector(
					(IInventory) world.getTileEntity(new BlockPos(x, y, z)));
		}

		return null;
	}

}
