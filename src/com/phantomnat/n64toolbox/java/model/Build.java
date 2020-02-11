/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phantomnat.n64toolbox.java.model;

/**
 *
 * @author Dorian Pilorge
 */
@SuppressWarnings("AccessStaticViaInstance")

public class Build {

    private static String title = "N64 Toolbox";
    private static String version = "1.0";
    private static String creator = "PhantomNatsu";
    private static String javafx = "8";

    public Build() {}

    // Getters
    public static String getTitle() { return title; }
    public static String getVersion() { return version; }
    public static String getCreator() { return creator; }
    public static String getJavaFX() { return javafx; }

    // Setters
    // public void setTitle(String title) { this.title = title; }
    // public void setVersion(String version) { this.version = version; }
    // public void setCreator(String creator) { this.creator = creator; }
    // public void setJavaFX(String javafx) { this.javafx = javafx; }

}
