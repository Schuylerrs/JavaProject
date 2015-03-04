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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import skippigoat.system.Settings;

/**
 *
 * @author devin
 */
public class StyleGui extends Application 
{
    @Override
    public void start(final Stage stage) 
    { 
        
        BorderPane borderPane = new BorderPane();
        
        //Scene setup and styles
        Scene scene = new Scene(borderPane, 400, 160);
        stage.setTitle("Style");
        stage.setScene(scene);
        scene.setFill(Color.OLDLACE);

        //Vbox for labels and labels for textfields 
        VBox vbLabels = new VBox();
        Label lfileName = new Label();
        Label lfolderStructure = new Label();
        Label lsortingLegend = new Label();
        lfileName.setText("File Name Format:");
        lfolderStructure.setText("Folder Structure:");
        
        //VBox for legend
        VBox vbLegend = new VBox();
        lsortingLegend.setText("a = Artist, A = Artist First Letter\n l = Album, L = Album First Letter\n s = Song Name S = Song First Letter");
        lsortingLegend.setWrapText(true);
        vbLegend.getChildren().add(lsortingLegend);
       
        vbLabels.setAlignment(Pos.CENTER);
        vbLabels.setPadding(new Insets(0, 10, 40, 5));
        vbLabels.setSpacing(10);
        vbLabels.getChildren().addAll(lfileName, lfolderStructure);
        
        //VBox for textFields
        VBox vbtextFields = new VBox();
        TextField fileTextField = new TextField();
        TextField folderTextField = new TextField();
        vbtextFields.setPadding(new Insets(0, 5, 0, 0));
        vbtextFields.setSpacing(5);
        vbtextFields.setAlignment(Pos.CENTER);   
        vbtextFields.getChildren().addAll(fileTextField, folderTextField, vbLegend);
        
        //HBox for buttons and button configs Start and Cancel and lsortingStyle
        HBox hbButtons = new HBox();
        final Button btnSave = new Button();
        final Button btnCancel = new Button();
        btnSave.setText("Save");
        btnCancel.setText("Cancel");
        btnSave.setMaxWidth(Double.MAX_VALUE);
        btnCancel.setMaxWidth(Double.MAX_VALUE);
        hbButtons.setSpacing(10);
        hbButtons.setMaxWidth(Double.MAX_VALUE);
        hbButtons.setPadding(new Insets(10, 0, 10, 10));
        hbButtons.setAlignment(Pos.CENTER_LEFT);
        hbButtons.getChildren().addAll(btnSave, btnCancel);
        
        
        //set position of second window, related to primary window   
        stage.show();
        stage.setY(scene.getHeight() - 100);
        borderPane.setLeft(vbLabels);
        borderPane.setCenter(vbtextFields);
        borderPane.setBottom(hbButtons);
        
                
        //btnSave action listener
        btnSave.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)  
            {
                Settings.setmFileNameStructure(null);
                Settings.setmFolderStructure(null);
                
                Stage stage = (Stage) btnSave.getScene().getWindow();
                stage.close();
            }
        });
        
        
        //btnCancel action listener
        btnCancel.setOnAction(new EventHandler<ActionEvent>()
        {
            
            @Override
            public void handle(ActionEvent event)
            {
                Stage stage = (Stage) btnCancel.getScene().getWindow();
                
                stage.close();
            }
        });
     
        
    }
   
}

  
