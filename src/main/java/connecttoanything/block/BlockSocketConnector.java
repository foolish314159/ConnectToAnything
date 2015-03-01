package connecttoanything.block;

import connecttoanything.ref.R;
import connecttoanything.tileentity.TileEntitySocketConnector;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockSocketConnector extends BlockContainer {

	public BlockSocketConnector() {
		super(Material.rock);

		setUnlocalizedName(R.Block.SOCKET_CONNECTOR.getUnlocalizedName());
		GameRegistry.registerBlock(this, R.Block.SOCKET_CONNECTOR.getName());
		GameRegistry.registerTileEntity(TileEntitySocketConnector.class,
				R.Block.SOCKET_CONNECTOR.getUnlocalizedName());

		setCreativeTab(CreativeTabs.tabBlock);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntitySocketConnector();
	}

	@Override
	public int getRenderType() {
		return 3;
	}
}
