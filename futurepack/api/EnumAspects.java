package futurepack.api;

import java.util.ArrayList;

import net.minecraft.util.ResourceLocation;

import com.google.gson.JsonArray;

public enum EnumAspects
{
	GEOLOGIE("geologie"),
	CHEMIE("chemie"),
	TIERHALTUNG("tierhaltung"),
	LOGISTIK("logistik"),
	METALLURGIE("metallurgie"),
	BIOLOGIE("biologie"),
	MORPHOLOGIE("morphologie"),
	NEONENERGIE("neonenergie"),
	ENERGIEVERBINDUNG("energieverbindung"),
	TERMODYNAMIC("termodynamic"),
	MASCHINENBAU("maschinenbau"),
	HYDRAULIC("hydraulic"),
	FP("fp"),
	FORSCHUNG("forschung"),
	PRODUCTION("production"),
	PRODUCTION1("production1"),
	PRODUCTION2("production2"),
	PRODUCTION3("production3"),
	WERKZEUGE("werkzeuge"),
	OVERCLOCK("overclock"),
	ENERGIESPEICHER("energiespeicher"),
	SUPPORTSPEICHER("supportspeicher"),
	XPSPEICHER("xpspeicher"),
	OPTIK("optik"),
	LASERTECHNIK1("lasertechnik1"),
	LASERTECHNIK2("lasertechnik2"),
	RAUMFAHRT("raumfahrt"),
	KI("ki"),
	ER_ENERGIE("er-energie"),
	WAFFENTECH("waffentech"),
	BERGBAU("bergbau"),
	MAGNETISMUS("magnetismus"),
	EN_ERZEUGUNG("en_erzeug"),
	EN_ERZEUGUNG_1("en_erzeug_1"),
	EN_ERZEUGUNG_2("en_erzeug_2"),
	EN_ERZEUGUNG_3("en_erzeug_3"),
	ASTRONOMIE("astronomie"),
	CHIP_ASSEMBLY("chip_assembly"),
	ENLOGISTIK("enlogistik"),
	HOLOTECH("holotech"),
	IONTECH("iontech"),
	ITEMLOG("itemlog"),
	NETZWERK("netzwerk"),
	QUANTENTECH("quantentech"),
	ENTOOLS("entools"),
	PLANET_ENTROS("planet_entros"),
	PLANET_TYROS("planet_tyros"),
	PLANET_MENELAUS("planet_menelaus"),
	PLANET_MINCRA("planet_mincra"),
	;
	
//	public static final Aspect GEOLOGIE = new Aspect("geologie");
//	public static final Aspect CHEMIE = new Aspect("chemie");
//	public static final Aspect TIERHALTUNG = new Aspect("tierhaltung");
//	public static final Aspect LOGISTIK = new Aspect("logistik");
//	public static final Aspect METALLUGIE = new Aspect("metallurgie");
//	public static final Aspect BIOLOGIE = new Aspect("biologie");
//	public static final Aspect MORPHOLOGIE = new Aspect("morphologie");
//	public static final Aspect NEONENERGIE = new Aspect("neonenergie");
//	public static final Aspect ENERGIEVERBINDUNG = new Aspect("energieverbindung");
//	public static final Aspect TERMODYNAMIC = new Aspect("termodynamic");
//	public static final Aspect MASCHIENENBAU = new Aspect("maschienenbau");
//	public static final Aspect HYDRAULIC = new Aspect("hydraulic");
	
	public static EnumAspects[] buttons = new EnumAspects[]
	{
		EnumAspects.GEOLOGIE,
		EnumAspects.CHEMIE,
		EnumAspects.TIERHALTUNG,
		EnumAspects.LOGISTIK,
		EnumAspects.METALLURGIE,
		EnumAspects.BIOLOGIE,
		EnumAspects.MORPHOLOGIE,
		EnumAspects.NEONENERGIE,
		EnumAspects.ENERGIEVERBINDUNG,
		EnumAspects.TERMODYNAMIC,
		EnumAspects.MASCHINENBAU,
		EnumAspects.HYDRAULIC
	};	
	
	
	
	public final String id;
	private final ResourceLocation pic;
	
	EnumAspects(String id)
	{
		this.id = id;
		pic = new ResourceLocation("fp", "textures/aspects/" + id + ".png");
	}
	
	public ResourceLocation getResourceLocation()
	{
		return pic;
	}
	
	public static EnumAspects[] getAspects(JsonArray arr)
	{
		ArrayList<EnumAspects> l = new ArrayList(arr.size());
		for(int i=0;i<arr.size();i++)
		{
			String s = arr.get(i).getAsString().toUpperCase();
			l.add(EnumAspects.valueOf(s));
		}
		return l.toArray(new EnumAspects[l.size()]);
	}
}
