/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phantomnat.n64toolbox.java.model;

import com.phantomnat.n64toolbox.java.model.Configuration;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author PhantomNatsu
 */
public class Message {
    
    // Configuration
    private Configuration config = new Configuration();
    
    private String title;
    private String message;
    private AlertType type;
    
    public Message() {}
    
    public Message(String title, String message, AlertType type) {
        this.title = title;
        this.message = message;
        this.type = type;
        createMessage();
    }
    
    // Display a Message Box
    final private void createMessage() {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(config.getIcon()));
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
}
