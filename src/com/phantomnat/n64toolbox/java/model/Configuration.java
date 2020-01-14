/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phantomnat.n64toolbox.java.model;

import java.io.File;
import java.util.Locale;

/**
 *
 * @author PhantomNatsu
 */
public class Configuration {
    
    protected static String cfgPath = "config.ini";
    protected static double width = 720;
    protected static double height = 480;
    protected static boolean resizable = true;
    protected static String appIcon = "/com/phantomnat/n64toolbox/resources/images/n64-icon.png";
    protected static Locale appLang = Locale.ENGLISH;
    protected static File romDir;
    
    protected static String CIC6101 = "/com/phantomnat/n64toolbox/resources/files/6101-CIC.bin";
    protected static String CIC6102 = "/com/phantomnat/n64toolbox/resources/files/6102-CIC.bin";
    protected static String CIC6103 = "/com/phantomnat/n64toolbox/resources/files/6103-CIC.bin";
    protected static String CIC6105 = "/com/phantomnat/n64toolbox/resources/files/6105-CIC.bin";
    protected static String CIC6106 = "/com/phantomnat/n64toolbox/resources/files/6106-CIC.bin";
    protected static String CRCList = "/com/phantomnat/n64toolbox/resources/files/N64-CRC-Database.txt";
    
    public Configuration() {}
    
    // Getters
    public static String getConfigPath() { return cfgPath; }
    public static double getWidth() { return width; }
    public static double getHeight() { return height; }
    public static boolean getResizable() { return resizable; }
    public static String getIcon() { return appIcon; }
    public static Locale getLanguage() { return appLang; }
    public static File getRomDirectory() { return romDir; }
    
    public static String getCIC6101() { return CIC6101; }
    public static String getCIC6102() { return CIC6102; }
    public static String getCIC6103() { return CIC6103; }
    public static String getCIC6105() { return CIC6105; }
    public static String getCIC6106() { return CIC6106; }
    public static String getCRCList() { return CRCList; }
    
    // Setters
    public void setWidth(double width) { this.width = width; }
    public void setHeight(double height) { this.height = height; }
    public void setResizable(boolean resizable) { this.resizable = resizable; }
    public void setLanguage(Locale appLang) { this.appLang = appLang; }
    public void setRomDirectory(File romDir) { this.romDir = romDir; }
    
}
