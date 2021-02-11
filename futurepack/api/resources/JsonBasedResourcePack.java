//package futurepack.api.resources;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.Set;
//import java.util.function.Predicate;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonIOException;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonSyntaxException;
//import com.google.gson.stream.JsonReader;
//
//import net.minecraft.resources.IResourcePack;
//import net.minecraft.resources.ResourcePackType;
//import net.minecraft.resources.data.IMetadataSectionSerializer;
//import net.minecraft.resources.data.PackMetadataSection;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.text.StringTextComponent;
//
////@ TODO: OnlyIn(Dist.CLIENT)
//public class JsonBasedResourcePack implements IResourcePack
//{
//	public String description = "External Resources";
//	public String name;
//	private Set<String> domains;
//	private Map<String, String> fileSystem;
//	private File objects;
//	
//	public JsonBasedResourcePack(String modname, String assetsVersion, File repositoryDir, boolean checkHashes) throws JsonIOException, JsonSyntaxException, IOException
//	{
//		name = modname + " default assets";
//		domains = new HashSet<String>(4);
//		
//		AssetsManager.isAssetsFolder(repositoryDir);
//		File indexes = new File(repositoryDir, "indexes");
//		objects = new File(repositoryDir, "objects");
//		File main = new File(indexes, modname+"-"+assetsVersion + ".json");
//		if(!main.exists())
//			throw new IllegalArgumentException("assets repository json not found");
//				
//		System.out.println("Start loading repofile");
//		Gson gson = new Gson();
//		JsonReader read = new JsonReader(new FileReader(main));
//		JsonObject obj = gson.fromJson(read, JsonObject.class);
//		read.close();
//		obj = obj.getAsJsonObject("objects");
//		Iterator<Entry<String, JsonElement>> iter = obj.entrySet().iterator();
//		int startS = obj.size();
//		
//		fileSystem = new HashMap<>(obj.size());
//		while(iter.hasNext())
//		{
//			Entry<String, JsonElement> e = iter.next();
//			JsonObject data = e.getValue().getAsJsonObject();
//			long size = data.get("size").getAsLong();
//			String hexHash = data.get("hash").getAsString().toLowerCase();
//			
//			File resource = new File(objects, hexHash.substring(0, 2) + "/" + hexHash);
//			if(resource.exists() && resource.isFile())
//			{
//				if(resource.length() != size)
//				{
//					iter.remove();
//				}
//				else
//				{
//					String path = e.getKey();
//					if(checkHashes)
//					{
//						String fileHashSHA1 = AssetsManager.toHexHash(AssetsManager.createFileHash(new FileInputStream(resource))).toLowerCase();
//						if(!fileHashSHA1.equals(hexHash))
//						{
//							System.out.printf("%s: %s != %s", path, hexHash, fileHashSHA1);
//							iter.remove();
//							continue;
//						}
//					}
//					String[] sp = path.split("/", 2);
//					if(sp.length!=2)
//						continue;
//					
//					domains.add(sp[0]);
//					fileSystem.put(sp[0] + ":" + sp[1], hexHash);
//				}
//			}
//			else
//			{
//				iter.remove();
//			}
//		}
//		
//		if(startS != obj.size())
//		{
//			System.out.println(startS + " != " + obj.size());
//		}
//	}
//	
//
//	@Override
//	public InputStream getResourceStream(ResourcePackType arg0, ResourceLocation location) throws IOException
//	{
//		String hexHash = fileSystem.get(location.toString());
//		if(hexHash!=null)
//		{
//			File resource = AssetsManager.getFile(objects, hexHash);
//			if(resource.exists() && resource.isFile())
//			{
//				return new FileInputStream(resource);
//			}
//			else
//			{
//				throw new FileNotFoundException(resource.toString());
//			}
//		}
//		throw new FileNotFoundException(location.toString());
//	}
//
//	@Override
//	public boolean resourceExists(ResourcePackType arg0, ResourceLocation location)
//	{
//		return fileSystem.containsKey(location.toString());
//	}
//
//	@Override
//	public Set<String> getResourceNamespaces(ResourcePackType arg0)
//	{
//		return domains;
//	}
//
//	@Override
//	public String getName()
//	{
//		return name;
//	}
//
//
//	@Override
//	public void close() throws IOException
//	{
//		// TODO Auto-generated method stub
//	}
//
//	@Override
//	public Collection<ResourceLocation> getAllResourceLocations(ResourcePackType arg0, String arg1, int arg2, Predicate<String> arg3)
//	{
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//
//	@Override
//	public <T> T getMetadata(IMetadataSectionSerializer<T> arg0) throws IOException
//	{
//		if(arg0.getSectionName().equals("pack"))
//		{
//			return (T) new PackMetadataSection(new StringTextComponent(description), 3);
//		}
//		return null;
//	}
//
//	@Override
//	public InputStream getRootResourceStream(String arg0) throws IOException
//	{
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//
//	
//}
/*
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Predicate;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.resources.data.IMetadataSectionSerializer;
import net.minecraft.resources.data.PackMetadataSection;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

//@ TODO: OnlyIn(Dist.CLIENT)
public class JsonBasedResourcePack implements IResourcePack
{
	public String description = "External Resources";
	public String name;
	private Set<String> domains;
	private Map<String, String> fileSystem;
	private File objects;
	
	public JsonBasedResourcePack(String modname, String assetsVersion, File repositoryDir, boolean checkHashes) throws JsonIOException, JsonSyntaxException, IOException
	{
		name = modname + " default assets";
		domains = new HashSet<String>(4);
		
		AssetsManager.isAssetsFolder(repositoryDir);
		File indexes = new File(repositoryDir, "indexes");
		objects = new File(repositoryDir, "objects");
		File main = new File(indexes, modname+"-"+assetsVersion + ".json");
		if(!main.exists())
			throw new IllegalArgumentException("assets repository json not found");
				
		System.out.println("Start loading repofile");
		Gson gson = new Gson();
		JsonReader read = new JsonReader(new FileReader(main));
		JsonObject obj = gson.fromJson(read, JsonObject.class);
		read.close();
		obj = obj.getAsJsonObject("objects");
		Iterator<Entry<String, JsonElement>> iter = obj.entrySet().iterator();
		int startS = obj.size();
		
		fileSystem = new HashMap<>(obj.size());
		while(iter.hasNext())
		{
			Entry<String, JsonElement> e = iter.next();
			JsonObject data = e.getValue().getAsJsonObject();
			long size = data.get("size").getAsLong();
			String hexHash = data.get("hash").getAsString().toLowerCase();
			
			File resource = new File(objects, hexHash.substring(0, 2) + "/" + hexHash);
			if(resource.exists() && resource.isFile())
			{
				if(resource.length() != size)
				{
					iter.remove();
				}
				else
				{
					String path = e.getKey();
					if(checkHashes)
					{
						String fileHashSHA1 = AssetsManager.toHexHash(AssetsManager.createFileHash(new FileInputStream(resource))).toLowerCase();
						if(!fileHashSHA1.equals(hexHash))
						{
							System.out.printf("%s: %s != %s", path, hexHash, fileHashSHA1);
							iter.remove();
							continue;
						}
					}
					String[] sp = path.split("/", 2);
					if(sp.length!=2)
						continue;
					
					domains.add(sp[0]);
					fileSystem.put(sp[0] + ":" + sp[1], hexHash);
				}
			}
			else
			{
				iter.remove();
			}
		}
		
		if(startS != obj.size())
		{
			System.out.println(startS + " != " + obj.size());
		}
	}
	

	@Override
	public InputStream getResourceStream(ResourcePackType arg0, ResourceLocation location) throws IOException
	{
		String hexHash = fileSystem.get(location.toString());
		if(hexHash!=null)
		{
			File resource = AssetsManager.getFile(objects, hexHash);
			if(resource.exists() && resource.isFile())
			{
				return new FileInputStream(resource);
			}
			else
			{
				throw new FileNotFoundException(resource.toString());
			}
		}
		throw new FileNotFoundException(location.toString());
	}

	@Override
	public boolean resourceExists(ResourcePackType arg0, ResourceLocation location)
	{
		return fileSystem.containsKey(location.toString());
	}

	@Override
	public Set<String> getResourceNamespaces(ResourcePackType arg0)
	{
		return domains;
	}

	@Override
	public String getName()
	{
		return name;
	}


	@Override
	public void close() throws IOException
	{
		// TODO Auto-generated method stub
	}


	@Override
	public <T> T getMetadata(IMetadataSectionSerializer<T> arg0) throws IOException
	{
		if(arg0.getSectionName().equals("pack"))
		{
			return (T) new PackMetadataSection(new StringTextComponent(description), 3);
		}
		return null;
	}

	@Override
	public InputStream getRootResourceStream(String arg0) throws IOException
	{
		// TODO Auto-generated method stub
		return null;
	}


    @Override
    public Collection<ResourceLocation> getAllResourceLocations(ResourcePackType type, String namespaceIn,
            String pathIn, int maxDepthIn, Predicate<String> filterIn) {
        // TODO Auto-generated method stub
        return null;
    }


	
}*/
