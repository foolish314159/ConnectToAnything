package connecttoanything.api;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import connecttoanything.util.Log;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3i;

/**
 * Basic version of a {@link TileEntity} implementing
 * {@link IConnectionProvider} which basically behaves like a cable.<br>
 * Can be subclassed by custom blocks to give access to a network while adding
 * additionally functionality.
 * 
 * @author Tom
 *
 */
public abstract class TileEntityConnectionProviderBase extends TileEntity
		implements IConnectionProvider {

	@Override
	public List<IConnectionProvider> getConnectedProviders() {
		final List<IConnectionProvider> connected = new ArrayList<IConnectionProvider>();

		Iterable<BlockPos> positions = BlockPos.getAllInBox(
				new BlockPos(pos.getX() - 1, pos.getY() - 1, pos.getZ() - 1),
				new BlockPos(pos.getX() + 2, pos.getY() + 2, pos.getZ() + 2));

		positions.forEach(new Consumer<BlockPos>() {
			@Override
			public void accept(BlockPos p) {
				TileEntity te = worldObj.getTileEntity(p);
				if (te instanceof IConnectionProvider) {
					connected.add((IConnectionProvider) te);
				}
			}
		});

		return connected;
	}

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
		IConnectionProvider master = getMaster(null);
		return master != null && master.isConnected();
	}

	@Override
	public void addConnectionListener(BlockPos pos, IConnectionListener listener) {
		IConnectionProvider master = getMaster(null);
		if (master != null) {
			master.addConnectionListener(pos, listener);
		}
	}

}
