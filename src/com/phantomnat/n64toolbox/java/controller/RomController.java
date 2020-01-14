/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phantomnat.n64toolbox.java.controller;

import com.phantomnat.n64toolbox.java.Launcher;
import com.phantomnat.n64toolbox.java.model.Configuration;
import com.phantomnat.n64toolbox.java.model.Rom;
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
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author PhantomNatsu
 */
public class RomController {
    
    @FXML
    private Label lblCRCStatus;
    
    // Configuration
    private static Configuration config = new Configuration();
    
    // Controlers
    private static ApplicationController appCtrl = new ApplicationController(); 
    
    // Models
    private static Rom rom = new Rom();                // Loaded ROM
    private static N64Util N64Util = new N64Util();    // N64 Functions
    
    // Check if ROM is Loaded
    public boolean isLoaded() {
        boolean state = rom.getFile().exists() ? true : false;
        return state;
    }
    
    // Check if ROM is Valid
    protected boolean isValid() throws FileNotFoundException, IOException {
        String fileName = rom.getFile().toString();
        String fileExt = FilenameUtils.getExtension(fileName);
        byte[] header = new byte[4];
        
        RandomAccessFile raf = new RandomAccessFile(rom.getFile(), "rw");
        raf.seek(0);
        raf.readFully(header);
        raf.close();
        
        boolean state;
        switch (fileExt) {
            case "n64":
                return state = Objects.equals("40 12 37 80", N64Util.convertBytestoHex(header)) ? true : false;
            case "v64":
                return state = Objects.equals("37 80 40 12", N64Util.convertBytestoHex(header)) ? true : false;
            case "z64":
                return state = Objects.equals("80 37 12 40", N64Util.convertBytestoHex(header)) ? true : false;
            default:
                return state = false;
        }
    }
    
    /*
     * ====================
     * General Informations
     * ====================
     */
    
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
        RandomAccessFile raf = new RandomAccessFile(rom.getFile(), "rw");
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
        
        raf.readFully(romMedia);
        raf.close();
        rom.setMedia(new String(romMedia));
    }
    
    // Return Loaded ROM Media
    protected String getMedia() {
        String romMedia = rom.getMedia();
        
        if (Objects.equals("N", new String(romMedia)))
            return new String(romMedia) + " (" + Launcher.bundle.getString("n64Cart") + ")";
        else if (Objects.equals("D", new String(romMedia)))
            return new String(romMedia) + " (" + Launcher.bundle.getString("64DDDisk") + ")";
        else if (Objects.equals("C", new String(romMedia)))
            return new String(romMedia) + " (" + Launcher.bundle.getString("n64CartExp") + ")";
        else if (Objects.equals("E", new String(romMedia)))
            return new String(romMedia) + " (" + Launcher.bundle.getString("64DDCartExp") + ")";
        else if (Objects.equals("Z", new String(romMedia)))
            return new String(romMedia) + " (" + Launcher.bundle.getString("aleck64Cart") + ")";
        else
            return new String(romMedia) + " (" + Launcher.bundle.getString("unknown") + ")";
    }
    
    // Load ROM Cartridge ID
    protected void loadCartID() throws IOException {
        String romFormat = rom.getFormat();
        RandomAccessFile raf = new RandomAccessFile(rom.getFile(), "rw");
        String romCartID = null;
        byte[] b1 = new byte[1];
        byte[] b2 = new byte[1];
        
        switch (romFormat) {
            case "n64":
                raf.seek(63);
                raf.readFully(b1);
                raf.seek(62);
                raf.readFully(b2);
                romCartID = new String(b1) + new String(b2);
                break;
            case "v64":
                raf.seek(61);
                raf.readFully(b1);
                raf.seek(60);
                raf.readFully(b2);
                romCartID = new String(b1) + new String(b2);
                break;
            case "z64":
                raf.seek(60);
                raf.readFully(b1);
                raf.seek(61);
                raf.readFully(b2);
                romCartID = new String(b1) + new String(b2);
                break;
        }
        
        raf.close();
        rom.setCartID(romCartID);
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
        RandomAccessFile raf = new RandomAccessFile(rom.getFile(), "rw");
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
        
        raf.readFully(romRegion);
        raf.close();
        
        rom.setRegion(new String(romRegion));
    }
    
    // Return Loaded ROM Region
    protected String getRegion() {
        String romRegion = rom.getRegion();
        
        if (Objects.equals("7", new String(romRegion)))
            return new String(romRegion) + " (" + Launcher.bundle.getString("regBeta") + ")";
        else if (Objects.equals("E", new String(romRegion)))
            return new String(romRegion) + " (" + Launcher.bundle.getString("regAmerican") + ")";
        else if (Objects.equals("J", new String(romRegion)))
            return new String(romRegion) + " (" + Launcher.bundle.getString("regJapanese") + ")";
        else if (Objects.equals("P", new String(romRegion)) || Objects.equals("X", new String(romRegion)) || Objects.equals("Y", new String(romRegion)))
            return new String(romRegion) + " (" + Launcher.bundle.getString("regEuropean") + ")";
        else if (Objects.equals("F", new String(romRegion)))
            return new String(romRegion) + " (" + Launcher.bundle.getString("regFrench") + ")";
        else if (Objects.equals("D", new String(romRegion)))
            return new String(romRegion) + " (" + Launcher.bundle.getString("regGerman") + ")";
        else if (Objects.equals("S", new String(romRegion)))
            return new String(romRegion) + " (" + Launcher.bundle.getString("regSpanish") + ")";
        else if (Objects.equals("I", new String(romRegion)))
            return new String(romRegion) + " (" + Launcher.bundle.getString("regItalian") + ")";
        else if (Objects.equals("A", new String(romRegion)))
            return new String(romRegion) + " (" + Launcher.bundle.getString("regAsian") + ")";
        else if (Objects.equals("B", new String(romRegion)))
            return new String(romRegion) + " (" + Launcher.bundle.getString("regBrazilian") + ")";
        else if (Objects.equals("C", new String(romRegion)))
            return new String(romRegion) + " (" + Launcher.bundle.getString("regChinese") + ")";
        else if (Objects.equals("H", new String(romRegion)))
            return new String(romRegion) + " (" + Launcher.bundle.getString("regDutch") + ")";
        else if (Objects.equals("K", new String(romRegion)))
            return new String(romRegion) + " (" + Launcher.bundle.getString("regKorean") + ")";
        else if (Objects.equals("N", new String(romRegion)))
            return new String(romRegion) + " (" + Launcher.bundle.getString("regCanadian") + ")";
        else if (Objects.equals("U", new String(romRegion)))
            return new String(romRegion) + " (" + Launcher.bundle.getString("regAustralian") + ")";
        else if (Objects.equals("I", new String(romRegion)))
            return new String(romRegion) + " (" + Launcher.bundle.getString("regItalian") + ")";
        else if (Objects.equals("W", new String(romRegion)))
            return new String(romRegion) + " (" + Launcher.bundle.getString("regScandinavian") + ")";
        else if (Objects.equals("G", new String(romRegion)))
            return new String(romRegion) + " (" + Launcher.bundle.getString("regNTSCGateway") + ")";
        else if (Objects.equals("L", new String(romRegion)))
            return new String(romRegion) + " (" + Launcher.bundle.getString("regPALGateway") + ")";
        
        return new String(romRegion) + " (" + Launcher.bundle.getString("unknown") + ")";
    }
    
    // Load ROM Version
    protected void loadVersion() throws IOException {
        String romFormat = rom.getFormat();
        RandomAccessFile raf = new RandomAccessFile(rom.getFile(), "rw");
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
    
    // Return Loaded ROM Version
    protected String getVersion() {
        String romVersion = rom.getVersion();
        
        int upper = Character.getNumericValue(romVersion.charAt(0)) + 1;
        int lower = Character.digit(romVersion.charAt(1), 16);
        
        return new String("v" + upper + "." + lower);
    }
    
    // Return Loaded ROM CIC
    protected String loadCIC() throws IOException {
        String fileName = rom.getFile().toString();
        String fileExt = FilenameUtils.getExtension(fileName);
        InputStream is1 = this.getClass().getResourceAsStream(config.getCIC6101());
        InputStream is2 = this.getClass().getResourceAsStream(config.getCIC6102());
        InputStream is3 = this.getClass().getResourceAsStream(config.getCIC6103());
        InputStream is5 = this.getClass().getResourceAsStream(config.getCIC6105());
        InputStream is6 = this.getClass().getResourceAsStream(config.getCIC6106());
        
        byte[] romCIC = N64Util.readFileToBytes(rom.getFile(), 64, 4032);
        byte[] CIC6101, CIC6102, CIC6103, CIC6105, CIC6106;
        CIC6101 = N64Util.readStreamToBytes(is1, 0, is1.available());
        CIC6102 = N64Util.readStreamToBytes(is2, 0, is2.available());
        CIC6103 = N64Util.readStreamToBytes(is3, 0, is3.available());
        CIC6105 = N64Util.readStreamToBytes(is5, 0, is5.available());
        CIC6106 = N64Util.readStreamToBytes(is6, 0, is6.available());

        switch (fileExt) {
            case "n64":
                if (Objects.equals(N64Util.convertN64toZ64(N64Util.convertBytestoHex(romCIC), true), N64Util.convertBytestoHex(CIC6101)))
                    return "CIC-NUS-6101";
                else if (Objects.equals(N64Util.convertN64toZ64(N64Util.convertBytestoHex(romCIC), true), N64Util.convertBytestoHex(CIC6102)))
                    return "CIC-NUS-6102";
                else if (Objects.equals(N64Util.convertN64toZ64(N64Util.convertBytestoHex(romCIC), true), N64Util.convertBytestoHex(CIC6103)))
                    return "CIC-NUS-6103";
                else if (Objects.equals(N64Util.convertN64toZ64(N64Util.convertBytestoHex(romCIC), true), N64Util.convertBytestoHex(CIC6105)))
                    return "CIC-NUS-6105";
                else if (Objects.equals(N64Util.convertN64toZ64(N64Util.convertBytestoHex(romCIC), true), N64Util.convertBytestoHex(CIC6106)))
                    return "CIC-NUS-6106";
            case "v64":
                if (Objects.equals(N64Util.convertV64toZ64(N64Util.convertBytestoHex(romCIC), true), N64Util.convertBytestoHex(CIC6101)))
                    return "CIC-NUS-6101";
                else if (Objects.equals(N64Util.convertV64toZ64(N64Util.convertBytestoHex(romCIC), true), N64Util.convertBytestoHex(CIC6102)))
                    return "CIC-NUS-6102";
                else if (Objects.equals(N64Util.convertV64toZ64(N64Util.convertBytestoHex(romCIC), true), N64Util.convertBytestoHex(CIC6103)))
                    return "CIC-NUS-6103";
                else if (Objects.equals(N64Util.convertV64toZ64(N64Util.convertBytestoHex(romCIC), true), N64Util.convertBytestoHex(CIC6105)))
                    return "CIC-NUS-6105";
                else if (Objects.equals(N64Util.convertV64toZ64(N64Util.convertBytestoHex(romCIC), true), N64Util.convertBytestoHex(CIC6106)))
                    return "CIC-NUS-6106";
            case "z64":
                if (Objects.equals(N64Util.convertBytestoHex(romCIC), N64Util.convertBytestoHex(CIC6101)))
                    return "CIC-NUS-6101";
                else if (Objects.equals(N64Util.convertBytestoHex(romCIC), N64Util.convertBytestoHex(CIC6102)))
                    return "CIC-NUS-6102";
                else if (Objects.equals(N64Util.convertBytestoHex(romCIC), N64Util.convertBytestoHex(CIC6103)))
                    return "CIC-NUS-6103";
                else if (Objects.equals(N64Util.convertBytestoHex(romCIC), N64Util.convertBytestoHex(CIC6105)))
                    return "CIC-NUS-6105";
                else if (Objects.equals(N64Util.convertBytestoHex(romCIC), N64Util.convertBytestoHex(CIC6106)))
                    return "CIC-NUS-6106";
            default:
                return Launcher.bundle.getString("invalid");
        }
    }
    
    // Return Loaded ROM CRC
    protected String loadCRC() throws IOException {
        String fileName = rom.getFile().toString();
        String fileExt = FilenameUtils.getExtension(fileName);
        byte[] romCRC = N64Util.readFileToBytes(rom.getFile(), 16, 8);
        
        switch (fileExt) {
            case "n64":
                return N64Util.convertN64toZ64(N64Util.convertBytestoHex(romCRC), true);
            case "v64":
                return N64Util.convertV64toZ64(N64Util.convertBytestoHex(romCRC), true);
            case "z64":
                return N64Util.convertBytestoHex(romCRC);
            default:
                return Launcher.bundle.getString("invalid");
        }
    }
    
    // Load ROM CRC Status
    protected void loadCRCStatus() throws IOException {
        InputStream is = this.getClass().getResourceAsStream(config.getCRCList());
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
            romCRCStatusColor = Color.BLACK;
        
        return romCRCStatusColor;
    }
    
    
    /*
     * ==========================
     * Miscellaneous Informations
     * ==========================
     */
    
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
    
    // Save Loaded ROM Infos
    protected void saveData() throws IOException {
        String fileName = rom.getFile().getName();
        File data = new File("dump/" + FilenameUtils.removeExtension(fileName) + ".txt");
        
        if (data.getParentFile().mkdir())
            data.createNewFile();
        
        // Generate Rom Infos
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        PrintWriter pw = new PrintWriter(data, "UTF-8");
        pw.println("----------------------------");
        pw.println(" N64 Rom Informations");
        pw.println(" Generated with N64 Toolbox");
        pw.println(" " + timeStamp);
        pw.println("----------------------------");
        pw.println("");
        pw.println("");
        pw.println("> GENERAL INFORMATIONS");
        pw.println("");
        pw.println("Filename:                   " + fileName);
        pw.println("Filetype:                   " + this.getFormat());
        pw.println("Rom Size:                   " + this.getSize());
        pw.println("Rom Name:                   " + this.getName());
        pw.println("Rom Media Format:           " + this.getMedia());
        pw.println("Rom Cartridge ID:           " + this.getCartID());
        //pw.println("Rom Region:                 " + this.getRegion());
        //pw.println("Rom Version:                " + this.getVersion());
        //pw.println("Rom CIC Version:            " + lblCICVersion.getText());
        //pw.println("Rom CRC:                    " + lblCRC.getText());
        //pw.println("Rom CRC Status:             " + lblCRCStatus.getText());
        //pw.println("");
        //pw.println("ZELDA INFORMATIONS");
        //pw.println("Real Rom Name:              " + lblZName.getText());
        //pw.println("Rom Edition:                " + lblZEdition.getText());
        //pw.println("Rom Status:                 " + lblZStatus.getText());
        //pw.println("Rom Creator:                " + lblZCreator.getText());
        //pw.println("Rom Build Date:             " + lblZBuildDate.getText());
        //pw.println("");
        //pw.println(">> Header Informations");
        //pw.println("Rom Byte Format:            " + lblByteFormat.getText());
        //pw.println("Rom Clock Rate Override:    " + lblClockRateOver.getText());
        //pw.println("Rom Program Counter:        " + lblProgramCounter.getText());
        //pw.println("Rom Release Address:        " + lblReleaseAddress.getText());
        pw.println("");
        pw.println("> CHECKSUMS INFORMATIONS");
        pw.println("");
        pw.println("MD5:                        " + this.getChecksum("md5"));
        pw.println("SHA-1:                      " + this.getChecksum("sha1"));
        pw.close();
    }
    
    // Print Loaded ROM Infos
    protected void debug() throws IOException {
        System.out.println("- GENERAL INFORMATIONS -");
        System.out.println("Filename:            " + FilenameUtils.removeExtension(rom.getFile().getName()));
        System.out.println("Filetype:            ." + FilenameUtils.getExtension(rom.getFile().getName()));
        System.out.println("Rom Size:            " + rom.getSize());
        System.out.println("Rom Name:            " + rom.getName());
        System.out.println("Rom Media:           " + rom.getMedia());
        System.out.println("Rom Cartridge ID:    " + rom.getCartID());
        System.out.println("Rom Region:          " + rom.getRegion());
        System.out.println("Rom Version:         " + rom.getVersion());
        System.out.println("Rom CIC:             " + rom.getCIC());
        System.out.println("Rom CRC:             " + rom.getCRC());
        System.out.println("");
        System.out.println("- CHECKSUMS INFORMATIONS -");
        System.out.println("MD5:                 " + this.getChecksum("md5"));
        System.out.println("SHA-1:               " + this.getChecksum("sha1"));
    }
    
}
