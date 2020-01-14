/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phantomnat.n64toolbox.java.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Objects;

/**
 *
 * @author PhantomNatsu
 */
public class N64Util {
    
    // Convert Bytes to Hex
    public String convertBytestoHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        
        for (byte b : bytes)
            sb.append(String.format("%02X ", b));
        
        String hex = sb.toString();
        return hex.substring(0, hex.length() - 1);
    }
    
    // Convert N64 to Z64 Format
    public String convertN64toZ64(String str, boolean hex) {
        StringBuilder sb = new StringBuilder();
        String s = str;
        int i;
        
        if (hex) {
            s = str.replaceAll("\\s+", "");
            
            for (i = 0; i < s.length() / 8; i++) {
                sb.append(s.charAt(i * 8 + 6));
                sb.append(s.charAt(i * 8 + 7));
                sb.append(s.charAt(i * 8 + 4));
                sb.append(s.charAt(i * 8 + 5));
                sb.append(s.charAt(i * 8 + 2));
                sb.append(s.charAt(i * 8 + 3));
                sb.append(s.charAt(i * 8));
                sb.append(s.charAt(i * 8 + 1));
            }
        }
        else {
            for (i = 0; i < s.length() / 4; i++) {
                sb.append(s.charAt(i * 4 + 3));
                sb.append(s.charAt(i * 4 + 2));
                sb.append(s.charAt(i * 4 + 1));
                sb.append(s.charAt(i * 4));
            }
        }
        
        s = sb.toString();
        
        if (hex) {
            s = s.replaceAll("..", "$0 ");
            return s.substring(0, s.length() - 1);
        }
        
        return s;
    }
    
    // Convert V64 to Z64 Format
    public String convertV64toZ64(String str, boolean hex) {
        StringBuilder sb = new StringBuilder();
        String s = str;
        int i;
        
        if (hex) {
            s = str.replaceAll("\\s+", "");
            
            for (i = 0; i < s.length() / 4; i++) {
                sb.append(s.charAt(i * 4 + 2));
                sb.append(s.charAt(i * 4 + 3));
                sb.append(s.charAt(i * 4));
                sb.append(s.charAt(i * 4 + 1));
            }
        }
        else {
            for (i = 0; i < s.length() / 2; i++) {
                sb.append(s.charAt(i * 2 + 1));
                sb.append(s.charAt(i * 2));
            }
        }
        
        s = sb.toString();
        
        if (hex) {
            s = s.replaceAll("..", "$0 ");
            return s.substring(0, s.length() - 1);
        }
        
        return s;
    }
    
    // Return the Real Zelda ROM Name
    public String realZRomName(String romCRC, String romRegion) {
        // TODO: Get Real Name from N64-CRC-Database!
        if (Objects.equals(romCRC, "EC 70 11 B7 76 16 D7 2B") && Objects.equals(romRegion, "E") || Objects.equals(romCRC, "93 52 2E 7B E5 06 D4 27") && Objects.equals(romRegion, "E"))
            return "Legend of Zelda - Ocarina of Time (U)";
        if (Objects.equals(romCRC, "D4 3D A8 1F 02 1E 1E 19") && Objects.equals(romRegion, "E") || Objects.equals(romCRC, "93 40 C3 93 67 6E EC 50") && Objects.equals(romRegion, "E"))
            return "Legend of Zelda - Ocarina of Time (U) (v1.1)";
        if (Objects.equals(romCRC, "B0 44 B5 69 37 3C 19 85") || Objects.equals(romCRC, "EE 9D 53 B5 BC 01 D0 15"))
            return "Legend of Zelda - Ocarina of Time (E)";
        if (Objects.equals(romCRC, "69 3B A2 AE B7 F1 4E 9F") && Objects.equals(romRegion, "E") || Objects.equals(romCRC, "2D 90 F1 2A 94 D3 CC DC") && Objects.equals(romRegion, "E"))
            return "Legend of Zelda - Ocarina of Time (U) (v1.2)";
        if (Objects.equals(romCRC, "B2 05 5F BD 0B AB 4E 0C") || Objects.equals(romCRC, "DC 55 D0 61 3D AE 74 68"))
            return "Legend of Zelda - Ocarina of Time (E) (v1.1)";
        if (Objects.equals(romCRC, "F3 DD 35 BA 41 52 E0 75") || Objects.equals(romCRC, "31 0D BE A1 F9 D0 13 81"))
            return "Legend of Zelda - Ocarina of Time (U) (GameCube Edition)";
        if (Objects.equals(romCRC, "09 46 5A C3 F8 CB 50 1B") || Objects.equals(romCRC, "55 06 D4 DF E6 0C AE 12"))
            return "Legend of Zelda - Ocarina of Time (E) (GameCube Edition)";
        if (Objects.equals(romCRC, "F0 34 00 1A AE 47 ED 06") || Objects.equals(romCRC, "35 4A CA 41 B7 BE 8E 27"))
            return "Legend of Zelda - Ocarina of Time Master Quest (U) (GameCube Edition)";
        if (Objects.equals(romCRC, "72 67 05 5B 7B E3 1E 26") || Objects.equals(romCRC, "91 7D 18 F6 69 BC 54 53"))
            return "Legend of Zelda - Ocarina of Time Master Quest (E) (GameCube Edition) (Debug)";
        if (Objects.equals(romCRC, "1D 41 36 F3 AF 63 EE A9") || Objects.equals(romCRC, "69 61 3A 0F B4 2D B8 E1"))
            return "Legend of Zelda - Ocarina of Time Master Quest (E) (GameCube Edition)";
        if (Objects.equals(romCRC, "BF 79 93 45 39 FF 7A 02") || Objects.equals(romCRC, "36 5A 04 08 B0 89 26 77"))
            return "Legend of Zelda - Majora's Mask (U) (Preview Demo)";
        if (Objects.equals(romCRC, "53 54 63 1C 03 A2 DE F0") || Objects.equals(romCRC, "DA 69 83 E7 50 67 44 58"))
            return "Legend of Zelda - Majora's Mask (U)";
        if (Objects.equals(romCRC, "E9 79 55 C6 BC 33 8D 38") || Objects.equals(romCRC, "C7 2B 05 D9 E3 86 42 05"))
            return "Legend of Zelda - Majora's Mask (E)";
        if (Objects.equals(romCRC, "9F C3 85 E5 3E CC 05 C7") || Objects.equals(romCRC, "F4 F3 9A 00 6D 8E 0A E2"))
            return "Legend of Zelda - Majora's Mask (E) (v1.1) (Debug)";
        if (Objects.equals(romCRC, "0A 5D 8F 83 98 C5 37 1A") || Objects.equals(romCRC, "6E 26 15 2E A3 E1 CF B1"))
            return "Legend of Zelda - Majora's Mask (E) (v1.1)";
        if (Objects.equals(romCRC, "B4 43 EB 08 4D B3 11 93") || Objects.equals(romCRC, "2D F1 D4 D4 83 BE FF DC"))
            return "Legend of Zelda - Majora's Mask (U) (GameCube Edition)";
        if (Objects.equals(romCRC, "6A EC EC 4F F0 92 48 14") || Objects.equals(romCRC, "80 56 28 2B 2B BE C7 BB"))
            return "Legend of Zelda - Majora's Mask (E) (GameCube Edition)";
        if (Objects.equals(romCRC, "EC 70 11 B7 76 16 D7 2B") && Objects.equals(romRegion, "J") || Objects.equals(romCRC, "93 52 2E 7B E5 06 D4 27") && Objects.equals(romRegion, "J"))
            return "Zelda no Densetsu - Toki no Ocarina (J)";
        if (Objects.equals(romCRC, "D4 3D A8 1F 02 1E 1E 19") && Objects.equals(romRegion, "J") || Objects.equals(romCRC, "93 40 C3 93 67 6E EC 50") && Objects.equals(romRegion, "J"))
            return "Zelda no Densetsu - Toki no Ocarina (J) (v1.1)";
        if (Objects.equals(romCRC, "69 3B A2 AE B7 F1 4E 9F") && Objects.equals(romRegion, "J") || Objects.equals(romCRC, "2D 90 F1 2A 94 D3 CC DC") && Objects.equals(romRegion, "J"))
            return "Zelda no Densetsu - Toki no Ocarina (J) (v1.2)";
        if (Objects.equals(romCRC, "F6 11 F4 BA C5 84 13 5C") || Objects.equals(romCRC, "36 86 1A 41 AE F0 56 22"))
            return "Zelda no Densetsu - Toki no Ocarina (J) (GameCube Edition)";
        if (Objects.equals(romCRC, "F4 3B 45 BA 2F 0E 9B 6F") || Objects.equals(romCRC, "32 18 0E C1 41 2E 86 42"))
            return "Zelda no Densetsu - Toki no Ocarina Ura (J) (GameCube Edition)";
        if (Objects.equals(romCRC, "EC 41 73 12 EB 31 DE 5F") || Objects.equals(romCRC, "49 D0 52 0D 0C 68 99 00"))
            return "Zelda no Densetsu - Majora no Kamen (J)";
        if (Objects.equals(romCRC, "69 AE 04 38 2C 63 F3 F3") || Objects.equals(romCRC, "94 B2 19 27 7E 02 33 42"))
            return "Zelda no Densetsu - Majora no Kamen (J) (v1.1)";
        if (Objects.equals(romCRC, "84 73 D0 C1 23 12 06 66") || Objects.equals(romCRC, "10 08 B3 C5 FA 59 11 8D"))
            return "Zelda no Densetsu - Majora no Kamen (J) (GameCube Edition)";
        
        return null;
    }
    
    // Read a File to Bytes
    public byte[] readFileToBytes(File file, int offset, int length) throws FileNotFoundException, IOException {
        byte[] data = new byte[length];
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        raf.seek(offset);
        raf.readFully(data);
        raf.close();
        
        return data;
    }
    
    // Read an InputStream to Bytes
    public byte[] readStreamToBytes(InputStream is, int offset, int length) throws IOException {
        byte[] data = new byte[length];
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        
        int i;
        while ((i = is.read(data, offset, data.length)) != -1) {
            buffer.write(data, offset, i);
        }
        
        buffer.close();
        
        return data;
    }

}
