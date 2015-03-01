package connecttoanything.init;

import net.minecraft.block.Block;
import connecttoanything.block.BlockSocketConnector;

public class BlocksConnectToAnything {

	public static Block blockSocketConnector;

	public static void init() {
		blockSocketConnector = new BlockSocketConnector();
	}

}
