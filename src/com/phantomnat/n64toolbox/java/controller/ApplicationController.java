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
import com.phantomnat.n64toolbox.java.model.RomZelda;
import java.io.File;
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
    private void open(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnOpen.getScene().getWindow();
        
        // Select a ROM
        FileChooser fc = new FileChooser();
        if (config.getRomDirectory() != null)
            fc.setInitialDirectory(config.getRomDirectory());
        fc.setTitle(bundle.getString("openRom"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter(bundle.getString("n64Roms"), "*.n64; *.v64; *.z64"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter(bundle.getString("allFiles"), "*.*"));
        
        // Save ROM Path
        File oldFile = romCtrl.getFile();
        File newFile = fc.showOpenDialog(stage);
        if (newFile != null) {
            //romCtrl.loadType(newFile);
            romCtrl.setFile(newFile);
            config.setRomDirectory(newFile.getParentFile());
        }
        else if (oldFile != null) {
            //romCtrl.loadType(oldFile);
            romCtrl.setFile(oldFile);
            config.setRomDirectory(oldFile.getParentFile());
        }
        else
            return;
        
        // Check ROM Header
        Message msgBox;
        try {
            if (romCtrl.isLoaded() && romCtrl.isValid()) {
                load();    // Load ROM Infos
                show();    // Display ROM Infos
                btnSave.setDisable(false);
            }
            else {
                romCtrl.setFile(oldFile);
                msgBox = new Message(bundle.getString("invalidRom"), bundle.getString("invalidRomText"), Alert.AlertType.ERROR);
            }
        }
        catch (java.lang.Exception ex) {
            Exception except = new Exception(bundle.getString("exceptionHandler"), bundle.getString("exceptionHandlerText"), bundle.getString("exceptionHeader"), ex);
        }
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
    
    // Load ROM Informations
    private void load() throws IOException {
        // General Informations
        romCtrl.loadFormat();
        romCtrl.loadSize();
        romCtrl.loadName();
        romCtrl.loadMedia();
        romCtrl.loadCartID();
        romCtrl.loadRegion();
        romCtrl.loadVersion();
        romCtrl.loadCIC();
        romCtrl.loadCRC();
        romCtrl.loadCRCStatus();
        // Zelda Informations
        //romCtrl.loadRealName();
        //romCtrl.loadEdition();
        //romCtrl.loadCreator();
        // Miscellaneous Informations
        romCtrl.loadChecksum("md5");
        romCtrl.loadChecksum("sha1");
    }
    
    // Display ROM Informations
    private void show() throws IOException {
        // General Informations
        lblFilename.setText(romCtrl.getFile().getName());
        lblFiletype.setText(romCtrl.getFormat());
        lblSize.setText(romCtrl.getSize());
        lblName.setText(romCtrl.getName());
        lblMedia.setText(romCtrl.getMedia());
        lblCartID.setText(romCtrl.getCartID());
        lblRegion.setText(romCtrl.getRegion());
        lblVersion.setText(romCtrl.getVersion());
        lblCIC.setText(romCtrl.getCIC());
        lblCRC.setText(romCtrl.getCRC());
        lblCRCStatus.setText(romCtrl.getCRCStatus());
        lblCRCStatus.setTextFill(romCtrl.getCRCStatusColor());
        // Zelda Informations
        //romCtrl.getRealName();
        //romCtrl.getEdition();
        //romCtrl.getCreator();
        // Miscellaneous Informations
        lblMD5.setText(romCtrl.getChecksum("md5"));
        lblSHA1.setText(romCtrl.getChecksum("sha1"));
        //romCtrl.debug();
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
