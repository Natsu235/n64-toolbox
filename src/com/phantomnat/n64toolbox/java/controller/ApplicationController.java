/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phantomnat.n64toolbox.java.controller;

import com.phantomnat.n64toolbox.java.model.Build;
import com.phantomnat.n64toolbox.java.model.Configuration;
import com.phantomnat.n64toolbox.java.model.Exception;
import com.phantomnat.n64toolbox.java.model.Message;
import com.phantomnat.n64toolbox.java.model.Rom;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author PhantomNatsu
 */
public class ApplicationController implements Initializable {
    
    // Configuration
    private static Configuration config = new Configuration();
    
    // Exception Handler
    private static Exception except = new Exception();
    
    // Controllers
    private static RomController romCtrl = new RomController();
    
    // Models
    private static Build build = new Build();    // Build Infos
    private static Rom rom = new Rom();          // Loaded ROM
    
    // Defined Language
    private static ResourceBundle bundle;
    
    @FXML
    private Button btnOpen, btnSave, btnExit;
    @FXML
    private ImageView imgAvatar;
    @FXML
    private Label lblFilename, lblFiletype, lblSize, lblName, lblMedia, lblCartID, lblRegion, lblVersion, lblCIC, lblCRC, lblCRCStatus;
    @FXML
    private Label lblZName, lblZEdition, lblZCreator, lblZBuildDate, lblZStatus;
    @FXML
    private Label lblByteFormat, lblClockRateOver, lblProgramCounter, lblReleaseAddress, lblCRC1, lblCRC2, lblMD5, lblSHA1;
    @FXML
    private Label lblPrjTitle, lblPrjVersion, lblPrjCreator, lblPrjJavaFX;
    
    @FXML
    // Open a ROM
    private void open(ActionEvent event) {
        
        // Choose a ROM
        FileChooser fc = new FileChooser();
        if (config.getRomDirectory() != null)
            fc.setInitialDirectory(config.getRomDirectory());
        fc.setTitle(bundle.getString("openRom"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter(bundle.getString("n64Roms"), "*.n64; *.v64; *.z64"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter(bundle.getString("allFiles"), "*.*"));
        
        // Save ROM Path
        rom.setFile(fc.showOpenDialog(null));
        config.setRomDirectory(rom.getFile().getParentFile());
        
        // Check ROM Header
        Message msgBox;
        try {
            if (romCtrl.isLoaded() && romCtrl.isValid()) {
                load();    // Load ROM Infos
                show();    // Display ROM Infos
            }
            else
                msgBox = new Message(bundle.getString("invalidRom"), bundle.getString("invalidRomText"), Alert.AlertType.ERROR);
        }
        catch (java.lang.Exception ex) {
            Exception except = new Exception(bundle.getString("exceptionHandler"), bundle.getString("exceptionHandlerText"), bundle.getString("exceptionHeader"), ex);
        }
    }
    
    // Load ROM Infos
    private void load() throws IOException {
        romCtrl.loadFormat();
        romCtrl.loadSize();
        romCtrl.loadName();
        romCtrl.loadMedia();
        romCtrl.loadCartID();
        romCtrl.loadRegion();
        romCtrl.loadVersion();
        rom.setCIC(romCtrl.loadCIC());
        rom.setCRC(romCtrl.loadCRC());
        romCtrl.loadCRCStatus();
        romCtrl.loadChecksum("md5");
        romCtrl.loadChecksum("sha1");
    }
    
    @FXML
    // Save ROM Infos
    private void save(ActionEvent event) {
        try {
            romCtrl.saveData();
        }
        catch (IOException ex) {
            Exception except = new Exception(bundle.getString("exceptionHandler"), bundle.getString("exceptionHandlerText"), bundle.getString("exceptionHeader"), ex);
        }
        Message msgBox = new Message(bundle.getString("romInfosSaved"), bundle.getString("romInfosSavedText"), Alert.AlertType.INFORMATION);
    }
    
    // Display ROM Infos
    private void show() throws IOException {
        // General Informations
        lblFilename.setText(rom.getFile().getName());
        lblFiletype.setText(romCtrl.getFormat());
        lblSize.setText(romCtrl.getSize());
        lblName.setText(romCtrl.getName());
        lblMedia.setText(romCtrl.getMedia());
        lblCartID.setText(romCtrl.getCartID());
        lblRegion.setText(romCtrl.getRegion());
        lblVersion.setText(romCtrl.getVersion());
        lblCIC.setText(rom.getCIC());
        lblCRC.setText(rom.getCRC());
        lblCRCStatus.setText(rom.getCRCStatus());
        lblCRCStatus.setTextFill(romCtrl.getCRCStatusColor());
        // Miscellaneous Informations
        lblMD5.setText(romCtrl.getChecksum("md5"));
        lblSHA1.setText(romCtrl.getChecksum("sha1"));
        romCtrl.debug();
    }
    
    @FXML
    // Exit Application
    private void exit(ActionEvent event) {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    // Change Language
    private void setLanguage(ActionEvent event) {
        String button = ((Button) event.getSource()).getId();
        
        switch (button) {
            case "btnFrench":
                config.setLanguage(Locale.FRENCH);
                break;
            case "btnGerman":
                // config.setLanguage(Locale.GERMAN);
                break;
            case "btnSpanish":
                // config.setLanguage(Locale.SPANISH);
                break;
            case "btnItalian":
                // config.setLanguage(Locale.ITALIAN);
                break;
            case "btnEnglish":
            default:
                config.setLanguage(Locale.ENGLISH);
                break;
        }
        
        Message msgBox = new Message(bundle.getString("langChanged"), bundle.getString("langChangedText"), Alert.AlertType.INFORMATION);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        this.bundle = resources;
        
        // Randomize Avatar Image
        if (Math.random() < 0.5) {
            Image avatar = new Image(this.getClass().getResource("/com/phantomnat/n64toolbox/resources/images/makoto-naegi-1.png").toString());
            imgAvatar.setImage(avatar);
        }
        
        // Fill About Informations
        lblPrjTitle.setText(build.getTitle());
        lblPrjVersion.setText(bundle.getString("appVersion") + " " + build.getVersion());
        lblPrjCreator.setText(bundle.getString("appCreator") + " " + build.getCreator());
        lblPrjJavaFX.setText(bundle.getString("appJavaFX") + " " + build.getJavaFX());
    }
    
}
