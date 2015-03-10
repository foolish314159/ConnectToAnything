package connecttoanything.tileentity;

import java.net.Socket;

import connecttoanything.api.IConnectionListener;
import connecttoanything.api.IReader;
import connecttoanything.api.TileEntityConnectionProviderBase;
import connecttoanything.util.Log;

public class TileEntitySocketReader extends TileEntityConnectionProviderBase
		implements IConnectionListener, IReader {

	@Override
	public void onConnected(Socket s) {
		Log.info("Reader notified: Connected");
	}

	@Override
	public void onFail(Exception e) {
		Log.severe("Reader notified: " + e.getMessage());
	}

	@Override
	public void onDisconnected() {
		Log.info("Reader notified: Disconnected");
	}

	@Override
	public void onRead(String line) {
		Log.info("Reader read line: " + line);
	}

}
