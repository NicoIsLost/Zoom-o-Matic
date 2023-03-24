package com.nicoislost.owo;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import io.wispforest.owo.config.annotation.RangeConstraint;

@Modmenu(modId="zoom-o-matic")
@Config(wrapperName = "ZoomOConfig", name = "Zoom-O-Config")
public class ZoomOConfigModel {
    @RangeConstraint(min = 1, max = 100)
    public double Zoom1=30;
    public boolean Zoom1SmoothCamera = false;
    @RangeConstraint(min = 1, max = 100)
    public double Zoom2=40;
    public boolean Zoom2SmoothCamera = false;
    @RangeConstraint(min = 1, max = 100)
    public double Zoom3=50;
    public boolean Zoom3SmoothCamera = false;

}
