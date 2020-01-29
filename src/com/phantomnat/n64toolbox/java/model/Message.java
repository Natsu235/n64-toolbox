/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phantomnat.n64toolbox.java.model;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Dorian Pilorge
 */
@SuppressWarnings("AccessStaticViaInstance")

public class Message {
    
    // Configuration
    private static final Configuration config = new Configuration();
    
    private static String title;
    private static String message;
    private static AlertType type;
    
    public Message() {}
    
    public Message(String title, String message, AlertType type) {
        this.title = title;
        this.message = message;
        this.type = type;
        createMessage();
    }
    
    // Display a Message Box
    private void createMessage() {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(config.getIcon()));
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
}
