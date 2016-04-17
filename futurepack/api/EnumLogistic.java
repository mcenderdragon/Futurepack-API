package futurepack.api;

import net.minecraft.util.ResourceLocation;

//Maximal 32 different subtypes
public enum EnumLogistic 
{
	NONE(null, false, false),
	IN("textures/model/overlay_in.png", true, false),
	OUT("textures/model/overlay_out.png", false, true),
	INOUT("textures/model/overlay_inout.png", true, true);
	
	private EnumLogistic(String s, boolean in, boolean out)
	{
		if(s==null)
			location=null;
		else
			location = new ResourceLocation("fp", s);
		this.in = in;
		this.out = out;
	}
	
	private final ResourceLocation location;
	private boolean in;
	private boolean out;
	
	public ResourceLocation getTexture()
	{
		return location;
	}
	
	public boolean canInsert()
	{
		return in;
	}
	
	public boolean canExtract()
	{
		return out;
	}
}
