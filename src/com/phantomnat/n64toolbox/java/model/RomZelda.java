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
    
    private static String realName;
    private static String edition;
    private static String creator;
    private static String build;
    private static boolean compression;
    private static Paint compressionColor;
    
    public RomZelda() {}
    
    public RomZelda(File file, String format, long size, String name, String media, String cartID, String region, String version, String CIC, String CRC, String CRCStatus, Paint CRCStatusColor, String byteFormat, String clockRate, String programCounter, String releaseAddress, String CRC1, String CRC2, String MD5, String SHA1, String realName, String edition, String creator, String build, boolean compression, Paint compressionColor) {
        super(file, format, size, name, media, cartID, region, version, CIC, CRC, CRCStatus, CRCStatusColor, byteFormat, clockRate, programCounter, releaseAddress, CRC1, CRC2, MD5, SHA1);
        this.realName = realName;
        this.edition = edition;
        this.creator = creator;
        this.build = build;
        this.compression = compression;
        this.compressionColor = compressionColor;
    }
    
    // Getters
    public static String getRealName() { return realName; }
    public static String getEdition() { return edition; }
    public static String getCreator() { return creator; }
    public static String getBuild() { return build; }
    public static boolean getCompression() { return compression; }
    public static Paint getCompressionColor() { return compressionColor; }
    
    // Setters
    public void setRealName(String realName) { this.realName = realName; }
    public void setEdition(String edition) { this.edition = edition; }
    public void setCreator(String creator) { this.creator = creator; }
    public void setBuild(String build) { this.build = build; }
    public void setCompression(boolean compression) { this.compression = compression; }
    public void setCompressionColor(Paint compressionColor) { this.compressionColor = compressionColor; }
    
}
