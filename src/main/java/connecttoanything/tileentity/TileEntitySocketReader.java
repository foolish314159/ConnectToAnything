package connecttoanything.tileentity;

import java.net.Socket;

import connecttoanything.api.IConnectionListener;
import connecttoanything.util.Log;

public class TileEntitySocketReader extends TileEntityConnectionProviderBase
		implements IConnectionListener {

	@Override
	public void onConnected(Socket s) {
		Log.info("Reader notified: Connected");
	}

	@Override
	public void onFail(Exception e) {
		Log.severe("Reader notified: " + e.getMessage());
	}

}
