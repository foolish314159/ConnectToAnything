package connecttoanything.tileentity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import connecttoanything.api.IConnectionProvider;
import connecttoanything.util.Log;

public class TileEntitySocketCable extends TileEntityConnectionProviderBase {

	@Override
	public boolean isMaster() {
		return false;
	}

	@Override
	public IConnectionProvider getMaster(List<IConnectionProvider> checked) {
		IConnectionProvider master = null;
		if (checked == null) {
			checked = new ArrayList<IConnectionProvider>();
		}

		checked.add(this);
		List<IConnectionProvider> toCheck = getConnectedProviders();
		toCheck.removeAll(checked);

		for (IConnectionProvider connected : toCheck) {
			IConnectionProvider possibleMaster = connected.getMaster(checked);
			if (possibleMaster != null) {
				master = possibleMaster;
				break;
			}
		}

		return master;
	}

	@Override
	public boolean isConnected() {
		return getMaster(null).isConnected();
	}

}
