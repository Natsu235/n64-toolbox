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
public class Rom {
    
    protected static File file;
    protected static String format;
    protected static long size;
    protected static String name;
    protected static String media;
    protected static String cartID;
    protected static String region;
    protected static String version;
    protected static String CIC;
    protected static String CRC;
    protected static String CRCStatus;
    protected static Paint CRCStatusColor;
    protected static String MD5;
    protected static String SHA1;
    
    public Rom() {}
    
    public Rom(File file, String format, long size, String name, String media, String cartID, String region, String version, String CIC, String CRC, String CRCStatus, Paint CRCStatusColor, String MD5, String SHA1) {
        this.file = file;
        this.format = format;
        this.size = size;
        this.name = name;
        this.media = media;
        this.cartID = cartID;
        this.region = region;
        this.version = version;
        this.CIC = CIC;
        this.CRC = CRC;
        this.CRCStatus = CRCStatus;
        this.CRCStatusColor = CRCStatusColor;
        this.MD5 = MD5;
        this.SHA1 = SHA1;
    }
    
    // Getters
    public static File getFile() { return file; }
    public static String getFormat() { return format; }
    public static long getSize() { return size; }
    public static String getName() { return name; }
    public static String getMedia() { return media; }
    public static String getCartID() { return cartID; }
    public static String getRegion() { return region; }
    public static String getVersion() { return version; }
    public static String getCIC() { return CIC; }
    public static String getCRC() { return CRC; }
    public static String getCRCStatus() { return CRCStatus; }
    public static Paint getCRCStatusColor() { return CRCStatusColor; }
    public static String getMD5() { return MD5; }
    public static String getSHA1() { return SHA1; }
    
    // Setters
    public void setFile(File file) { this.file = file; }
    public void setFormat(String format) { this.format = format; }
    public void setSize(long size) { this.size = size; }
    public void setName(String name) { this.name = name; }
    public void setMedia(String media) { this.media = media; }
    public void setCartID(String cartID) { this.cartID = cartID; }
    public void setRegion(String region) { this.region = region; }
    public void setVersion(String version) { this.version = version; }
    public void setCIC(String CIC) { this.CIC = CIC; }
    public void setCRC(String CRC) { this.CRC = CRC; }
    public void setCRCStatus(String CRCStatus) { this.CRCStatus = CRCStatus; }
    public void setCRCStatusColor(Paint CRCStatusColor) { this.CRCStatusColor = CRCStatusColor; }
    public void setMD5(String MD5) { this.MD5 = MD5; }
    public void setSHA1(String SHA1) { this.SHA1 = SHA1; }
    
}
