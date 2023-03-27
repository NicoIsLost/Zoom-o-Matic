# Zoom-o-Matic
A simple, but customizable multi-button zoom tool for Minecraft.

It allows for up to 3 keybinds with customizable zoom and on / off smooth camera.

_This mod is for learning most of all, feel free to use it, but its biggest purpose is learning and teaching to mod fabric._

## Dependencies
Fabric Mod Loader (Install to use)
https://fabricmc.net/use/installer/

Fabric API (Place in mod folder)
https://github.com/FabricMC/fabric

owo-lib (Place in mod folder)
https://github.com/wisp-forest/owo-lib

## To install
After fabric mod loader has been installed for the correct version place the latest Zoom-o-matic release with compatible dependencies in the .minecraft/mods folder.

## Usage
**The default Zoom 1 Keybind is "c" which is already bound, so you need to unbind "Save Hotbar Activator" or bind zoom1 to another key to make the mod work.**

### Mod Menu
The mod is registered in mod menu, so you can adjust settings through that GUI if you desire, otherwise you can use commands. 

### Keybinds
Are found in the Zoom-o-Matic section of the normal minecraft keybind menu. 

Options -> Controls -> Keybinds

### Zoom Amount
Adjusts the amount of zooming, with 

`/ZoomO ZoomAmount Zoom_X YY`

- **X** = Zoom Keybind #, (1, 2, or 3.)
- **YY** is the % zoom, (1 - 99)

### Smooth Camera
Toggles whether smooth_camera should be active when zooming, which slows down movement.

`/ZoomO SmoothCamera Zoom_X YY`

- **X** = Zoom Keybind #, (1, 2, or 3.)
- **YY** true/false

# About This Mod & Plans
This mod is meant to be a place to learn how to mod fabric mods for minecraft. This our first mod, so we know it's not 100% perfect, but that's not the point.

The goal is to optimize it, make it better, and improve the features in order to help learn more about modding.

## Here is a list of improvements we would like to make, in order of want:
- Remove owo dependency - i.e. Create a world-independent data saving system.
- Add scrolling zoom
- Add a custom, customizable smoother,
- Add a custom GUI
  - Add more keybinds (including more keybinds, via GUI)
- Add animations
  - Presets like easing.
- Add customizable animations 

# Credit
Fej1Fun has been hand and in this entire process, coding large portions himself, and helping me when I get stuck with my portions!