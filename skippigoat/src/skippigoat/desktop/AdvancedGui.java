/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package skippigoat.desktop;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import skippigoat.system.Prompt;
import skippigoat.system.Settings;

/**
 *
 * @author devin
 */
public class AdvancedGui extends Application {
   
    @Override
    public void start(final Stage stage) 
    {
        BorderPane borderPane = new BorderPane();
        
        //Scene setup and config
        Scene scene = new Scene(borderPane, 400, 200);
        stage.setTitle("Style");
        stage.setScene(scene);
        scene.setFill(Color.OLDLACE);
        
        //HBox for label "Home Directory", textField, Browse button along with compenents
        //for the HBox and all their respective configs
        HBox hbHomeDirectory = new HBox();
        Label lhomeDirectory = new Label();
        lhomeDirectory.setText("Home Directory");
        final TextField homeDirectoryTextField = new TextField();
        Button btnBrowse = new Button();
        btnBrowse.setText("Browse");
        hbHomeDirectory.setAlignment(Pos.BASELINE_CENTER);
        hbHomeDirectory.setSpacing(10);
        hbHomeDirectory.setPadding(new Insets(10, 5, 0, 5));
        hbHomeDirectory.getChildren().addAll(lhomeDirectory,homeDirectoryTextField,btnBrowse);     
        
        //VBox checkboxes mp3 mp4 m4a 
        VBox vbCheckBox1 = new VBox();
        final CheckBox cmp3 = new CheckBox();
        final CheckBox cmp4 = new CheckBox();
        final CheckBox cm4a = new CheckBox();
        cmp3.setText("mp3");
        cmp4.setText("mp4");
        cm4a.setText("m4a");  
        vbCheckBox1.setPadding(new Insets(10, 5, 10, 5));
        vbCheckBox1.setSpacing(10);
        vbCheckBox1.getChildren().addAll(cmp3, cmp4, cm4a);
        
        //VBox for checkboxes wav flac other
        VBox vbCheckBox2 = new VBox();
        final CheckBox cwav = new CheckBox();
        final CheckBox cflac = new CheckBox();
        cwav.setText("wav");
        cflac.setText("flac");      
        vbCheckBox2.setPadding(new Insets(10, 5, 10, 5));
        vbCheckBox2.setSpacing(10);
        vbCheckBox2.getChildren().addAll(cwav, cflac);
        
        //borderpane for checkbox1, checkbox2, label
        BorderPane bpCheckBoxes = new BorderPane();
        Label lformats = new Label();
        lformats.setText("Formats to Pull");
        bpCheckBoxes.setTop(lformats);
        bpCheckBoxes.setLeft(vbCheckBox1);
        bpCheckBoxes.setRight(vbCheckBox2);
        
        
        //HBox for bpCheckBoxes and configs
        HBox hbCheckBoxes = new HBox();
        hbCheckBoxes.setPadding(new Insets(10, 10, 0, 0));
        hbCheckBoxes.setAlignment(Pos.BASELINE_CENTER);
        hbCheckBoxes.getChildren().addAll(bpCheckBoxes);
        
        
        //HBox for buttons Cancel and Save and Buttons Cancel and Save configs.
        HBox hbButtons = new HBox();
        final Button btnCancel = new Button();
        Button btnSave = new Button();
        final CheckBox cother = new CheckBox();
        cother.setText("other");
        final TextField otherTextField = new TextField();
        btnSave.setText("Save");
        btnCancel.setText("Cancel");
        hbButtons.setSpacing(10);
        hbButtons.setAlignment(Pos.BASELINE_CENTER); 
        hbButtons.setPadding(new Insets(0, 10, 10, 2));
        hbButtons.getChildren().addAll(btnSave,btnCancel,cother,otherTextField);
        
        //set position of second window, related to primary window   
        stage.show();
        stage.setY(scene.getHeight() - 100);
        borderPane.setCenter(hbCheckBoxes);
        borderPane.setTop(hbHomeDirectory);
        borderPane.setBottom(hbButtons);
        
        //btnCancel action event
        btnCancel.setOnAction(new EventHandler<ActionEvent>()
        {
        
            @Override
            public void handle(ActionEvent event)
            {
                Stage stage = (Stage) btnCancel.getScene().getWindow();
                
                stage.close();
            }
        });
        
        //btnSave action event
        btnSave.setOnAction(new EventHandler<ActionEvent>() 
        {
            
            @Override
            public void handle(ActionEvent event) 
            {
                
               if (cmp3.isSelected())
               {
                   Settings.addExtension(cmp3.getText()); 
                   System.out.println(Settings.getExtensions());
               }
               else if (!cmp3.isSelected())
               {
                   Settings.removeExtension(cmp3.getText());
                   System.out.println(Settings.getExtensions());
               }
               
                if (cmp4.isSelected())
               {
                   Settings.addExtension(cmp4.getText()); 
                   System.out.println(Settings.getExtensions());
               }
               else if (!cmp4.isSelected())
               {
                   Settings.removeExtension(cmp4.getText());
                   System.out.println(Settings.getExtensions());
               }
               
                 if (cm4a.isSelected())
               {
                   Settings.addExtension(cm4a.getText()); 
                   System.out.println(Settings.getExtensions());
               }
               else if (!cm4a.isSelected())
               {
                   Settings.removeExtension(cm4a.getText());
                   System.out.println(Settings.getExtensions());
               }
                 
                  if (cwav.isSelected())
               {
                   Settings.addExtension(cwav.getText()); 
                   System.out.println(Settings.getExtensions());
               }
               else if (!cwav.isSelected())
               {
                   Settings.removeExtension(cwav.getText());
                   System.out.println(Settings.getExtensions());
               }
                  
                   if (cflac.isSelected())
               {
                   Settings.addExtension(cflac.getText()); 
                   System.out.println(Settings.getExtensions());
               }
               else if (!cflac.isSelected())
               {
                   Settings.removeExtension(cflac.getText());
                   System.out.println(Settings.getExtensions());
               }
                   
                    if (cother.isSelected())
               {
                   Settings.addExtension(otherTextField.getText().toString()); 
                   System.out.println(Settings.getExtensions());
               }
               else if (!cother.isSelected())
               {
                   Settings.removeExtension(otherTextField.getText().toString());
                   System.out.println(Settings.getExtensions());
               }
  
            }
        });
        
        btnBrowse.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override        
            public void handle(ActionEvent event)
            {
                try
                {
                    //Prompt.getDir(stage.getTitle());
                    homeDirectoryTextField.setText(Prompt.getDir("here").toString());
                }
                catch (Exception e)
                {
                    System.out.println(e);
                }
            }
        });
        
        
        
        
       
    }

    
}
