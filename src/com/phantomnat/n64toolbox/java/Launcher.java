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
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.ini4j.Ini;

/**
 *
 * @author PhantomNatsu
 */
public class Launcher extends Application {
    
    // Configuration
    private static Configuration config = new Configuration();
    private static Ini options = new Ini();
    
    // Models
    private static Build build = new Build();
    
    // Defined Language
    public static ResourceBundle bundle;
    
    @Override
    // On Application Openning
    public void start(Stage stage) throws IOException {
        
        // Read Configuration
        File configFile = new File(config.getConfigPath());
        if (configFile.exists()) {
            options.load(new FileReader(config.getConfigPath()));
            config.setWidth(options.get("Window", "Width", double.class));
            config.setHeight(options.get("Window", "Height", double.class));
            config.setResizable(options.get("Window", "IsResizable", boolean.class));
            config.setLanguage(new Locale(options.get("Local", "Language", String.class)));
            config.setRomDirectory(options.get("Local", "RomDirectory", File.class));
        }
        
        bundle = ResourceBundle.getBundle("com.phantomnat.n64toolbox.resources.bundles.language", config.getLanguage());
        Parent root = FXMLLoader.load(getClass().getResource("view/ApplicationLayout.fxml"), bundle);
        Image icon = new Image(getClass().getResource(config.getIcon()).toString());
        
        Scene scene = new Scene(root, config.getWidth(), config.getHeight());
        stage.setScene(scene);
        stage.setTitle(build.getTitle() + " " + build.getVersion());
        stage.getIcons().add(icon);
        stage.setResizable(config.getResizable());
        stage.show();
    }
    
    @Override
    // On Application Closing
    public void stop() throws IOException {
        
        // Save Configuration
        options.put("Window", "Width", config.getWidth());
        options.put("Window", "Height", config.getHeight());
        options.put("Window", "IsResizable", config.getResizable());
        options.put("Local", "Language", config.getLanguage());
        options.put("Local", "RomDirectory", config.getRomDirectory());
        try {
            options.store(new FileOutputStream(config.getConfigPath()));
        }
        catch (java.lang.Exception ex) {
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
