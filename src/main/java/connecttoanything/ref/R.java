package connecttoanything.ref;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;

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

	public static enum GUI {
		CONNECTION_CARD("item/connection_card.png", 176, 110), SOCKET_CONNECTOR(
				"inventory/socket_connector.png", 176, 166), SOCKET_READER(
				"inventory/socket_reader.png", 176, 166);

		private ResourceLocation res;
		private int width, height;

		private GUI(String resourceLocation, int width, int height) {
			res = new ResourceLocation(MODID + ":textures/gui/"
					+ resourceLocation);
			this.width = width;
			this.height = height;
		}

		public ResourceLocation getResource() {
			return res;
		}

		public int centerX(int screenWidth) {
			return (screenWidth - width) / 2;
		}

		public int centerY(int screenHeight) {
			return (screenHeight - height) / 2;
		}

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}
	}

}
