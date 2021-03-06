package connecttoanything.block;

import java.util.ArrayList;
import java.util.List;

import connecttoanything.ConnectToAnything;
import connecttoanything.ref.R;
import connecttoanything.tileentity.TileEntitySocketConnector;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSocketConnector extends BlockContainerBase {

	public BlockSocketConnector() {
		super(TileEntitySocketConnector.class, R.Block.SOCKET_CONNECTOR);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntitySocketConnector();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos,
			IBlockState state, EntityPlayer playerIn, EnumFacing side,
			float hitX, float hitY, float hitZ) {
		playerIn.openGui(ConnectToAnything.instance,
				R.GUI.SOCKET_CONNECTOR.ordinal(), worldIn, pos.getX(),
				pos.getY(), pos.getZ());

		return true;
	}

}
