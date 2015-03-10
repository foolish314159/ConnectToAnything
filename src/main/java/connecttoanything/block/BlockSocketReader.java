package connecttoanything.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import connecttoanything.ref.R;
import connecttoanything.tileentity.TileEntitySocketReader;

public class BlockSocketReader extends BlockContainerBase {

	public BlockSocketReader() {
		super(TileEntitySocketReader.class, R.Block.SOCKET_READER);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntitySocketReader();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos,
			IBlockState state, EntityPlayer playerIn, EnumFacing side,
			float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			TileEntitySocketReader te = (TileEntitySocketReader) worldIn
					.getTileEntity(pos);
			te.addConnectionListener(pos, te);
			te.addReader(pos, te);
		}

		return true;
	}

}
