package connecttoanything.proxy;

import connecttoanything.init.BlocksConnectToAnything;
import connecttoanything.ref.R;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.item.Item;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenders() {
		ItemModelMesher itemModelMesher = Minecraft.getMinecraft()
				.getRenderItem().getItemModelMesher();

		itemModelMesher
				.register(
						Item.getItemFromBlock(BlocksConnectToAnything.blockSocketConnector),
						0, R.Block.SOCKET_CONNECTOR.getResourceLocation());
		itemModelMesher
				.register(
						Item.getItemFromBlock(BlocksConnectToAnything.blockSocketCable),
						0, R.Block.SOCKET_CABLE.getResourceLocation());
	}

}
