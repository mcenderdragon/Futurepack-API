//package futurepack.api.resources;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.Calendar;
//import java.util.Iterator;
//import java.util.function.Predicate;
//
//import com.google.gson.JsonIOException;
//import com.google.gson.JsonSyntaxException;
//
//import futurepack.api.resources.AssetsDownloader.Resource;
//import net.minecraft.client.Minecraft;
//import net.minecraft.resources.IReloadableResourceManager;
//import net.minecraft.resources.IResource;
//import net.minecraft.resources.IResourceManager;
//import net.minecraft.resources.SimpleReloadableResourceManager;
//import net.minecraftforge.resource.IResourceType;
//import net.minecraftforge.resource.ISelectiveResourceReloadListener;
//
////@ TODO: OnlyIn(Dist.CLIENT)
//public class FuturepackDefaultResources
//{
//	public static final boolean allowDownloading = false;
//	
//	public static void downloadFuturepackRersources()
//	{
//		File assets = new File(AssetsManager.getMinecraftDir(), "assets");
//		try {
//			int year = Calendar.getInstance().get(Calendar.YEAR);
//			if(year <= 2020)//dont ddos the server from old clients
//			{
//				AssetsDownloader down = new AssetsDownloader("Futurepack", "1.12.2", assets);
//				registerFuturepackDefaults(down);
//				down.getResourcesToDownload().forEach(r -> r.doDownload = allowDownloading);
//				Thread t = new Thread(down, down.toString());
//				t.start();
//			}
//		} catch (JsonIOException e) {
//			e.printStackTrace();
//		} catch (JsonSyntaxException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public static void registerFuturepackDefaults(AssetsDownloader down)
//	{
//		try 
//		{
//			URL urlBase = new URL("https://redsnake-games.de/Downloads/fp/resources/1.12/sounds/");
//			String[] paths = new String[]
//			{
//				"airlock.ogg",
//				"corelaser.ogg",
//				"elektrofeld.ogg",
//				"generator.ogg",
//				"granatschuss.ogg",
//				"jump.ogg",
//				"laserschuss1.ogg",
//				"laserschuss2.ogg",
//				"loade.ogg",
//				"optibench.ogg",
//				"part_press.ogg",
//				"shield_hit.ogg",
//				"soundtrack.ogg",
//				"unknown.ogg",
//				"voice_entros.ogg",
//				"voice_envia.ogg",
//				"voice_menelaus.ogg",
//				"voice_tyros.ogg"
//			};
//			down.registerResources(urlBase, "futurepack:sounds/", paths);
//			urlBase = new URL("https://redsnake-games.de/Downloads/fp/resources/1.12/textures/gui/");
//			paths = new String[]
//			{
//				"research_bg.jpg",
//				"research_bg_2.jpg",
//				"research_bg_3.jpg"
//			};
//			down.registerResources(urlBase, "futurepack:textures/gui/", paths);
//			
//			down.registerDownloadCallback(FuturepackDefaultResources::extractDefaults);
//			down.registerFinishedCallback(FuturepackDefaultResources::registerResourcePack);
//		}
//		catch (MalformedURLException e)
//		{
//			e.printStackTrace();
//		}
//	}
//	
//	public static void extractDefaults(AssetsDownloader down)
//	{
//		if(!down.isDownloadFinished())
//			throw new IllegalStateException("Call when downloads have finished");
//		
//		IResourceManager man = Minecraft.getInstance().getResourceManager();
//		Iterator<Resource> iter = down.getResourcesToDownload().iterator();
//		while(iter.hasNext())
//		{
//			Resource r = iter.next();
//			if(r.downloaded==false)
//			{
//				try
//				{
//					IResource ir = man.getResource(r.res);
//					if(ir==null)
//						continue;
//					InputStream stream = ir.getInputStream();
//					if(stream == null)
//						continue;
//					
//					ByteArrayOutputStream bout = new ByteArrayOutputStream(stream.available());
//					byte[] data = new byte[4096];
//					while(stream.available()>0)
//					{
//						int l = stream.read(data);
//						bout.write(data, 0, l);
//					}
//					bout.close();
//					stream = null;
//					ir = null;
//					byte[] memoryFile = bout.toByteArray();
//					bout = null;
//					ByteArrayInputStream bin = new ByteArrayInputStream(memoryFile);
//					String hash = AssetsManager.toHexHash(AssetsManager.createFileHash(bin)).toLowerCase();
//					r.localHash = hash;
//					bin.reset();
//					stream = bin;
//					
//					File resource = AssetsManager.getFile(down.getObjectsDir(), hash);
//					OutputStream out = new FileOutputStream(resource);
//					data = new byte[4096];
//					while(stream.available()>0)
//					{
//						int l = stream.read(data);
//						out.write(data, 0, l);
//					}
//					bin.close();
//					bin = null;
//					memoryFile = null;
//					out.close();
//					r.localFile = resource;
//					r.size = resource.length();
//					out = null;
//
//					
//					down.onSuccsess(r);
//				}
//				catch (IOException e)
//				{
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//	
//	public static void registerResourcePack(AssetsDownloader down)
//	{
//		try
//		{
//			JsonBasedResourcePack pack = new JsonBasedResourcePack(down.modname, down.assetsVersion, down.getObjectsDir().getParentFile(), true);
//			ISelectiveResourceReloadListener reload = new ISelectiveResourceReloadListener()
//			{
//				@Override
//				public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate)
//				{
//					if(resourceManager instanceof SimpleReloadableResourceManager)
//					{
//						((SimpleReloadableResourceManager) resourceManager).addResourcePack(pack);
//					}
//				}
//			};
//			IReloadableResourceManager manager = ((IReloadableResourceManager)Minecraft.getInstance().getResourceManager());
//			manager.addReloadListener(reload);
//		}
//		catch (JsonIOException e)
//		{
//			e.printStackTrace();
//		}
//		catch (JsonSyntaxException e) {
//			e.printStackTrace();
//		}
//		catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//}
