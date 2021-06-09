[![](http://cf.way2muchnoise.eu/full_futurepack_downloads.svg)](https://minecraft.curseforge.com/projects/futurepack)
[![](http://cf.way2muchnoise.eu/versions/Available%20For%20Minecraft_futurepack_all.svg)](https://minecraft.curseforge.com/projects/futurepack)

[![](http://redsnake-games.de/jenkins/api.php?id=futurepack.svg)]()

#  Futurepack Source Repository
You propably wonder where the source is: not here. Futureack is not open source, but rather visible source. You need a gitlab.com account and then simply mail me your name (goto.minecraft@gmail.com) and I will add you to the projekt. 

# Futurepack-API
A small Api for the Futurepack

# Maven Integration
Use this if you want to make an addon for the futurepack.
```
repositories 
{
  maven {
    name = "ModMaven"
    url = "https://modmaven.k-4u.nl"//location of the futurepack maven files.
  }
  maven {
    name = "RedSnakeMaven"
    url = "https://redsnake-games.de/maven/"//self hosted maven in case modmaven is not available
  }
}
dependencies {
    compileOnly fg.deobf("redsnakegames.mcenderdragon.futurepack:<minecraft version>:<futurepack version>:api") { transitive = false } // compile against the Futurepack API
    runtimeOnly fg.deobf("redsnakegames.mcenderdragon.futurepack:<minecraft version>:<futurepack version>") { transitive = false }// at runtime, use the full Futurepack jar
}
```
[Futurepack Translation Project](https://github.com/Wugand/FuturePack-Language)

[Futurepack Mod (Minecraftforum)](https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/2644868-futurepack-mod-discover-new-dimensions)

[Futurepack on Curse](http://mods.curse.com/mc-mods/minecraft/237333-futurepack#t1:description)

[Twitter](https://twitter.com/MCenderdragonxD)

[Discord](https://discord.gg/UpdVfFk)

[<img alt="Patreon" src=http://i.imgur.com/k44o58p.png width=256\>](https://www.patreon.com/mcenderdragon)
