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
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT_MIPPED;
	}

}
