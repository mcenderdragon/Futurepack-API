package futurepack.api.helper;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;

import org.lwjgl.opengl.GL11;

public class HelperRenderBlocks
{
	private static void renderBlock(IBlockState state, BlockPos pos, IBlockAccess acces)
	{
		BlockRendererDispatcher renderer = Minecraft.getMinecraft().getBlockRendererDispatcher();
		
		Minecraft.getMinecraft().renderEngine.bindTexture(Minecraft.getMinecraft().getTextureMapBlocks().locationBlocksTexture);
		Tessellator tes = Tessellator.getInstance();
		tes.getWorldRenderer().begin(7, DefaultVertexFormats.BLOCK);
		
		//tes.getWorldRenderer().setTranslation((double)(pos.getX()), (double)(pos.getY()), (double)(pos.getZ()));
		//renderer.renderBlockBrightness(state, acces.getCombinedLight(pos, 0)/15F);
		
		renderer.renderBlock(state, pos, acces, tes.getWorldRenderer());

		
//		IBakedModel model = renderer.getModelFromBlockState(state, acces, pos);
//		EnumFacing[] faces = EnumFacing.values();
//		for(int i=0;i<faces.length;i++)
//		{
//			EnumFacing face = faces[i];
//			System.out.println(face.name());
//			List<BakedQuad> l1 = model.getFaceQuads(face);
//			for(BakedQuad qu : l1)
//			{
//				int[] vertex = qu.getVertexData();
//				System.out.println(Arrays.toString(vertex));
//				for(int j=0;j<4;j++)
//				{
//					int bn = j *7;
//					System.out.println("Vertex:"+j);
//					System.out.println(String.format("X:%s, Y:%s, Z:%s",Float.intBitsToFloat(vertex[0+bn]),Float.intBitsToFloat(vertex[1+bn]),Float.intBitsToFloat(vertex[2+bn])));
//					System.out.println("Color:"+vertex[3+bn]);
//					System.out.println(String.format("U:%s, V:%s",Float.intBitsToFloat(vertex[4+bn]),Float.intBitsToFloat(vertex[5+bn])));
//					System.out.println("Pixelsize:" + (0.40623048-0.37501952)*0.0625);
//					System.out.println("Zero:"+vertex[6+bn]);
//				}
//				
//				
//			}
//		}

		//tes.getWorldRenderer().setTranslation(0,0,0);
		tes.draw();
	}
	
	public static void renderBlock(Block b, int meta, BlockPos at, World w)
	{
		IBlockState state = b.getStateFromMeta(meta);
		renderBlock(state, at, w);
	}
	
	public static void renderBlock(IBlockState state, BlockPos at, World w)
	{
		GlStateManager.disableLighting();
		GL11.glTranslated(-at.getX(), -at.getY(), -at.getZ());
		
		IBlockAccess acc = new TemporaryWorld(w, at, state);
		renderBlock(state, at, acc);
		
		GL11.glTranslated(at.getX(), at.getY(), at.getZ());
		GlStateManager.enableLighting();
	}
	
	public static MiniWorld create(World original, BlockPos start, BlockPos end, EnumFacing face, Vec3 rotpoint)
	{
		MiniWorld w = new MiniWorld(start, end.subtract(start));
		w.extendedLevels = original.extendedLevelsInChunkCache();
		w.type = original.getWorldType();
		w.face = face;
		w.rotationpoint = rotpoint;
		
		for(BlockPos pos : ((Iterable<BlockPos>)BlockPos.getAllInBox(start, end)))
		{
			w.setObject(w.states, pos, original.getBlockState(pos));
			w.setObject(w.tiles, pos, original.getTileEntity(pos));
			w.setObject(w.bioms, pos, original.getBiomeGenForCoords(pos));
			
			Integer[] red = new Integer[6];
			for(int i=0;i<red.length;i++)
			{
				red[i] = original.getStrongPower(pos, EnumFacing.getFront(i));
			}
			w.setObject(w.redstone, pos, red);
			
			w.setObject(w.skylight, pos,  original.getLightFromNeighborsFor(EnumSkyBlock.SKY, pos));
			w.setObject(w.blocklight, pos, original.getLightFromNeighborsFor(EnumSkyBlock.BLOCK, pos));
			
		}
		return w;
	}
	
	public static void renderWorld(MiniWorld w)
	{
		GlStateManager.disableLighting();
		
		//GL11.glTranslated(w.rotationpoint.xCoord, w.rotationpoint.yCoord, w.rotationpoint.zCoord);
		GL11.glRotatef(w.rot, w.face.getFrontOffsetX(),  w.face.getFrontOffsetY(),  w.face.getFrontOffsetZ());
		GL11.glTranslated(-w.rotationpoint.xCoord, -w.rotationpoint.yCoord, -w.rotationpoint.zCoord);
		
		GL11.glTranslated(-w.start.getX(), -w.start.getY(), -w.start.getZ());
		
		BlockRendererDispatcher renderer = Minecraft.getMinecraft().getBlockRendererDispatcher();
		
		Minecraft.getMinecraft().renderEngine.bindTexture(Minecraft.getMinecraft().getTextureMapBlocks().locationBlocksTexture);
		Tessellator tes = Tessellator.getInstance();
		tes.getWorldRenderer().begin(7, DefaultVertexFormats.BLOCK);
		
		for(int x=0;x<w.width;x++)
		{
			for(int y=0;y<w.height;y++)
			{
				for(int z=0;z<w.depth;z++)
				{
					BlockPos pos = w.start.add(x,y,z);
					
					IBlockState b = w.getBlockState(pos);
					if(b.getBlock()==Blocks.air)
						continue;
					
					IBlockState state = b.getBlock().getActualState(b, w, pos);
					renderer.renderBlock(state, pos, w, tes.getWorldRenderer());
				}
			}
		}
		
		tes.draw();
		
		GL11.glTranslated(w.start.getX(), w.start.getY(), w.start.getZ());
		
		GL11.glTranslated(w.rotationpoint.xCoord, w.rotationpoint.yCoord, w.rotationpoint.zCoord);
		GL11.glRotatef(-w.rot, w.face.getFrontOffsetX(),  w.face.getFrontOffsetY(),  w.face.getFrontOffsetZ());
		//GL11.glTranslated(-w.rotationpoint.xCoord, -w.rotationpoint.yCoord, -w.rotationpoint.zCoord);
		
		GlStateManager.enableLighting();
	}
	
	private static class TemporaryWorld implements IBlockAccess
	{
		IBlockAccess original;
		BlockPos pos;
		IBlockState fake;
		TileEntity e;
		boolean debug = true;
		
		public TemporaryWorld(IBlockAccess ori, BlockPos p, IBlockState f, TileEntity tile) 
		{
			original = ori;
			pos = p;
			fake = f;
			e = tile;
			debug = false;
		}
		
		public TemporaryWorld(World ori, BlockPos p, IBlockState f) 
		{
			this(ori, p, f, f.getBlock().createTileEntity(ori, f));
			debug = true;
		}
		
		@Override
		public TileEntity getTileEntity(BlockPos pos)
		{
			if(pos.equals(this.pos))
			{
				return e;
			}
			return original.getTileEntity(pos);
		}

		@Override
		public int getCombinedLight(BlockPos pos, int p_175626_2_)
		{
			return original.getCombinedLight(pos, p_175626_2_);
		}

		@Override
		public IBlockState getBlockState(BlockPos pos)
		{
			if(pos.equals(this.pos))
			{
				return fake;
			}
			return original.getBlockState(pos);
		}

		@Override
		public boolean isAirBlock(BlockPos pos)
		{
			return original.isAirBlock(pos);
		}

		@Override
		public BiomeGenBase getBiomeGenForCoords(BlockPos pos)
		{
			return original.getBiomeGenForCoords(pos);
		}

		@Override
		public boolean extendedLevelsInChunkCache()
		{
			return original.extendedLevelsInChunkCache();
		}

		@Override
		public int getStrongPower(BlockPos pos, EnumFacing direction)
		{
			return original.getStrongPower(pos, direction);
		}

		@Override
		public WorldType getWorldType()
		{
			return debug ? WorldType.DEBUG_WORLD : original.getWorldType();
		}

		@Override
		public boolean isSideSolid(BlockPos pos, EnumFacing side, boolean _default)
		{
			return original.isSideSolid(pos, side, _default);
		}
		
	}
	
	public static class MiniWorld implements IBlockAccess
	{
		TileEntity[][][] tiles;
		IBlockState[][][] states;
		BiomeGenBase[][][] bioms;
		Integer[][][][] redstone;
		Integer[][][] skylight;
		Integer[][][] blocklight;
		
		boolean extendedLevels;
		WorldType type;
		
		BlockPos start;
		int height,width,depth;
		
		EnumFacing face;
		Vec3 rotationpoint;
		
		/**In degrees*/
		float rot;
		
		private MiniWorld(BlockPos start, Vec3i whd)
		{
			this.start = start;
			width=whd.getX();
			height=whd.getY();
			depth=whd.getZ();
			tiles = new TileEntity[width+1][height+1][depth+1];
			states = new IBlockState[width+1][height+1][depth+1];
			bioms = new BiomeGenBase[width+1][height+1][depth+1];
			redstone = new Integer[width+1][height+1][depth+1][6];
			skylight = new Integer[width+1][height+1][depth+1];
			blocklight = new Integer[width+1][height+1][depth+1];
			
		}
		
		@Override
		public TileEntity getTileEntity(BlockPos pos)
		{
			if(isInBounds(pos))
			{
				return getObject(tiles, pos);
			}
			return null;
		}

		@Override
		public int getCombinedLight(BlockPos pos, int val)
		{
			if(isInBounds(pos))
			{
				
//				double sin = Math.sin(Math.toRadians(rot));
//				double cos = Math.cos(Math.toRadians(rot));
//				
//				int x = pos.getX() - start.getX();
//				int y = pos.getY() - start.getY();
//				int z = pos.getZ() - start.getZ();
//				
//				Vec3 blockVec = rotationpoint.subtract(x, y, z);
//				pos = start.add(rotationpoint.xCoord,rotationpoint.yCoord,rotationpoint.zCoord);
//				
//				if(face.getAxis()==Axis.X)
//				{
//					double dis = blockVec.distanceTo(new Vec3(blockVec.xCoord, 0, 0));
//					sin*=dis;
//					cos*=dis;
//					
//					pos = pos.offset(face.rotateAround(Axis.Z), (int)sin).offset(face.rotateAround(Axis.Y), (int)cos);				
//				}
//				else if(face.getAxis()==Axis.Y)
//				{
//					double dis = blockVec.distanceTo(new Vec3(0, blockVec.yCoord, 0));
//					sin*=dis;
//					cos*=dis;
//									
//					pos = pos.offset(face.rotateAround(Axis.X), (int)sin).offset(face.rotateAround(Axis.Z), (int)cos);					
//				}
//				else if(face.getAxis()==Axis.Z)
//				{
//					double dis = blockVec.distanceTo(new Vec3(0, 0, blockVec.zCoord));
//					sin*=dis;
//					cos*=dis;
//					
//					pos = pos.offset(face.rotateAround(Axis.X), (int)sin).offset(face.rotateAround(Axis.Y), (int)cos);									
//				}
//				
				int j = getObject(skylight, pos);
		        int k = getObject(blocklight, pos);

		        if (k < val)
		        {
		            k = val;
		        }

		        return j << 20 | k << 4;
			}
			return 15 << 20 | 15 << 4;
			
			
		}

		@Override
		public IBlockState getBlockState(BlockPos pos)
		{
			if(isInBounds(pos))
			{
				return getObject(states, pos);
			}
			return Blocks.air.getDefaultState();
		}

		@Override
		public boolean isAirBlock(BlockPos pos)
		{
			return getBlockState(pos).getBlock().isAir(this, pos);
		}

		@Override
		public BiomeGenBase getBiomeGenForCoords(BlockPos pos)
		{
			if(isInBounds(pos))
			{
				return getObject(bioms, pos);
			}
			return BiomeGenBase.plains;
		}

		@Override
		public boolean extendedLevelsInChunkCache()
		{
			return extendedLevels;
		}

		@Override
		public int getStrongPower(BlockPos pos, EnumFacing direction)
		{
			if(isInBounds(pos))
			{
				return getObject(redstone, pos)[direction.getIndex()];
			}
			return 0;
		}

		@Override
		public WorldType getWorldType()
		{
			return type;
		}

		@Override
		public boolean isSideSolid(BlockPos pos, EnumFacing side, boolean _default)
		{
			return getBlockState(pos).getBlock().isSideSolid(this, pos, side);
		}
		
		private boolean isInBounds(BlockPos pos)
		{
			int x = pos.getX() - start.getX();
			int y = pos.getY() - start.getY();
			int z = pos.getZ() - start.getZ();
			
			if(x>=0 && x<width)
			{
				if(y>=0 && y<height)
				{
					if(z>=0 && z<depth)
					{
						return true;
					}
				}
			}
			
			return false;
		}	
		
		private<T> T getObject(T[][][] var, BlockPos pos)
		{
			int x = pos.getX() - start.getX();
			int y = pos.getY() - start.getY();
			int z = pos.getZ() - start.getZ();
			
			return var[x][y][z];
		}
		
		private<T> void setObject(T[][][] var, BlockPos pos, T val)
		{
			int x = pos.getX() - start.getX();
			int y = pos.getY() - start.getY();
			int z = pos.getZ() - start.getZ();
			
			var[x][y][z] = val;
		}
		
		public void setRotation(float f)
		{
			this.rot = f;
		}
		
	}

}
