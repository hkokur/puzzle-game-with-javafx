import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Scanner;
import javax.swing.text.Position;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class Main extends Application{
    // class to manage levels and drag events
    private Management management = new Management();

    public static void main(String[] args) {
        // Launch the JavaFX application
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
		
		StackPane startPane = new StackPane();
		ImageView startgif = new ImageView(new Image("background/btngif.gif"));
		
		startgif.fitHeightProperty().bind(startPane.heightProperty());
    	startgif.fitWidthProperty().bind(startPane.widthProperty());
    	
    	String musicFile= "src/music/music.mp3";
    	Media music = new Media(new File(musicFile).toURI().toString());
    	MediaPlayer mediaPlayer = new MediaPlayer(music);
    	mediaPlayer.play();
    	mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.seek(Duration.ZERO);


		startPane.setOnMouseClicked(e ->  {
            management.prime();
            game(stage);
		});

        stage.setOnCloseRequest(windowEvent -> {
            mediaPlayer.stop();
        });
		
		startPane.getChildren().add(startgif);

		Scene scene = new Scene(startPane,1000,562);
		stage.setTitle("start");
		stage.setScene(scene);
		stage.show();
	}

    public void game(Stage primaryStage){
        ArrayList<Block> blocks = management.getBlocks();

        GridPane gpane = new GridPane();
        // make grid lines visible
        gpane.setGridLinesVisible(true);
        // set V and H gaps 
        gpane.setVgap(5); 
        gpane.setHgap(5);

        gpane.setAlignment(Pos.CENTER);
        
        gpane.setStyle("-fx-background-image: url('background/background.jpg')");
    
        // add image nodes to gpane by position property
        for(int i = 0; i < blocks.size(); i++){
            Block block = blocks.get(i);
            ImageView image = new ImageView(block.getImg());
            image.setOnDragDetected(new EventHandler<MouseEvent>(){
                public void handle(MouseEvent e){
                    management.setFirstBlock(block);            
                }
            });
            image.setOnMouseEntered(new EventHandler<MouseEvent>(){
                public void handle(MouseEvent e){
                    if (management.getFirstBlock() != null){
                        management.setSecondBlock(block);
                        if (management.switchBlocks())
                            game(primaryStage);
                    }
                }
            });
            gpane.add(image, block.getColumn(), block.getRow());
        }
        Scene scene = new Scene(gpane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("this is a title");
        primaryStage.show(); 
    }
}
