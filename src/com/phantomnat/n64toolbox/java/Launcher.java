/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phantomnat.n64toolbox.java;

import com.phantomnat.n64toolbox.java.model.Build;
import com.phantomnat.n64toolbox.java.model.Configuration;
import com.phantomnat.n64toolbox.java.model.Exception;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.ini4j.Ini;

/**
 *
 * @author Dorian Pilorge
 */
@SuppressWarnings("AccessStaticViaInstance")

public class Launcher extends Application {

    // Configuration
    private static final Configuration config = new Configuration();
    private static final Ini options = new Ini();

    // Models
    private static final Build build = new Build();

    // Defined Language
    public static ResourceBundle bundle;

    @Override
    // On Application Openning
    public void start(Stage stage) {

        // Load Arguments
        boolean debug = false;
        List<String> params = getParameters().getUnnamed();
        for (String param : params) {
            if (Objects.equals("-debug", param))
                debug = true;
        }
        String lang = getParameters().getNamed().get("lang");

        // Load Manifest
        String buildDate = null;
        try {
            InputStream is = getClass().getResourceAsStream("/META-INF/MANIFEST.MF");
            Manifest manifest = new Manifest(is);
            Attributes attributes = manifest.getMainAttributes();
            buildDate = attributes.getValue("Build-Date");
        }
        catch (IOException ex) {
            Exception except = new Exception(bundle.getString("exceptionHandler"), bundle.getString("exceptionHandlerText"), bundle.getString("exceptionHeader"), ex);
        }

        // Load Configuration
        File configFile = new File(config.getConfigPath());
        if (configFile.exists()) {
            try {
                options.load(new FileReader(config.getConfigPath()));
            }
            catch (IOException ex) {
                Exception except = new Exception(bundle.getString("exceptionHandler"), bundle.getString("exceptionHandlerText"), bundle.getString("exceptionHeader"), ex);
            }
            config.setRomDirectory(options.get("Local", "RomDirectory", File.class));
            config.setLanguage(new Locale(lang != null ? lang : options.get("Local", "Language", String.class)));
            config.setWidth(options.get("Window", "Width", double.class));
            config.setHeight(options.get("Window", "Height", double.class));
            config.setResizable(options.get("Window", "IsResizable", boolean.class));
            config.setDebug(debug ? true : options.get("Debug", "IsDebug", boolean.class));
        }

        // Load Language
        bundle = ResourceBundle.getBundle("com.phantomnat.n64toolbox.resources.bundles.language", config.getLanguage());

        // Load Interface
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("view/ApplicationLayout.fxml"), bundle);
        }
        catch (IOException ex) {
            Exception except = new Exception(bundle.getString("exceptionHandler"), bundle.getString("exceptionHandlerText"), bundle.getString("exceptionHeader"), ex);
        }

        Image icon = new Image(getClass().getResource(config.getIcon()).toString());
        Scene scene = new Scene(root, config.getWidth(), config.getHeight());
        stage.setScene(scene);
        stage.setTitle(build.getTitle() + " " + build.getVersion() + (config.getDebug() ? (buildDate != null ? " - " + buildDate + " (Debug)" : " - (Debug)") : ""));
        stage.getIcons().add(icon);
        stage.setResizable(config.getResizable());
        stage.show();
    }

    @Override
    // On Application Closing
    public void stop() {

        // Save Configuration
        options.put("Local", "RomDirectory", config.getRomDirectory());
        options.put("Local", "Language", config.getLanguage());
        options.put("Window", "Width", config.getWidth());
        options.put("Window", "Height", config.getHeight());
        options.put("Window", "IsResizable", config.getResizable());
        options.put("Debug", "IsDebug", config.getDebug());
        try {
            options.store(new FileOutputStream(config.getConfigPath()));
        }
        catch (IOException ex) {
            Exception except = new Exception(bundle.getString("exceptionHandler"), bundle.getString("exceptionHandlerText"), bundle.getString("exceptionHeader"), ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
