import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.*;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Scanner;
import javax.swing.text.Position;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.VLineTo;

import java.io.File;

public class Main extends Application{
    // class to manage levels and drag events
    private Management management = new Management();
    private CheckPath path = new CheckPath();
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

        Button muteBtn = new Button("Mute");

        muteBtn.setOnMouseClicked(e -> {
            mediaPlayer.stop();
        });


		startPane.setOnMouseClicked(e ->  {
            management.prime();
            game(stage);
		});

        stage.setOnCloseRequest(windowEvent -> {
            mediaPlayer.stop();
        });
		
		startPane.getChildren().add(startgif);
        startPane.getChildren().add(muteBtn);

		Scene scene = new Scene(startPane,1000,562);
		stage.setTitle("start");
		stage.setScene(scene);
		stage.show();
	}

    public void game(Stage primaryStage){
        ArrayList<Block> blocks = management.getBlocks();

        Pane pane = new Pane();
    
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
            image.setLayoutX(block.getColumn()*150);
            image.setLayoutY(block.getRow()*150); 
            pane.getChildren().add(image);
        }

        pane.setStyle("-fx-background-image: url('background/background.jpg')");

        ImageView btnImg = new ImageView(new Image("images/next.png"));
        Button btn = new Button("");
        btn.setGraphic(btnImg);
        btn.setLayoutX(450);

        ImageView ball = new ImageView("images/ball.png");
        ball.setFitHeight(25);
        ball.setFitWidth(25);


        Block starter;
        int x = 0;
        int y = 0;
        for(Block block: management.getBlocks()){
            if ( block.getType().equals("starter")){
                starter = block;
                x = starter.getColumn()*150;
                y = starter.getRow()*150;
            }
        }
        ball.setLayoutX(x + 65);
        ball.setLayoutY(y + 65);

        pane.getChildren().add(ball);

        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("this is a title");
        primaryStage.show(); 

        if(path.checkPath(blocks)) {
            pane.getChildren().add(btn);
            PathTransition pt = animation(ball, 150, 150);
            pt.play();
        }
        btn.setOnMouseClicked(e -> {
            management.setLevel();
            management.prime();
            game(primaryStage);
        });

        
    }

    public PathTransition animation(Node ball,double width, double height){ 
        Block[] blocks = new Block[3];
        management.findNextBlock(blocks);

        Path path = new Path();
        // postpone path because path will be draw relatively
        path.setLayoutX(-ball.getBoundsInParent().getMinX());
        path.setLayoutY(-ball.getBoundsInParent().getMinY());

        // set starter point 
        path.getElements().add(new MoveTo(ball.getBoundsInParent().getMinX()+ 12.5 ,ball.getBoundsInParent().getMinY()+ 12.5));

        while(true){
            double relativeX;
            double relativeY ;

            if(blocks[0].getType().equals("starter")){
                relativeX = blocks[0].getColumn()* 150;
                relativeY = blocks[0].getRow() * 150;

                if( blocks[0].getProperty().equals("Vertical"))
                    path.getElements().add(new VLineTo(relativeY + height));
                else
                    path.getElements().add(new HLineTo(relativeX));
            }

            relativeX = blocks[1].getColumn() * 150;
            relativeY = blocks[1].getRow() * 150;
            String property = blocks[1].getProperty();

            if ( property.equals("Horizontal")){
                if ( blocks[0].getColumn() < blocks[1].getColumn())
                    path.getElements().add(new HLineTo(relativeX + width));
                else
                    path.getElements().add(new HLineTo(relativeX));
            }
            else if (property.equals("Vertical")){
                if (blocks[0].getRow() < blocks[1].getRow() )
                    path.getElements().add(new VLineTo(relativeY + height));
                else 
                    path.getElements().add(new VLineTo(relativeY));
            }
            else if ( property.equals("00") || property.equals("01") || property.equals("10") || property.equals("11")){
                ArcTo arcTo = new ArcTo();
                arcTo.setRadiusX(width/2);
                arcTo.setRadiusY(height/2);
                arcTo.setLargeArcFlag(false);

                switch(property){
                    case "00":
                        if (blocks[0].getColumn() == blocks[1].getColumn()){
                            arcTo.setX(relativeX);
                            arcTo.setY(relativeY + height/2);
                            arcTo.setSweepFlag(true);
                        }
                        else{
                            arcTo.setX(relativeX + width/2);
                            arcTo.setY(relativeY);
                            arcTo.setSweepFlag(false);
                        }
                        break;
                    case "01":
                        if ( blocks[0].getColumn() == blocks[1].getColumn() ){
                            arcTo.setSweepFlag(false);
                            arcTo.setX(relativeX + width);
                            arcTo.setY(relativeY + height/2);
                        }
                        else{
                            arcTo.setSweepFlag(true);
                            arcTo.setX(relativeX + width/2);
                            arcTo.setY(relativeY);
                        }
                        break;
                    case "10":
                        if (blocks[0].getRow() == blocks[1].getRow()){
                            arcTo.setSweepFlag(true);
                            arcTo.setX(relativeX + width/2);
                            arcTo.setY(relativeY + height);
                        }
                        else{
                            arcTo.setSweepFlag(false);
                            arcTo.setX(relativeX);
                            arcTo.setY(relativeY + width/2);
                        }
                        break;
                    case "11":
                        if ( blocks[0].getColumn() == blocks[1].getColumn()){
                            arcTo.setSweepFlag(true);
                            arcTo.setX(relativeX + width);
                            arcTo.setY(relativeY + height/2);
                        }
                        else{
                            arcTo.setSweepFlag(false);
                            arcTo.setX(relativeX + width/2);
                            arcTo.setY(relativeY + height);
                        }
                        break;
                }
                path.getElements().add(arcTo);
            }

            if ( blocks[2].getType().equals("end")){
                relativeX = blocks[2].getColumn() * 150;
                relativeY = blocks[2].getRow() * 150;

                if (blocks[2].getProperty().equals("Vertical")){
                    path.getElements().add(new VLineTo(relativeY + height/2));
                }
                else{
                    path.getElements().add(new HLineTo(relativeX + width/2));
                }
                break;
            }
            management.findNextBlock(blocks);
        }

        // creating animation part
        PathTransition pt = new PathTransition(Duration.seconds(5),path, ball);
        return pt;
    }
}
