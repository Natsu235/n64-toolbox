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
 * @author Dorian Pilorge
 */
@SuppressWarnings("AccessStaticViaInstance")

public class Rom {

    private static File file;
    private static String format;
    private static long size;
    private static String name;
    private static String media;
    private static String cartID;
    private static String region;
    private static String version;
    private static String CIC;
    private static String CRC;
    private static String CRCStatus;
    private static Paint CRCStatusColor;
    private static String byteFormat;
    private static String clockRate;
    private static String programCounter;
    private static String releaseAddress;
    private static String CRC1;
    private static String CRC2;
    private static String MD5;
    private static String SHA1;

    public Rom() {}

    public Rom(File file, String format, long size, String name, String media, String cartID, String region, String version, String CIC, String CRC, String CRCStatus, Paint CRCStatusColor, String byteFormat, String clockRate, String programCounter, String releaseAddress, String CRC1, String CRC2, String MD5, String SHA1) {
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
        this.byteFormat = byteFormat;
        this.clockRate = clockRate;
        this.programCounter = programCounter;
        this.releaseAddress = releaseAddress;
        this.CRC1 = CRC1;
        this.CRC2 = CRC2;
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
    public static String getByteFormat() { return byteFormat; }
    public static String getClockRate() { return clockRate; }
    public static String getProgramCounter() { return programCounter; }
    public static String getReleaseAddress() { return releaseAddress; }
    public static String getCRC1() { return CRC1; }
    public static String getCRC2() { return CRC2; }
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
    public void setByteFormat(String byteFormat) { this.byteFormat = byteFormat; }
    public void setClockRate(String clockRate) { this.clockRate = clockRate; }
    public void setProgramCounter(String programCounter) { this.programCounter = programCounter; }
    public void setReleaseAddress(String releaseAddress) { this.releaseAddress = releaseAddress; }
    public void setCRC1(String CRC1) { this.CRC1 = CRC1; }
    public void setCRC2(String CRC2) { this.CRC2 = CRC2; }
    public void setMD5(String MD5) { this.MD5 = MD5; }
    public void setSHA1(String SHA1) { this.SHA1 = SHA1; }

}
