package com.nicoislost.owo;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import io.wispforest.owo.config.annotation.RangeConstraint;

@Modmenu(modId="zoom-o-matic")
@Config(wrapperName = "ZoomOConfig", name = "Zoom-O-Config")
public class ZoomOConfigModel {
    public boolean actionBarWriting = true;
    @RangeConstraint(min = 1, max = 99)
    public int Zoom1=77;
    public boolean Zoom1SmoothCamera = true;
    @RangeConstraint(min = 1, max = 99)
    public int Zoom2=70;
    public boolean Zoom2SmoothCamera = false;
    @RangeConstraint(min = 1, max = 99)
    public int Zoom3=60;
    public boolean Zoom3SmoothCamera = false;

}
