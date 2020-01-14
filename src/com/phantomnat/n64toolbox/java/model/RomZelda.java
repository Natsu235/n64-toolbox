/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phantomnat.n64toolbox.java.model;

import java.io.File;
import javafx.scene.paint.Paint;

/**
 *
 * @author PhantomNatsu
 */
public class RomZelda extends Rom {
    
    protected static String realName;
    protected static String plateform;
    protected static String creator;
    protected static String build;
    protected static String compression;
    
    public RomZelda() {}
    
    public RomZelda(File file, String format, long size, String name, String media, String cartID, String region, String version, String CIC, String CRC, String CRCStatus, Paint CRCStatusColor, String MD5, String SHA1, String realName, String plateform, String creator, String build, String compression) {
        super(file, format, size, name, media, cartID, region, version, CIC, CRC, CRCStatus, CRCStatusColor, MD5, SHA1);
        this.realName = realName;
        this.plateform = plateform;
        this.creator = creator;
        this.build = build;
        this.compression = compression;
    }
    
    // Getters
    public static String getRealName() { return realName; }
    public static String getPlateform() { return plateform; }
    public static String getCreator() { return creator; }
    public static String getBuild() { return build; }
    public static String getCompression() { return compression; }
    
    // Setters
    public void setRealName(String realName) { this.realName = realName; }
    public void setPlateform(String plateform) { this.plateform = plateform; }
    public void setCreator(String creator) { this.creator = creator; }
    public void setBuild(String build) { this.build = build; }
    public void setCompression(String compression) { this.compression = compression; }
    
}
