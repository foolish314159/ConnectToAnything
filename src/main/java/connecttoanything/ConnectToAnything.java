package connecttoanything;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import connecttoanything.handler.GUIHandler;
import connecttoanything.init.BlocksConnectToAnything;
import connecttoanything.init.ItemsConnectToAnything;
import connecttoanything.network.NetworkHandler;
import connecttoanything.proxy.CommonProxy;
import connecttoanything.ref.R;
import connecttoanything.tileentity.TileEntitySocketConnector;

@Mod(modid = R.MODID, name = R.MODNAME, version = R.VERSION)
public class ConnectToAnything {

	@Instance(R.MODID)
	public static ConnectToAnything instance;

	@SidedProxy(clientSide = R.CLIENT_PROXY_CLASS, serverSide = R.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	@EventHandler
	public void preinit(FMLPreInitializationEvent event) {
		BlocksConnectToAnything.init();
		ItemsConnectToAnything.init();
		NetworkHandler.init();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GUIHandler());
		proxy.registerRenders();
	}

	@EventHandler
	public void serverStopping(FMLServerStoppingEvent event) {
		for (Socket s : TileEntitySocketConnector.sockets) {
			if (s != null) {
				try {
					s.close();
				} catch (IOException e) {
				}
			}
		}
	}

}