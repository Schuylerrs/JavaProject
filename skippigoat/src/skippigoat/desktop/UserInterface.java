package skippigoat.desktop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import skippigoat.system.Prompt;
import skippigoat.system.Settings;
import skippigoat.service.FindFiles;
import skippigoat.service.SongFile;
import skippigoat.service.SortFiles;


public class UserInterface 
extends Application 
{
  
    public static void main(String[] args) 
    {
        Settings.Load();
        launch(args);
    }
 
    @Override
    public void start(final Stage stage) 
    {       
        Settings.Load();
        Settings.Save();
        Logger.getLogger("org.jaudiotagger").setLevel(Level.WARNING);
        System.out.println(Settings.getHome());
        stage.setTitle("Personal Music Sorter");
        BorderPane border = new BorderPane();
        VBox root = new VBox();
        Scene scene = new Scene (root, 400, 300);
        stage.setResizable(true);
        
        
        scene.setFill(Color.OLDLACE);
        
        //labels
        Label lFiles = new Label();
        lFiles.setText("Files");
        
        
        //VBox for textarea and textarea for displaying files imported
        VBox vbdisplayFiles = new VBox();
        final ListView<SongFile> lstSong = new ListView<>();
        final ObservableList<SongFile> listItems = FXCollections.observableArrayList();
        lstSong.setItems(listItems);
        
        //VbdisplayFiles configurations and adding children
        lstSong.setMaxWidth(Double.MAX_VALUE);
        lstSong.setMaxHeight(Double.MAX_VALUE);
        vbdisplayFiles.setSpacing(10);
        vbdisplayFiles.setPadding(new Insets(10,5,0,10));
        vbdisplayFiles.getChildren().addAll(lFiles,lstSong);
        
        //buttons in VBox
        VBox vboxButtons = new VBox();
        Button btnAddDirectory = new Button();
        Button btnEdit = new Button();
        Button btnGetData = new Button();
        Button btnAuto = new Button();
        
        //Button names
        btnAddDirectory.setText("Add Folder");
        btnEdit.setText("Edit File");
        btnGetData.setText("Get Metadata");
        btnAuto.setText("Auto Style");
        
        //configuring buttons to be all the same size and distance from eachother    
        btnAddDirectory.setMaxWidth(Double.MAX_VALUE);
        btnEdit.setMaxWidth(Double.MAX_VALUE);
        btnGetData.setMaxWidth(Double.MAX_VALUE);
        btnAuto.setMaxWidth(Double.MAX_VALUE);
        vboxButtons.setSpacing(10);
        vboxButtons.setMaxWidth(140);
        vboxButtons.setPadding(new Insets(20, 0, 0, 5));    
        
        //Adding to vboxbuttons
        vboxButtons.getChildren().addAll(btnAddDirectory, btnEdit, btnGetData, btnAuto);
        
         //Menu bar
        MenuBar menuBar = new MenuBar();
        
        // --- Menu File
        MenuItem menuAddSongs = new MenuItem("Add Songs");
        Menu menuFile = new Menu("File");
        MenuItem about = new MenuItem("About");
        MenuItem menuSort = new MenuItem("Sort");
        MenuItem menuExit = new MenuItem("Exit");
        menuFile.getItems().addAll(about, menuSort, menuAddSongs, menuExit);
        
        // --- Menu Edit
        Menu menuEdit = new Menu("Edit");
        MenuItem menuEditSong = new MenuItem("Edit File");
        MenuItem menuClear = new MenuItem("Clear List");
        MenuItem menuRemove = new MenuItem("Remove Selected");
        menuEdit.getItems().addAll(menuEditSong, menuRemove, menuClear);
        
        // --- Menu View
        Menu menuSettings = new Menu("Settings");
        MenuItem menuStyle = new MenuItem("Style");
        MenuItem menuAdvanced = new MenuItem("Advanced");
        menuSettings.getItems().addAll(menuStyle, menuAdvanced);
        menuBar.getMenus().addAll(menuFile, menuEdit, menuSettings);
        
        // Progress bar
        VBox vbProgressBar = new VBox();
        ProgressBar progressBar = new ProgressBar();
        ProgressIndicator progressInd = new ProgressIndicator();
        progressBar.setMaxWidth(scene.getWidth());
        progressBar.setMinHeight(20);
        Label lStatus = new Label();
        lStatus.setText("Status");
        vbProgressBar.setPadding(new Insets(10, 10, 10, 10));
        vbProgressBar.setMaxHeight(600);
        vbProgressBar.getChildren().addAll(lStatus, progressBar);
        
        
        //BorderPane for layout of stage       
        border.setLeft(vboxButtons);
        border.setCenter(vbdisplayFiles);
        border.setTop(menuBar);
        border.setBottom(vbProgressBar);
        
        menuAdvanced.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                new AdvancedGui().start(new Stage());
            }
        });
        
        menuAddSongs.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                try 
                {
                    List<File> add = Prompt.getFiles("Choose Songs To Add");
                    for (File song : add)
                    {
                        listItems.add(new SongFile((song.getPath()).toString()));
                    }
                } 
                catch (Exception ex) 
                {
                    Logger.getLogger(UserInterface.class.getName()).log(Level.INFO, null, ex);
                }
            }
        });
        
        menuRemove.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                int selectedItem = lstSong.getSelectionModel().getSelectedIndex();
                if (selectedItem >= 0) 
                {
                    listItems.remove(selectedItem);
                }
            }
        });
        
        menuSort.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                ArrayList<SongFile> songs = new ArrayList<>();
                for (SongFile song : listItems)
                {
                    if (song.hasID3())
                    {
                        songs.add(song);
                    }
                }
                SortFiles.sort(songs);
                listItems.removeAll(songs);
            }
        });
        
        menuClear.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                listItems.clear();
            }
        });
        
        menuEditSong.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                int selectedItem = lstSong.getSelectionModel().getSelectedIndex();
                if (selectedItem >= 0) 
                {
                    SongFile tempSong = listItems.get(selectedItem);
                    EditFile test = new EditFile(stage, tempSong);
                    listItems.set(selectedItem, tempSong);
                }
            }
        });
        
        menuStyle.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                StyleGui styleWindow = new StyleGui();
                Stage stage = new Stage();
                styleWindow.start(stage);  
            }
        });
        
        //button 1 setonAction
        btnAddDirectory.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) 
            {
                try 
                {
                    List<String> songs = new ArrayList<>();
                    try 
                    {
                        File dir = Prompt.getDir("Open Resource File");
                        FindFiles finder = new FindFiles(Settings.getExtensions());
                        System.out.print(Settings.getExtensions());
                        try 
                        {
                            Files.walkFileTree(dir.toPath(), finder);
                        } 
                        catch (Exception e) 
                        {
                            System.out.println(e);
                        }
                        songs.addAll(finder.getFiles());
                    } 
                    catch (Exception ex) 
                    {
                        //Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    for (String song : songs) 
                    {
                        //currentJob.setText("Analizing: " + song.substring(song.lastIndexOf(System.getProperty("file.separator")) + 1));
                        System.out.print(song);
                        SongFile test = new SongFile(song);
                        listItems.add(test);
                    }
                } 
                catch (Exception ex) 
                {
                    Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        //button 2 setOnAction
         btnEdit.setOnAction(new EventHandler<ActionEvent>()
        {
           
            @Override
            public void handle(ActionEvent event)
            {
                int selectedItem = lstSong.getSelectionModel().getSelectedIndex();
                if (selectedItem >= 0) 
                {
                    SongFile tempSong = listItems.get(selectedItem);
                    EditFile test = new EditFile(stage, tempSong);
                    listItems.set(selectedItem, tempSong);
                }
            }
        });
        
        btnAuto.setOnAction(new EventHandler<ActionEvent>()
        {
           
            @Override
            public void handle(ActionEvent event)
            {
                int selectedItem = lstSong.getSelectionModel().getSelectedIndex();
                if (selectedItem >= 0) 
                {
                    SongFile temp = listItems.get(selectedItem);
                    listItems.remove(temp);
                    temp.rename(temp.getName());
                    System.out.println(temp.getName());
                    listItems.add(temp);
                }
            }
        });
         
          //button 3 setOnAction
        btnGetData.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                //pullLibrary("http://repo1.maven.org/maven2/org/jaudiotagger/2.0.3/jaudiotagger-2.0.3.jar");
                int selectedItem = lstSong.getSelectionModel().getSelectedIndex();
                if (selectedItem >= 0) 
                {
                    SongFile origionalSong = listItems.get(selectedItem);
                    SongFile tempSong = new SongFile(origionalSong);
                    if (tempSong.searchSongData())
                    {
                        EditFile test = new EditFile(stage, tempSong);
                        listItems.set(selectedItem, tempSong);
                    }
                }
            }
        });
                          
        //actions
        //file menu item opens new window
        about.setOnAction(new EventHandler<ActionEvent>()
        {
           @Override
           public void handle(ActionEvent e)
           {
              Label secondLabel = new Label("About Stuff");
              
              StackPane secondaryLayout = new StackPane();
              secondaryLayout.getChildren().add(secondLabel);
              
              Scene secondScene = new Scene(secondaryLayout, 200, 100);
              
              Stage secondStage = new Stage();
              secondStage.setTitle("Second Stage");
              secondStage.setScene(secondScene);
              
              //set position of second window, related to primary window
              secondStage.setX(stage.getX() + 250);
              secondStage.setY(stage.getY() + 100);
              
              secondStage.show();
              
           }
            
        });
         
        root.getChildren().add(border);
        stage.setScene(scene);
        stage.show();
        
             
        
        
    }
    
    public static void pullLibrary(String pURL)
    {
        URL url;
        try 
        {
            String classPath = System.getProperty("java.class.path");
            String separator = System.getProperty("file.separator");
            url = new URL(pURL);
            InputStream is = null;
            OutputStream os = null;
            System.out.println(classPath.substring(0, classPath.lastIndexOf(separator) + 1) + pURL.substring(pURL.lastIndexOf("/") + 1));
            os = new FileOutputStream(new File(classPath.substring(0, classPath.lastIndexOf(separator) + 1) + pURL.substring(pURL.lastIndexOf("/") + 1)));
            is = url.openStream();
            int count;
            final byte data[] = new byte[1024];
            while ((count = is.read(data, 0, 1024)) != -1) 
            {
                os.write(data, 0, count);
            }
            is.close();
            os.close();
        } 
        catch (Exception exp) 
        {
            System.out.println(exp);
        }
    }

}