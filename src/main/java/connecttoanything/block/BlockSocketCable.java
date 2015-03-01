package connecttoanything.block;

import connecttoanything.ref.R;
import connecttoanything.tileentity.TileEntitySocketCable;
import connecttoanything.tileentity.TileEntitySocketConnector;
import connecttoanything.util.Log;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockSocketCable extends BlockContainer {

	public BlockSocketCable() {
		super(Material.rock);

		setUnlocalizedName(R.Block.SOCKET_CABLE.getUnlocalizedName());
		GameRegistry.registerBlock(this, R.Block.SOCKET_CABLE.getName());
		GameRegistry.registerTileEntity(TileEntitySocketCable.class,
				R.Block.SOCKET_CABLE.getUnlocalizedName());

		setCreativeTab(CreativeTabs.tabBlock);
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
	public int getRenderType() {
		return 3;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT_MIPPED;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntitySocketCable();
	}

}
