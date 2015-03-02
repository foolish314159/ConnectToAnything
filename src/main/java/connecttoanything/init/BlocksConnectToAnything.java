package connecttoanything.init;

import net.minecraft.block.Block;
import connecttoanything.block.BlockSocketCable;
import connecttoanything.block.BlockSocketConnector;
import connecttoanything.block.BlockSocketReader;

public class BlocksConnectToAnything {

	public static Block blockSocketConnector;
	public static Block blockSocketCable;
	public static Block blockSocketReader;

	public static void init() {
		blockSocketConnector = new BlockSocketConnector();
		blockSocketCable = new BlockSocketCable();
		blockSocketReader = new BlockSocketReader();
	}

}
