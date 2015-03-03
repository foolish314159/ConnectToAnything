package connecttoanything.ref;

import javafx.beans.Observable;
import net.minecraft.client.resources.model.ModelResourceLocation;

public class R {

	public static final String MODID = "connecttoanything";
	public static final String MODNAME = "Connect To Anything";
	public static final String VERSION = "0.1";
	public static final String CLIENT_PROXY_CLASS = "connecttoanything.proxy.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "connecttoanything.proxy.ServerProxy";

	public static enum Item {
		CONNECTION_CARD("connection_card");
		
		private String name;

		private Item(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public String getUnlocalizedName() {
			return MODID + "_" + name;
		}

		public ModelResourceLocation getResourceLocation() {
			return new ModelResourceLocation(MODID + ":" + name, "inventory");
		}
	}
	
	public static enum Block {
		SOCKET_CONNECTOR("socket_connector"), SOCKET_CABLE("socket_cable"), SOCKET_READER(
				"socket_reader"), SOCKET_WRITER("socket_writer");

		private String name;

		private Block(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public String getUnlocalizedName() {
			return MODID + "_" + name;
		}

		public ModelResourceLocation getResourceLocation() {
			return new ModelResourceLocation(MODID + ":" + name, "inventory");
		}
	}

}
