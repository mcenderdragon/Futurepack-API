package futurepack.api.resources;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.io.FilenameUtils;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import net.minecraft.util.ResourceLocation;

public class AssetsDownloader implements Runnable
{
	private boolean downloadStarted;
	private boolean downloadFinished;
	
	private List<Resource> toDownload;
	private int process;
	private int maxDownload, failes;
	
	private File main, objects;
	
	private JsonObject repository;
	
	public final String modname, assetsVersion;
	
	public AssetsDownloader(String modname, String assetsVersion, File repositoryDir) throws JsonIOException, JsonSyntaxException, IOException
	{
		downloadStarted = false;
		downloadFinished = false;
		toDownload = new ArrayList(48);
		
		this.modname = modname;
		this.assetsVersion = assetsVersion;
		
		AssetsManager.isAssetsFolder(repositoryDir);
		File indexes = new File(repositoryDir, "indexes");
		objects = new File(repositoryDir, "objects");
		main = new File(indexes, modname+"-"+assetsVersion + ".json");
		if(!main.exists())
		{
			repository = new JsonObject();
		}
		else
		{
			Gson gson = new Gson();
			JsonReader read = new JsonReader(new FileReader(main));
			JsonObject obj = gson.fromJson(read, JsonObject.class);
			read.close();
			repository = obj.getAsJsonObject("objects");
		}
	}
	
	public void registerResource(ResourceLocation res, URL download)
	{
		if(downloadStarted)
			throw new IllegalStateException("Download has already started, register your resources earlyier.");
		
		JsonObject obj = repository.getAsJsonObject(getPath(res));
		
		if(obj!=null)
		{
			Resource r = new Resource(res, download);
			r.size = obj.get("size").getAsLong();
			r.localHash = obj.get("hash").getAsString().toLowerCase();
			if(r.doesFileExists(objects))
			{
				if(!r.isFileSizeCorrect())
				{
					if(!r.localFile.delete())
					{
						//meh
					}
					r.downloaded = false;
					r.size = -1;
					r.localHash = null;
					r.localFile = null;
				}
			}
			else
			{
				r.size = -1;
				r.localHash = null;
			}
			toDownload.add(r);
		}
		else
		{
			toDownload.add(new Resource(res, download));
		}
	}
	
	public void registerResources(URL urlbase, String namespace, String[] paths) throws MalformedURLException
	{
		for(String p : paths)
		{
			URL url = new URL(urlbase, p);
			ResourceLocation res = new ResourceLocation(namespace + p);
			registerResource(res, url);
		}
	}
	
	public boolean isDownloadStarted()
	{
		return downloadStarted;
	}
	
	public boolean isDownloadFinished()
	{
		return downloadFinished;
	}
	
	private List<Consumer<AssetsDownloader>> downloadCallbacks = new ArrayList();
	private List<Consumer<AssetsDownloader>> finishedCallbacks = new ArrayList();
	
	public void registerDownloadCallback(Consumer<AssetsDownloader> callback)
	{
		this.downloadCallbacks.add(callback);
	}
	
	public void registerFinishedCallback(Consumer<AssetsDownloader> callback)
	{
		this.finishedCallbacks.add(callback);
	}
	
	@Override
	public void run()
	{
		downloadStarted = true;
		
		downloadCallbacks = Collections.unmodifiableList(downloadCallbacks);
		finishedCallbacks = Collections.unmodifiableList(finishedCallbacks);
		
		maxDownload = toDownload.size();
		failes = 0;
		for(process=0;process<maxDownload;process++)
		{
			Resource r = toDownload.get(process);
			try
			{
				r.startCheckedDownload(objects);
				onSuccsess(r);
			}
			catch(FileNotFoundException e)
			{
				//not available at server
				failes++;
			}
			catch (IOException e)
			{
				e.printStackTrace();
				failes++;
			}
		}
		System.out.println("Finished " + maxDownload + " Downloads, " + failes + " failed.");
		downloadFinished = true;
		
		downloadCallbacks.forEach(c -> c.accept(AssetsDownloader.this));
		
		Gson gson = new Gson();
		try
		{
			JsonWriter write = new JsonWriter(new FileWriter(main));
			JsonObject obj = new JsonObject();
			obj.add("objects", repository);
			gson.toJson(obj, write);
			write.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		finishedCallbacks.forEach(c -> c.accept(AssetsDownloader.this));
	}
	
	public void onSuccsess(Resource r)
	{
		JsonObject obj = repository.getAsJsonObject(getPath(r.res));
		if(obj==null)
		{
			obj = new JsonObject();
		}
		
		obj.addProperty("size", r.getSize(true));
		obj.addProperty("hash", r.getHash());
		
		repository.add(getPath(r.res), obj);
	}
	
	public List<Resource> getResourcesToDownload()
	{
		return Collections.unmodifiableList(toDownload);
	}
	
	public File getObjectsDir()
	{
		return objects;
	}
	
	public File getMainRepoFile()
	{
		return main;
	}
	
	public static String getPath(ResourceLocation res)
	{
		return  res.getNamespace() + "/" + res.getPath();
	}
	
	public static class Resource
	{
		String filename;
		ResourceLocation res;
		URL url;
		URL sha1Hash;
		long size;
		String serverHash, localHash;
		Boolean downloaded = null;
		File localFile = null;
		
		public boolean doDownload = true;
		
		public Resource(String filename, ResourceLocation res, URL file, URL sha1Hash, long size, String hash)
		{
			super();
			this.filename = filename;
			this.res = res;
			this.url = file;
			this.sha1Hash = sha1Hash;
			this.size = size;
			if(hash!=null)
				this.serverHash = hash.toLowerCase();
			else
				serverHash = null;
		}
		
		public boolean isFileSizeCorrect()
		{
			if(localFile!=null)
			{
				return localFile.length() == getSize(false);
			}
			return false;
		}

		public Resource(ResourceLocation res, URL download) 
		{
			this(FilenameUtils.getName(download.getPath()), res, download, AssetsManager.getHashURL(download), -1, null);
		}
		
		/**
		 * @param check if the file size is unknown the server is asked for the size
		 * @return the file size
		 */
		public long getSize(boolean check)
		{
			if(size == -1)//unkown
			{
				if(check)
				{
					if(downloaded == true)
					{
						size = localFile.length();
					}
					else
					{
						try
						{
							URLConnection con = url.openConnection();
							con.setRequestProperty("Accept-Encoding", "gzip");
							con.setConnectTimeout(3000);
							size = con.getContentLengthLong();
							return size;
						}
						catch(IOException e)
						{
							e.printStackTrace();
						}
					}
				}
			}
			return size;
		}
		
		/**
		 * @param download if not hash is there, download it from the hashURL
		 * @return the hash as Hex-String (lower case)
		 */
		public String getServerHash(boolean download)
		{
			if(serverHash==null && download)
			{
				try
				{
					ByteArrayOutputStream bout = new ByteArrayOutputStream(100);
					AssetsManager.downloadContent(sha1Hash, bout);
					serverHash = new String(bout.toByteArray()).toLowerCase();
					bout.close();
				}
				catch(FileNotFoundException e)
				{
					//can not get server hash
				}
				catch(UnknownHostException e)
				{
					//can not get server hash
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				
			}
			return serverHash;
		}
		
		/**
		 * Gets the localHash or the serverHash if not local file was found
		 * @return
		 */
		public String getHash()
		{
			if(localHash!=null)
			{
				return localHash;
			}
			return getServerHash(true);
		}
		
		
		public boolean doesFileExists(File objectsDir)
		{
			if(downloaded!=null)
				return downloaded;
			
			String hexHash = getHash();
			if(hexHash!=null)
			{
				File resource = AssetsManager.getFile(objectsDir, hexHash);
				downloaded = resource.exists();
				if(downloaded)
					localFile = resource;
			}
			else
			{
				downloaded = false;
			}
			
			return downloaded;
		}
		
		public boolean startCheckedDownload(File objectsDir) throws IOException
		{
			if(doesFileExists(objectsDir))
			{
				if(localHash == null)
				{
					localHash = AssetsManager.toHexHash(AssetsManager.createFileHash(new FileInputStream(localFile))).toLowerCase();
				}
				
				if(localHash.equals(getServerHash(true)))
				{
					return true;
				}
				else
				{
					if(serverHash != null) //we have a server connection
					{
						if(!localFile.delete())
						{
							System.out.println("Could not delete wrong file (Resource:" + res+ ") at " + localFile);
						}
					}
					
					return false;
				}
			}
			
			if(doDownload)
			{
				File downloadFile = File.createTempFile(filename, ".part");
				FileOutputStream out = new FileOutputStream(downloadFile);
				AssetsManager.downloadContent(url, out);
				out.close();
					
				localHash = AssetsManager.toHexHash(AssetsManager.createFileHash(new FileInputStream(downloadFile))).toLowerCase();
					
				if(localHash.equals(getServerHash(true)))
				{
					Path from = downloadFile.toPath();
					Path target = AssetsManager.getFile(objectsDir, localHash).toPath();
					Files.move(from, target, StandardCopyOption.REPLACE_EXISTING);
					localFile = target.toFile();
					downloaded = true;
					return true;
				}
				else
				{
					if(!downloadFile.delete())
					{
						downloadFile.deleteOnExit();
						System.out.println("Could not delete incorrect (hash mismatch) download at " + downloaded);
					}
				}
			}
			
			return false;
		}
	}
}
