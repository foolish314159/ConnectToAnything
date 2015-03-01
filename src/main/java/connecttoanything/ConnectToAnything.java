package connecttoanything;

import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import connecttoanything.block.BlockSocketConnector;
import connecttoanything.init.BlocksConnectToAnything;
import connecttoanything.proxy.CommonProxy;
import connecttoanything.ref.R;
import connecttoanything.tileentity.TileEntitySocketConnector;
import connecttoanything.util.Log;

@Mod(modid = R.MODID, name = R.MODNAME, version = R.VERSION)
public class ConnectToAnything {

	@SidedProxy(clientSide = R.CLIENT_PROXY_CLASS, serverSide = R.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	@EventHandler
	public void preinit(FMLPreInitializationEvent event) {

	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		BlocksConnectToAnything.init();
		proxy.registerRenders();
	}

}