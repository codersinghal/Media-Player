/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaplayer;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import static sun.audio.AudioPlayer.player;
import javafx.application.Platform; 

/**
 *
 * @author user
 */
public class FXMLDocumentController implements Initializable {
    @FXML
    private MediaView mediaView;
    @FXML
    private Slider seekslider;
    @FXML
    private Label label;
    private  String filepath;
    private MediaPlayer mediaPlayer;
    @FXML
    private Slider slider;
    @FXML
    private void handleButtonAction(ActionEvent event) {
        FileChooser filechooser=new FileChooser();
        FileChooser.ExtensionFilter filter=new FileChooser.ExtensionFilter("Select a file (*.mp4)", "*.mp4","*.mp3");
        filechooser.getExtensionFilters().add(filter);
        File file=filechooser.showOpenDialog(null);
        filepath=file.toURI().toString();
        if(filepath!=null)
        {
            Media media=new Media(filepath);
            mediaPlayer=new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            DoubleProperty width=mediaView.fitWidthProperty();
            DoubleProperty height=mediaView.fitHeightProperty();
            width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
            height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
            slider.setValue(mediaPlayer.getVolume()*100);
            slider.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    mediaPlayer.setVolume(slider.getValue()/100);
                    
                }
            });   
                
             mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() { 
                public void invalidated(Observable ov) 
                {
                    updatesValues();
                }
            }); 
  
            // Inorder to jump to the certain part of video 
            seekslider.valueProperty().addListener(new InvalidationListener() { 
                public void invalidated(Observable ov) 
                { 
                    if (seekslider.isPressed()) { // It would set the time 
                        // as specified by user by pressing 
                        mediaPlayer.seek(mediaPlayer.getMedia().getDuration().multiply(seekslider.getValue() / 100));
                        
                    } 
                } 
            }); 
                       
            
            
            mediaPlayer.play();
        }
    }
    
    protected void updatesValues() 
        { 
            Platform.runLater(new Runnable() { 
                public void run() 
                { 
                    // Updating to the new time value 
                    // This will move the slider while running your video 
                    seekslider.setValue(mediaPlayer.getCurrentTime().toMillis()/ 
                                      mediaPlayer.getTotalDuration() 
                                          .toMillis() 
                                  * 100);
                                      
                } 
            }); 
        } 
    
    @FXML
    private void playVideo(ActionEvent event)
    {
        mediaPlayer.play();
        mediaPlayer.setRate(1);
    }
    @FXML
    private void pauseVideo(ActionEvent event)
    {
        mediaPlayer.pause();
    }
    @FXML
    private void stopVideo(ActionEvent event)
    {
        mediaPlayer.stop();
    }
    @FXML
    private void fastVideo(ActionEvent event)
    {
        mediaPlayer.setRate(1.5);
    }
    @FXML
    private void fasterVideo(ActionEvent event)
    {
        mediaPlayer.setRate(2);
    }
    @FXML
    private void slowVideo(ActionEvent event)
    {
        mediaPlayer.setRate(0.75);
    }
    @FXML
    private void slowerVideo(ActionEvent event)
    {
        mediaPlayer.setRate(0.5);
    }
    @FXML
    private void exitVideo(ActionEvent event)
    {
        System.exit(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
