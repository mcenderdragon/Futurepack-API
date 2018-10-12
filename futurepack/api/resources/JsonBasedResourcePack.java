package futurepack.api.resources;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
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
	public InputStream getInputStream(ResourceLocation location) throws IOException
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
	public boolean resourceExists(ResourceLocation location)
	{
		return fileSystem.containsKey(location.toString());
	}

	@Override
	public Set<String> getResourceDomains()
	{
		return domains;
	}

	@Override
	public <T extends IMetadataSection> T getPackMetadata(MetadataSerializer metadataSerializer, String metadataSectionName) throws IOException
	{
		if(metadataSectionName.equals("pack"))
		{
			return (T) new PackMetadataSection(new TextComponentString(description), 3);
		}
		return null;
	}

	@Override
	public BufferedImage getPackImage() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPackName()
	{
		return name;
	}

	
}
