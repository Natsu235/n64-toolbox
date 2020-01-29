/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phantomnat.n64toolbox.java.controller;

import com.phantomnat.n64toolbox.java.Launcher;
import com.phantomnat.n64toolbox.java.model.Configuration;
import com.phantomnat.n64toolbox.java.model.Rom;
import com.phantomnat.n64toolbox.java.model.RomZelda;
import com.phantomnat.n64toolbox.java.util.N64Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Dorian Pilorge
 */
@SuppressWarnings("AccessStaticViaInstance")

public class RomController {
    
    // Configuration
    private static final Configuration config = new Configuration();
    
    // Models
    private static final Rom rom = new Rom();                // Loaded ROM
    private static final RomZelda romZ = new RomZelda();     // Loaded ROM (Type Zelda)
    private static final N64Util N64Util = new N64Util();    // N64 Functions
    
    // Check if ROM is Loaded
    protected boolean isLoaded() {
        boolean state = rom.getFile().exists();
        return state;
    }
    
    // Check if ROM is Valid
    protected boolean isValid() throws FileNotFoundException, IOException {
        String fileName = rom.getFile().toString();
        String fileExt = FilenameUtils.getExtension(fileName);
        byte[] header = new byte[4];
        
        try (RandomAccessFile raf = new RandomAccessFile(rom.getFile(), "r")) {
            raf.seek(0);
            raf.readFully(header);
        }
        
        boolean state;
        switch (fileExt) {
            case "n64":
                return state = Objects.equals("40 12 37 80", N64Util.convertBytestoHex(header));
            case "v64":
                return state = Objects.equals("37 80 40 12", N64Util.convertBytestoHex(header));
            case "z64":
                return state = Objects.equals("80 37 12 40", N64Util.convertBytestoHex(header));
            default:
                return state = false;
        }
    }
    
    // Check if ROM is Type Zelda
    protected boolean isZelda() {
        String romCartID = rom.getCartID();
        
        boolean state;
        switch (romCartID) {
            case "ZL":    // OOT
            case "ZS":    // MM
            case "DL":    // MM DEMO
                return state = true;
            default:
                return state = false;
        }
    }
    
    /*
     * ====================
     * General Informations
     * ====================
     */
    
    // Define Loaded ROM File
    protected void setFile(File $romFile) {
        rom.setFile($romFile);
    }
    
    // Return Loaded ROM File
    protected File getFile() {
        File romFile = rom.getFile();
        return romFile;
    }
    
    // Load ROM Format
    protected void loadFormat() {
        String fileName = rom.getFile().toString();
        String fileExt = FilenameUtils.getExtension(fileName);
        rom.setFormat(fileExt);
    }
    
    // Return Loaded ROM Format
    protected String getFormat() {
        String romFormat = rom.getFormat();
        
        switch (romFormat) {
            case "n64":
                return "Little Endian (N64 Format)";
            case "v64":
                return "Byteswapped (Doctor V64 Format)";
            case "z64":
                return "Big Endian (Mr. Backup Z64 Format)";
            default:
                return Launcher.bundle.getString("invalid");
        }
    }
    
    // Load ROM Size
    protected void loadSize() {
        long romSize = rom.getFile().length();
        rom.setSize(romSize);
    }
    
    // Return Loaded ROM Size
    protected String getSize() {
        long romSize = rom.getSize();
        
        if (romSize <= 8388608)
            return "8 MB (" + romSize + " " + Launcher.bundle.getString("bytes") + ")";
        else if (romSize <= 12582912)
            return "12 MB (" + romSize + " " + Launcher.bundle.getString("bytes") + ")";
        else if (romSize <= 16777216)
            return "16 MB (" + romSize + " " + Launcher.bundle.getString("bytes") + ")";
        else if (romSize <= 33554432)
            return "32 MB (" + romSize + " " + Launcher.bundle.getString("bytes") + ")";
        else if (romSize <= 41943040)
            return "40 MB (" + romSize + " " + Launcher.bundle.getString("bytes") + ")";
        else if (romSize <= 67108864)
            return "64 MB (" + romSize + " " + Launcher.bundle.getString("bytes") + ")";
        else
            return "> 64 MB (" + romSize + " " + Launcher.bundle.getString("bytes") + ")";
    }
    
    // Load ROM Name
    protected void loadName() throws IOException {
        String romFormat = rom.getFormat();
        byte[] romName = N64Util.readFileToBytes(rom.getFile(), 32, 20);
        
        switch (romFormat) {
            case "n64":
                rom.setName(N64Util.convertN64toZ64(new String(romName), false).trim());
                break;
            case "v64":
                rom.setName(N64Util.convertV64toZ64(new String(romName), false).trim());
                break;
            case "z64":
            default:
                rom.setName(new String(romName).trim());
                break;
        }
    }
    
    // Return Loaded ROM Name
    protected String getName() {
        String romName = rom.getName();
        
        if (romName == null)
            romName = Launcher.bundle.getString("invalid");
        
        return romName;
    }
    
    // Load ROM Media
    protected void loadMedia() throws IOException {
        String romFormat = rom.getFormat();
        try (RandomAccessFile raf = new RandomAccessFile(rom.getFile(), "r")) {
            byte[] romMedia = new byte[1];

            switch (romFormat) {
                case "n64":
                    raf.seek(56);
                    break;
                case "v64":
                    raf.seek(58);
                    break;
                case "z64":
                    raf.seek(59);
                    break;
            }

            raf.read(romMedia);
            raf.close();
            rom.setMedia(new String(romMedia));
        }
    }
    
    // Return Loaded ROM Media
    protected String getMedia() {
        String romMedia = rom.getMedia();
        
        if (Objects.equals("N", romMedia))
            return romMedia + " (" + Launcher.bundle.getString("n64Cart") + ")";
        else if (Objects.equals("D", romMedia))
            return romMedia + " (" + Launcher.bundle.getString("64DDDisk") + ")";
        else if (Objects.equals("C", romMedia))
            return romMedia + " (" + Launcher.bundle.getString("n64CartExp") + ")";
        else if (Objects.equals("E", romMedia))
            return romMedia + " (" + Launcher.bundle.getString("64DDCartExp") + ")";
        else if (Objects.equals("Z", romMedia))
            return romMedia + " (" + Launcher.bundle.getString("aleck64Cart") + ")";
        else
            return romMedia + " (" + Launcher.bundle.getString("unknown") + ")";
    }
    
    // Load ROM Cartridge ID
    protected void loadCartID() throws IOException {
        String romFormat = rom.getFormat();
        try (RandomAccessFile raf = new RandomAccessFile(rom.getFile(), "r")) {
            String romCartID;
            byte[] b1 = new byte[1];
            byte[] b2 = new byte[1];
            long p1 = 0;
            long p2 = 0;

            switch (romFormat) {
                case "n64":
                    p1 = 63;
                    p2 = 62;
                    break;
                case "v64":
                    p1 = 61;
                    p2 = 60;
                    break;
                case "z64":
                    p1 = 60;
                    p2 = 61;
                    break;
            }

            raf.seek(p1);
            raf.read(b1);
            raf.seek(p2);
            raf.read(b2);
            raf.close();

            romCartID = new String(b1) + new String(b2);
            rom.setCartID(romCartID);
        }
    }
    
    // Return Loaded ROM Cartridge ID
    protected String getCartID() {
        String romCartID = rom.getCartID();
        
        if (romCartID == null)
            romCartID = Launcher.bundle.getString("invalid");
        
        return romCartID;
    }
    
    // Load ROM Region
    protected void loadRegion() throws IOException {
        String romFormat = rom.getFormat();
        try (RandomAccessFile raf = new RandomAccessFile(rom.getFile(), "r")) {
            byte[] romRegion = new byte[1];

            switch (romFormat) {
                case "n64":
                    raf.seek(61);
                    break;
                case "v64":
                    raf.seek(63);
                    break;
                case "z64":
                    raf.seek(62);
                    break;
            }

            raf.read(romRegion);
            raf.close();
            rom.setRegion(new String(romRegion));
        }
    }
    
    // Return Loaded ROM Region
    protected String getRegion() {
        String romRegion = rom.getRegion();
        
        if (Objects.equals("7", romRegion))
            return romRegion + " (" + Launcher.bundle.getString("regBeta") + ")";
        else if (Objects.equals("E", romRegion))
            return romRegion + " (" + Launcher.bundle.getString("regAmerican") + ")";
        else if (Objects.equals("J", romRegion))
            return romRegion + " (" + Launcher.bundle.getString("regJapanese") + ")";
        else if (Objects.equals("P", romRegion) || Objects.equals("X", romRegion) || Objects.equals("Y", romRegion))
            return romRegion + " (" + Launcher.bundle.getString("regEuropean") + ")";
        else if (Objects.equals("F", romRegion))
            return romRegion + " (" + Launcher.bundle.getString("regFrench") + ")";
        else if (Objects.equals("D", romRegion))
            return romRegion + " (" + Launcher.bundle.getString("regGerman") + ")";
        else if (Objects.equals("S", romRegion))
            return romRegion + " (" + Launcher.bundle.getString("regSpanish") + ")";
        else if (Objects.equals("I", romRegion))
            return romRegion + " (" + Launcher.bundle.getString("regItalian") + ")";
        else if (Objects.equals("A", romRegion))
            return romRegion + " (" + Launcher.bundle.getString("regAsian") + ")";
        else if (Objects.equals("B", romRegion))
            return romRegion + " (" + Launcher.bundle.getString("regBrazilian") + ")";
        else if (Objects.equals("C", romRegion))
            return romRegion + " (" + Launcher.bundle.getString("regChinese") + ")";
        else if (Objects.equals("H", romRegion))
            return romRegion + " (" + Launcher.bundle.getString("regDutch") + ")";
        else if (Objects.equals("K", romRegion))
            return romRegion + " (" + Launcher.bundle.getString("regKorean") + ")";
        else if (Objects.equals("N", romRegion))
            return romRegion + " (" + Launcher.bundle.getString("regCanadian") + ")";
        else if (Objects.equals("U", romRegion))
            return romRegion + " (" + Launcher.bundle.getString("regAustralian") + ")";
        else if (Objects.equals("I", romRegion))
            return romRegion + " (" + Launcher.bundle.getString("regItalian") + ")";
        else if (Objects.equals("W", romRegion))
            return romRegion + " (" + Launcher.bundle.getString("regScandinavian") + ")";
        else if (Objects.equals("G", romRegion))
            return romRegion + " (" + Launcher.bundle.getString("regNTSCGateway") + ")";
        else if (Objects.equals("L", romRegion))
            return romRegion + " (" + Launcher.bundle.getString("regPALGateway") + ")";
        
        return romRegion + " (" + Launcher.bundle.getString("unknown") + ")";
    }
    
    // Load ROM Version
    protected void loadVersion() throws IOException {
        String romFormat = rom.getFormat();
        try (RandomAccessFile raf = new RandomAccessFile(rom.getFile(), "r")) {
            byte[] romVersion = new byte[1];

            switch (romFormat) {
                case "n64":
                    raf.seek(60);
                    break;
                case "v64":
                    raf.seek(62);
                    break;
                case "z64":
                    raf.seek(63);
                    break;
            }

            raf.read(romVersion);
            raf.close();
            rom.setVersion(N64Util.convertBytestoHex(romVersion));
        }
    }
    
    // Return Loaded ROM Version
    protected String getVersion() {
        String romVersion = rom.getVersion();
        
        if (romVersion == null)
            romVersion = Launcher.bundle.getString("invalid");
        else {
            int upper = Character.getNumericValue(romVersion.charAt(0)) + 1;
            int lower = Character.digit(romVersion.charAt(1), 16);
            romVersion = "v" + upper + "." + lower;
        }
        
        return romVersion;
    }
    
    // Load ROM CIC
    protected void loadCIC() throws IOException {
        String romFormat = rom.getFormat();
        byte[] romCIC = N64Util.readFileToBytes(rom.getFile(), 64, 4032);
        byte[][] bootcodes = new byte[4][];
        String[] cic = {"6101-CIC", "6102-CIC", "6103-CIC", "6105-CIC", "6106-CIC"};
        InputStream[] is = new InputStream[4];
        for (int i = 0; i < is.length; i++) {
            is[i] = this.getClass().getResourceAsStream(config.getCICPath(cic[i]));
            bootcodes[i] = N64Util.readStreamToBytes(is[i], 0, is[i].available());
        }
        
        switch (romFormat) {
            case "n64":
                if (Objects.equals(N64Util.convertN64toZ64(N64Util.convertBytestoHex(romCIC), true), N64Util.convertBytestoHex(bootcodes[0])))
                    rom.setCIC("CIC-NUS-6101");
                else if (Objects.equals(N64Util.convertN64toZ64(N64Util.convertBytestoHex(romCIC), true), N64Util.convertBytestoHex(bootcodes[1])))
                    rom.setCIC("CIC-NUS-6102");
                else if (Objects.equals(N64Util.convertN64toZ64(N64Util.convertBytestoHex(romCIC), true), N64Util.convertBytestoHex(bootcodes[2])))
                    rom.setCIC("CIC-NUS-6103");
                else if (Objects.equals(N64Util.convertN64toZ64(N64Util.convertBytestoHex(romCIC), true), N64Util.convertBytestoHex(bootcodes[3])))
                    rom.setCIC("CIC-NUS-6105");
                else if (Objects.equals(N64Util.convertN64toZ64(N64Util.convertBytestoHex(romCIC), true), N64Util.convertBytestoHex(bootcodes[4])))
                    rom.setCIC("CIC-NUS-6106");
                break;
            case "v64":
                if (Objects.equals(N64Util.convertV64toZ64(N64Util.convertBytestoHex(romCIC), true), N64Util.convertBytestoHex(bootcodes[0])))
                    rom.setCIC("CIC-NUS-6101");
                else if (Objects.equals(N64Util.convertV64toZ64(N64Util.convertBytestoHex(romCIC), true), N64Util.convertBytestoHex(bootcodes[1])))
                    rom.setCIC("CIC-NUS-6102");
                else if (Objects.equals(N64Util.convertV64toZ64(N64Util.convertBytestoHex(romCIC), true), N64Util.convertBytestoHex(bootcodes[2])))
                    rom.setCIC("CIC-NUS-6103");
                else if (Objects.equals(N64Util.convertV64toZ64(N64Util.convertBytestoHex(romCIC), true), N64Util.convertBytestoHex(bootcodes[3])))
                    rom.setCIC("CIC-NUS-6105");
                else if (Objects.equals(N64Util.convertV64toZ64(N64Util.convertBytestoHex(romCIC), true), N64Util.convertBytestoHex(bootcodes[4])))
                    rom.setCIC("CIC-NUS-6106");
                break;
            case "z64":
                if (Objects.equals(N64Util.convertBytestoHex(romCIC), N64Util.convertBytestoHex(bootcodes[0])))
                    rom.setCIC("CIC-NUS-6101");
                else if (Objects.equals(N64Util.convertBytestoHex(romCIC), N64Util.convertBytestoHex(bootcodes[1])))
                    rom.setCIC("CIC-NUS-6102");
                else if (Objects.equals(N64Util.convertBytestoHex(romCIC), N64Util.convertBytestoHex(bootcodes[2])))
                    rom.setCIC("CIC-NUS-6103");
                else if (Objects.equals(N64Util.convertBytestoHex(romCIC), N64Util.convertBytestoHex(bootcodes[3])))
                    rom.setCIC("CIC-NUS-6105");
                else if (Objects.equals(N64Util.convertBytestoHex(romCIC), N64Util.convertBytestoHex(bootcodes[4])))
                    rom.setCIC("CIC-NUS-6106");
                break;
        }
    }
    
    // Return Loaded ROM CIC
    protected String getCIC() {
        String romCIC = rom.getCIC();
        
        if (romCIC == null)
            romCIC = Launcher.bundle.getString("invalid");
        
        return romCIC;
    }
    
    // Load ROM CRC
    protected void loadCRC() throws IOException {
        String romFormat = rom.getFormat();
        byte[] romCRC = N64Util.readFileToBytes(rom.getFile(), 16, 8);
        
        switch (romFormat) {
            case "n64":
                rom.setCRC(N64Util.convertN64toZ64(N64Util.convertBytestoHex(romCRC), true));
                break;
            case "v64":
                rom.setCRC(N64Util.convertV64toZ64(N64Util.convertBytestoHex(romCRC), true));
                break;
            case "z64":
                rom.setCRC(N64Util.convertBytestoHex(romCRC));
                break;
        }
    }
    
    // Return Loaded ROM CRC
    protected String getCRC() {
        String romCRC = rom.getCRC();
        
        if (romCRC == null)
            romCRC = Launcher.bundle.getString("invalid");
        
        return romCRC;
    }
    
    // Load ROM CRC Status
    protected void loadCRCStatus() throws IOException {
        try (InputStream is = this.getClass().getResourceAsStream(config.getCRCList())) {
            byte[] CRCList;
            CRCList = N64Util.readStreamToBytes(is, 0, is.available());
            is.close();

            if (new String(CRCList).contains(rom.getCRC())) {
                rom.setCRCStatus(Launcher.bundle.getString("verified"));
                rom.setCRCStatusColor(Color.MEDIUMSEAGREEN);
            }
            else {
                rom.setCRCStatus(Launcher.bundle.getString("modified"));
                rom.setCRCStatusColor(Color.CRIMSON);
            }
        }
    }
    
    // Return Loaded ROM CRC Status
    protected String getCRCStatus() {
        String romCRCStatus = rom.getCRCStatus();
        
        if (romCRCStatus == null)
            romCRCStatus = Launcher.bundle.getString("invalid");
        
        return romCRCStatus;
    }
    
    // Return Loaded ROM CRC Status Color
    protected Paint getCRCStatusColor() {
        Paint romCRCStatusColor = rom.getCRCStatusColor();
        
        if (romCRCStatusColor == null)
            romCRCStatusColor = Color.web("#333333");
        
        return romCRCStatusColor;
    }
    
    /*
     * ==================
     * Zelda Informations
     * ==================
     */
    
    // Load ROM Edition (Type Zelda)
    protected void loadEdition() throws IOException {
        String romFormat = rom.getFormat();
        try (RandomAccessFile raf = new RandomAccessFile(rom.getFile(), "r")) {
            String romZEdition;
            byte[] b1 = new byte[1];
            byte[] b2 = new byte[1];
            long p1 = 0;
            long p2 = 0;

            switch (romFormat) {
                case "n64":
                    p1 = 13;
                    p2 = 12;
                    break;
                case "v64":
                    p1 = 15;
                    p2 = 14;
                    break;
                case "z64":
                    p1 = 14;
                    p2 = 15;
                    break;
            }

            raf.seek(p1);
            raf.read(b1);
            raf.seek(p2);
            raf.read(b2);
            raf.close();

            romZEdition = N64Util.convertBytestoHex(b1) + " " + N64Util.convertBytestoHex(b2);

            if (Objects.equals(romZEdition, "14 49") || Objects.equals(romZEdition, "14 4B"))
                romZ.setEdition("Nintendo 64");
            else if (Objects.equals(romZEdition, "14 4C"))
                romZ.setEdition("Nintendo GameCube");
            else
                romZ.setEdition(null);
        }
    }
    
    // Return Loaded ROM Edition (Type Zelda)
    protected String getEdition() {
        String romZEdition = romZ.getEdition();
        
        if (this.isZelda()) {
            if (romZEdition == null)
                romZEdition = Launcher.bundle.getString("invalid");
        }
        else
            romZEdition = Launcher.bundle.getString("unsupported");
        
        return romZEdition;
    }
    
    // Load ROM Creator (Type Zelda)
    protected void loadCreator() throws IOException {
        File romFile = rom.getFile();
        String romFormat = rom.getFormat();
        byte[] romZCreator = N64Util.readFileToBytes(romFile, 28672, 151552);
        String romZEdition = romZ.getEdition();
        int offset = new String(romZCreator).indexOf("zelda@") + 28672;
        
        if (offset != -1 && Objects.equals(romFormat, "z64")) {
            if (Objects.equals(romZEdition, "Nintendo 64"))
                romZ.setCreator(new String(N64Util.readFileToBytes(romFile, offset, 11)));
            else if (Objects.equals(romZEdition, "Nintendo GameCube"))
                romZ.setCreator(new String (N64Util.readFileToBytes(romFile, offset, 13)));
        }
    }
    
    // Return Loaded ROM Creator (Type Zelda)
    protected String getCreator() {
        String romZCreator = romZ.getCreator();
        
        if (this.isZelda()) {
            if (romZCreator == null)
                romZCreator = Launcher.bundle.getString("invalid");
        }
        else
            romZCreator = Launcher.bundle.getString("unsupported");
        
        return romZCreator;
    }
    
    // Load ROM Compression (Type Zelda)
    protected void loadCompression() {
        long romSize = rom.getSize();
        String romCRC = rom.getCRC();

        if (romSize <= 33554432 || Objects.equals(romCRC, "9F C3 85 E5 3E CC 05 C7"))
            romZ.setCompression(true);
        else
            romZ.setCompression(false);
    }
    
    // Return Loaded ROM Compression (Type Zelda)
    protected String getCompression() {
        boolean romZCompression = romZ.getCompression();
        
        if (this.isZelda()) {
            if (romZCompression == true) {
                romZ.setCompressionColor(Color.CORAL);
                return Launcher.bundle.getString("compressed");
            }
            else {
                romZ.setCompressionColor(Color.CORNFLOWERBLUE);
                return Launcher.bundle.getString("decompressed");
            }
        }
        else
            return Launcher.bundle.getString("unsupported");
    }
    
    // Return Loaded ROM Compression Color (Type Zelda)
    protected Paint getCompressionColor() {
        Paint romCompressionColor = romZ.getCompressionColor();
        
        if (romCompressionColor == null || !this.isZelda())
            romCompressionColor = Color.web("#333333");
        
        return romCompressionColor;
    }
    
    /*
     * ==========================
     * Miscellaneous Informations
     * ==========================
     */
    
    // Load ROM Specified Header Parameter
    protected void loadHeader(String param) throws java.lang.Exception {
        File romFile = rom.getFile();
        String romFormat = rom.getFormat();
        byte[] romHeader;
        int offset = 0;
        int length = 0;
        
        switch (param) {
            case "byteFormat":
                offset = 0;
                length = 4;
                break;
            case "clockRate":
                offset = 4;
                length = 4;
                break;
            case "programCounter":
                offset = 8;
                length = 4;
                break;
            case "releaseAddress":
                offset = 12;
                length = 4;
                break;
        }
        
        romHeader = N64Util.readFileToBytes(romFile, offset, length);
        String setter = "set" + param.substring(0, 1).toUpperCase() + param.substring(1);
        
        if (!"byteFormat".equals(param)) {
            switch (romFormat) {
                case "n64":
                    rom.getClass().getMethod(setter, String.class).invoke(rom, new Object[]{"0x" + N64Util.convertN64toZ64(N64Util.convertBytestoHex(romHeader), true).replaceAll("\\s+", "")});
                case "v64":
                    rom.getClass().getMethod(setter, String.class).invoke(rom, new Object[]{"0x" + N64Util.convertV64toZ64(N64Util.convertBytestoHex(romHeader), true).replaceAll("\\s+", "")});
                case "z64":
                    rom.getClass().getMethod(setter, String.class).invoke(rom, new Object[]{"0x" + N64Util.convertBytestoHex(romHeader).replaceAll("\\s+", "")});
            }
        }
        else if ("byteFormat".equals(param)) {
            rom.setByteFormat("0x" + N64Util.convertBytestoHex(romHeader).replaceAll("\\s+", ""));
        }
    }
    
    // Return Loaded ROM Specified Header Parameter
    protected String getHeader(String param) throws java.lang.Exception {
        String getter = "get" + param.substring(0, 1).toUpperCase() + param.substring(1);
        String romHeader = (String) rom.getClass().getMethod(getter).invoke(rom);
        
        if (romHeader == null)
            romHeader = Launcher.bundle.getString("invalid");
        
        return romHeader;
    }
    
    // Load ROM Specified Checksum
    protected void loadChecksum(String hash) throws IOException {
        InputStream is = new FileInputStream(rom.getFile());
        
        switch (hash) {
            case "md5":
                rom.setMD5(DigestUtils.md5Hex(is));
                break;
            case "sha1":
                rom.setSHA1(DigestUtils.sha1Hex(is));
                break;
        }
    }
    
    // Return Loaded ROM Specified Checksum
    protected String getChecksum(String hash) {
        switch (hash) {
            case "md5":
                String romMD5 = rom.getMD5();
                if (romMD5 != null)
                    return romMD5;
                break;
            case "sha1":
                String romSHA1 = rom.getSHA1();
                if (romSHA1 != null)
                    return romSHA1;
                break;
        }
        
        return Launcher.bundle.getString("invalid");
    }
    
    // Save Loaded ROM Informations
    protected void saveData() throws java.lang.Exception {
        String fileName = rom.getFile().getName();
        File data = new File("dump/" + FilenameUtils.removeExtension(fileName) + ".txt");
        
        if (data.getParentFile().mkdir())
            data.createNewFile();
        
        // Generate Rom Infos
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        try (PrintWriter pw = new PrintWriter(data, "UTF-8")) {
            pw.println("----------------------------");
            pw.println(" N64 Rom Informations");
            pw.println(" Generated with N64 Toolbox");
            pw.println(" " + timeStamp);
            pw.println("----------------------------");
            pw.println("");
            pw.println("");
            pw.println("----------------------");
            pw.println(" General Informations");
            pw.println("----------------------");
            pw.println("Filename:                   " + fileName);
            pw.println("Filetype:                   " + this.getFormat());
            pw.println("Rom Size:                   " + this.getSize());
            pw.println("Rom Name:                   " + this.getName());
            pw.println("Rom Media:                  " + this.getMedia());
            pw.println("Rom Cartridge ID:           " + this.getCartID());
            pw.println("Rom Region:                 " + this.getRegion());
            pw.println("Rom Version:                " + this.getVersion());
            pw.println("Rom CIC:                    " + this.getCIC());
            pw.println("Rom CRC:                    " + this.getCRC());
            pw.println("Rom CRC Status:             " + this.getCRCStatus());
            //pw.println("");
            //pw.println("--------------------");
            //pw.println(" Zelda Informations");
            //pw.println("--------------------");
            //pw.println("Real Rom Name:              " + lblZName.getText());
            //pw.println("Rom Edition:                " + lblZEdition.getText());
            //pw.println("Rom Status:                 " + lblZStatus.getText());
            //pw.println("Rom Creator:                " + lblZCreator.getText());
            //pw.println("Rom Build Date:             " + lblZBuildDate.getText());
            pw.println("");
            pw.println("---------------------");
            pw.println(" Header Informations");
            pw.println("---------------------");
            pw.println("Rom Byte Format:            " + this.getHeader("byteFormat"));
            pw.println("Rom Clock Rate Override:    " + this.getHeader("clockRate"));
            pw.println("Rom Program Counter:        " + this.getHeader("programCounter"));
            pw.println("Rom Release Address:        " + this.getHeader("releaseAddress"));
            pw.println("");
            pw.println("------------------------");
            pw.println(" Checksums Informations");
            pw.println("------------------------");
            pw.println("MD5:                        " + this.getChecksum("md5"));
            pw.println("SHA-1:                      " + this.getChecksum("sha1"));
        }
    }
    
    // Output Loaded ROM Informations (Debug Mode)
    protected void debug() {
        System.out.println("");
        System.out.println("-- GENERAL INFORMATIONS --");
        System.out.println("Filename:                   " + FilenameUtils.removeExtension(rom.getFile().getName()));
        System.out.println("Filetype:                   ." + FilenameUtils.getExtension(rom.getFile().getName()));
        System.out.println("Rom Size:                   " + rom.getSize());
        System.out.println("Rom Name:                   " + rom.getName());
        System.out.println("Rom Media:                  " + rom.getMedia());
        System.out.println("Rom Cartridge ID:           " + rom.getCartID());
        System.out.println("Rom Region:                 " + rom.getRegion());
        System.out.println("Rom Version:                " + rom.getVersion());
        System.out.println("Rom CIC:                    " + rom.getCIC());
        System.out.println("Rom CRC:                    " + rom.getCRC());
        System.out.println("");
        System.out.println("-- HEADER INFORMATIONS --");
        System.out.println("Rom Byte Format:            " + rom.getByteFormat());
        System.out.println("Rom Clock Rate Override:    " + rom.getClockRate());
        System.out.println("Rom Program Counter:        " + rom.getProgramCounter());
        System.out.println("Rom Release Address:        " + rom.getReleaseAddress());
        System.out.println("");
        System.out.println("-- CHECKSUMS INFORMATIONS --");
        System.out.println("MD5:                        " + rom.getMD5());
        System.out.println("SHA-1:                      " + rom.getSHA1());
    }
    
}
