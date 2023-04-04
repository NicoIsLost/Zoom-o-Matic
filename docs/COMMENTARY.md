# Introduction

When Fej and I decided to work on this mod we set out to use it as an example to help us learn fabric modding. Now that it is done we are hoping that it can be a resource to help others learn fabric modding. 

The purpose of this commentary is not to present ourselves as experts (we are certainly not!), but instead to show what we were thinking and how we go there. 

In its current form this is the "first pass" draft that I am writing last minute before going on vacation for a couple of days, so it's not as detailed as we would like, but we will continue to work on it and upgrade it with the mod itself. We also hope that others with more experiance will help contribute to the mod and maybe some of the documentation to show better ways of achieving our goals.

# Setup & Starting Out
The first thing here is, although I fully believe it's best to jump in as soon as possible, it is going to be very hard if you have _no_ programming background. You don't have to be an expert (I'm not!), but some background is nessesary. If you have no background at all, I would suggest you do the [W3 Java Tutorial](https://www.w3schools.com/java/). This will give you what you need to get started. 

You will need a good Java IDE, most people use IntellJ, but VSCode and Eclipse, or whatever you want will work. 

The best place to start getting set up is with the fabric documentations. It's quite good in general, but is also limited, so you will not find everything you need there and it will take some digging. Remember, most of the learning is dependent on how much you can grind, if you put in the effort the information is out there. 

First you should [set up your modding environment with the fabric documentation](https://fabricmc.net/wiki/tutorial:setup). This will also decompile the minecraft source code if you are interested in code digging. 

After that, the best way to start with modding is to use the official fabric template. [Fabric provides a generator that is very easy to use.](https://fabricmc.net/develop/template/)

While I believe the "split client and common sources" option is best practice, I never got it to work correctly so did not do that option despite this being a client side only mod.

Once you have this template there are a couple of things you should / can do. First is to modify the [fabric.mod.json](/src/main/resources/fabric.mod.json) which is the main configuration for the mod itself.

**Note:** There is another configuration, but for the development environment, the [gradle.properties](/gradle.properties) file, but we will cover this later.

**Note:** If you aren't familiar with JSON, it is quite simple and very important, [read about it here](https://www.w3schools.com/whatis/whatis_json.asp). 

The top part of **fabric.mod.json** is tagging and contact info etc.. The later portion actually determines how your program launches, so this is the most important:

```json
"environment": "client",
	"entrypoints": {
		"client": [
			"com.nicoislost.ZoomO"
		]
	},
	"mixins": [
		{
			"config": "zoom-o-matic.mixins.json",
			"environment": "client"
		}
	],
	"depends": {
		"fabricloader": ">=0.14.17",
		"minecraft": ">=1.19.3",
		"java": ">=17",
		"fabric-api": "*"
	},
	"suggests": {
		"another-mod": "*"
	}
```

Since we are using a client side only mod we define the entrypoint and environment as client. 

The reason why we use `>=1.19.3`, `">=0.14.17"` and `>=17"` is because we have written this mod for 1.19.3 and 1.19.4. The `"fabric-api": "*"` is OK because it must be a valid api for a version compatable with this mod or it will fail to launch.

We will get into the mixin in a second. 

## General Structure

The general structure of the mod is quite simple. Your main directory is the project directory with mostly gradle support etc.. 

Your code will be in `/src/main/` with `/src/main/java/com.yourname/` being the main java directory and `/src/main/resources/` being the supporting directoy. 

You may have noticed that the fabric starter mod has a `/src/client` also, we deleted this since it's a client side only mod. It is important if you do this that the [fabric.mod.json](../src/main/resources/fabric.mod.json) is formatted correctly for the mod to work.

So, we have organized our code a bit into folders, but the main file for us is [ZoomO.java](/src/main/java/com/nicoislost/ZoomO.java). You can rename (refractor) this to whatever your mod is named, but make sure it is updated in [fabric.mod.json](../src/main/resources/fabric.mod.json).

### ZoomO.java

This is our main file. It includes our mod initializer. If we want, we can just initialize the code here and have all of our other code in other files and just call to it. This is probably the most organized way, but we did a partial approach with utilities such as keybinds, commands, etc.. outside the main file, but the core opterational functions within the main file.

The base of this file is:

```java
package com.nicoislost;
import net.fabricmc.api.ClientModInitializer;

public class ZoomO implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		//What happens when the code starts!
	}
}
```

Again here we use the **ClientModInitializer** because it's client side. This is our main mod class. 

Outside of the core operational functions we have two meathods that we call:

```java
public class ZoomO implements ClientModInitializer { //Mod initializer
    
    public static final ZoomOConfig CONFIG = ZoomOConfig.createAndLoad();

    @Override
    public void onInitializeClient() {
        ModRegistries.registerModPackages();
    }
}
```

The first method: `public static final ZoomOConfig CONFIG = ZoomOConfig.createAndLoad();` is loading owo-lib, which we use to save data not-dependent on world (a goal is to remove this dependency).

The second method: `ModRegistries.registerModPackages();` is loading our [ModRegistries](/src/main/java/com/nicoislost/util/ModRegistries.java) class, which loads registers our keybindings and commands:

```java
package com.nicoislost.util;

import com.nicoislost.inputs.Commands;
import com.nicoislost.inputs.KeyBinds;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class ModRegistries {
    public static void registerModPackages() {
        KeyBinds.RegisterKeyBinds(); // Register keybinds
        registerCommands();
    }
    private static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(Commands::register);} //Register commands
}
```

Both our keybinds and commands are put in the `/inputs` folder. 

At this point I am not going to go through all the explanation for this as there is good documentation. 

### Custom Keybinding
Keybinding is quite simple, see the [fabric documentation for this](https://fabricmc.net/wiki/tutorial:keybinds). It covers all the basics quite well. 

I will say, we do not use `ClientTickEvents` in this mod, this is something "borrowed" from [Logical Zoom](https://github.com/LogicalGeekBoy/logical_zoom) which confused me when first reading it. 

Instead, we use our mixin, which is processed when the game is rendered, to check to see if the keybinding has been pressed. 

Once our keybinds have been registered we set up a couple of cheat methods of ease of use:
```java
public class ZoomO implements ClientModInitializer {
    ...
    public static boolean key1() {
        return KeyBinds.keyBinding1.isPressed();
    }

    public static boolean key2() {
        return KeyBinds.keyBinding2.isPressed();
    }

    public static boolean key3() {
        return KeyBinds.keyBinding3.isPressed();
    }
}
```
These are booleans and just output what the method outputs, true or false whether the kindbind is pressed or not.

Then we have a method that checks if any of the three are pressed.  
```java
public class ZoomO implements ClientModInitializer { 
    ...
      public static boolean key1() {
        return KeyBinds.keyBinding1.isPressed();
    }

    public static boolean key2() {
        return KeyBinds.keyBinding2.isPressed();
    }

    public static boolean key3() {
        return KeyBinds.keyBinding3.isPressed();
    }
    ...
	public static boolean isZooming() {
		return key1() || key2() || key3();
	}
}
```

Again this returns a boolean, true is any of the keybindings are pressed, and false if none are. 

THEN to check if it's actually pressed without the event handler `ClientTickEvents`, we just use an if-statement in the [render-mixin](/src/main/java/com/nicoislost/mixin/ZoomOMixin.java). 

```java

if (ZoomO.isZooming()){
    ...
}

```
It seems a little round-about but makes sense as this is a render based mod, so it checks whether the key is pressed every time a render is processed.

### Custom Commands

Commands are not too hard either, but the fabric documentation is a little bit lacking. 

I would recommend starting with [the fabric documentation](https://fabricmc.net/wiki/tutorial:commands), then using [the Brigadier ReadMe](https://github.com/Mojang/brigadier) to help with some of the stuff not covered in the fabric documentation. This along with the code should be manageable to understand. 

# To do
I will add to this soon, but I have to catch a flight!

**To add:**
- Mixins
- Owo Library
- Core Code Explaination
- Translations
- More details in general