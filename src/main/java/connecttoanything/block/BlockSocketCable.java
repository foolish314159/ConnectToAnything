package connecttoanything.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import connecttoanything.ref.R;
import connecttoanything.tileentity.TileEntitySocketCable;
import connecttoanything.tileentity.TileEntitySocketConnector;

public class BlockSocketCable extends BlockContainerBase {

	public BlockSocketCable() {
		super(TileEntitySocketCable.class, R.Block.SOCKET_CABLE);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntitySocketCable();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos,
			IBlockState state, EntityPlayer playerIn, EnumFacing side,
			float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) {
			TileEntitySocketCable te = (TileEntitySocketCable) worldIn
					.getTileEntity(pos);
			TileEntitySocketConnector master = (TileEntitySocketConnector) te
					.getMaster(null);

			worldIn.setBlockState(new BlockPos(master.getPos().getX(), master
					.getPos().getY() + 1, master.getPos().getZ()),
					Blocks.cobblestone.getDefaultState());
		}

		return true;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT_MIPPED;
	}

}
