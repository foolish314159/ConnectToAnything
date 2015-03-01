package connecttoanything.tileentity;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import connecttoanything.api.IConnectionProvider;
import connecttoanything.util.Log;

public class TileEntitySocketConnector extends TileEntityConnectionProviderBase {

	// Socket connection constants
	private static final String HOST_UNDEFINED = "";
	private static final int PORT_UNDEFINED = -1;

	// NBT keys
	private static final String NBT_BASE = TileEntitySocketConnector.class
			.getSimpleName();
	private static final String NBT_HOST = NBT_BASE + ".host";
	private static final String NBT_PORT = NBT_BASE + ".port";

	// Tile members
	private Socket socket = null;
	private String host = HOST_UNDEFINED;
	private int port = PORT_UNDEFINED;

	public void connect(String host, int port, boolean disconnect) {
		if (disconnect && socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				Log.severe(e.getMessage());
			} finally {
				socket = null;
			}
		} else if (!host.equals(HOST_UNDEFINED) && port != PORT_UNDEFINED) {
			this.host = host;
			this.port = port;
			try {
				socket = new Socket(host, port);
			} catch (UnknownHostException e) {
				Log.severe(e.getMessage());
			} catch (IOException e) {
				Log.severe(e.getMessage());
			}
		}
	}

	public void writeLine(String msg) {
		if (socket != null) {
			try {
				socket.getOutputStream().write((msg + "\n").getBytes());
			} catch (IOException e) {
				Log.severe(e.getMessage());
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		host = compound.getString(NBT_HOST);
		port = compound.getInteger(NBT_PORT);
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		compound.setString(NBT_HOST, host);
		compound.setInteger(NBT_PORT, port);

		connect(HOST_UNDEFINED, PORT_UNDEFINED, true);
	}

	@Override
	public boolean isMaster() {
		return true;
	}

	@Override
	public IConnectionProvider getMaster(List<IConnectionProvider> checked) {
		return this;
	}

	@Override
	public boolean isConnected() {
		return socket != null && !socket.isClosed();
	}

}
