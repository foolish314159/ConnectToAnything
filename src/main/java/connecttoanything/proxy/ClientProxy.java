package connecttoanything.proxy;

import connecttoanything.init.BlocksConnectToAnything;
import connecttoanything.init.ItemsConnectToAnything;
import connecttoanything.ref.R;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.item.Item;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenders() {
		ItemModelMesher itemModelMesher = Minecraft.getMinecraft()
				.getRenderItem().getItemModelMesher();

		// Blocks
		registerRender(itemModelMesher,
				BlocksConnectToAnything.blockSocketConnector,
				R.Block.SOCKET_CONNECTOR);
		registerRender(itemModelMesher,
				BlocksConnectToAnything.blockSocketCable, R.Block.SOCKET_CABLE);
		registerRender(itemModelMesher,
				BlocksConnectToAnything.blockSocketReader,
				R.Block.SOCKET_READER);

		// Items
		registerRender(itemModelMesher,
				ItemsConnectToAnything.itemConnectionCard,
				R.Item.CONNECTION_CARD);
	}

	private static void registerRender(ItemModelMesher itemModelMesher,
			Block block, connecttoanything.ref.R.Block blockRef) {
		itemModelMesher.register(Item.getItemFromBlock(block), 0,
				blockRef.getResourceLocation());
	}

	private static void registerRender(ItemModelMesher itemModelMesher,
			Item item, connecttoanything.ref.R.Item itemRef) {
		itemModelMesher.register(item, 0, itemRef.getResourceLocation());
	}

}
