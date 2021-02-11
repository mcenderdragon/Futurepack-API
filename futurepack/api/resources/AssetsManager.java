//package futurepack.api.resources;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLConnection;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.security.SignatureException;
//import java.util.zip.GZIPInputStream;
//
//import javax.xml.bind.DatatypeConverter;
//
//import org.apache.commons.io.FilenameUtils;
//
//import com.google.gson.JsonIOException;
//import com.google.gson.JsonSyntaxException;
//
//import net.minecraft.resources.ResourcePackType;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.Util;
//import net.minecraft.util.Util.OS;
//
//public class AssetsManager
//{
//	/**
//	 * 
//	 * @return OS dependend .minecraft folder
//	 */
//	public static String getMinecraftDir()
//	{
//		OS os = Util.getOSType();
//		String home = System.getProperty("user.home");
//		if(os == OS.WINDOWS)
//		{
//			return home + "/AppData/Roaming/.minecraft/";
//		}
//		else if(os == OS.OSX)
//		{
//			return home + "/Library/Application Support/minecraft/";
//		}
//		else //Linux / solaris / unknown
//		{
//			return home + "/.minecraft/";
//		}
//	}
//	
//	/**
//	 * 
//	 * @param url HTTPS is recomended
//	 * @param out
//	 * @throws IOException
//	 */
//	public static void downloadContent(URL url, OutputStream out) throws IOException
//	{
//		URLConnection con = url.openConnection();
//		con.setRequestProperty("Accept-Encoding", "gzip");
//		con.setRequestProperty("User-Agent", "Futurepack AssetsManager( 1.12.2 @ 26.4.13)");
//		con.setConnectTimeout(3000);
//		long length = con.getContentLengthLong();
//		
//		if(con instanceof HttpURLConnection)
//		{
//			int code = ((HttpURLConnection) con).getResponseCode();
//			if(code != 200)
//				System.out.println("Conection: " + code);
//			if(code >= 400)
//			{
//				throw new FileNotFoundException(url + " responded '" + ((HttpURLConnection) con).getResponseMessage() + "'");
//			}
//		}
//		
//		InputStream in = null;
//		if ("gzip".equals(con.getContentEncoding())) {
//			in = new GZIPInputStream(con.getInputStream());
//		}
//		else {
//			in = con.getInputStream();
//		}
//		byte[] bytes = new byte[1024 * 512];
//		while(in.available() > 0)
//		{
//			int len = in.read(bytes);
//			if(len > 0)
//			{
//				out.write(bytes, 0, len);
//			}
//		}
//	}
//	
//	/**
//	 * 
//	 * @param in a InputStream to the input file
//	 * @return a byte[] representing the file hash
//	 */
//	public static byte[] createFileHash(InputStream in) throws IOException
//	{
//		try
//		{
//			MessageDigest digest = MessageDigest.getInstance("SHA1");
//			byte[] block = new byte[4096];
//			int length;
//			while ((length = in.read(block)) > 0) {
//				digest.update(block, 0, length);
//			}
//			in.close();
//			return digest.digest();
//		}
//		catch (NoSuchAlgorithmException e)
//		{
//			e.printStackTrace();
//		}
//		return new byte[0];
//	}
//	
//	/**
//	 * Transforms a byte[]-hash in a more readable hex-String-hash 
//	 */
//	public static String toHexHash(byte[] bytes)
//	{
//		return DatatypeConverter.printHexBinary(bytes).toLowerCase();
//	}
//	
//	public static File downloadAndCheckFile(URL url, File dir) throws FileNotFoundException, IOException, NoSuchAlgorithmException, SignatureException
//	{
//		String filename = FilenameUtils.getName(url.getPath());
//		File downloaded = new File(dir, filename);
//		if(!downloaded.exists())//download file if not exists
//		{
//			FileOutputStream out = new FileOutputStream(downloaded);
//			downloadContent(url, out);
//			out.close();
//		}
//		
//		URL urlSHA1 = getHashURL(url);
//		ByteArrayOutputStream bout = new ByteArrayOutputStream(100);
//		downloadContent(urlSHA1, bout);
//		String hexHashExpected = new String(bout.toByteArray()).toUpperCase();
//		bout.close();
//		
//		String hexString = toHexHash(createFileHash(new FileInputStream(downloaded)));
//		
//		if(!hexHashExpected.equals(hexString))
//		{
//			downloaded.delete();
//			throw new java.security.SignatureException("File hashes don't match!");
//		}
//		
//		return downloaded;
//	}
//	
//	public static URL getHashURL(URL url) 
//	{
//		String filename = FilenameUtils.getName(url.getPath());
//		String shaFile = filename+".sha1";
//		URL urlSHA1;
//		try
//		{
//			urlSHA1 = new URL(url.toString().replaceFirst(filename, shaFile));
//			return urlSHA1;
//		} catch (MalformedURLException e)
//		{
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	public static void isAssetsFolder(File repositoryDir)
//	{
//		if(repositoryDir.exists())
//		{
//			if(!repositoryDir.isDirectory())
//			{
//				throw new IllegalArgumentException("File must be a Directory");
//			}
//		}
//		else
//		{
//			repositoryDir.mkdirs();
//		}
//		
//		File indexes = new File(repositoryDir, "indexes");
//		if(indexes.exists())
//		{
//			if(!indexes.isDirectory())
//				throw new IllegalArgumentException("./indexes must be a directory");
//		}
//		else
//		{
//			indexes.mkdirs();
//		}
//
//		File objects = new File(repositoryDir, "objects");
//		if(objects.exists())
//		{
//			if(!objects.isDirectory())
//				throw new IllegalArgumentException("./objects must be a directory");
//		}
//		else
//		{
//			objects.mkdirs();
//		}
//
//	}
//	
//	public static File getFile(File parentDir, String hexHash)
//	{
//		hexHash = hexHash.toLowerCase();
//		File folder = new File(parentDir,  hexHash.substring(0, 2));
//		folder.mkdir();
//		return new File(folder, hexHash);
//	}
//	
//	public static void main(String[] args)
//	{
////		File dir = new File("./downloads");
////		dir.mkdirs();
////		try
////		{
////			downloadAndCheckFile(new URL("http://dvs1.progwml6.com/files/maven/mezz/jei/jei_1.12.2/4.9.2.196/jei_1.12.2-4.9.2.196-api.jar"), dir);
////		} 
////		catch (FileNotFoundException e) {
////			e.printStackTrace();
////		}
////		catch (NoSuchAlgorithmException e) {
////			e.printStackTrace();
////		} 
////		catch (SignatureException e) {
////			e.printStackTrace();
////		} 
////		catch (MalformedURLException e) {
////			e.printStackTrace();
////		}
////		catch (IOException e) {
////			e.printStackTrace();
////		}
//		File assets = new File(getMinecraftDir(), "assets");
//		try {
//			long st = System.currentTimeMillis();
//			JsonBasedResourcePack pack = new JsonBasedResourcePack("1.12", "af", assets, true);
//			System.out.println(pack.resourceExists(ResourcePackType.CLIENT_RESOURCES, new ResourceLocation("minecraft:sounds/dig/cloth1.ogg")));
//			st = System.currentTimeMillis() - st;
//			System.out.println(st);
//			
//		} catch (JsonIOException e) {
//			e.printStackTrace();
//		} catch (JsonSyntaxException e) {
//			e.printStackTrace();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//	}
//}
