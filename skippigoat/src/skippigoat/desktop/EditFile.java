/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package skippigoat.desktop;

import java.io.File;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import skippigoat.service.SongFile;

/**
 *
 * @author Schuyler
 */
public class EditFile 
{
    public EditFile (Stage stage, final SongFile song)
    {
        Media songMedia = new Media((new File(song.getmPath()).toURI().toString()));
        final MediaPlayer songPlayer = new MediaPlayer(songMedia);
        
        Label secondLabel = new Label(song.toString());
                      
        //buttons in VBox
        VBox vboxButtons = new VBox();
        final Button btnSave = new Button();
        final Button btnCancel = new Button();
        Button btnLookup = new Button();
        final Button btnPlayStop = new Button();
        Button btnAutoName = new Button();
        
        //Button names
        btnSave.setText("Save");
        btnCancel.setText("Cancel");
        btnLookup.setText("Lookup");
        btnPlayStop.setText("Play");
        btnAutoName.setText("Auto Name");
        
        //configuring buttons to be all the same size and distance from eachother    
        btnSave.setMaxWidth(Double.MAX_VALUE);
        btnCancel.setMaxWidth(Double.MAX_VALUE);
        btnLookup.setMaxWidth(Double.MAX_VALUE);
        vboxButtons.setSpacing(10);
        vboxButtons.setMaxWidth(120);
        vboxButtons.setPadding(new Insets(20, 0, 0, 5));
        
        //Adding to vboxbuttons
        vboxButtons.getChildren().addAll(btnSave, btnCancel, btnLookup, btnAutoName, btnPlayStop);
        
        //Adding text fields
        VBox txtFields = new VBox();
        final TextField txtFileName;
        txtFileName = new TextField(song.toString().substring(0, song.toString().lastIndexOf(".")));
        final TextField txtAlbum = new TextField(song.getmAlbum());
        final TextField txtTitle = new TextField(song.getmTitle());
        final TextField txtArtist = new TextField(song.getmArtist());        
        txtFields.setSpacing(10);
        txtFields.setMaxWidth(Double.MAX_VALUE);
        txtFields.setPadding(new Insets(20, 0, 0, 0));
        txtFields.getChildren().addAll(txtFileName, txtTitle, txtArtist, txtAlbum);
        
        
        //Adding Labels
        VBox vbLabels = new VBox();
        Label lblFileName = new Label("File Name:");
        Label lblAlbum = new Label("Album:");
        Label lblTitle = new Label("Title:");
        Label lblArtist = new Label("Artist:");
        vbLabels.setSpacing(16);
        vbLabels.setAlignment(Pos.TOP_RIGHT);
        vbLabels.setMaxWidth(100);
        vbLabels.setPadding(new Insets(20, 0, 10, 0));
        vbLabels.getChildren().addAll(lblFileName, lblTitle, lblArtist, lblAlbum);
        
        HBox hbLabelsAndFields = new HBox();
        hbLabelsAndFields.setSpacing(5);
        hbLabelsAndFields.setMaxWidth(Double.MAX_VALUE);
        hbLabelsAndFields.getChildren().addAll(vbLabels, txtFields);
        
        StackPane secondaryLayout = new StackPane();
        
        BorderPane border = new BorderPane();
        border.setLeft(vboxButtons);
        border.setCenter(hbLabelsAndFields);
        border.setTop(secondLabel);
        //border.setCenter(txtFields);
        secondaryLayout.getChildren().add(border);
        Scene secondScene = new Scene(secondaryLayout, 400, 250);

        Stage secondStage = new Stage();
        secondStage.setTitle("Second Stage");
        secondStage.setScene(secondScene);
        secondScene.setFill(Color.OLDLACE);

        //set position of second window, related to primary window
        secondStage.setX(stage.getX() + 250);
        secondStage.setY(stage.getY() + 200);

        secondStage.show();
        
        txtAlbum.setText(song.getmAlbum());
        txtTitle.setText(song.getmTitle());
        txtArtist.setText(song.getmArtist());
        
        btnLookup.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                SongFile temp = new SongFile(song);
        
                if (temp.searchSongData())
                {
                    txtAlbum.setText(temp.getmAlbum());
                    txtTitle.setText(temp.getmTitle());
                    txtArtist.setText(temp.getmArtist());
                }
                else
                {
                    System.out.println("Failed lookup");
                }
            }
        });
        
        btnCancel.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                songPlayer.dispose();
                
                Stage stage = (Stage) btnCancel.getScene().getWindow();
                
                stage.close();
            }
        });
        
        btnSave.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                songPlayer.dispose();
                song.setmAlbum(txtAlbum.getText());
                song.setmArtist(txtTitle.getText());
                song.setmTitle(txtArtist.getText());
                song.rename(txtFileName.getText());
                
                song.writeData(true);
                Stage stage = (Stage) btnSave.getScene().getWindow();
                
                stage.close();
            }
        });
        
        btnAutoName.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                txtFileName.setText(song.getName());
            }
        });
        
        btnPlayStop.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if (btnPlayStop.getText().equals("Play"))
                {
                    songPlayer.play();
                    btnPlayStop.setText("Stop");
                }
                else
                {
                    songPlayer.stop();
                    btnPlayStop.setText("Play");
                }
            }
        });
        
    }
}
