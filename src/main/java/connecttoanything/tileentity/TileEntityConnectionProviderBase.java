package connecttoanything.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import connecttoanything.api.IConnectionProvider;
import connecttoanything.util.Log;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3i;

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
}
