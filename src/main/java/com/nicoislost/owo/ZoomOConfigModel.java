package com.nicoislost.owo;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import io.wispforest.owo.config.annotation.RangeConstraint;

@Modmenu(modId="zoom-o-matic")
@Config(wrapperName = "ZoomOConfig", name = "Zoom-O-Config")
public class ZoomOConfigModel {
    public boolean actionBarWriting = true;
    @RangeConstraint(min = 1, max = 99)
    public int zoom1=77;
    public boolean zoom1SmoothCamera = true;
    @RangeConstraint(min = 1, max = 99)
    public int zoom2=70;
    public boolean zoom2SmoothCamera = false;
    @RangeConstraint(min = 1, max = 99)
    public int zoom3=60;
    public boolean zoom3SmoothCamera = false;

}
