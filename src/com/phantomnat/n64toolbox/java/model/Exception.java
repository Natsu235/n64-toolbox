/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phantomnat.n64toolbox.java.model;

import com.phantomnat.n64toolbox.java.model.Configuration;
import java.io.PrintWriter;
import java.io.StringWriter;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 *
 * @author PhantomNatsu
 */
public class Exception {
    
    // Configuration
    private Configuration config = new Configuration();
    
    private String title;
    private String message;
    private String error;
    
    public Exception() {}
    
    public Exception(String title, String message, String error, java.lang.Exception exception) {
        this.title = title;
        this.message = message;
        this.error = error;
        createException(exception);
    }
    
    // Display an Exception
    final private void createException(java.lang.Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(this.title);
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(config.getIcon()));
        alert.setHeaderText(this.message);
        alert.setContentText(this.error);
        alert.getDialogPane().setMinSize(460, 200);
        
        // Create Expandable Exception
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String exception = sw.toString();
        
        Label blank = new Label("");
        Label label = new Label("The exception stacktrace was:");
        
        TextArea textArea = new TextArea(exception);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);
        
        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(blank, 0, 0);
        expContent.add(label, 0, 1);
        expContent.add(textArea, 0, 2);
        
        // Set Expandable Exception Dialog Pane
        alert.getDialogPane().setExpandableContent(expContent);
        alert.showAndWait();
    }
    
}
