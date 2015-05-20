#Sporniket Game Framework

This framework is intended to be platform independant. For instance, the game code MUST be the same for a standalone Swing application and an android application. The code to launch the game (thus dependant from the platform) SHOULD be as short as possible.

# How to use in your project with maven

## Build and install the library
To get the latest available code, one must clone the git repository, build and install to the maven local repository.

```
git clone https://github.com/sporniket/game.git
cd game/sporniket-game
git checkout version_to_use
mvn install
```

## Add a dependency to your project
Add the needed dependencies in your pom file (sporniket-game-papi-swing and sporniket-game-papi-android are mutually exclusives) :

```
<dependency>
	<groupId>com.sporniket.game</groupId>
	<artifactId>sporniket-game-api</artifactId>
	<version><!-- the version to use --></version>
</dependency>
<dependency>
	<groupId>com.sporniket.game</groupId>
	<artifactId>sporniket-game-papi</artifactId>
	<version><!-- the version to use --></version>
</dependency>
<dependency>
	<groupId>com.sporniket.game</groupId>
	<artifactId>sporniket-game-papi-swing</artifactId>
	<version><!-- the version to use --></version>
</dependency>
<dependency>
	<groupId>com.sporniket.game</groupId>
	<artifactId>sporniket-game-papi-android</artifactId>
	<version><!-- the version to use --></version>
</dependency>
```
