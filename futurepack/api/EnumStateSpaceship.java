package futurepack.api;

import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public enum EnumStateSpaceship
{
	lowEnergie,
	open,
	missingThruster,
	missingEngine,
	missingBeam,
	missingFuel,
	GO;
	
	private EnumStateSpaceship()
	{
		
	}
	
	public ChatComponentTranslation getRawChat(Object ...args)
	{
		return new ChatComponentTranslation("chat.spaceship." + this.name(),args);
	}
	
	public ChatComponentTranslation getChatFormated(Object ...args)
	{
		ChatComponentTranslation trans = getRawChat(args);
		trans.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED));
		return trans;
	}
}
