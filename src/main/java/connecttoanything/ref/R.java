package connecttoanything.ref;

public class R {

	public static final String MODID = "connecttoanything";
	public static final String MODNAME = "Connect To Anything";
	public static final String VERSION = "0.1";
	public static final String CLIENT_PROXY_CLASS = "connecttoanything.proxy.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "connecttoanything.proxy.ServerProxy";

	public static enum Blocks {
		TODO("todo");

		private String name;

		private Blocks(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public String getUnlocalizedName() {
			return MODID + "_" + name;
		}

		public String getResourceLocation() {
			return MODID + ":" + name;
		}
	}

}
