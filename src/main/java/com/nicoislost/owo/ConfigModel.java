package com.nicoislost.owo;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import io.wispforest.owo.config.annotation.RangeConstraint;

/**
 * The configuration model for the mod.
 * Used by the owo-config library to generate a config file and a config screen.
 * ZoomConfig generated on gradle build.
 */
@Modmenu(modId="zoom-o-matic")
@Config(wrapperName = "ZoomConfig", name = "zoom-o-config")
public class ConfigModel {
    public boolean actionBarWriting = true;
    @RangeConstraint(min = 1, max = 99)
    public int zoom1 = 77;
    public boolean zoom1SmoothCamera = true;
    @RangeConstraint(min = 1, max = 99)
    public int zoom2 = 70;
    public boolean zoom2SmoothCamera = false;
    @RangeConstraint(min = 1, max = 99)
    public int zoom3 = 60;
    public boolean zoom3SmoothCamera = false;

}
