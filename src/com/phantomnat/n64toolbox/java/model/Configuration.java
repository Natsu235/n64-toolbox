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
 * @author Dorian Pilorge
 */
public class Configuration {

    // Local
    private static File romDir;
    private static String cfgPath = "config.ini";
    private static String cicPath = "/com/phantomnat/n64toolbox/resources/bootcodes/";
    private static String crcList = "/com/phantomnat/n64toolbox/resources/checksums/N64-CRC-Database.txt";
    private static String appIcon = "/com/phantomnat/n64toolbox/resources/images/n64-icon.png";
    private static Locale appLang = Locale.ENGLISH;
    // Window
    private static int width = 720;
    private static int height = 480;
    private static boolean resizable = true;
    // Debug
    private static boolean debug = false;

    public Configuration() {}

    // Getters
    public static File getRomDirectory() { return romDir; }
    public static String getConfigPath() { return cfgPath; }
    public static String getCICPath(String bootcode) { return cicPath + bootcode + ".bin"; }
    public static String getCRCList() { return crcList; }
    public static String getIcon() { return appIcon; }
    public static Locale getLanguage() { return appLang; }
    public static int getWidth() { return width; }
    public static int getHeight() { return height; }
    public static boolean getResizable() { return resizable; }
    public static boolean getDebug() { return debug; }

    // Setters
    public void setRomDirectory(File romDir) { this.romDir = romDir; }
    //public void getConfigPath(String cfgPath) { this.cfgPath = cfgPath; }
    //public void getCICPath(String cicPath) { this.cicPath = cicPath; }
    //public void getCRCList(String crcList) { this.crcList = crcList; }
    //public void setIcon(String appIcon) { this.appIcon = appIcon; }
    public void setLanguage(Locale appLang) { this.appLang = appLang; }
    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }
    public void setResizable(boolean resizable) { this.resizable = resizable; }
    public void setDebug(boolean debug) { this.debug = debug; }

}
